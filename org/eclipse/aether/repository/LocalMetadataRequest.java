package org.eclipse.aether.repository;

import org.eclipse.aether.metadata.Metadata;

public final class LocalMetadataRequest {
   private Metadata metadata;
   private String context = "";
   private RemoteRepository repository = null;

   public LocalMetadataRequest() {
      super();
   }

   public LocalMetadataRequest(Metadata var1, RemoteRepository var2, String var3) {
      super();
      this.setMetadata(var1);
      this.setRepository(var2);
      this.setContext(var3);
   }

   public Metadata getMetadata() {
      return this.metadata;
   }

   public LocalMetadataRequest setMetadata(Metadata var1) {
      this.metadata = var1;
      return this;
   }

   public String getContext() {
      return this.context;
   }

   public LocalMetadataRequest setContext(String var1) {
      this.context = var1 != null ? var1 : "";
      return this;
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }

   public LocalMetadataRequest setRepository(RemoteRepository var1) {
      this.repository = var1;
      return this;
   }

   public String toString() {
      return this.getMetadata() + " @ " + this.getRepository();
   }
}
