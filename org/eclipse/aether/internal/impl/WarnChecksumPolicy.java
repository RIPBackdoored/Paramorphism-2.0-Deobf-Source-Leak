package org.eclipse.aether.internal.impl;

import org.eclipse.aether.transfer.ChecksumFailureException;
import org.eclipse.aether.transfer.TransferResource;

final class WarnChecksumPolicy extends AbstractChecksumPolicy {
   WarnChecksumPolicy(TransferResource var1) {
      super(var1);
   }

   public boolean onTransferChecksumFailure(ChecksumFailureException var1) {
      String var2 = "Could not validate integrity of download from " + this.resource.getRepositoryUrl() + this.resource.getResourceName();
      if (this.logger.isDebugEnabled()) {
         this.logger.warn((String)var2, (Throwable)var1);
      } else {
         this.logger.warn(var2 + ": " + var1.getMessage());
      }

      return true;
   }
}
