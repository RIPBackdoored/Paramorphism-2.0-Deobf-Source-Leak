package org.eclipse.aether.version;

import org.eclipse.aether.RepositoryException;

public class InvalidVersionSpecificationException extends RepositoryException {
   private final String version;

   public InvalidVersionSpecificationException(String var1, String var2) {
      super(var2);
      this.version = var1;
   }

   public InvalidVersionSpecificationException(String var1, Throwable var2) {
      super("Could not parse version specification " + var1 + getMessage(": ", var2), var2);
      this.version = var1;
   }

   public InvalidVersionSpecificationException(String var1, String var2, Throwable var3) {
      super(var2, var3);
      this.version = var1;
   }

   public String getVersion() {
      return this.version;
   }
}
