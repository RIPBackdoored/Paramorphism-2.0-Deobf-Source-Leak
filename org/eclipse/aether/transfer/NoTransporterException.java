package org.eclipse.aether.transfer;

import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.repository.RemoteRepository;

public class NoTransporterException extends RepositoryException {
   private final transient RemoteRepository repository;

   public NoTransporterException(RemoteRepository var1) {
      this(var1, toMessage(var1));
   }

   public NoTransporterException(RemoteRepository var1, String var2) {
      super(var2);
      this.repository = var1;
   }

   public NoTransporterException(RemoteRepository var1, Throwable var2) {
      this(var1, toMessage(var1), var2);
   }

   public NoTransporterException(RemoteRepository var1, String var2, Throwable var3) {
      super(var2, var3);
      this.repository = var1;
   }

   private static String toMessage(RemoteRepository var0) {
      return var0 != null ? "Unsupported transport protocol " + var0.getProtocol() : "Unsupported transport protocol";
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }
}
