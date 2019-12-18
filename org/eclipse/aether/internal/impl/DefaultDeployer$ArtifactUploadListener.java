package org.eclipse.aether.internal.impl;

import org.eclipse.aether.spi.connector.ArtifactUpload;
import org.eclipse.aether.transfer.ArtifactTransferException;
import org.eclipse.aether.transfer.TransferCancelledException;
import org.eclipse.aether.transfer.TransferEvent;

final class DefaultDeployer$ArtifactUploadListener extends SafeTransferListener {
   private final DefaultDeployer$EventCatapult catapult;
   private final ArtifactUpload transfer;

   DefaultDeployer$ArtifactUploadListener(DefaultDeployer$EventCatapult var1, ArtifactUpload var2) {
      super(var1.getSession());
      this.catapult = var1;
      this.transfer = var2;
   }

   public void transferInitiated(TransferEvent var1) throws TransferCancelledException {
      super.transferInitiated(var1);
      this.catapult.artifactDeploying(this.transfer.getArtifact(), this.transfer.getFile());
   }

   public void transferFailed(TransferEvent var1) {
      super.transferFailed(var1);
      this.catapult.artifactDeployed(this.transfer.getArtifact(), this.transfer.getFile(), this.transfer.getException());
   }

   public void transferSucceeded(TransferEvent var1) {
      super.transferSucceeded(var1);
      this.catapult.artifactDeployed(this.transfer.getArtifact(), this.transfer.getFile(), (ArtifactTransferException)null);
   }
}
