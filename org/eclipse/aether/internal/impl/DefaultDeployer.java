package org.eclipse.aether.internal.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.SyncContext;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.deployment.DeployRequest;
import org.eclipse.aether.deployment.DeployResult;
import org.eclipse.aether.deployment.DeploymentException;
import org.eclipse.aether.impl.Deployer;
import org.eclipse.aether.impl.MetadataGenerator;
import org.eclipse.aether.impl.MetadataGeneratorFactory;
import org.eclipse.aether.impl.OfflineController;
import org.eclipse.aether.impl.RemoteRepositoryManager;
import org.eclipse.aether.impl.RepositoryConnectorProvider;
import org.eclipse.aether.impl.RepositoryEventDispatcher;
import org.eclipse.aether.impl.SyncContextFactory;
import org.eclipse.aether.impl.UpdateCheck;
import org.eclipse.aether.impl.UpdateCheckManager;
import org.eclipse.aether.metadata.MergeableMetadata;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.metadata.Metadata$Nature;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.RepositoryPolicy;
import org.eclipse.aether.spi.connector.ArtifactUpload;
import org.eclipse.aether.spi.connector.MetadataDownload;
import org.eclipse.aether.spi.connector.MetadataUpload;
import org.eclipse.aether.spi.connector.RepositoryConnector;
import org.eclipse.aether.spi.io.FileProcessor;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.transfer.MetadataNotFoundException;
import org.eclipse.aether.transfer.MetadataTransferException;
import org.eclipse.aether.transfer.NoRepositoryConnectorException;
import org.eclipse.aether.transfer.RepositoryOfflineException;
import org.eclipse.aether.transform.FileTransformer;
import org.eclipse.aether.transform.FileTransformerManager;

@Named
public class DefaultDeployer implements Deployer, Service {
   private FileProcessor fileProcessor;
   private RepositoryEventDispatcher repositoryEventDispatcher;
   private RepositoryConnectorProvider repositoryConnectorProvider;
   private RemoteRepositoryManager remoteRepositoryManager;
   private UpdateCheckManager updateCheckManager;
   private Collection metadataFactories = new ArrayList();
   private SyncContextFactory syncContextFactory;
   private OfflineController offlineController;

   public DefaultDeployer() {
      super();
   }

   @Inject
   DefaultDeployer(FileProcessor var1, RepositoryEventDispatcher var2, RepositoryConnectorProvider var3, RemoteRepositoryManager var4, UpdateCheckManager var5, Set var6, SyncContextFactory var7, OfflineController var8) {
      super();
      this.setFileProcessor(var1);
      this.setRepositoryEventDispatcher(var2);
      this.setRepositoryConnectorProvider(var3);
      this.setRemoteRepositoryManager(var4);
      this.setUpdateCheckManager(var5);
      this.setMetadataGeneratorFactories(var6);
      this.setSyncContextFactory(var7);
      this.setOfflineController(var8);
   }

   public void initService(ServiceLocator var1) {
      this.setFileProcessor((FileProcessor)var1.getService(FileProcessor.class));
      this.setRepositoryEventDispatcher((RepositoryEventDispatcher)var1.getService(RepositoryEventDispatcher.class));
      this.setRepositoryConnectorProvider((RepositoryConnectorProvider)var1.getService(RepositoryConnectorProvider.class));
      this.setRemoteRepositoryManager((RemoteRepositoryManager)var1.getService(RemoteRepositoryManager.class));
      this.setUpdateCheckManager((UpdateCheckManager)var1.getService(UpdateCheckManager.class));
      this.setMetadataGeneratorFactories(var1.getServices(MetadataGeneratorFactory.class));
      this.setSyncContextFactory((SyncContextFactory)var1.getService(SyncContextFactory.class));
      this.setOfflineController((OfflineController)var1.getService(OfflineController.class));
   }

   public DefaultDeployer setFileProcessor(FileProcessor var1) {
      this.fileProcessor = (FileProcessor)Objects.requireNonNull(var1, "file processor cannot be null");
      return this;
   }

   public DefaultDeployer setRepositoryEventDispatcher(RepositoryEventDispatcher var1) {
      this.repositoryEventDispatcher = (RepositoryEventDispatcher)Objects.requireNonNull(var1, "repository event dispatcher cannot be null");
      return this;
   }

   public DefaultDeployer setRepositoryConnectorProvider(RepositoryConnectorProvider var1) {
      this.repositoryConnectorProvider = (RepositoryConnectorProvider)Objects.requireNonNull(var1, "repository connector provider cannot be null");
      return this;
   }

   public DefaultDeployer setRemoteRepositoryManager(RemoteRepositoryManager var1) {
      this.remoteRepositoryManager = (RemoteRepositoryManager)Objects.requireNonNull(var1, "remote repository provider cannot be null");
      return this;
   }

   public DefaultDeployer setUpdateCheckManager(UpdateCheckManager var1) {
      this.updateCheckManager = (UpdateCheckManager)Objects.requireNonNull(var1, "update check manager cannot be null");
      return this;
   }

   public DefaultDeployer addMetadataGeneratorFactory(MetadataGeneratorFactory var1) {
      this.metadataFactories.add(Objects.requireNonNull(var1, "metadata generator factory cannot be null"));
      return this;
   }

   public DefaultDeployer setMetadataGeneratorFactories(Collection var1) {
      if (var1 == null) {
         this.metadataFactories = new ArrayList();
      } else {
         this.metadataFactories = var1;
      }

      return this;
   }

   public DefaultDeployer setSyncContextFactory(SyncContextFactory var1) {
      this.syncContextFactory = (SyncContextFactory)Objects.requireNonNull(var1, "sync context factory cannot be null");
      return this;
   }

   public DefaultDeployer setOfflineController(OfflineController var1) {
      this.offlineController = (OfflineController)Objects.requireNonNull(var1, "offline controller cannot be null");
      return this;
   }

   public DeployResult deploy(RepositorySystemSession var1, DeployRequest var2) throws DeploymentException {
      try {
         Utils.checkOffline(var1, this.offlineController, var2.getRepository());
      } catch (RepositoryOfflineException var18) {
         throw new DeploymentException("Cannot deploy while " + var2.getRepository().getId() + " (" + var2.getRepository().getUrl() + ") is in offline mode", var18);
      }

      SyncContext var3 = this.syncContextFactory.newInstance(var1, false);
      Throwable var4 = null;
      boolean var14 = false;

      DeployResult var5;
      try {
         var14 = true;
         var5 = this.deploy(var3, var1, var2);
         var14 = false;
      } catch (Throwable var17) {
         var4 = var17;
         throw var17;
      } finally {
         if (var14) {
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

         }
      }

      if (var3 != null) {
         if (var4 != null) {
            try {
               var3.close();
            } catch (Throwable var16) {
               var4.addSuppressed(var16);
            }
         } else {
            var3.close();
         }
      }

      return var5;
   }

   private DeployResult deploy(SyncContext var1, RepositorySystemSession var2, DeployRequest var3) throws DeploymentException {
      DeployResult var4 = new DeployResult(var3);
      RequestTrace var5 = RequestTrace.newChild(var3.getTrace(), var3);
      RemoteRepository var6 = var3.getRepository();

      RepositoryConnector var7;
      try {
         var7 = this.repositoryConnectorProvider.newRepositoryConnector(var2, var6);
      } catch (NoRepositoryConnectorException var27) {
         throw new DeploymentException("Failed to deploy artifacts/metadata: " + var27.getMessage(), var27);
      }

      boolean var26 = false;

      try {
         var26 = true;
         List var8 = this.getMetadataGenerators(var2, var3);
         FileTransformerManager var9 = var2.getFileTransformerManager();
         ArrayList var10 = new ArrayList();
         ArrayList var11 = new ArrayList();
         IdentityHashMap var12 = new IdentityHashMap();
         DefaultDeployer$EventCatapult var13 = new DefaultDeployer$EventCatapult(var2, var5, var6, this.repositoryEventDispatcher);
         ArrayList var14 = new ArrayList(var3.getArtifacts());
         List var15 = Utils.prepareMetadata(var8, var14);
         var1.acquire(var14, Utils.combine(var3.getMetadata(), var15));
         Iterator var16 = var15.iterator();

         Metadata var17;
         while(var16.hasNext()) {
            var17 = (Metadata)var16.next();
            this.upload(var11, var2, var17, var6, var7, var13);
            var12.put(var17, (Object)null);
         }

         for(int var29 = 0; var29 < var14.size(); ++var29) {
            Artifact var30 = (Artifact)var14.get(var29);

            MetadataGenerator var19;
            for(Iterator var18 = var8.iterator(); var18.hasNext(); var30 = var19.transformArtifact(var30)) {
               var19 = (MetadataGenerator)var18.next();
            }

            var14.set(var29, var30);
            Collection var32 = var9.getTransformersForArtifact(var30);
            if (!var32.isEmpty()) {
               Iterator var35 = var32.iterator();

               while(var35.hasNext()) {
                  FileTransformer var20 = (FileTransformer)var35.next();
                  Artifact var21 = var20.transformArtifact(var30);
                  ArtifactUpload var22 = new ArtifactUpload(var21, var30.getFile(), var20);
                  var22.setTrace(var5);
                  var22.setListener(new DefaultDeployer$ArtifactUploadListener(var13, var22));
                  var10.add(var22);
               }
            } else {
               ArtifactUpload var34 = new ArtifactUpload(var30, var30.getFile());
               var34.setTrace(var5);
               var34.setListener(new DefaultDeployer$ArtifactUploadListener(var13, var34));
               var10.add(var34);
            }
         }

         var7.put(var10, (Collection)null);
         var16 = var10.iterator();

         while(var16.hasNext()) {
            ArtifactUpload var31 = (ArtifactUpload)var16.next();
            if (var31.getException() != null) {
               throw new DeploymentException("Failed to deploy artifacts: " + var31.getException().getMessage(), var31.getException());
            }

            var4.addArtifact(var31.getArtifact());
         }

         var15 = Utils.finishMetadata(var8, var14);
         var1.acquire((Collection)null, var15);
         var16 = var15.iterator();

         while(var16.hasNext()) {
            var17 = (Metadata)var16.next();
            this.upload(var11, var2, var17, var6, var7, var13);
            var12.put(var17, (Object)null);
         }

         var16 = var3.getMetadata().iterator();

         while(var16.hasNext()) {
            var17 = (Metadata)var16.next();
            if (!var12.containsKey(var17)) {
               this.upload(var11, var2, var17, var6, var7, var13);
               var12.put(var17, (Object)null);
            }
         }

         var7.put((Collection)null, var11);
         var16 = var11.iterator();

         while(true) {
            if (!var16.hasNext()) {
               var26 = false;
               break;
            }

            MetadataUpload var33 = (MetadataUpload)var16.next();
            if (var33.getException() != null) {
               throw new DeploymentException("Failed to deploy metadata: " + var33.getException().getMessage(), var33.getException());
            }

            var4.addMetadata(var33.getMetadata());
         }
      } finally {
         if (var26) {
            var7.close();
         }
      }

      var7.close();
      return var4;
   }

   private List getMetadataGenerators(RepositorySystemSession var1, DeployRequest var2) {
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

   private void upload(Collection var1, RepositorySystemSession var2, Metadata var3, RemoteRepository var4, RepositoryConnector var5, DefaultDeployer$EventCatapult var6) throws DeploymentException {
      LocalRepositoryManager var7 = var2.getLocalRepositoryManager();
      File var8 = var7.getRepository().getBasedir();
      File var9 = new File(var8, var7.getPathForRemoteMetadata(var3, var4, ""));
      if (var3 instanceof MergeableMetadata) {
         if (!((MergeableMetadata)var3).isMerged()) {
            RepositoryEvent$Builder var10 = new RepositoryEvent$Builder(var2, RepositoryEvent$EventType.METADATA_RESOLVING);
            var10.setTrace(var6.getTrace());
            var10.setMetadata(var3);
            var10.setRepository(var4);
            this.repositoryEventDispatcher.dispatch(var10.build());
            var10 = new RepositoryEvent$Builder(var2, RepositoryEvent$EventType.METADATA_DOWNLOADING);
            var10.setTrace(var6.getTrace());
            var10.setMetadata(var3);
            var10.setRepository(var4);
            this.repositoryEventDispatcher.dispatch(var10.build());
            RepositoryPolicy var11 = this.getPolicy(var2, var4, var3.getNature());
            MetadataDownload var12 = new MetadataDownload();
            var12.setMetadata(var3);
            var12.setFile(var9);
            var12.setChecksumPolicy(var11.getChecksumPolicy());
            var12.setListener(SafeTransferListener.wrap(var2));
            var12.setTrace(var6.getTrace());
            var5.get((Collection)null, Arrays.asList(var12));
            MetadataTransferException var13 = var12.getException();
            if (var13 instanceof MetadataNotFoundException) {
               var9.delete();
            }

            var10 = new RepositoryEvent$Builder(var2, RepositoryEvent$EventType.METADATA_DOWNLOADED);
            var10.setTrace(var6.getTrace());
            var10.setMetadata(var3);
            var10.setRepository(var4);
            var10.setException(var13);
            var10.setFile(var9);
            this.repositoryEventDispatcher.dispatch(var10.build());
            var10 = new RepositoryEvent$Builder(var2, RepositoryEvent$EventType.METADATA_RESOLVED);
            var10.setTrace(var6.getTrace());
            var10.setMetadata(var3);
            var10.setRepository(var4);
            var10.setException(var13);
            var10.setFile(var9);
            this.repositoryEventDispatcher.dispatch(var10.build());
            if (var13 != null && !(var13 instanceof MetadataNotFoundException)) {
               throw new DeploymentException("Failed to retrieve remote metadata " + var3 + ": " + var13.getMessage(), var13);
            }
         }

         try {
            ((MergeableMetadata)var3).merge(var9, var9);
         } catch (RepositoryException var15) {
            throw new DeploymentException("Failed to update metadata " + var3 + ": " + var15.getMessage(), var15);
         }
      } else {
         if (var3.getFile() == null) {
            throw new DeploymentException("Failed to update metadata " + var3 + ": No file attached.");
         }

         try {
            this.fileProcessor.copy(var3.getFile(), var9);
         } catch (IOException var14) {
            throw new DeploymentException("Failed to update metadata " + var3 + ": " + var14.getMessage(), var14);
         }
      }

      UpdateCheck var16 = new UpdateCheck();
      var16.setItem(var3);
      var16.setFile(var9);
      var16.setRepository(var4);
      var16.setAuthoritativeRepository(var4);
      this.updateCheckManager.touchMetadata(var2, var16);
      MetadataUpload var17 = new MetadataUpload(var3, var9);
      var17.setTrace(var6.getTrace());
      var17.setListener(new DefaultDeployer$MetadataUploadListener(var6, var17));
      var1.add(var17);
   }

   private RepositoryPolicy getPolicy(RepositorySystemSession var1, RemoteRepository var2, Metadata$Nature var3) {
      boolean var4 = !Metadata$Nature.SNAPSHOT.equals(var3);
      boolean var5 = !Metadata$Nature.RELEASE.equals(var3);
      return this.remoteRepositoryManager.getPolicy(var1, var2, var4, var5);
   }
}
