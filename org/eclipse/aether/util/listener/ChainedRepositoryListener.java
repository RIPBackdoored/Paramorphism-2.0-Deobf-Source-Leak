package org.eclipse.aether.util.listener;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.eclipse.aether.AbstractRepositoryListener;
import org.eclipse.aether.RepositoryEvent;
import org.eclipse.aether.RepositoryListener;

public final class ChainedRepositoryListener extends AbstractRepositoryListener {
   private final List listeners = new CopyOnWriteArrayList();

   public static RepositoryListener newInstance(RepositoryListener var0, RepositoryListener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (RepositoryListener)(var1 == null ? var0 : new ChainedRepositoryListener(new RepositoryListener[]{var0, var1}));
      }
   }

   public ChainedRepositoryListener(RepositoryListener... var1) {
      super();
      if (var1 != null) {
         this.add((Collection)Arrays.asList(var1));
      }

   }

   public ChainedRepositoryListener(Collection var1) {
      super();
      this.add(var1);
   }

   public void add(Collection var1) {
      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            RepositoryListener var3 = (RepositoryListener)var2.next();
            this.add(var3);
         }
      }

   }

   public void add(RepositoryListener var1) {
      if (var1 != null) {
         this.listeners.add(var1);
      }

   }

   public void remove(RepositoryListener var1) {
      if (var1 != null) {
         this.listeners.remove(var1);
      }

   }

   protected void handleError(RepositoryEvent var1, RepositoryListener var2, RuntimeException var3) {
   }

   public void artifactDeployed(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.artifactDeployed(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void artifactDeploying(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.artifactDeploying(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void artifactDescriptorInvalid(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.artifactDescriptorInvalid(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void artifactDescriptorMissing(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.artifactDescriptorMissing(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void artifactDownloaded(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.artifactDownloaded(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void artifactDownloading(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.artifactDownloading(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void artifactInstalled(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.artifactInstalled(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void artifactInstalling(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.artifactInstalling(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void artifactResolved(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.artifactResolved(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void artifactResolving(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.artifactResolving(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void metadataDeployed(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.metadataDeployed(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void metadataDeploying(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.metadataDeploying(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void metadataDownloaded(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.metadataDownloaded(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void metadataDownloading(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.metadataDownloading(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void metadataInstalled(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.metadataInstalled(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void metadataInstalling(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.metadataInstalling(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void metadataInvalid(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.metadataInvalid(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void metadataResolved(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.metadataResolved(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void metadataResolving(RepositoryEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         RepositoryListener var3 = (RepositoryListener)var2.next();

         try {
            var3.metadataResolving(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }
}
