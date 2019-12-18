package org.eclipse.aether.repository;

import org.eclipse.aether.RepositoryException;

public class NoLocalRepositoryManagerException extends RepositoryException {
   private final transient LocalRepository repository;

   public NoLocalRepositoryManagerException(LocalRepository var1) {
      this(var1, toMessage(var1));
   }

   public NoLocalRepositoryManagerException(LocalRepository var1, String var2) {
      super(var2);
      this.repository = var1;
   }

   public NoLocalRepositoryManagerException(LocalRepository var1, Throwable var2) {
      this(var1, toMessage(var1), var2);
   }

   public NoLocalRepositoryManagerException(LocalRepository var1, String var2, Throwable var3) {
      super(var2, var3);
      this.repository = var1;
   }

   private static String toMessage(LocalRepository var0) {
      return var0 != null ? "No manager available for local repository (" + var0.getBasedir().getAbsolutePath() + ") of type " + var0.getContentType() : "No manager available for local repository";
   }

   public LocalRepository getRepository() {
      return this.repository;
   }
}
