package org.eclipse.aether.internal.impl;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.transfer.AbstractTransferListener;
import org.eclipse.aether.transfer.TransferCancelledException;
import org.eclipse.aether.transfer.TransferEvent;
import org.eclipse.aether.transfer.TransferListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SafeTransferListener extends AbstractTransferListener {
   private static final Logger LOGGER = LoggerFactory.getLogger(SafeTransferListener.class);
   private final TransferListener listener;

   public static TransferListener wrap(RepositorySystemSession var0) {
      TransferListener var1 = var0.getTransferListener();
      return var1 == null ? null : new SafeTransferListener(var1);
   }

   protected SafeTransferListener(RepositorySystemSession var1) {
      this(var1.getTransferListener());
   }

   private SafeTransferListener(TransferListener var1) {
      super();
      this.listener = var1;
   }

   private void logError(TransferEvent var1, Throwable var2) {
      LOGGER.debug("Failed to dispatch transfer event '{}' to {}", var1, this.listener.getClass().getCanonicalName(), var2);
   }

   public void transferInitiated(TransferEvent var1) throws TransferCancelledException {
      if (this.listener != null) {
         try {
            this.listener.transferInitiated(var1);
         } catch (LinkageError | RuntimeException var3) {
            this.logError(var1, var3);
         }
      }

   }

   public void transferStarted(TransferEvent var1) throws TransferCancelledException {
      if (this.listener != null) {
         try {
            this.listener.transferStarted(var1);
         } catch (LinkageError | RuntimeException var3) {
            this.logError(var1, var3);
         }
      }

   }

   public void transferProgressed(TransferEvent var1) throws TransferCancelledException {
      if (this.listener != null) {
         try {
            this.listener.transferProgressed(var1);
         } catch (LinkageError | RuntimeException var3) {
            this.logError(var1, var3);
         }
      }

   }

   public void transferCorrupted(TransferEvent var1) throws TransferCancelledException {
      if (this.listener != null) {
         try {
            this.listener.transferCorrupted(var1);
         } catch (LinkageError | RuntimeException var3) {
            this.logError(var1, var3);
         }
      }

   }

   public void transferSucceeded(TransferEvent var1) {
      if (this.listener != null) {
         try {
            this.listener.transferSucceeded(var1);
         } catch (LinkageError | RuntimeException var3) {
            this.logError(var1, var3);
         }
      }

   }

   public void transferFailed(TransferEvent var1) {
      if (this.listener != null) {
         try {
            this.listener.transferFailed(var1);
         } catch (LinkageError | RuntimeException var3) {
            this.logError(var1, var3);
         }
      }

   }
}
