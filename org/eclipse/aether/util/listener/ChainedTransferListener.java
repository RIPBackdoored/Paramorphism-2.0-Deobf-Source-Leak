package org.eclipse.aether.util.listener;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.eclipse.aether.transfer.AbstractTransferListener;
import org.eclipse.aether.transfer.TransferCancelledException;
import org.eclipse.aether.transfer.TransferEvent;
import org.eclipse.aether.transfer.TransferListener;

public final class ChainedTransferListener extends AbstractTransferListener {
   private final List listeners = new CopyOnWriteArrayList();

   public static TransferListener newInstance(TransferListener var0, TransferListener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (TransferListener)(var1 == null ? var0 : new ChainedTransferListener(new TransferListener[]{var0, var1}));
      }
   }

   public ChainedTransferListener(TransferListener... var1) {
      super();
      if (var1 != null) {
         this.add((Collection)Arrays.asList(var1));
      }

   }

   public ChainedTransferListener(Collection var1) {
      super();
      this.add(var1);
   }

   public void add(Collection var1) {
      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            TransferListener var3 = (TransferListener)var2.next();
            this.add(var3);
         }
      }

   }

   public void add(TransferListener var1) {
      if (var1 != null) {
         this.listeners.add(var1);
      }

   }

   public void remove(TransferListener var1) {
      if (var1 != null) {
         this.listeners.remove(var1);
      }

   }

   protected void handleError(TransferEvent var1, TransferListener var2, RuntimeException var3) {
   }

   public void transferInitiated(TransferEvent var1) throws TransferCancelledException {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         TransferListener var3 = (TransferListener)var2.next();

         try {
            var3.transferInitiated(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void transferStarted(TransferEvent var1) throws TransferCancelledException {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         TransferListener var3 = (TransferListener)var2.next();

         try {
            var3.transferStarted(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void transferProgressed(TransferEvent var1) throws TransferCancelledException {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         TransferListener var3 = (TransferListener)var2.next();

         try {
            var3.transferProgressed(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void transferCorrupted(TransferEvent var1) throws TransferCancelledException {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         TransferListener var3 = (TransferListener)var2.next();

         try {
            var3.transferCorrupted(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void transferSucceeded(TransferEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         TransferListener var3 = (TransferListener)var2.next();

         try {
            var3.transferSucceeded(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }

   public void transferFailed(TransferEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         TransferListener var3 = (TransferListener)var2.next();

         try {
            var3.transferFailed(var1);
         } catch (RuntimeException var5) {
            this.handleError(var1, var3, var5);
         }
      }

   }
}
