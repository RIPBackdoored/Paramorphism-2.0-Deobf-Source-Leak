package org.eclipse.aether.transfer;

import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.RemoteRepository;

public class MetadataTransferException extends RepositoryException {
   private final transient Metadata metadata;
   private final transient RemoteRepository repository;
   private final boolean fromCache;

   static String getString(String var0, RemoteRepository var1) {
      return var1 == null ? "" : var0 + var1.getId() + " (" + var1.getUrl() + ")";
   }

   public MetadataTransferException(Metadata var1, RemoteRepository var2, String var3) {
      this(var1, var2, var3, false);
   }

   public MetadataTransferException(Metadata var1, RemoteRepository var2, String var3, boolean var4) {
      super(var3);
      this.metadata = var1;
      this.repository = var2;
      this.fromCache = var4;
   }

   public MetadataTransferException(Metadata var1, RemoteRepository var2, Throwable var3) {
      this(var1, var2, "Could not transfer metadata " + var1 + getString(" from/to ", var2) + getMessage(": ", var3), var3);
   }

   public MetadataTransferException(Metadata var1, RemoteRepository var2, String var3, Throwable var4) {
      super(var3, var4);
      this.metadata = var1;
      this.repository = var2;
      this.fromCache = false;
   }

   public Metadata getMetadata() {
      return this.metadata;
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }

   public boolean isFromCache() {
      return this.fromCache;
   }
}
