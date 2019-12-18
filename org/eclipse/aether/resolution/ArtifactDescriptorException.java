package org.eclipse.aether.resolution;

import org.eclipse.aether.RepositoryException;

public class ArtifactDescriptorException extends RepositoryException {
   private final transient ArtifactDescriptorResult result;

   public ArtifactDescriptorException(ArtifactDescriptorResult var1) {
      super("Failed to read artifact descriptor" + (var1 != null ? " for " + var1.getRequest().getArtifact() : ""), getCause(var1));
      this.result = var1;
   }

   public ArtifactDescriptorException(ArtifactDescriptorResult var1, String var2) {
      super(var2, getCause(var1));
      this.result = var1;
   }

   public ArtifactDescriptorException(ArtifactDescriptorResult var1, String var2, Throwable var3) {
      super(var2, var3);
      this.result = var1;
   }

   public ArtifactDescriptorResult getResult() {
      return this.result;
   }

   private static Throwable getCause(ArtifactDescriptorResult var0) {
      Throwable var1 = null;
      if (var0 != null && !var0.getExceptions().isEmpty()) {
         var1 = (Throwable)var0.getExceptions().get(0);
      }

      return var1;
   }
}
