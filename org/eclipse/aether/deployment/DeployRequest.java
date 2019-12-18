package org.eclipse.aether.deployment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.RemoteRepository;

public final class DeployRequest {
   private Collection artifacts = Collections.emptyList();
   private Collection metadata = Collections.emptyList();
   private RemoteRepository repository;
   private RequestTrace trace;

   public DeployRequest() {
      super();
   }

   public Collection getArtifacts() {
      return this.artifacts;
   }

   public DeployRequest setArtifacts(Collection var1) {
      if (var1 == null) {
         this.artifacts = Collections.emptyList();
      } else {
         this.artifacts = var1;
      }

      return this;
   }

   public DeployRequest addArtifact(Artifact var1) {
      if (var1 != null) {
         if (this.artifacts.isEmpty()) {
            this.artifacts = new ArrayList();
         }

         this.artifacts.add(var1);
      }

      return this;
   }

   public Collection getMetadata() {
      return this.metadata;
   }

   public DeployRequest setMetadata(Collection var1) {
      if (var1 == null) {
         this.metadata = Collections.emptyList();
      } else {
         this.metadata = var1;
      }

      return this;
   }

   public DeployRequest addMetadata(Metadata var1) {
      if (var1 != null) {
         if (this.metadata.isEmpty()) {
            this.metadata = new ArrayList();
         }

         this.metadata.add(var1);
      }

      return this;
   }

   public RemoteRepository getRepository() {
      return this.repository;
   }

   public DeployRequest setRepository(RemoteRepository var1) {
      this.repository = var1;
      return this;
   }

   public RequestTrace getTrace() {
      return this.trace;
   }

   public DeployRequest setTrace(RequestTrace var1) {
      this.trace = var1;
      return this;
   }

   public String toString() {
      return this.getArtifacts() + ", " + this.getMetadata() + " > " + this.getRepository();
   }
}
