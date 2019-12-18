package org.eclipse.aether.internal.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.aether.RepositoryEvent$Builder;
import org.eclipse.aether.RepositoryEvent$EventType;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.SyncContext;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.impl.ArtifactResolver;
import org.eclipse.aether.impl.OfflineController;
import org.eclipse.aether.impl.RemoteRepositoryManager;
import org.eclipse.aether.impl.RepositoryConnectorProvider;
import org.eclipse.aether.impl.RepositoryEventDispatcher;
import org.eclipse.aether.impl.SyncContextFactory;
import org.eclipse.aether.impl.UpdateCheck;
import org.eclipse.aether.impl.UpdateCheckManager;
import org.eclipse.aether.impl.VersionResolver;
import org.eclipse.aether.repository.ArtifactRepository;
import org.eclipse.aether.repository.LocalArtifactRegistration;
import org.eclipse.aether.repository.LocalArtifactRequest;
import org.eclipse.aether.repository.LocalArtifactResult;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.RepositoryPolicy;
import org.eclipse.aether.repository.WorkspaceReader;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.VersionRequest;
import org.eclipse.aether.resolution.VersionResolutionException;
import org.eclipse.aether.resolution.VersionResult;
import org.eclipse.aether.spi.connector.ArtifactDownload;
import org.eclipse.aether.spi.connector.RepositoryConnector;
import org.eclipse.aether.spi.io.FileProcessor;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.transfer.ArtifactNotFoundException;
import org.eclipse.aether.transfer.ArtifactTransferException;
import org.eclipse.aether.transfer.NoRepositoryConnectorException;
import org.eclipse.aether.transfer.RepositoryOfflineException;
import org.eclipse.aether.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class DefaultArtifactResolver implements ArtifactResolver, Service {
   private static final String CONFIG_PROP_SNAPSHOT_NORMALIZATION = "aether.artifactResolver.snapshotNormalization";
   private static final Logger LOGGER = LoggerFactory.getLogger(DefaultArtifactResolver.class);
   private FileProcessor fileProcessor;
   private RepositoryEventDispatcher repositoryEventDispatcher;
   private VersionResolver versionResolver;
   private UpdateCheckManager updateCheckManager;
   private RepositoryConnectorProvider repositoryConnectorProvider;
   private RemoteRepositoryManager remoteRepositoryManager;
   private SyncContextFactory syncContextFactory;
   private OfflineController offlineController;

   public DefaultArtifactResolver() {
      super();
   }

   @Inject
   DefaultArtifactResolver(FileProcessor var1, RepositoryEventDispatcher var2, VersionResolver var3, UpdateCheckManager var4, RepositoryConnectorProvider var5, RemoteRepositoryManager var6, SyncContextFactory var7, OfflineController var8) {
      super();
      this.setFileProcessor(var1);
      this.setRepositoryEventDispatcher(var2);
      this.setVersionResolver(var3);
      this.setUpdateCheckManager(var4);
      this.setRepositoryConnectorProvider(var5);
      this.setRemoteRepositoryManager(var6);
      this.setSyncContextFactory(var7);
      this.setOfflineController(var8);
   }

   public void initService(ServiceLocator var1) {
      this.setFileProcessor((FileProcessor)var1.getService(FileProcessor.class));
      this.setRepositoryEventDispatcher((RepositoryEventDispatcher)var1.getService(RepositoryEventDispatcher.class));
      this.setVersionResolver((VersionResolver)var1.getService(VersionResolver.class));
      this.setUpdateCheckManager((UpdateCheckManager)var1.getService(UpdateCheckManager.class));
      this.setRepositoryConnectorProvider((RepositoryConnectorProvider)var1.getService(RepositoryConnectorProvider.class));
      this.setRemoteRepositoryManager((RemoteRepositoryManager)var1.getService(RemoteRepositoryManager.class));
      this.setSyncContextFactory((SyncContextFactory)var1.getService(SyncContextFactory.class));
      this.setOfflineController((OfflineController)var1.getService(OfflineController.class));
   }

   /** @deprecated */
   @Deprecated
   public DefaultArtifactResolver setLoggerFactory(org.eclipse.aether.spi.log.LoggerFactory var1) {
      return this;
   }

   public DefaultArtifactResolver setFileProcessor(FileProcessor var1) {
      this.fileProcessor = (FileProcessor)Objects.requireNonNull(var1, "file processor cannot be null");
      return this;
   }

   public DefaultArtifactResolver setRepositoryEventDispatcher(RepositoryEventDispatcher var1) {
      this.repositoryEventDispatcher = (RepositoryEventDispatcher)Objects.requireNonNull(var1, "repository event dispatcher cannot be null");
      return this;
   }

   public DefaultArtifactResolver setVersionResolver(VersionResolver var1) {
      this.versionResolver = (VersionResolver)Objects.requireNonNull(var1, "version resolver cannot be null");
      return this;
   }

   public DefaultArtifactResolver setUpdateCheckManager(UpdateCheckManager var1) {
      this.updateCheckManager = (UpdateCheckManager)Objects.requireNonNull(var1, "update check manager cannot be null");
      return this;
   }

   public DefaultArtifactResolver setRepositoryConnectorProvider(RepositoryConnectorProvider var1) {
      this.repositoryConnectorProvider = (RepositoryConnectorProvider)Objects.requireNonNull(var1, "repository connector provider cannot be null");
      return this;
   }

   public DefaultArtifactResolver setRemoteRepositoryManager(RemoteRepositoryManager var1) {
      this.remoteRepositoryManager = (RemoteRepositoryManager)Objects.requireNonNull(var1, "remote repository provider cannot be null");
      return this;
   }

   public DefaultArtifactResolver setSyncContextFactory(SyncContextFactory var1) {
      this.syncContextFactory = (SyncContextFactory)Objects.requireNonNull(var1, "sync context factory cannot be null");
      return this;
   }

   public DefaultArtifactResolver setOfflineController(OfflineController var1) {
      this.offlineController = (OfflineController)Objects.requireNonNull(var1, "offline controller cannot be null");
      return this;
   }

   public ArtifactResult resolveArtifact(RepositorySystemSession var1, ArtifactRequest var2) throws ArtifactResolutionException {
      return (ArtifactResult)this.resolveArtifacts(var1, Collections.singleton(var2)).get(0);
   }

   public List resolveArtifacts(RepositorySystemSession var1, Collection var2) throws ArtifactResolutionException {
      SyncContext var3 = this.syncContextFactory.newInstance(var1, false);
      Throwable var4 = null;
      boolean var14 = false;

      List var19;
      try {
         var14 = true;
         ArrayList var5 = new ArrayList(var2.size());
         Iterator var6 = var2.iterator();

         while(true) {
            if (!var6.hasNext()) {
               var3.acquire(var5, (Collection)null);
               var19 = this.resolve(var1, var2);
               var14 = false;
               break;
            }

            ArtifactRequest var7 = (ArtifactRequest)var6.next();
            if (var7.getArtifact().getProperty("localPath", (String)null) == null) {
               var5.add(var7.getArtifact());
            }
         }
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

      return var19;
   }

   private List resolve(RepositorySystemSession var1, Collection var2) throws ArtifactResolutionException {
      ArrayList var3 = new ArrayList(var2.size());
      boolean var4 = false;
      LocalRepositoryManager var5 = var1.getLocalRepositoryManager();
      WorkspaceReader var6 = var1.getWorkspaceReader();
      ArrayList var7 = new ArrayList();
      Iterator var8 = var2.iterator();

      while(true) {
         label129:
         while(var8.hasNext()) {
            ArtifactRequest var9 = (ArtifactRequest)var8.next();
            RequestTrace var10 = RequestTrace.newChild(var9.getTrace(), var9);
            ArtifactResult var11 = new ArtifactResult(var9);
            var3.add(var11);
            Artifact var12 = var9.getArtifact();
            List var13 = var9.getRepositories();
            this.artifactResolving(var1, var10, var12);
            String var14 = var12.getProperty("localPath", (String)null);
            if (var14 != null) {
               File var32 = new File(var14);
               if (!var32.isFile()) {
                  var4 = true;
                  var11.addException(new ArtifactNotFoundException(var12, (RemoteRepository)null));
               } else {
                  var12 = var12.setFile(var32);
                  var11.setArtifact(var12);
                  this.artifactResolved(var1, var10, var12, (ArtifactRepository)null, var11.getExceptions());
               }
            } else {
               VersionResult var15;
               try {
                  VersionRequest var16 = new VersionRequest(var12, var13, var9.getRequestContext());
                  var16.setTrace(var10);
                  var15 = this.versionResolver.resolveVersion(var1, var16);
               } catch (VersionResolutionException var24) {
                  var11.addException(var24);
                  continue;
               }

               var12 = var12.setVersion(var15.getVersion());
               if (var15.getRepository() != null) {
                  if (var15.getRepository() instanceof RemoteRepository) {
                     var13 = Collections.singletonList((RemoteRepository)var15.getRepository());
                  } else {
                     var13 = Collections.emptyList();
                  }
               }

               if (var6 != null) {
                  File var33 = var6.findArtifact(var12);
                  if (var33 != null) {
                     var12 = var12.setFile(var33);
                     var11.setArtifact(var12);
                     var11.setRepository(var6.getRepository());
                     this.artifactResolved(var1, var10, var12, var11.getRepository(), (List)null);
                     continue;
                  }
               }

               LocalArtifactResult var34 = var5.find(var1, new LocalArtifactRequest(var12, var13, var9.getRequestContext()));
               if (this.isLocallyInstalled(var34, var15)) {
                  if (var34.getRepository() != null) {
                     var11.setRepository(var34.getRepository());
                  } else {
                     var11.setRepository(var5.getRepository());
                  }

                  try {
                     var12 = var12.setFile(this.getFile(var1, var12, var34.getFile()));
                     var11.setArtifact(var12);
                     this.artifactResolved(var1, var10, var12, var11.getRepository(), (List)null);
                  } catch (ArtifactTransferException var23) {
                     var11.addException(var23);
                  }

                  if (!var34.isAvailable()) {
                     var5.add(var1, new LocalArtifactRegistration(var12));
                  }
               } else {
                  if (var34.getFile() != null) {
                     LOGGER.debug((String)"Verifying availability of {} from {}", (Object)var34.getFile(), (Object)var13);
                  }

                  AtomicBoolean var17 = new AtomicBoolean(false);
                  Iterator var18 = var7.iterator();
                  Iterator var19 = var13.iterator();

                  while(true) {
                     RemoteRepository var20;
                     while(true) {
                        do {
                           if (!var19.hasNext()) {
                              continue label129;
                           }

                           var20 = (RemoteRepository)var19.next();
                        } while(!var20.getPolicy(var12.isSnapshot()).isEnabled());

                        try {
                           Utils.checkOffline(var1, this.offlineController, var20);
                           break;
                        } catch (RepositoryOfflineException var25) {
                           ArtifactNotFoundException var22 = new ArtifactNotFoundException(var12, var20, "Cannot access " + var20.getId() + " (" + var20.getUrl() + ") in offline mode and the artifact " + var12 + " has not been downloaded from it before.", var25);
                           var11.addException(var22);
                        }
                     }

                     DefaultArtifactResolver$ResolutionGroup var21 = null;

                     while(var18.hasNext()) {
                        DefaultArtifactResolver$ResolutionGroup var35 = (DefaultArtifactResolver$ResolutionGroup)var18.next();
                        if (var35.matches(var20)) {
                           var21 = var35;
                           break;
                        }
                     }

                     if (var21 == null) {
                        var21 = new DefaultArtifactResolver$ResolutionGroup(var20);
                        var7.add(var21);
                        var18 = Collections.emptyList().iterator();
                     }

                     var21.items.add(new DefaultArtifactResolver$ResolutionItem(var10, var12, var17, var11, var34, var20));
                  }
               }
            }
         }

         var8 = var7.iterator();

         while(var8.hasNext()) {
            DefaultArtifactResolver$ResolutionGroup var26 = (DefaultArtifactResolver$ResolutionGroup)var8.next();
            this.performDownloads(var1, var26);
         }

         var8 = var3.iterator();

         while(true) {
            ArtifactResult var27;
            ArtifactRequest var28;
            Artifact var29;
            do {
               if (!var8.hasNext()) {
                  if (var4) {
                     throw new ArtifactResolutionException(var3);
                  }

                  return var3;
               }

               var27 = (ArtifactResult)var8.next();
               var28 = var27.getRequest();
               var29 = var27.getArtifact();
            } while(var29 != null && var29.getFile() != null);

            var4 = true;
            if (var27.getExceptions().isEmpty()) {
               ArtifactNotFoundException var30 = new ArtifactNotFoundException(var28.getArtifact(), (RemoteRepository)null);
               var27.addException(var30);
            }

            RequestTrace var31 = RequestTrace.newChild(var28.getTrace(), var28);
            this.artifactResolved(var1, var31, var28.getArtifact(), (ArtifactRepository)null, var27.getExceptions());
         }
      }
   }

   private boolean isLocallyInstalled(LocalArtifactResult var1, VersionResult var2) {
      if (var1.isAvailable()) {
         return true;
      } else {
         if (var1.getFile() != null) {
            if (var2.getRepository() instanceof LocalRepository) {
               return true;
            }

            if (var2.getRepository() == null && var1.getRequest().getRepositories().isEmpty()) {
               return true;
            }
         }

         return false;
      }
   }

   private File getFile(RepositorySystemSession var1, Artifact var2, File var3) throws ArtifactTransferException {
      if (var2.isSnapshot() && !var2.getVersion().equals(var2.getBaseVersion()) && ConfigUtils.getBoolean(var1, true, "aether.artifactResolver.snapshotNormalization")) {
         String var4 = var3.getName().replace(var2.getVersion(), var2.getBaseVersion());
         File var5 = new File(var3.getParent(), var4);
         boolean var6 = var5.length() != var3.length() || var5.lastModified() != var3.lastModified();
         if (var6) {
            try {
               this.fileProcessor.copy(var3, var5);
               var5.setLastModified(var3.lastModified());
            } catch (IOException var8) {
               throw new ArtifactTransferException(var2, (RemoteRepository)null, var8);
            }
         }

         var3 = var5;
      }

      return var3;
   }

   private void performDownloads(RepositorySystemSession var1, DefaultArtifactResolver$ResolutionGroup var2) {
      List var3 = this.gatherDownloads(var1, var2);
      if (!var3.isEmpty()) {
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            ArtifactDownload var5 = (ArtifactDownload)var4.next();
            this.artifactDownloading(var1, var5.getTrace(), var5.getArtifact(), var2.repository);
         }

         try {
            RepositoryConnector var21 = this.repositoryConnectorProvider.newRepositoryConnector(var1, var2.repository);
            Throwable var23 = null;
            boolean var14 = false;

            try {
               var14 = true;
               var21.get(var3, (Collection)null);
               var14 = false;
            } catch (Throwable var17) {
               var23 = var17;
               throw var17;
            } finally {
               if (var14) {
                  if (var21 != null) {
                     if (var23 != null) {
                        try {
                           var21.close();
                        } catch (Throwable var15) {
                           var23.addSuppressed(var15);
                        }
                     } else {
                        var21.close();
                     }
                  }

               }
            }

            if (var21 != null) {
               if (var23 != null) {
                  try {
                     var21.close();
                  } catch (Throwable var16) {
                     var23.addSuppressed(var16);
                  }
               } else {
                  var21.close();
               }
            }
         } catch (NoRepositoryConnectorException var19) {
            NoRepositoryConnectorException var20 = var19;
            Iterator var22 = var3.iterator();

            while(var22.hasNext()) {
               ArtifactDownload var6 = (ArtifactDownload)var22.next();
               var6.setException(new ArtifactTransferException(var6.getArtifact(), var2.repository, var20));
            }
         }

         this.evaluateDownloads(var1, var2);
      }
   }

   private List gatherDownloads(RepositorySystemSession var1, DefaultArtifactResolver$ResolutionGroup var2) {
      LocalRepositoryManager var3 = var1.getLocalRepositoryManager();
      ArrayList var4 = new ArrayList();
      Iterator var5 = var2.items.iterator();

      while(true) {
         while(true) {
            DefaultArtifactResolver$ResolutionItem var6;
            Artifact var7;
            do {
               if (!var5.hasNext()) {
                  return var4;
               }

               var6 = (DefaultArtifactResolver$ResolutionItem)var5.next();
               var7 = var6.artifact;
            } while(var6.resolved.get());

            ArtifactDownload var8 = new ArtifactDownload();
            var8.setArtifact(var7);
            var8.setRequestContext(var6.request.getRequestContext());
            var8.setListener(SafeTransferListener.wrap(var1));
            var8.setTrace(var6.trace);
            if (var6.local.getFile() != null) {
               var8.setFile(var6.local.getFile());
               var8.setExistenceCheck(true);
            } else {
               String var9 = var3.getPathForRemoteArtifact(var7, var2.repository, var6.request.getRequestContext());
               var8.setFile(new File(var3.getRepository().getBasedir(), var9));
            }

            boolean var13 = var7.isSnapshot();
            RepositoryPolicy var10 = this.remoteRepositoryManager.getPolicy(var1, var2.repository, !var13, var13);
            int var11 = Utils.getPolicy(var1, var7, var2.repository);
            if ((var11 & 3) != 0) {
               UpdateCheck var12 = new UpdateCheck();
               var12.setItem(var7);
               var12.setFile(var8.getFile());
               var12.setFileValid(false);
               var12.setRepository(var2.repository);
               var12.setPolicy(var10.getUpdatePolicy());
               var6.updateCheck = var12;
               this.updateCheckManager.checkArtifact(var1, var12);
               if (!var12.isRequired()) {
                  var6.result.addException(var12.getException());
                  continue;
               }
            }

            var8.setChecksumPolicy(var10.getChecksumPolicy());
            var8.setRepositories(var6.repository.getMirroredRepositories());
            var4.add(var8);
            var6.download = var8;
         }
      }
   }

   private void evaluateDownloads(RepositorySystemSession var1, DefaultArtifactResolver$ResolutionGroup var2) {
      LocalRepositoryManager var3 = var1.getLocalRepositoryManager();
      Iterator var4 = var2.items.iterator();

      while(var4.hasNext()) {
         DefaultArtifactResolver$ResolutionItem var5 = (DefaultArtifactResolver$ResolutionItem)var4.next();
         ArtifactDownload var6 = var5.download;
         if (var6 != null) {
            Artifact var7 = var6.getArtifact();
            if (var6.getException() == null) {
               var5.resolved.set(true);
               var5.result.setRepository(var2.repository);

               try {
                  var7 = var7.setFile(this.getFile(var1, var7, var6.getFile()));
                  var5.result.setArtifact(var7);
                  var3.add(var1, new LocalArtifactRegistration(var7, var2.repository, var6.getSupportedContexts()));
               } catch (ArtifactTransferException var9) {
                  var6.setException(var9);
                  var5.result.addException(var9);
               }
            } else {
               var5.result.addException(var6.getException());
            }

            if (var5.updateCheck != null) {
               var5.updateCheck.setException(var6.getException());
               this.updateCheckManager.touchArtifact(var1, var5.updateCheck);
            }

            this.artifactDownloaded(var1, var6.getTrace(), var7, var2.repository, var6.getException());
            if (var6.getException() == null) {
               this.artifactResolved(var1, var6.getTrace(), var7, var2.repository, (List)null);
            }
         }
      }

   }

   private void artifactResolving(RepositorySystemSession var1, RequestTrace var2, Artifact var3) {
      RepositoryEvent$Builder var4 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.ARTIFACT_RESOLVING);
      var4.setTrace(var2);
      var4.setArtifact(var3);
      this.repositoryEventDispatcher.dispatch(var4.build());
   }

   private void artifactResolved(RepositorySystemSession var1, RequestTrace var2, Artifact var3, ArtifactRepository var4, List var5) {
      RepositoryEvent$Builder var6 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.ARTIFACT_RESOLVED);
      var6.setTrace(var2);
      var6.setArtifact(var3);
      var6.setRepository(var4);
      var6.setExceptions(var5);
      if (var3 != null) {
         var6.setFile(var3.getFile());
      }

      this.repositoryEventDispatcher.dispatch(var6.build());
   }

   private void artifactDownloading(RepositorySystemSession var1, RequestTrace var2, Artifact var3, RemoteRepository var4) {
      RepositoryEvent$Builder var5 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.ARTIFACT_DOWNLOADING);
      var5.setTrace(var2);
      var5.setArtifact(var3);
      var5.setRepository(var4);
      this.repositoryEventDispatcher.dispatch(var5.build());
   }

   private void artifactDownloaded(RepositorySystemSession var1, RequestTrace var2, Artifact var3, RemoteRepository var4, Exception var5) {
      RepositoryEvent$Builder var6 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.ARTIFACT_DOWNLOADED);
      var6.setTrace(var2);
      var6.setArtifact(var3);
      var6.setRepository(var4);
      var6.setException(var5);
      if (var3 != null) {
         var6.setFile(var3.getFile());
      }

      this.repositoryEventDispatcher.dispatch(var6.build());
   }
}
