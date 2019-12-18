package org.eclipse.aether.internal.impl;

import org.eclipse.aether.spi.connector.MetadataUpload;
import org.eclipse.aether.transfer.TransferCancelledException;
import org.eclipse.aether.transfer.TransferEvent;

final class DefaultDeployer$MetadataUploadListener extends SafeTransferListener {
   private final DefaultDeployer$EventCatapult catapult;
   private final MetadataUpload transfer;

   DefaultDeployer$MetadataUploadListener(DefaultDeployer$EventCatapult var1, MetadataUpload var2) {
      super(var1.getSession());
      this.catapult = var1;
      this.transfer = var2;
   }

   public void transferInitiated(TransferEvent var1) throws TransferCancelledException {
      super.transferInitiated(var1);
      this.catapult.metadataDeploying(this.transfer.getMetadata(), this.transfer.getFile());
   }

   public void transferFailed(TransferEvent var1) {
      super.transferFailed(var1);
      this.catapult.metadataDeployed(this.transfer.getMetadata(), this.transfer.getFile(), this.transfer.getException());
   }

   public void transferSucceeded(TransferEvent var1) {
      super.transferSucceeded(var1);
      this.catapult.metadataDeployed(this.transfer.getMetadata(), this.transfer.getFile(), (Exception)null);
   }
}
