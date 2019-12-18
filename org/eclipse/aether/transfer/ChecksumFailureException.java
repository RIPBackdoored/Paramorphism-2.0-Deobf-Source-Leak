package org.eclipse.aether.transfer;

import org.eclipse.aether.RepositoryException;

public class ChecksumFailureException extends RepositoryException {
   private final String expected;
   private final String actual;
   private final boolean retryWorthy;

   public ChecksumFailureException(String var1, String var2) {
      super("Checksum validation failed, expected " + var1 + " but is " + var2);
      this.expected = var1;
      this.actual = var2;
      this.retryWorthy = true;
   }

   public ChecksumFailureException(String var1) {
      this(false, var1, (Throwable)null);
   }

   public ChecksumFailureException(Throwable var1) {
      this("Checksum validation failed" + getMessage(": ", var1), var1);
   }

   public ChecksumFailureException(String var1, Throwable var2) {
      this(false, var1, var2);
   }

   public ChecksumFailureException(boolean var1, String var2, Throwable var3) {
      super(var2, var3);
      this.expected = "";
      this.actual = "";
      this.retryWorthy = var1;
   }

   public String getExpected() {
      return this.expected;
   }

   public String getActual() {
      return this.actual;
   }

   public boolean isRetryWorthy() {
      return this.retryWorthy;
   }
}
