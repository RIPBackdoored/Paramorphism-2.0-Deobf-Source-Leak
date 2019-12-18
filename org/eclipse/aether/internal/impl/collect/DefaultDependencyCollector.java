package org.eclipse.aether.internal.impl.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.collection.DependencyGraphTransformer;
import org.eclipse.aether.collection.DependencyManager;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.collection.DependencyTraverser;
import org.eclipse.aether.collection.VersionFilter;
import org.eclipse.aether.graph.DefaultDependencyNode;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.impl.ArtifactDescriptorReader;
import org.eclipse.aether.impl.DependencyCollector;
import org.eclipse.aether.impl.RemoteRepositoryManager;
import org.eclipse.aether.impl.VersionRangeResolver;
import org.eclipse.aether.repository.ArtifactRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactDescriptorException;
import org.eclipse.aether.resolution.ArtifactDescriptorRequest;
import org.eclipse.aether.resolution.ArtifactDescriptorResult;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.util.graph.transformer.TransformationContextKeys;
import org.eclipse.aether.version.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class DefaultDependencyCollector implements DependencyCollector, Service {
   private static final String CONFIG_PROP_MAX_EXCEPTIONS = "aether.dependencyCollector.maxExceptions";
   private static final int CONFIG_PROP_MAX_EXCEPTIONS_DEFAULT = 50;
   private static final String CONFIG_PROP_MAX_CYCLES = "aether.dependencyCollector.maxCycles";
   private static final int CONFIG_PROP_MAX_CYCLES_DEFAULT = 10;
   private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDependencyCollector.class);
   private RemoteRepositoryManager remoteRepositoryManager;
   private ArtifactDescriptorReader descriptorReader;
   private VersionRangeResolver versionRangeResolver;

   public DefaultDependencyCollector() {
      super();
   }

   @Inject
   DefaultDependencyCollector(RemoteRepositoryManager var1, ArtifactDescriptorReader var2, VersionRangeResolver var3) {
      super();
      this.setRemoteRepositoryManager(var1);
      this.setArtifactDescriptorReader(var2);
      this.setVersionRangeResolver(var3);
   }

   public void initService(ServiceLocator var1) {
      this.setRemoteRepositoryManager((RemoteRepositoryManager)var1.getService(RemoteRepositoryManager.class));
      this.setArtifactDescriptorReader((ArtifactDescriptorReader)var1.getService(ArtifactDescriptorReader.class));
      this.setVersionRangeResolver((VersionRangeResolver)var1.getService(VersionRangeResolver.class));
   }

   public DefaultDependencyCollector setRemoteRepositoryManager(RemoteRepositoryManager var1) {
      this.remoteRepositoryManager = (RemoteRepositoryManager)Objects.requireNonNull(var1, "remote repository provider cannot be null");
      return this;
   }

   public DefaultDependencyCollector setArtifactDescriptorReader(ArtifactDescriptorReader var1) {
      this.descriptorReader = (ArtifactDescriptorReader)Objects.requireNonNull(var1, "artifact descriptor reader cannot be null");
      return this;
   }

   public DefaultDependencyCollector setVersionRangeResolver(VersionRangeResolver var1) {
      this.versionRangeResolver = (VersionRangeResolver)Objects.requireNonNull(var1, "version range resolver cannot be null");
      return this;
   }

   public CollectResult collectDependencies(RepositorySystemSession var1, CollectRequest var2) throws DependencyCollectionException {
      var1 = optimizeSession(var1);
      RequestTrace var3 = RequestTrace.newChild(var2.getTrace(), var2);
      CollectResult var4 = new CollectResult(var2);
      DependencySelector var5 = var1.getDependencySelector();
      DependencyManager var6 = var1.getDependencyManager();
      DependencyTraverser var7 = var1.getDependencyTraverser();
      VersionFilter var8 = var1.getVersionFilter();
      Dependency var9 = var2.getRoot();
      List var10 = var2.getRepositories();
      List var11 = var2.getDependencies();
      List var12 = var2.getManagedDependencies();
      LinkedHashMap var13 = LOGGER.isDebugEnabled() ? new LinkedHashMap() : null;
      long var14 = System.nanoTime();
      DefaultDependencyNode var16;
      if (var9 != null) {
         List var17;
         VersionRangeResult var18;
         try {
            VersionRangeRequest var19 = new VersionRangeRequest(var9.getArtifact(), var2.getRepositories(), var2.getRequestContext());
            var19.setTrace(var3);
            var18 = this.versionRangeResolver.resolveVersionRange(var1, var19);
            var17 = filterVersions(var9, var18, var8, new DefaultVersionFilterContext(var1));
         } catch (VersionRangeResolutionException var27) {
            var4.addException(var27);
            throw new DependencyCollectionException(var4, var27.getMessage());
         }

         Version var30 = (Version)var17.get(var17.size() - 1);
         var9 = var9.setArtifact(var9.getArtifact().setVersion(var30.toString()));

         ArtifactDescriptorResult var20;
         try {
            ArtifactDescriptorRequest var21 = new ArtifactDescriptorRequest();
            var21.setArtifact(var9.getArtifact());
            var21.setRepositories(var2.getRepositories());
            var21.setRequestContext(var2.getRequestContext());
            var21.setTrace(var3);
            if (isLackingDescriptor(var9.getArtifact())) {
               var20 = new ArtifactDescriptorResult(var21);
            } else {
               var20 = this.descriptorReader.readArtifactDescriptor(var1, var21);
            }
         } catch (ArtifactDescriptorException var26) {
            var4.addException(var26);
            throw new DependencyCollectionException(var4, var26.getMessage());
         }

         var9 = var9.setArtifact(var20.getArtifact());
         if (!var1.isIgnoreArtifactDescriptorRepositories()) {
            var10 = this.remoteRepositoryManager.aggregateRepositories(var1, var10, var20.getRepositories(), true);
         }

         var11 = this.mergeDeps(var11, var20.getDependencies());
         var12 = this.mergeDeps(var12, var20.getManagedDependencies());
         var16 = new DefaultDependencyNode(var9);
         var16.setRequestContext(var2.getRequestContext());
         var16.setRelocations(var20.getRelocations());
         var16.setVersionConstraint(var18.getVersionConstraint());
         var16.setVersion(var30);
         var16.setAliases(var20.getAliases());
         var16.setRepositories(var2.getRepositories());
      } else {
         var16 = new DefaultDependencyNode(var2.getRootArtifact());
         var16.setRequestContext(var2.getRequestContext());
         var16.setRepositories(var2.getRepositories());
      }

      var4.setRoot(var16);
      boolean var28 = var9 == null || var7 == null || var7.traverseDependency(var9);
      String var29 = null;
      if (var28 && !var11.isEmpty()) {
         DataPool var31 = new DataPool(var1);
         NodeStack var33 = new NodeStack();
         var33.push(var16);
         DefaultDependencyCollectionContext var34 = new DefaultDependencyCollectionContext(var1, var2.getRootArtifact(), var9, var12);
         DefaultVersionFilterContext var22 = new DefaultVersionFilterContext(var1);
         DefaultDependencyCollector$Args var23 = new DefaultDependencyCollector$Args(var1, var3, var31, var33, var34, var22, var2);
         DefaultDependencyCollector$Results var24 = new DefaultDependencyCollector$Results(var4, var1);
         this.process(var23, var24, var11, var10, var5 != null ? var5.deriveChildSelector(var34) : null, var6 != null ? var6.deriveChildManager(var34) : null, var7 != null ? var7.deriveChildTraverser(var34) : null, var8 != null ? var8.deriveChildFilter(var34) : null);
         var29 = var24.errorPath;
      }

      long var32 = System.nanoTime();
      DependencyGraphTransformer var35 = var1.getDependencyGraphTransformer();
      if (var35 != null) {
         try {
            DefaultDependencyGraphTransformationContext var36 = new DefaultDependencyGraphTransformationContext(var1);
            var36.put(TransformationContextKeys.STATS, var13);
            var4.setRoot(var35.transformGraph(var16, var36));
         } catch (RepositoryException var25) {
            var4.addException(var25);
         }
      }

      if (var13 != null) {
         long var37 = System.nanoTime();
         var13.put("DefaultDependencyCollector.collectTime", var32 - var14);
         var13.put("DefaultDependencyCollector.transformTime", var37 - var32);
         LOGGER.debug("Dependency collection stats: " + var13);
      }

      if (var29 != null) {
         throw new DependencyCollectionException(var4, "Failed to collect dependencies at " + var29);
      } else if (!var4.getExceptions().isEmpty()) {
         throw new DependencyCollectionException(var4);
      } else {
         return var4;
      }
   }

   private static RepositorySystemSession optimizeSession(RepositorySystemSession var0) {
      DefaultRepositorySystemSession var1 = new DefaultRepositorySystemSession(var0);
      var1.setArtifactTypeRegistry(CachingArtifactTypeRegistry.newInstance(var0));
      return var1;
   }

   private List mergeDeps(List var1, List var2) {
      Object var3;
      if (var1 != null && !var1.isEmpty()) {
         if (var2 != null && !var2.isEmpty()) {
            int var4 = var1.size() + var2.size();
            var3 = new ArrayList(var4);
            HashSet var5 = new HashSet(var4, 1.0F);
            Iterator var6 = var1.iterator();

            Dependency var7;
            while(var6.hasNext()) {
               var7 = (Dependency)var6.next();
               var5.add(getId(var7.getArtifact()));
               ((List)var3).add(var7);
            }

            var6 = var2.iterator();

            while(var6.hasNext()) {
               var7 = (Dependency)var6.next();
               if (!var5.contains(getId(var7.getArtifact()))) {
                  ((List)var3).add(var7);
               }
            }
         } else {
            var3 = var1;
         }
      } else {
         var3 = var2;
      }

      return (List)var3;
   }

   private static String getId(Artifact var0) {
      return var0.getGroupId() + ':' + var0.getArtifactId() + ':' + var0.getClassifier() + ':' + var0.getExtension();
   }

   private void process(DefaultDependencyCollector$Args var1, DefaultDependencyCollector$Results var2, List var3, List var4, DependencySelector var5, DependencyManager var6, DependencyTraverser var7, VersionFilter var8) {
      Iterator var9 = var3.iterator();

      while(var9.hasNext()) {
         Dependency var10 = (Dependency)var9.next();
         this.processDependency(var1, var2, var4, var5, var6, var7, var8, var10);
      }

   }

   private void processDependency(DefaultDependencyCollector$Args var1, DefaultDependencyCollector$Results var2, List var3, DependencySelector var4, DependencyManager var5, DependencyTraverser var6, VersionFilter var7, Dependency var8) {
      List var9 = Collections.emptyList();
      boolean var10 = false;
      this.processDependency(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   private void processDependency(DefaultDependencyCollector$Args var1, DefaultDependencyCollector$Results var2, List var3, DependencySelector var4, DependencyManager var5, DependencyTraverser var6, VersionFilter var7, Dependency var8, List var9, boolean var10) {
      if (var4 == null || var4.selectDependency(var8)) {
         DefaultDependencyCollector$PremanagedDependency var11 = DefaultDependencyCollector$PremanagedDependency.create(var5, var8, var10, var1.premanagedState);
         var8 = var11.managedDependency;
         boolean var12 = isLackingDescriptor(var8.getArtifact());
         boolean var13 = !var12 && (var6 == null || var6.traverseDependency(var8));

         List var14;
         VersionRangeResult var15;
         try {
            VersionRangeRequest var16 = createVersionRangeRequest(var1, var3, var8);
            var15 = this.cachedResolveRangeResult(var16, var1.pool, var1.session);
            var14 = filterVersions(var8, var15, var7, var1.versionContext);
         } catch (VersionRangeResolutionException var27) {
            var2.addException(var8, var27, var1.nodes);
            return;
         }

         Iterator var28 = var14.iterator();

         while(true) {
            while(true) {
               while(var28.hasNext()) {
                  Version var17 = (Version)var28.next();
                  Artifact var18 = var8.getArtifact().setVersion(var17.toString());
                  Dependency var19 = var8.setArtifact(var18);
                  ArtifactDescriptorRequest var20 = createArtifactDescriptorRequest(var1, var3, var19);
                  ArtifactDescriptorResult var21 = this.getArtifactDescriptorResult(var1, var2, var12, var19, var20);
                  DependencyNode var22;
                  if (var21 != null) {
                     var19 = var19.setArtifact(var21.getArtifact());
                     var22 = var1.nodes.top();
                     int var29 = var1.nodes.find(var19.getArtifact());
                     DefaultDependencyNode var25;
                     if (var29 >= 0) {
                        var2.addCycle(var1.nodes, var29, var19);
                        DependencyNode var30 = var1.nodes.get(var29);
                        if (var30.getDependency() != null) {
                           var25 = createDependencyNode(var9, var11, var15, var17, var19, var21, var30);
                           var22.getChildren().add(var25);
                           continue;
                        }
                     }

                     if (!var21.getRelocations().isEmpty()) {
                        boolean var32 = var18.getGroupId().equals(var19.getArtifact().getGroupId()) && var18.getArtifactId().equals(var19.getArtifact().getArtifactId());
                        this.processDependency(var1, var2, var3, var4, var5, var6, var7, var19, var21.getRelocations(), var32);
                        return;
                     }

                     var19 = var1.pool.intern(var19.setArtifact(var1.pool.intern(var19.getArtifact())));
                     List var31 = getRemoteRepositories(var15.getRepository(var17), var3);
                     var25 = createDependencyNode(var9, var11, var15, var17, var19, var21.getAliases(), var31, var1.request.getRequestContext());
                     var22.getChildren().add(var25);
                     boolean var26 = var13 && !var21.getDependencies().isEmpty();
                     if (var26) {
                        this.doRecurse(var1, var2, var3, var4, var5, var6, var7, var19, var21, var25);
                     }
                  } else {
                     var22 = var1.nodes.top();
                     List var23 = getRemoteRepositories(var15.getRepository(var17), var3);
                     DefaultDependencyNode var24 = createDependencyNode(var9, var11, var15, var17, var19, (Collection)null, var23, var1.request.getRequestContext());
                     var22.getChildren().add(var24);
                  }
               }

               return;
            }
         }
      }
   }

   private void doRecurse(DefaultDependencyCollector$Args var1, DefaultDependencyCollector$Results var2, List var3, DependencySelector var4, DependencyManager var5, DependencyTraverser var6, VersionFilter var7, Dependency var8, ArtifactDescriptorResult var9, DefaultDependencyNode var10) {
      DefaultDependencyCollectionContext var11 = var1.collectionContext;
      var11.set(var8, var9.getManagedDependencies());
      DependencySelector var12 = var4 != null ? var4.deriveChildSelector(var11) : null;
      DependencyManager var13 = var5 != null ? var5.deriveChildManager(var11) : null;
      DependencyTraverser var14 = var6 != null ? var6.deriveChildTraverser(var11) : null;
      VersionFilter var15 = var7 != null ? var7.deriveChildFilter(var11) : null;
      List var16 = var1.ignoreRepos ? var3 : this.remoteRepositoryManager.aggregateRepositories(var1.session, var3, var9.getRepositories(), true);
      Object var17 = var1.pool.toKey(var8.getArtifact(), var16, var12, var13, var14, var15);
      List var18 = var1.pool.getChildren(var17);
      if (var18 == null) {
         var1.pool.putChildren(var17, var10.getChildren());
         var1.nodes.push(var10);
         this.process(var1, var2, var9.getDependencies(), var16, var12, var13, var14, var15);
         var1.nodes.pop();
      } else {
         var10.setChildren(var18);
      }

   }

   private ArtifactDescriptorResult getArtifactDescriptorResult(DefaultDependencyCollector$Args var1, DefaultDependencyCollector$Results var2, boolean var3, Dependency var4, ArtifactDescriptorRequest var5) {
      return var3 ? new ArtifactDescriptorResult(var5) : this.resolveCachedArtifactDescriptor(var1.pool, var5, var1.session, var4, var2, var1);
   }

   private ArtifactDescriptorResult resolveCachedArtifactDescriptor(DataPool var1, ArtifactDescriptorRequest var2, RepositorySystemSession var3, Dependency var4, DefaultDependencyCollector$Results var5, DefaultDependencyCollector$Args var6) {
      Object var7 = var1.toKey(var2);
      ArtifactDescriptorResult var8 = var1.getDescriptor(var7, var2);
      if (var8 == null) {
         try {
            var8 = this.descriptorReader.readArtifactDescriptor(var3, var2);
            var1.putDescriptor(var7, var8);
         } catch (ArtifactDescriptorException var10) {
            var5.addException(var4, var10, var6.nodes);
            var1.putDescriptor(var7, var10);
            return null;
         }
      } else if (var8 == DataPool.NO_DESCRIPTOR) {
         return null;
      }

      return var8;
   }

   private static DefaultDependencyNode createDependencyNode(List var0, DefaultDependencyCollector$PremanagedDependency var1, VersionRangeResult var2, Version var3, Dependency var4, Collection var5, List var6, String var7) {
      DefaultDependencyNode var8 = new DefaultDependencyNode(var4);
      var1.applyTo(var8);
      var8.setRelocations(var0);
      var8.setVersionConstraint(var2.getVersionConstraint());
      var8.setVersion(var3);
      var8.setAliases(var5);
      var8.setRepositories(var6);
      var8.setRequestContext(var7);
      return var8;
   }

   private static DefaultDependencyNode createDependencyNode(List var0, DefaultDependencyCollector$PremanagedDependency var1, VersionRangeResult var2, Version var3, Dependency var4, ArtifactDescriptorResult var5, DependencyNode var6) {
      DefaultDependencyNode var7 = createDependencyNode(var0, var1, var2, var3, var4, var5.getAliases(), var6.getRepositories(), var6.getRequestContext());
      var7.setChildren(var6.getChildren());
      return var7;
   }

   private static ArtifactDescriptorRequest createArtifactDescriptorRequest(DefaultDependencyCollector$Args var0, List var1, Dependency var2) {
      ArtifactDescriptorRequest var3 = new ArtifactDescriptorRequest();
      var3.setArtifact(var2.getArtifact());
      var3.setRepositories(var1);
      var3.setRequestContext(var0.request.getRequestContext());
      var3.setTrace(var0.trace);
      return var3;
   }

   private static VersionRangeRequest createVersionRangeRequest(DefaultDependencyCollector$Args var0, List var1, Dependency var2) {
      VersionRangeRequest var3 = new VersionRangeRequest();
      var3.setArtifact(var2.getArtifact());
      var3.setRepositories(var1);
      var3.setRequestContext(var0.request.getRequestContext());
      var3.setTrace(var0.trace);
      return var3;
   }

   private VersionRangeResult cachedResolveRangeResult(VersionRangeRequest var1, DataPool var2, RepositorySystemSession var3) throws VersionRangeResolutionException {
      Object var4 = var2.toKey(var1);
      VersionRangeResult var5 = var2.getConstraint(var4, var1);
      if (var5 == null) {
         var5 = this.versionRangeResolver.resolveVersionRange(var3, var1);
         var2.putConstraint(var4, var5);
      }

      return var5;
   }

   private static boolean isLackingDescriptor(Artifact var0) {
      return var0.getProperty("localPath", (String)null) != null;
   }

   private static List getRemoteRepositories(ArtifactRepository var0, List var1) {
      if (var0 instanceof RemoteRepository) {
         return Collections.singletonList((RemoteRepository)var0);
      } else {
         return var0 != null ? Collections.emptyList() : var1;
      }
   }

   private static List filterVersions(Dependency var0, VersionRangeResult var1, VersionFilter var2, DefaultVersionFilterContext var3) throws VersionRangeResolutionException {
      if (var1.getVersions().isEmpty()) {
         throw new VersionRangeResolutionException(var1, "No versions available for " + var0.getArtifact() + " within specified range");
      } else {
         List var4;
         if (var2 != null && var1.getVersionConstraint().getRange() != null) {
            var3.set(var0, var1);

            try {
               var2.filterVersions(var3);
            } catch (RepositoryException var6) {
               throw new VersionRangeResolutionException(var1, "Failed to filter versions for " + var0.getArtifact() + ": " + var6.getMessage(), var6);
            }

            var4 = var3.get();
            if (var4.isEmpty()) {
               throw new VersionRangeResolutionException(var1, "No acceptable versions for " + var0.getArtifact() + ": " + var1.getVersions());
            }
         } else {
            var4 = var1.getVersions();
         }

         return var4;
      }
   }
}
