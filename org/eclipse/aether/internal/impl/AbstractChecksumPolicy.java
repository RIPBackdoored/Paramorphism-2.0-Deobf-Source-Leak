package org.eclipse.aether.internal.impl;

import org.eclipse.aether.spi.connector.checksum.ChecksumPolicy;
import org.eclipse.aether.transfer.ChecksumFailureException;
import org.eclipse.aether.transfer.TransferResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractChecksumPolicy implements ChecksumPolicy {
   protected final Logger logger = LoggerFactory.getLogger(this.getClass());
   protected final TransferResource resource;

   protected AbstractChecksumPolicy(TransferResource var1) {
      super();
      this.resource = var1;
   }

   public boolean onChecksumMatch(String var1, int var2) {
      return true;
   }

   public void onChecksumMismatch(String var1, int var2, ChecksumFailureException var3) throws ChecksumFailureException {
      if ((var2 & 1) == 0) {
         throw var3;
      }
   }

   public void onChecksumError(String var1, int var2, ChecksumFailureException var3) throws ChecksumFailureException {
      this.logger.debug("Could not validate {} checksum for {}", var1, this.resource.getResourceName(), var3);
   }

   public void onNoMoreChecksums() throws ChecksumFailureException {
      throw new ChecksumFailureException("Checksum validation failed, no checksums available");
   }

   public void onTransferRetry() {
   }
}
