package org.eclipse.aether.resolution;

import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.RemoteRepository;

public final class MetadataRequest {
   private Metadata metadata;
   private RemoteRepository repository;
   private String context = "";
   private boolean deleteLocalCopyIfMissing;
   private boolean favorLocalRepository;
   private RequestTrace trace;

   public MetadataRequest() {
      super();
   }

   public MetadataRequest(Metadata var1) {
      super();
      this.setMetadata(var1);
   }

   public MetadataRequest(Metadata var1, RemoteRepository var2, String var3) {
      super();
      this.setMetadata(var1);
      this.setRepository(var2);
      this.setRequestContext(var3);
   }

   public Metadata getMetadata() {
      return this.metadata;
   }

   public MetadataRequest setMetadata(Metadata var1) {
      this.metadata = var1;
      return this;
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }

   public MetadataRequest setRepository(RemoteRepository var1) {
      this.repository = var1;
      return this;
   }

   public String getRequestContext() {
      return this.context;
   }

   public MetadataRequest setRequestContext(String var1) {
      this.context = var1 != null ? var1 : "";
      return this;
   }

   public boolean isDeleteLocalCopyIfMissing() {
      return this.deleteLocalCopyIfMissing;
   }

   public MetadataRequest setDeleteLocalCopyIfMissing(boolean var1) {
      this.deleteLocalCopyIfMissing = var1;
      return this;
   }

   public boolean isFavorLocalRepository() {
      return this.favorLocalRepository;
   }

   public MetadataRequest setFavorLocalRepository(boolean var1) {
      this.favorLocalRepository = var1;
      return this;
   }

   public RequestTrace getTrace() {
      return this.trace;
   }

   public MetadataRequest setTrace(RequestTrace var1) {
      this.trace = var1;
      return this;
   }

   public String toString() {
      return this.getMetadata() + " < " + this.getRepository();
   }
}
