package org.eclipse.aether.repository;

import java.io.File;
import java.util.Objects;

public final class LocalArtifactResult {
   private final LocalArtifactRequest request;
   private File file;
   private boolean available;
   private RemoteRepository repository;

   public LocalArtifactResult(LocalArtifactRequest var1) {
      super();
      this.request = (LocalArtifactRequest)Objects.requireNonNull(var1, "local artifact request cannot be null");
   }

   public LocalArtifactRequest getRequest() {
      return this.request;
   }

   public File getFile() {
      return this.file;
   }

   public LocalArtifactResult setFile(File var1) {
      this.file = var1;
      return this;
   }

   public boolean isAvailable() {
      return this.available;
   }

   public LocalArtifactResult setAvailable(boolean var1) {
      this.available = var1;
      return this;
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }

   public LocalArtifactResult setRepository(RemoteRepository var1) {
      this.repository = var1;
      return this;
   }

   public String toString() {
      return this.getFile() + " (" + (this.isAvailable() ? "available" : "unavailable") + ")";
   }
}
