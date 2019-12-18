package org.eclipse.aether.resolution;

import org.eclipse.aether.RepositoryException;

public class VersionRangeResolutionException extends RepositoryException {
   private final transient VersionRangeResult result;

   public VersionRangeResolutionException(VersionRangeResult var1) {
      super(getMessage(var1), getCause(var1));
      this.result = var1;
   }

   private static String getMessage(VersionRangeResult var0) {
      StringBuilder var1 = new StringBuilder(256);
      var1.append("Failed to resolve version range");
      if (var0 != null) {
         var1.append(" for ").append(var0.getRequest().getArtifact());
         if (!var0.getExceptions().isEmpty()) {
            var1.append(": ").append(((Exception)var0.getExceptions().iterator().next()).getMessage());
         }
      }

      return var1.toString();
   }

   private static Throwable getCause(VersionRangeResult var0) {
      Throwable var1 = null;
      if (var0 != null && !var0.getExceptions().isEmpty()) {
         var1 = (Throwable)var0.getExceptions().get(0);
      }

      return var1;
   }

   public VersionRangeResolutionException(VersionRangeResult var1, String var2) {
      super(var2);
      this.result = var1;
   }

   public VersionRangeResolutionException(VersionRangeResult var1, String var2, Throwable var3) {
      super(var2, var3);
      this.result = var1;
   }

   public VersionRangeResult getResult() {
      return this.result;
   }
}
