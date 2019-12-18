package org.eclipse.aether.installation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.metadata.Metadata;

public final class InstallRequest {
   private Collection artifacts = Collections.emptyList();
   private Collection metadata = Collections.emptyList();
   private RequestTrace trace;

   public InstallRequest() {
      super();
   }

   public Collection getArtifacts() {
      return this.artifacts;
   }

   public InstallRequest setArtifacts(Collection var1) {
      if (var1 == null) {
         this.artifacts = Collections.emptyList();
      } else {
         this.artifacts = var1;
      }

      return this;
   }

   public InstallRequest addArtifact(Artifact var1) {
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

   public InstallRequest setMetadata(Collection var1) {
      if (var1 == null) {
         this.metadata = Collections.emptyList();
      } else {
         this.metadata = var1;
      }

      return this;
   }

   public InstallRequest addMetadata(Metadata var1) {
      if (var1 != null) {
         if (this.metadata.isEmpty()) {
            this.metadata = new ArrayList();
         }

         this.metadata.add(var1);
      }

      return this;
   }

   public RequestTrace getTrace() {
      return this.trace;
   }

   public InstallRequest setTrace(RequestTrace var1) {
      this.trace = var1;
      return this;
   }

   public String toString() {
      return this.getArtifacts() + ", " + this.getMetadata();
   }
}
