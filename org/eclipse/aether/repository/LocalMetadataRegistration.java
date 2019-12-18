package org.eclipse.aether.repository;

import java.util.Collection;
import java.util.Collections;
import org.eclipse.aether.metadata.Metadata;

public final class LocalMetadataRegistration {
   private Metadata metadata;
   private RemoteRepository repository;
   private Collection contexts = Collections.emptyList();

   public LocalMetadataRegistration() {
      super();
   }

   public LocalMetadataRegistration(Metadata var1) {
      super();
      this.setMetadata(var1);
   }

   public LocalMetadataRegistration(Metadata var1, RemoteRepository var2, Collection var3) {
      super();
      this.setMetadata(var1);
      this.setRepository(var2);
      this.setContexts(var3);
   }

   public Metadata getMetadata() {
      return this.metadata;
   }

   public LocalMetadataRegistration setMetadata(Metadata var1) {
      this.metadata = var1;
      return this;
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }

   public LocalMetadataRegistration setRepository(RemoteRepository var1) {
      this.repository = var1;
      return this;
   }

   public Collection getContexts() {
      return this.contexts;
   }

   public LocalMetadataRegistration setContexts(Collection var1) {
      if (var1 != null) {
         this.contexts = var1;
      } else {
         this.contexts = Collections.emptyList();
      }

      return this;
   }
}
