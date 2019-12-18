package org.eclipse.aether.transfer;

import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.repository.RemoteRepository;

public class NoRepositoryConnectorException extends RepositoryException {
   private final transient RemoteRepository repository;

   public NoRepositoryConnectorException(RemoteRepository var1) {
      this(var1, toMessage(var1));
   }

   public NoRepositoryConnectorException(RemoteRepository var1, String var2) {
      super(var2);
      this.repository = var1;
   }

   public NoRepositoryConnectorException(RemoteRepository var1, Throwable var2) {
      this(var1, toMessage(var1), var2);
   }

   public NoRepositoryConnectorException(RemoteRepository var1, String var2, Throwable var3) {
      super(var2, var3);
      this.repository = var1;
   }

   private static String toMessage(RemoteRepository var0) {
      return var0 != null ? "No connector available to access repository " + var0.getId() + " (" + var0.getUrl() + ") of type " + var0.getContentType() : "No connector available to access repository";
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }
}
