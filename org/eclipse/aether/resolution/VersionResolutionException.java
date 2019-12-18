package org.eclipse.aether.resolution;

import org.eclipse.aether.RepositoryException;

public class VersionResolutionException extends RepositoryException {
   private final transient VersionResult result;

   public VersionResolutionException(VersionResult var1) {
      super(getMessage(var1), getCause(var1));
      this.result = var1;
   }

   private static String getMessage(VersionResult var0) {
      StringBuilder var1 = new StringBuilder(256);
      var1.append("Failed to resolve version");
      if (var0 != null) {
         var1.append(" for ").append(var0.getRequest().getArtifact());
         if (!var0.getExceptions().isEmpty()) {
            var1.append(": ").append(((Exception)var0.getExceptions().iterator().next()).getMessage());
         }
      }

      return var1.toString();
   }

   private static Throwable getCause(VersionResult var0) {
      Throwable var1 = null;
      if (var0 != null && !var0.getExceptions().isEmpty()) {
         var1 = (Throwable)var0.getExceptions().get(0);
      }

      return var1;
   }

   public VersionResolutionException(VersionResult var1, String var2) {
      super(var2, getCause(var1));
      this.result = var1;
   }

   public VersionResolutionException(VersionResult var1, String var2, Throwable var3) {
      super(var2, var3);
      this.result = var1;
   }

   public VersionResult getResult() {
      return this.result;
   }
}
