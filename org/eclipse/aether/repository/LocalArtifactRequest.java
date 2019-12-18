package org.eclipse.aether.repository;

import java.util.Collections;
import java.util.List;
import org.eclipse.aether.artifact.Artifact;

public final class LocalArtifactRequest {
   private Artifact artifact;
   private String context = "";
   private List repositories = Collections.emptyList();

   public LocalArtifactRequest() {
      super();
   }

   public LocalArtifactRequest(Artifact var1, List var2, String var3) {
      super();
      this.setArtifact(var1);
      this.setRepositories(var2);
      this.setContext(var3);
   }

   public Artifact getArtifact() {
      return this.artifact;
   }

   public LocalArtifactRequest setArtifact(Artifact var1) {
      this.artifact = var1;
      return this;
   }

   public String getContext() {
      return this.context;
   }

   public LocalArtifactRequest setContext(String var1) {
      this.context = var1 != null ? var1 : "";
      return this;
   }

   public List getRepositories() {
      return this.repositories;
   }

   public LocalArtifactRequest setRepositories(List var1) {
      if (var1 != null) {
         this.repositories = var1;
      } else {
         this.repositories = Collections.emptyList();
      }

      return this;
   }

   public String toString() {
      return this.getArtifact() + " @ " + this.getRepositories();
   }
}
