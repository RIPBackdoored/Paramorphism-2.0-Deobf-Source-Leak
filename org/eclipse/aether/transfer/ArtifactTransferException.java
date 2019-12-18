package org.eclipse.aether.transfer;

import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.RemoteRepository;

public class ArtifactTransferException extends RepositoryException {
   private final transient Artifact artifact;
   private final transient RemoteRepository repository;
   private final boolean fromCache;

   static String getString(String var0, RemoteRepository var1) {
      return var1 == null ? "" : var0 + var1.getId() + " (" + var1.getUrl() + ")";
   }

   public ArtifactTransferException(Artifact var1, RemoteRepository var2, String var3) {
      this(var1, var2, var3, false);
   }

   public ArtifactTransferException(Artifact var1, RemoteRepository var2, String var3, boolean var4) {
      super(var3);
      this.artifact = var1;
      this.repository = var2;
      this.fromCache = var4;
   }

   public ArtifactTransferException(Artifact var1, RemoteRepository var2, Throwable var3) {
      this(var1, var2, "Could not transfer artifact " + var1 + getString(" from/to ", var2) + getMessage(": ", var3), var3);
   }

   public ArtifactTransferException(Artifact var1, RemoteRepository var2, String var3, Throwable var4) {
      super(var3, var4);
      this.artifact = var1;
      this.repository = var2;
      this.fromCache = false;
   }

   public Artifact getArtifact() {
      return this.artifact;
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }

   public boolean isFromCache() {
      return this.fromCache;
   }
}
