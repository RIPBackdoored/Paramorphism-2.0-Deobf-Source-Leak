package org.eclipse.aether.internal.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.aether.RepositoryEvent$Builder;
import org.eclipse.aether.RepositoryEvent$EventType;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.SyncContext;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.impl.Installer;
import org.eclipse.aether.impl.MetadataGenerator;
import org.eclipse.aether.impl.MetadataGeneratorFactory;
import org.eclipse.aether.impl.RepositoryEventDispatcher;
import org.eclipse.aether.impl.SyncContextFactory;
import org.eclipse.aether.installation.InstallRequest;
import org.eclipse.aether.installation.InstallResult;
import org.eclipse.aether.installation.InstallationException;
import org.eclipse.aether.metadata.MergeableMetadata;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.LocalArtifactRegistration;
import org.eclipse.aether.repository.LocalMetadataRegistration;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.spi.io.FileProcessor;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.transform.FileTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class DefaultInstaller implements Installer, Service {
   private static final Logger LOGGER = LoggerFactory.getLogger(DefaultInstaller.class);
   private FileProcessor fileProcessor;
   private RepositoryEventDispatcher repositoryEventDispatcher;
   private Collection metadataFactories = new ArrayList();
   private SyncContextFactory syncContextFactory;

   public DefaultInstaller() {
      super();
   }

   @Inject
   DefaultInstaller(FileProcessor var1, RepositoryEventDispatcher var2, Set var3, SyncContextFactory var4) {
      super();
      this.setFileProcessor(var1);
      this.setRepositoryEventDispatcher(var2);
      this.setMetadataGeneratorFactories(var3);
      this.setSyncContextFactory(var4);
   }

   public void initService(ServiceLocator var1) {
      this.setFileProcessor((FileProcessor)var1.getService(FileProcessor.class));
      this.setRepositoryEventDispatcher((RepositoryEventDispatcher)var1.getService(RepositoryEventDispatcher.class));
      this.setMetadataGeneratorFactories(var1.getServices(MetadataGeneratorFactory.class));
      this.setSyncContextFactory((SyncContextFactory)var1.getService(SyncContextFactory.class));
   }

   public DefaultInstaller setFileProcessor(FileProcessor var1) {
      this.fileProcessor = (FileProcessor)Objects.requireNonNull(var1, "file processor cannot be null");
      return this;
   }

   public DefaultInstaller setRepositoryEventDispatcher(RepositoryEventDispatcher var1) {
      this.repositoryEventDispatcher = (RepositoryEventDispatcher)Objects.requireNonNull(var1, "repository event dispatcher cannot be null");
      return this;
   }

   public DefaultInstaller addMetadataGeneratorFactory(MetadataGeneratorFactory var1) {
      this.metadataFactories.add(Objects.requireNonNull(var1, "metadata generator factory cannot be null"));
      return this;
   }

   public DefaultInstaller setMetadataGeneratorFactories(Collection var1) {
      if (var1 == null) {
         this.metadataFactories = new ArrayList();
      } else {
         this.metadataFactories = var1;
      }

      return this;
   }

   public DefaultInstaller setSyncContextFactory(SyncContextFactory var1) {
      this.syncContextFactory = (SyncContextFactory)Objects.requireNonNull(var1, "sync context factory cannot be null");
      return this;
   }

   public InstallResult install(RepositorySystemSession var1, InstallRequest var2) throws InstallationException {
      SyncContext var3 = this.syncContextFactory.newInstance(var1, false);
      Throwable var4 = null;
      boolean var13 = false;

      InstallResult var5;
      try {
         var13 = true;
         var5 = this.install(var3, var1, var2);
         var13 = false;
      } catch (Throwable var16) {
         var4 = var16;
         throw var16;
      } finally {
         if (var13) {
            if (var3 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var14) {
                     var4.addSuppressed(var14);
                  }
               } else {
                  var3.close();
               }
            }

         }
      }

      if (var3 != null) {
         if (var4 != null) {
            try {
               var3.close();
            } catch (Throwable var15) {
               var4.addSuppressed(var15);
            }
         } else {
            var3.close();
         }
      }

      return var5;
   }

   private InstallResult install(SyncContext var1, RepositorySystemSession var2, InstallRequest var3) throws InstallationException {
      InstallResult var4 = new InstallResult(var3);
      RequestTrace var5 = RequestTrace.newChild(var3.getTrace(), var3);
      List var6 = this.getMetadataGenerators(var2, var3);
      ArrayList var7 = new ArrayList(var3.getArtifacts());
      IdentityHashMap var8 = new IdentityHashMap();
      List var9 = Utils.prepareMetadata(var6, var7);
      var1.acquire(var7, Utils.combine(var3.getMetadata(), var9));
      Iterator var10 = var9.iterator();

      Metadata var11;
      while(var10.hasNext()) {
         var11 = (Metadata)var10.next();
         this.install(var2, var5, var11);
         var8.put(var11, (Object)null);
         var4.addMetadata(var11);
      }

      for(int var14 = 0; var14 < var7.size(); ++var14) {
         Artifact var15 = (Artifact)var7.get(var14);

         MetadataGenerator var13;
         for(Iterator var12 = var6.iterator(); var12.hasNext(); var15 = var13.transformArtifact(var15)) {
            var13 = (MetadataGenerator)var12.next();
         }

         var7.set(var14, var15);
         this.install(var2, var5, var15);
         var4.addArtifact(var15);
      }

      var9 = Utils.finishMetadata(var6, var7);
      var1.acquire((Collection)null, var9);
      var10 = var9.iterator();

      while(var10.hasNext()) {
         var11 = (Metadata)var10.next();
         this.install(var2, var5, var11);
         var8.put(var11, (Object)null);
         var4.addMetadata(var11);
      }

      var10 = var3.getMetadata().iterator();

      while(var10.hasNext()) {
         var11 = (Metadata)var10.next();
         if (!var8.containsKey(var11)) {
            this.install(var2, var5, var11);
            var4.addMetadata(var11);
         }
      }

      return var4;
   }

   private List getMetadataGenerators(RepositorySystemSession var1, InstallRequest var2) {
      PrioritizedComponents var3 = Utils.sortMetadataGeneratorFactories(var1, this.metadataFactories);
      ArrayList var4 = new ArrayList();
      Iterator var5 = var3.getEnabled().iterator();

      while(var5.hasNext()) {
         PrioritizedComponent var6 = (PrioritizedComponent)var5.next();
         MetadataGenerator var7 = ((MetadataGeneratorFactory)var6.getComponent()).newInstance(var1, var2);
         if (var7 != null) {
            var4.add(var7);
         }
      }

      return var4;
   }

   private void install(RepositorySystemSession var1, RequestTrace var2, Artifact var3) throws InstallationException {
      LocalRepositoryManager var4 = var1.getLocalRepositoryManager();
      File var5 = var3.getFile();
      Collection var6 = var1.getFileTransformerManager().getTransformersForArtifact(var3);
      if (var6.isEmpty()) {
         this.install(var1, var2, var3, var4, var5, (FileTransformer)null);
      } else {
         Iterator var7 = var6.iterator();

         while(var7.hasNext()) {
            FileTransformer var8 = (FileTransformer)var7.next();
            this.install(var1, var2, var3, var4, var5, var8);
         }
      }

   }

   private void install(RepositorySystemSession var1, RequestTrace var2, Artifact var3, LocalRepositoryManager var4, File var5, FileTransformer var6) throws InstallationException {
      Artifact var7;
      if (var6 != null) {
         var7 = var6.transformArtifact(var3);
      } else {
         var7 = var3;
      }

      File var8 = new File(var4.getRepository().getBasedir(), var4.getPathForLocalArtifact(var7));
      this.artifactInstalling(var1, var2, var7, var8);
      Exception var9 = null;
      boolean var23 = false;

      try {
         var23 = true;
         if (var8.equals(var5)) {
            throw new IllegalStateException("cannot install " + var8 + " to same path");
         }

         boolean var10 = "pom".equals(var7.getExtension()) || var5.lastModified() != var8.lastModified() || var5.length() != var8.length() || !var5.exists();
         if (!var10) {
            LOGGER.debug((String)"Skipped re-installing {} to {}, seems unchanged", (Object)var5, (Object)var8);
         } else if (var6 != null) {
            InputStream var11 = var6.transformData(var5);
            Throwable var12 = null;
            boolean var30 = false;

            try {
               var30 = true;
               this.fileProcessor.write(var8, var11);
               var8.setLastModified(var5.lastModified());
               var30 = false;
            } catch (Throwable var33) {
               var12 = var33;
               throw var33;
            } finally {
               if (var30) {
                  if (var11 != null) {
                     if (var12 != null) {
                        try {
                           var11.close();
                        } catch (Throwable var31) {
                           var12.addSuppressed(var31);
                        }
                     } else {
                        var11.close();
                     }
                  }

               }
            }

            if (var11 != null) {
               if (var12 != null) {
                  try {
                     var11.close();
                  } catch (Throwable var32) {
                     var12.addSuppressed(var32);
                  }
               } else {
                  var11.close();
               }
            }
         } else {
            this.fileProcessor.copy(var5, var8);
            var8.setLastModified(var5.lastModified());
         }

         var4.add(var1, new LocalArtifactRegistration(var7));
         var23 = false;
      } catch (Exception var35) {
         var9 = var35;
         throw new InstallationException("Failed to install artifact " + var7 + ": " + var35.getMessage(), var35);
      } finally {
         if (var23) {
            this.artifactInstalled(var1, var2, var7, var8, var9);
         }
      }

      this.artifactInstalled(var1, var2, var7, var8, var9);
   }

   private void install(RepositorySystemSession var1, RequestTrace var2, Metadata var3) throws InstallationException {
      LocalRepositoryManager var4 = var1.getLocalRepositoryManager();
      File var5 = new File(var4.getRepository().getBasedir(), var4.getPathForLocalMetadata(var3));
      this.metadataInstalling(var1, var2, var3, var5);
      Exception var6 = null;
      boolean var11 = false;

      try {
         var11 = true;
         if (var3 instanceof MergeableMetadata) {
            ((MergeableMetadata)var3).merge(var5, var5);
         } else {
            if (var5.equals(var3.getFile())) {
               throw new IllegalStateException("cannot install " + var5 + " to same path");
            }

            this.fileProcessor.copy(var3.getFile(), var5);
         }

         var4.add(var1, new LocalMetadataRegistration(var3));
         var11 = false;
      } catch (Exception var12) {
         var6 = var12;
         throw new InstallationException("Failed to install metadata " + var3 + ": " + var12.getMessage(), var12);
      } finally {
         if (var11) {
            this.metadataInstalled(var1, var2, var3, var5, var6);
         }
      }

      this.metadataInstalled(var1, var2, var3, var5, var6);
   }

   private void artifactInstalling(RepositorySystemSession var1, RequestTrace var2, Artifact var3, File var4) {
      RepositoryEvent$Builder var5 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.ARTIFACT_INSTALLING);
      var5.setTrace(var2);
      var5.setArtifact(var3);
      var5.setRepository(var1.getLocalRepositoryManager().getRepository());
      var5.setFile(var4);
      this.repositoryEventDispatcher.dispatch(var5.build());
   }

   private void artifactInstalled(RepositorySystemSession var1, RequestTrace var2, Artifact var3, File var4, Exception var5) {
      RepositoryEvent$Builder var6 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.ARTIFACT_INSTALLED);
      var6.setTrace(var2);
      var6.setArtifact(var3);
      var6.setRepository(var1.getLocalRepositoryManager().getRepository());
      var6.setFile(var4);
      var6.setException(var5);
      this.repositoryEventDispatcher.dispatch(var6.build());
   }

   private void metadataInstalling(RepositorySystemSession var1, RequestTrace var2, Metadata var3, File var4) {
      RepositoryEvent$Builder var5 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.METADATA_INSTALLING);
      var5.setTrace(var2);
      var5.setMetadata(var3);
      var5.setRepository(var1.getLocalRepositoryManager().getRepository());
      var5.setFile(var4);
      this.repositoryEventDispatcher.dispatch(var5.build());
   }

   private void metadataInstalled(RepositorySystemSession var1, RequestTrace var2, Metadata var3, File var4, Exception var5) {
      RepositoryEvent$Builder var6 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.METADATA_INSTALLED);
      var6.setTrace(var2);
      var6.setMetadata(var3);
      var6.setRepository(var1.getLocalRepositoryManager().getRepository());
      var6.setFile(var4);
      var6.setException(var5);
      this.repositoryEventDispatcher.dispatch(var6.build());
   }
}
