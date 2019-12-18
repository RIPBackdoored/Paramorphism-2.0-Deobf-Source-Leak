package org.eclipse.aether.transfer;

import org.eclipse.aether.RepositoryException;

public class TransferCancelledException extends RepositoryException {
   public TransferCancelledException() {
      super("The operation was cancelled.");
   }

   public TransferCancelledException(String var1) {
      super(var1);
   }

   public TransferCancelledException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
