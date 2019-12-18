package org.eclipse.aether.resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.RemoteRepository;

public final class VersionRangeRequest {
   private Artifact artifact;
   private List repositories = Collections.emptyList();
   private String context = "";
   private RequestTrace trace;

   public VersionRangeRequest() {
      super();
   }

   public VersionRangeRequest(Artifact var1, List var2, String var3) {
      super();
      this.setArtifact(var1);
      this.setRepositories(var2);
      this.setRequestContext(var3);
   }

   public Artifact getArtifact() {
      return this.artifact;
   }

   public VersionRangeRequest setArtifact(Artifact var1) {
      this.artifact = var1;
      return this;
   }

   public List getRepositories() {
      return this.repositories;
   }

   public VersionRangeRequest setRepositories(List var1) {
      if (var1 == null) {
         this.repositories = Collections.emptyList();
      } else {
         this.repositories = var1;
      }

      return this;
   }

   public VersionRangeRequest addRepository(RemoteRepository var1) {
      if (var1 != null) {
         if (this.repositories.isEmpty()) {
            this.repositories = new ArrayList();
         }

         this.repositories.add(var1);
      }

      return this;
   }

   public String getRequestContext() {
      return this.context;
   }

   public VersionRangeRequest setRequestContext(String var1) {
      this.context = var1 != null ? var1 : "";
      return this;
   }

   public RequestTrace getTrace() {
      return this.trace;
   }

   public VersionRangeRequest setTrace(RequestTrace var1) {
      this.trace = var1;
      return this;
   }

   public String toString() {
      return this.getArtifact() + " < " + this.getRepositories();
   }
}
