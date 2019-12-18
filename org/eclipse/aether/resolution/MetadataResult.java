package org.eclipse.aether.resolution;

import java.util.Objects;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.transfer.MetadataNotFoundException;

public final class MetadataResult {
   private final MetadataRequest request;
   private Exception exception;
   private boolean updated;
   private Metadata metadata;

   public MetadataResult(MetadataRequest var1) {
      super();
      this.request = (MetadataRequest)Objects.requireNonNull(var1, "metadata request cannot be null");
   }

   public MetadataRequest getRequest() {
      return this.request;
   }

   public Metadata getMetadata() {
      return this.metadata;
   }

   public MetadataResult setMetadata(Metadata var1) {
      this.metadata = var1;
      return this;
   }

   public MetadataResult setException(Exception var1) {
      this.exception = var1;
      return this;
   }

   public Exception getException() {
      return this.exception;
   }

   public MetadataResult setUpdated(boolean var1) {
      this.updated = var1;
      return this;
   }

   public boolean isUpdated() {
      return this.updated;
   }

   public boolean isResolved() {
      return this.getMetadata() != null && this.getMetadata().getFile() != null;
   }

   public boolean isMissing() {
      return this.getException() instanceof MetadataNotFoundException;
   }

   public String toString() {
      return this.getMetadata() + (this.isUpdated() ? " (updated)" : " (cached)");
   }
}
