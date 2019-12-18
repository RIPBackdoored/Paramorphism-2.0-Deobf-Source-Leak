package org.eclipse.aether.repository;

import java.io.File;
import java.util.Objects;

public final class LocalMetadataResult {
   private final LocalMetadataRequest request;
   private File file;
   private boolean stale;

   public LocalMetadataResult(LocalMetadataRequest var1) {
      super();
      this.request = (LocalMetadataRequest)Objects.requireNonNull(var1, "local metadata request cannot be null");
   }

   public LocalMetadataRequest getRequest() {
      return this.request;
   }

   public File getFile() {
      return this.file;
   }

   public LocalMetadataResult setFile(File var1) {
      this.file = var1;
      return this;
   }

   public boolean isStale() {
      return this.stale;
   }

   public LocalMetadataResult setStale(boolean var1) {
      this.stale = var1;
      return this;
   }

   public String toString() {
      return this.request.toString() + "(" + this.getFile() + ")";
   }
}
