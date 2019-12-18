package org.eclipse.aether.resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.repository.RemoteRepository;

public final class ArtifactRequest {
   private Artifact artifact;
   private DependencyNode node;
   private List repositories = Collections.emptyList();
   private String context = "";
   private RequestTrace trace;

   public ArtifactRequest() {
      super();
   }

   public ArtifactRequest(Artifact var1, List var2, String var3) {
      super();
      this.setArtifact(var1);
      this.setRepositories(var2);
      this.setRequestContext(var3);
   }

   public ArtifactRequest(DependencyNode var1) {
      super();
      this.setDependencyNode(var1);
      this.setRepositories(var1.getRepositories());
      this.setRequestContext(var1.getRequestContext());
   }

   public Artifact getArtifact() {
      return this.artifact;
   }

   public ArtifactRequest setArtifact(Artifact var1) {
      this.artifact = var1;
      return this;
   }

   public DependencyNode getDependencyNode() {
      return this.node;
   }

   public ArtifactRequest setDependencyNode(DependencyNode var1) {
      this.node = var1;
      if (var1 != null) {
         this.setArtifact(var1.getDependency().getArtifact());
      }

      return this;
   }

   public List getRepositories() {
      return this.repositories;
   }

   public ArtifactRequest setRepositories(List var1) {
      if (var1 == null) {
         this.repositories = Collections.emptyList();
      } else {
         this.repositories = var1;
      }

      return this;
   }

   public ArtifactRequest addRepository(RemoteRepository var1) {
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

   public ArtifactRequest setRequestContext(String var1) {
      this.context = var1 != null ? var1 : "";
      return this;
   }

   public RequestTrace getTrace() {
      return this.trace;
   }

   public ArtifactRequest setTrace(RequestTrace var1) {
      this.trace = var1;
      return this;
   }

   public String toString() {
      return this.getArtifact() + " < " + this.getRepositories();
   }
}
