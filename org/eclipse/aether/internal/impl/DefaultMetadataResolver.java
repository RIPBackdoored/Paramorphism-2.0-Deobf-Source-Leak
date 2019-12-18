package org.eclipse.aether.internal.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.aether.RepositoryEvent$Builder;
import org.eclipse.aether.RepositoryEvent$EventType;
import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.SyncContext;
import org.eclipse.aether.impl.MetadataResolver;
import org.eclipse.aether.impl.OfflineController;
import org.eclipse.aether.impl.RemoteRepositoryManager;
import org.eclipse.aether.impl.RepositoryConnectorProvider;
import org.eclipse.aether.impl.RepositoryEventDispatcher;
import org.eclipse.aether.impl.SyncContextFactory;
import org.eclipse.aether.impl.UpdateCheck;
import org.eclipse.aether.impl.UpdateCheckManager;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.metadata.Metadata$Nature;
import org.eclipse.aether.repository.ArtifactRepository;
import org.eclipse.aether.repository.LocalMetadataRequest;
import org.eclipse.aether.repository.LocalMetadataResult;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.RepositoryPolicy;
import org.eclipse.aether.resolution.MetadataRequest;
import org.eclipse.aether.resolution.MetadataResult;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.transfer.MetadataNotFoundException;
import org.eclipse.aether.transfer.RepositoryOfflineException;
import org.eclipse.aether.util.ConfigUtils;
import org.eclipse.aether.util.concurrency.RunnableErrorForwarder;
import org.eclipse.aether.util.concurrency.WorkerThreadFactory;

@Named
public class DefaultMetadataResolver implements MetadataResolver, Service {
   private static final String CONFIG_PROP_THREADS = "aether.metadataResolver.threads";
   private RepositoryEventDispatcher repositoryEventDispatcher;
   private UpdateCheckManager updateCheckManager;
   private RepositoryConnectorProvider repositoryConnectorProvider;
   private RemoteRepositoryManager remoteRepositoryManager;
   private SyncContextFactory syncContextFactory;
   private OfflineController offlineController;

   public DefaultMetadataResolver() {
      super();
   }

   @Inject
   DefaultMetadataResolver(RepositoryEventDispatcher var1, UpdateCheckManager var2, RepositoryConnectorProvider var3, RemoteRepositoryManager var4, SyncContextFactory var5, OfflineController var6) {
      super();
      this.setRepositoryEventDispatcher(var1);
      this.setUpdateCheckManager(var2);
      this.setRepositoryConnectorProvider(var3);
      this.setRemoteRepositoryManager(var4);
      this.setSyncContextFactory(var5);
      this.setOfflineController(var6);
   }

   public void initService(ServiceLocator var1) {
      this.setRepositoryEventDispatcher((RepositoryEventDispatcher)var1.getService(RepositoryEventDispatcher.class));
      this.setUpdateCheckManager((UpdateCheckManager)var1.getService(UpdateCheckManager.class));
      this.setRepositoryConnectorProvider((RepositoryConnectorProvider)var1.getService(RepositoryConnectorProvider.class));
      this.setRemoteRepositoryManager((RemoteRepositoryManager)var1.getService(RemoteRepositoryManager.class));
      this.setSyncContextFactory((SyncContextFactory)var1.getService(SyncContextFactory.class));
      this.setOfflineController((OfflineController)var1.getService(OfflineController.class));
   }

   public DefaultMetadataResolver setRepositoryEventDispatcher(RepositoryEventDispatcher var1) {
      this.repositoryEventDispatcher = (RepositoryEventDispatcher)Objects.requireNonNull(var1, "repository event dispatcher cannot be null");
      return this;
   }

   public DefaultMetadataResolver setUpdateCheckManager(UpdateCheckManager var1) {
      this.updateCheckManager = (UpdateCheckManager)Objects.requireNonNull(var1, "update check manager cannot be null");
      return this;
   }

   public DefaultMetadataResolver setRepositoryConnectorProvider(RepositoryConnectorProvider var1) {
      this.repositoryConnectorProvider = (RepositoryConnectorProvider)Objects.requireNonNull(var1, "repository connector provider cannot be null");
      return this;
   }

   public DefaultMetadataResolver setRemoteRepositoryManager(RemoteRepositoryManager var1) {
      this.remoteRepositoryManager = (RemoteRepositoryManager)Objects.requireNonNull(var1, "remote repository provider cannot be null");
      return this;
   }

   public DefaultMetadataResolver setSyncContextFactory(SyncContextFactory var1) {
      this.syncContextFactory = (SyncContextFactory)Objects.requireNonNull(var1, "sync context factory cannot be null");
      return this;
   }

   public DefaultMetadataResolver setOfflineController(OfflineController var1) {
      this.offlineController = (OfflineController)Objects.requireNonNull(var1, "offline controller cannot be null");
      return this;
   }

   public List resolveMetadata(RepositorySystemSession var1, Collection var2) {
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
               var3.acquire((Collection)null, var5);
               var19 = this.resolve(var1, var2);
               var14 = false;
               break;
            }

            MetadataRequest var7 = (MetadataRequest)var6.next();
            var5.add(var7.getMetadata());
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

   private List resolve(RepositorySystemSession var1, Collection var2) {
      ArrayList var3 = new ArrayList(var2.size());
      ArrayList var4 = new ArrayList(var2.size());
      HashMap var5 = new HashMap();
      Iterator var6 = var2.iterator();

      while(true) {
         Metadata var10;
         while(var6.hasNext()) {
            MetadataRequest var7 = (MetadataRequest)var6.next();
            RequestTrace var8 = RequestTrace.newChild(var7.getTrace(), var7);
            MetadataResult var9 = new MetadataResult(var7);
            var3.add(var9);
            var10 = var7.getMetadata();
            RemoteRepository var11 = var7.getRepository();
            if (var11 == null) {
               LocalRepository var37 = var1.getLocalRepositoryManager().getRepository();
               this.metadataResolving(var1, var8, var10, var37);
               File var40 = this.getLocalFile(var1, var10);
               if (var40 != null) {
                  var10 = var10.setFile(var40);
                  var9.setMetadata(var10);
               } else {
                  var9.setException(new MetadataNotFoundException(var10, var37));
               }

               this.metadataResolved(var1, var8, var10, var37, var9.getException());
            } else {
               List var12 = this.getEnabledSourceRepositories(var11, var10.getNature());
               if (!var12.isEmpty()) {
                  this.metadataResolving(var1, var8, var10, var11);
                  LocalRepositoryManager var13 = var1.getLocalRepositoryManager();
                  LocalMetadataRequest var14 = new LocalMetadataRequest(var10, var11, var7.getRequestContext());
                  LocalMetadataResult var15 = var13.find(var1, var14);
                  File var16 = var15.getFile();

                  try {
                     Utils.checkOffline(var1, this.offlineController, var11);
                  } catch (RepositoryOfflineException var29) {
                     if (var16 != null) {
                        var10 = var10.setFile(var16);
                        var9.setMetadata(var10);
                     } else {
                        String var18 = "Cannot access " + var11.getId() + " (" + var11.getUrl() + ") in offline mode and the metadata " + var10 + " has not been downloaded from it before";
                        var9.setException(new MetadataNotFoundException(var10, var11, var18, var29));
                     }

                     this.metadataResolved(var1, var8, var10, var11, var9.getException());
                     continue;
                  }

                  Long var17 = null;
                  if (var7.isFavorLocalRepository()) {
                     File var41 = this.getLocalFile(var1, var10);
                     var17 = (Long)var5.get(var41);
                     if (var17 == null) {
                        var17 = var41 != null ? var41.lastModified() : 0L;
                        var5.put(var41, var17);
                     }
                  }

                  ArrayList var42 = new ArrayList();
                  RepositoryException var19 = null;
                  Iterator var20 = var12.iterator();

                  while(var20.hasNext()) {
                     RemoteRepository var21 = (RemoteRepository)var20.next();
                     UpdateCheck var22 = new UpdateCheck();
                     var22.setLocalLastUpdated(var17 != null ? var17 : 0L);
                     var22.setItem(var10);
                     File var23 = new File(var1.getLocalRepository().getBasedir(), var1.getLocalRepositoryManager().getPathForRemoteMetadata(var10, var11, var7.getRequestContext()));
                     var22.setFile(var23);
                     var22.setRepository(var11);
                     var22.setAuthoritativeRepository(var21);
                     var22.setPolicy(this.getPolicy(var1, var21, var10.getNature()).getUpdatePolicy());
                     if (var15.isStale()) {
                        var42.add(var22);
                     } else {
                        this.updateCheckManager.checkMetadata(var1, var22);
                        if (var22.isRequired()) {
                           var42.add(var22);
                        } else if (var19 == null) {
                           var19 = var22.getException();
                        }
                     }
                  }

                  if (!var42.isEmpty()) {
                     RepositoryPolicy var43 = this.getPolicy(var1, var11, var10.getNature());
                     File var44 = new File(var1.getLocalRepository().getBasedir(), var1.getLocalRepositoryManager().getPathForRemoteMetadata(var10, var7.getRepository(), var7.getRequestContext()));
                     DefaultMetadataResolver$ResolveTask var45 = new DefaultMetadataResolver$ResolveTask(this, var1, var8, var9, var44, var42, var43.getChecksumPolicy());
                     var4.add(var45);
                  } else {
                     var9.setException(var19);
                     if (var16 != null) {
                        var10 = var10.setFile(var16);
                        var9.setMetadata(var10);
                     }

                     this.metadataResolved(var1, var8, var10, var11, var9.getException());
                  }
               }
            }
         }

         if (!var4.isEmpty()) {
            int var30 = ConfigUtils.getInteger((RepositorySystemSession)var1, 4, "aether.metadataResolver.threads");
            Executor var31 = this.getExecutor(Math.min(var4.size(), var30));
            boolean var27 = false;

            try {
               var27 = true;
               RunnableErrorForwarder var32 = new RunnableErrorForwarder();
               Iterator var34 = var4.iterator();

               DefaultMetadataResolver$ResolveTask var38;
               while(var34.hasNext()) {
                  var38 = (DefaultMetadataResolver$ResolveTask)var34.next();
                  var31.execute(var32.wrap(var38));
               }

               var32.await();
               var34 = var4.iterator();

               while(true) {
                  if (!var34.hasNext()) {
                     var27 = false;
                     break;
                  }

                  var38 = (DefaultMetadataResolver$ResolveTask)var34.next();
                  var38.result.setException(var38.exception);
               }
            } finally {
               if (var27) {
                  this.shutdown(var31);
               }
            }

            this.shutdown(var31);

            DefaultMetadataResolver$ResolveTask var35;
            for(Iterator var33 = var4.iterator(); var33.hasNext(); this.metadataResolved(var1, var35.trace, var10, var35.request.getRepository(), var35.result.getException())) {
               var35 = (DefaultMetadataResolver$ResolveTask)var33.next();
               var10 = var35.request.getMetadata();
               LocalMetadataRequest var36 = new LocalMetadataRequest(var10, var35.request.getRepository(), var35.request.getRequestContext());
               File var39 = var1.getLocalRepositoryManager().find(var1, var36).getFile();
               if (var39 != null) {
                  var10 = var10.setFile(var39);
                  var35.result.setMetadata(var10);
               }

               if (var35.result.getException() == null) {
                  var35.result.setUpdated(true);
               }
            }
         }

         return var3;
      }
   }

   private File getLocalFile(RepositorySystemSession var1, Metadata var2) {
      LocalRepositoryManager var3 = var1.getLocalRepositoryManager();
      LocalMetadataResult var4 = var3.find(var1, new LocalMetadataRequest(var2, (RemoteRepository)null, (String)null));
      return var4.getFile();
   }

   private List getEnabledSourceRepositories(RemoteRepository var1, Metadata$Nature var2) {
      ArrayList var3 = new ArrayList();
      if (var1.isRepositoryManager()) {
         Iterator var4 = var1.getMirroredRepositories().iterator();

         while(var4.hasNext()) {
            RemoteRepository var5 = (RemoteRepository)var4.next();
            if (this.isEnabled(var5, var2)) {
               var3.add(var5);
            }
         }
      } else if (this.isEnabled(var1, var2)) {
         var3.add(var1);
      }

      return var3;
   }

   private boolean isEnabled(RemoteRepository var1, Metadata$Nature var2) {
      if (!Metadata$Nature.SNAPSHOT.equals(var2) && var1.getPolicy(false).isEnabled()) {
         return true;
      } else {
         return !Metadata$Nature.RELEASE.equals(var2) && var1.getPolicy(true).isEnabled();
      }
   }

   private RepositoryPolicy getPolicy(RepositorySystemSession var1, RemoteRepository var2, Metadata$Nature var3) {
      boolean var4 = !Metadata$Nature.SNAPSHOT.equals(var3);
      boolean var5 = !Metadata$Nature.RELEASE.equals(var3);
      return this.remoteRepositoryManager.getPolicy(var1, var2, var4, var5);
   }

   private void metadataResolving(RepositorySystemSession var1, RequestTrace var2, Metadata var3, ArtifactRepository var4) {
      RepositoryEvent$Builder var5 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.METADATA_RESOLVING);
      var5.setTrace(var2);
      var5.setMetadata(var3);
      var5.setRepository(var4);
      this.repositoryEventDispatcher.dispatch(var5.build());
   }

   private void metadataResolved(RepositorySystemSession var1, RequestTrace var2, Metadata var3, ArtifactRepository var4, Exception var5) {
      RepositoryEvent$Builder var6 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.METADATA_RESOLVED);
      var6.setTrace(var2);
      var6.setMetadata(var3);
      var6.setRepository(var4);
      var6.setException(var5);
      var6.setFile(var3.getFile());
      this.repositoryEventDispatcher.dispatch(var6.build());
   }

   private void metadataDownloading(RepositorySystemSession var1, RequestTrace var2, Metadata var3, ArtifactRepository var4) {
      RepositoryEvent$Builder var5 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.METADATA_DOWNLOADING);
      var5.setTrace(var2);
      var5.setMetadata(var3);
      var5.setRepository(var4);
      this.repositoryEventDispatcher.dispatch(var5.build());
   }

   private void metadataDownloaded(RepositorySystemSession var1, RequestTrace var2, Metadata var3, ArtifactRepository var4, File var5, Exception var6) {
      RepositoryEvent$Builder var7 = new RepositoryEvent$Builder(var1, RepositoryEvent$EventType.METADATA_DOWNLOADED);
      var7.setTrace(var2);
      var7.setMetadata(var3);
      var7.setRepository(var4);
      var7.setException(var6);
      var7.setFile(var5);
      this.repositoryEventDispatcher.dispatch(var7.build());
   }

   private Executor getExecutor(int var1) {
      return (Executor)(var1 <= 1 ? new DefaultMetadataResolver$1(this) : new ThreadPoolExecutor(var1, var1, 3L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new WorkerThreadFactory((String)null)));
   }

   private void shutdown(Executor var1) {
      if (var1 instanceof ExecutorService) {
         ((ExecutorService)var1).shutdown();
      }

   }

   static void access$000(DefaultMetadataResolver var0, RepositorySystemSession var1, RequestTrace var2, Metadata var3, ArtifactRepository var4) {
      var0.metadataDownloading(var1, var2, var3, var4);
   }

   static RepositoryConnectorProvider access$100(DefaultMetadataResolver var0) {
      return var0.repositoryConnectorProvider;
   }

   static UpdateCheckManager access$200(DefaultMetadataResolver var0) {
      return var0.updateCheckManager;
   }

   static void access$300(DefaultMetadataResolver var0, RepositorySystemSession var1, RequestTrace var2, Metadata var3, ArtifactRepository var4, File var5, Exception var6) {
      var0.metadataDownloaded(var1, var2, var3, var4, var5, var6);
   }
}
