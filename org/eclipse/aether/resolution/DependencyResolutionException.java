package org.eclipse.aether.resolution;

import org.eclipse.aether.RepositoryException;

public class DependencyResolutionException extends RepositoryException {
   private final transient DependencyResult result;

   public DependencyResolutionException(DependencyResult var1, Throwable var2) {
      super(getMessage(var2), var2);
      this.result = var1;
   }

   public DependencyResolutionException(DependencyResult var1, String var2, Throwable var3) {
      super(var2, var3);
      this.result = var1;
   }

   private static String getMessage(Throwable var0) {
      String var1 = null;
      if (var0 != null) {
         var1 = var0.getMessage();
      }

      if (var1 == null || var1.length() <= 0) {
         var1 = "Could not resolve transitive dependencies";
      }

      return var1;
   }

   public DependencyResult getResult() {
      return this.result;
   }
}
