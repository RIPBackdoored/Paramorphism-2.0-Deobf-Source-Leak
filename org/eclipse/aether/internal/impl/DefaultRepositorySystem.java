package org.eclipse.aether.internal.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.SyncContext;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.deployment.DeployRequest;
import org.eclipse.aether.deployment.DeployResult;
import org.eclipse.aether.deployment.DeploymentException;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyVisitor;
import org.eclipse.aether.impl.ArtifactDescriptorReader;
import org.eclipse.aether.impl.ArtifactResolver;
import org.eclipse.aether.impl.DependencyCollector;
import org.eclipse.aether.impl.Deployer;
import org.eclipse.aether.impl.Installer;
import org.eclipse.aether.impl.LocalRepositoryProvider;
import org.eclipse.aether.impl.MetadataResolver;
import org.eclipse.aether.impl.RemoteRepositoryManager;
import org.eclipse.aether.impl.SyncContextFactory;
import org.eclipse.aether.impl.VersionRangeResolver;
import org.eclipse.aether.impl.VersionResolver;
import org.eclipse.aether.installation.InstallRequest;
import org.eclipse.aether.installation.InstallResult;
import org.eclipse.aether.installation.InstallationException;
import org.eclipse.aether.repository.Authentication;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.NoLocalRepositoryManagerException;
import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.RemoteRepository$Builder;
import org.eclipse.aether.resolution.ArtifactDescriptorException;
import org.eclipse.aether.resolution.ArtifactDescriptorRequest;
import org.eclipse.aether.resolution.ArtifactDescriptorResult;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.resolution.DependencyResult;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.resolution.VersionRequest;
import org.eclipse.aether.resolution.VersionResolutionException;
import org.eclipse.aether.resolution.VersionResult;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.spi.log.LoggerFactory;
import org.eclipse.aether.util.graph.visitor.FilteringDependencyVisitor;
import org.eclipse.aether.util.graph.visitor.TreeDependencyVisitor;

@Named
public class DefaultRepositorySystem implements RepositorySystem, Service {
   private VersionResolver versionResolver;
   private VersionRangeResolver versionRangeResolver;
   private ArtifactResolver artifactResolver;
   private MetadataResolver metadataResolver;
   private ArtifactDescriptorReader artifactDescriptorReader;
   private DependencyCollector dependencyCollector;
   private Installer installer;
   private Deployer deployer;
   private LocalRepositoryProvider localRepositoryProvider;
   private SyncContextFactory syncContextFactory;
   private RemoteRepositoryManager remoteRepositoryManager;

   public DefaultRepositorySystem() {
      super();
   }

   @Inject
   DefaultRepositorySystem(VersionResolver var1, VersionRangeResolver var2, ArtifactResolver var3, MetadataResolver var4, ArtifactDescriptorReader var5, DependencyCollector var6, Installer var7, Deployer var8, LocalRepositoryProvider var9, SyncContextFactory var10, RemoteRepositoryManager var11) {
      super();
      this.setVersionResolver(var1);
      this.setVersionRangeResolver(var2);
      this.setArtifactResolver(var3);
      this.setMetadataResolver(var4);
      this.setArtifactDescriptorReader(var5);
      this.setDependencyCollector(var6);
      this.setInstaller(var7);
      this.setDeployer(var8);
      this.setLocalRepositoryProvider(var9);
      this.setSyncContextFactory(var10);
      this.setRemoteRepositoryManager(var11);
   }

   public void initService(ServiceLocator var1) {
      this.setVersionResolver((VersionResolver)var1.getService(VersionResolver.class));
      this.setVersionRangeResolver((VersionRangeResolver)var1.getService(VersionRangeResolver.class));
      this.setArtifactResolver((ArtifactResolver)var1.getService(ArtifactResolver.class));
      this.setMetadataResolver((MetadataResolver)var1.getService(MetadataResolver.class));
      this.setArtifactDescriptorReader((ArtifactDescriptorReader)var1.getService(ArtifactDescriptorReader.class));
      this.setDependencyCollector((DependencyCollector)var1.getService(DependencyCollector.class));
      this.setInstaller((Installer)var1.getService(Installer.class));
      this.setDeployer((Deployer)var1.getService(Deployer.class));
      this.setLocalRepositoryProvider((LocalRepositoryProvider)var1.getService(LocalRepositoryProvider.class));
      this.setRemoteRepositoryManager((RemoteRepositoryManager)var1.getService(RemoteRepositoryManager.class));
      this.setSyncContextFactory((SyncContextFactory)var1.getService(SyncContextFactory.class));
   }

   /** @deprecated */
   @Deprecated
   public DefaultRepositorySystem setLoggerFactory(LoggerFactory var1) {
      return this;
   }

   public DefaultRepositorySystem setVersionResolver(VersionResolver var1) {
      this.versionResolver = (VersionResolver)Objects.requireNonNull(var1, "version resolver cannot be null");
      return this;
   }

   public DefaultRepositorySystem setVersionRangeResolver(VersionRangeResolver var1) {
      this.versionRangeResolver = (VersionRangeResolver)Objects.requireNonNull(var1, "version range resolver cannot be null");
      return this;
   }

   public DefaultRepositorySystem setArtifactResolver(ArtifactResolver var1) {
      this.artifactResolver = (ArtifactResolver)Objects.requireNonNull(var1, "artifact resolver cannot be null");
      return this;
   }

   public DefaultRepositorySystem setMetadataResolver(MetadataResolver var1) {
      this.metadataResolver = (MetadataResolver)Objects.requireNonNull(var1, "metadata resolver cannot be null");
      return this;
   }

   public DefaultRepositorySystem setArtifactDescriptorReader(ArtifactDescriptorReader var1) {
      this.artifactDescriptorReader = (ArtifactDescriptorReader)Objects.requireNonNull(var1, "artifact descriptor reader cannot be null");
      return this;
   }

   public DefaultRepositorySystem setDependencyCollector(DependencyCollector var1) {
      this.dependencyCollector = (DependencyCollector)Objects.requireNonNull(var1, "dependency collector cannot be null");
      return this;
   }

   public DefaultRepositorySystem setInstaller(Installer var1) {
      this.installer = (Installer)Objects.requireNonNull(var1, "installer cannot be null");
      return this;
   }

   public DefaultRepositorySystem setDeployer(Deployer var1) {
      this.deployer = (Deployer)Objects.requireNonNull(var1, "deployer cannot be null");
      return this;
   }

   public DefaultRepositorySystem setLocalRepositoryProvider(LocalRepositoryProvider var1) {
      this.localRepositoryProvider = (LocalRepositoryProvider)Objects.requireNonNull(var1, "local repository provider cannot be null");
      return this;
   }

   public DefaultRepositorySystem setSyncContextFactory(SyncContextFactory var1) {
      this.syncContextFactory = (SyncContextFactory)Objects.requireNonNull(var1, "sync context factory cannot be null");
      return this;
   }

   public DefaultRepositorySystem setRemoteRepositoryManager(RemoteRepositoryManager var1) {
      this.remoteRepositoryManager = (RemoteRepositoryManager)Objects.requireNonNull(var1, "remote repository provider cannot be null");
      return this;
   }

   public VersionResult resolveVersion(RepositorySystemSession var1, VersionRequest var2) throws VersionResolutionException {
      this.validateSession(var1);
      return this.versionResolver.resolveVersion(var1, var2);
   }

   public VersionRangeResult resolveVersionRange(RepositorySystemSession var1, VersionRangeRequest var2) throws VersionRangeResolutionException {
      this.validateSession(var1);
      return this.versionRangeResolver.resolveVersionRange(var1, var2);
   }

   public ArtifactDescriptorResult readArtifactDescriptor(RepositorySystemSession var1, ArtifactDescriptorRequest var2) throws ArtifactDescriptorException {
      this.validateSession(var1);
      return this.artifactDescriptorReader.readArtifactDescriptor(var1, var2);
   }

   public ArtifactResult resolveArtifact(RepositorySystemSession var1, ArtifactRequest var2) throws ArtifactResolutionException {
      this.validateSession(var1);
      return this.artifactResolver.resolveArtifact(var1, var2);
   }

   public List resolveArtifacts(RepositorySystemSession var1, Collection var2) throws ArtifactResolutionException {
      this.validateSession(var1);
      return this.artifactResolver.resolveArtifacts(var1, var2);
   }

   public List resolveMetadata(RepositorySystemSession var1, Collection var2) {
      this.validateSession(var1);
      return this.metadataResolver.resolveMetadata(var1, var2);
   }

   public CollectResult collectDependencies(RepositorySystemSession var1, CollectRequest var2) throws DependencyCollectionException {
      this.validateSession(var1);
      return this.dependencyCollector.collectDependencies(var1, var2);
   }

   public DependencyResult resolveDependencies(RepositorySystemSession var1, DependencyRequest var2) throws DependencyResolutionException {
      this.validateSession(var1);
      RequestTrace var3 = RequestTrace.newChild(var2.getTrace(), var2);
      DependencyResult var4 = new DependencyResult(var2);
      DependencyCollectionException var5 = null;
      ArtifactResolutionException var6 = null;
      if (var2.getRoot() != null) {
         var4.setRoot(var2.getRoot());
      } else {
         if (var2.getCollectRequest() == null) {
            throw new NullPointerException("dependency node and collect request cannot be null");
         }

         CollectResult var7;
         try {
            var2.getCollectRequest().setTrace(var3);
            var7 = this.dependencyCollector.collectDependencies(var1, var2.getCollectRequest());
         } catch (DependencyCollectionException var14) {
            var5 = var14;
            var7 = var14.getResult();
         }

         var4.setRoot(var7.getRoot());
         var4.setCycles(var7.getCycles());
         var4.setCollectExceptions(var7.getExceptions());
      }

      ArtifactRequestBuilder var15 = new ArtifactRequestBuilder(var3);
      DependencyFilter var8 = var2.getFilter();
      Object var9 = var8 != null ? new FilteringDependencyVisitor(var15, var8) : var15;
      TreeDependencyVisitor var16 = new TreeDependencyVisitor((DependencyVisitor)var9);
      if (var4.getRoot() != null) {
         var4.getRoot().accept(var16);
      }

      List var10 = var15.getRequests();

      List var11;
      try {
         var11 = this.artifactResolver.resolveArtifacts(var1, var10);
      } catch (ArtifactResolutionException var13) {
         var6 = var13;
         var11 = var13.getResults();
      }

      var4.setArtifactResults(var11);
      this.updateNodesWithResolvedArtifacts(var11);
      if (var5 != null) {
         throw new DependencyResolutionException(var4, var5);
      } else if (var6 != null) {
         throw new DependencyResolutionException(var4, var6);
      } else {
         return var4;
      }
   }

   private void updateNodesWithResolvedArtifacts(List var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         ArtifactResult var3 = (ArtifactResult)var2.next();
         Artifact var4 = var3.getArtifact();
         if (var4 != null) {
            var3.getRequest().getDependencyNode().setArtifact(var4);
         }
      }

   }

   public InstallResult install(RepositorySystemSession var1, InstallRequest var2) throws InstallationException {
      this.validateSession(var1);
      return this.installer.install(var1, var2);
   }

   public DeployResult deploy(RepositorySystemSession var1, DeployRequest var2) throws DeploymentException {
      this.validateSession(var1);
      return this.deployer.deploy(var1, var2);
   }

   public LocalRepositoryManager newLocalRepositoryManager(RepositorySystemSession var1, LocalRepository var2) {
      LocalRepositoryManager var10000;
      try {
         var10000 = this.localRepositoryProvider.newLocalRepositoryManager(var1, var2);
      } catch (NoLocalRepositoryManagerException var4) {
         throw new IllegalArgumentException(var4.getMessage(), var4);
      }

      return var10000;
   }

   public SyncContext newSyncContext(RepositorySystemSession var1, boolean var2) {
      this.validateSession(var1);
      return this.syncContextFactory.newInstance(var1, var2);
   }

   public List newResolutionRepositories(RepositorySystemSession var1, List var2) {
      this.validateSession(var1);
      var2 = this.remoteRepositoryManager.aggregateRepositories(var1, new ArrayList(), var2, true);
      return var2;
   }

   public RemoteRepository newDeploymentRepository(RepositorySystemSession var1, RemoteRepository var2) {
      this.validateSession(var1);
      RemoteRepository$Builder var3 = new RemoteRepository$Builder(var2);
      Authentication var4 = var1.getAuthenticationSelector().getAuthentication(var2);
      var3.setAuthentication(var4);
      Proxy var5 = var1.getProxySelector().getProxy(var2);
      var3.setProxy(var5);
      return var3.build();
   }

   private void validateSession(RepositorySystemSession var1) {
      Objects.requireNonNull(var1, "repository system session cannot be null");
      this.invalidSession(var1.getLocalRepositoryManager(), "local repository manager");
      this.invalidSession(var1.getSystemProperties(), "system properties");
      this.invalidSession(var1.getUserProperties(), "user properties");
      this.invalidSession(var1.getConfigProperties(), "config properties");
      this.invalidSession(var1.getMirrorSelector(), "mirror selector");
      this.invalidSession(var1.getProxySelector(), "proxy selector");
      this.invalidSession(var1.getAuthenticationSelector(), "authentication selector");
      this.invalidSession(var1.getArtifactTypeRegistry(), "artifact type registry");
      this.invalidSession(var1.getData(), "data");
   }

   private void invalidSession(Object var1, String var2) {
      Objects.requireNonNull(var1, "repository system session's " + var2 + " cannot be null");
   }
}
