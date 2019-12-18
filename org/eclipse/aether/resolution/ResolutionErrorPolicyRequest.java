package org.eclipse.aether.resolution;

import org.eclipse.aether.repository.RemoteRepository;

public final class ResolutionErrorPolicyRequest {
   private Object item;
   private RemoteRepository repository;

   public ResolutionErrorPolicyRequest() {
      super();
   }

   public ResolutionErrorPolicyRequest(Object var1, RemoteRepository var2) {
      super();
      this.setItem(var1);
      this.setRepository(var2);
   }

   public Object getItem() {
      return this.item;
   }

   public ResolutionErrorPolicyRequest setItem(Object var1) {
      this.item = var1;
      return this;
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }

   public ResolutionErrorPolicyRequest setRepository(RemoteRepository var1) {
      this.repository = var1;
      return this;
   }

   public String toString() {
      return this.getItem() + " < " + this.getRepository();
   }
}
