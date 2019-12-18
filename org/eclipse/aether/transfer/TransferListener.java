package org.eclipse.aether.transfer;

public interface TransferListener {
   void transferInitiated(TransferEvent var1) throws TransferCancelledException;

   void transferStarted(TransferEvent var1) throws TransferCancelledException;

   void transferProgressed(TransferEvent var1) throws TransferCancelledException;

   void transferCorrupted(TransferEvent var1) throws TransferCancelledException;

   void transferSucceeded(TransferEvent var1);

   void transferFailed(TransferEvent var1);
}
