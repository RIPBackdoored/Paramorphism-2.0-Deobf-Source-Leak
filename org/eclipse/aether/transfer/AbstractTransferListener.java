package org.eclipse.aether.transfer;

public abstract class AbstractTransferListener implements TransferListener {
   protected AbstractTransferListener() {
      super();
   }

   public void transferInitiated(TransferEvent var1) throws TransferCancelledException {
   }

   public void transferStarted(TransferEvent var1) throws TransferCancelledException {
   }

   public void transferProgressed(TransferEvent var1) throws TransferCancelledException {
   }

   public void transferCorrupted(TransferEvent var1) throws TransferCancelledException {
   }

   public void transferSucceeded(TransferEvent var1) {
   }

   public void transferFailed(TransferEvent var1) {
   }
}
