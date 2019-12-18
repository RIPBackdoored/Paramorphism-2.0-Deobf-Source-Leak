package org.eclipse.aether.collection;

import org.eclipse.aether.RepositoryException;

public class DependencyCollectionException extends RepositoryException {
   private final transient CollectResult result;

   public DependencyCollectionException(CollectResult var1) {
      super("Failed to collect dependencies for " + getSource(var1), getCause(var1));
      this.result = var1;
   }

   public DependencyCollectionException(CollectResult var1, String var2) {
      super(var2, getCause(var1));
      this.result = var1;
   }

   public DependencyCollectionException(CollectResult var1, String var2, Throwable var3) {
      super(var2, var3);
      this.result = var1;
   }

   public CollectResult getResult() {
      return this.result;
   }

   private static String getSource(CollectResult var0) {
      if (var0 == null) {
         return "";
      } else {
         CollectRequest var1 = var0.getRequest();
         if (var1.getRoot() != null) {
            return var1.getRoot().toString();
         } else {
            return var1.getRootArtifact() != null ? var1.getRootArtifact().toString() : var1.getDependencies().toString();
         }
      }
   }

   private static Throwable getCause(CollectResult var0) {
      Throwable var1 = null;
      if (var0 != null && !var0.getExceptions().isEmpty()) {
         var1 = (Throwable)var0.getExceptions().get(0);
      }

      return var1;
   }
}
