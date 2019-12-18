package org.eclipse.aether.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

public final class CollectRequest {
   private Artifact rootArtifact;
   private Dependency root;
   private List dependencies = Collections.emptyList();
   private List managedDependencies = Collections.emptyList();
   private List repositories = Collections.emptyList();
   private String context = "";
   private RequestTrace trace;

   public CollectRequest() {
      super();
   }

   public CollectRequest(Dependency var1, List var2) {
      super();
      this.setRoot(var1);
      this.setRepositories(var2);
   }

   public CollectRequest(Dependency var1, List var2, List var3) {
      super();
      this.setRoot(var1);
      this.setDependencies(var2);
      this.setRepositories(var3);
   }

   public CollectRequest(List var1, List var2, List var3) {
      super();
      this.setDependencies(var1);
      this.setManagedDependencies(var2);
      this.setRepositories(var3);
   }

   public Artifact getRootArtifact() {
      return this.rootArtifact;
   }

   public CollectRequest setRootArtifact(Artifact var1) {
      this.rootArtifact = var1;
      return this;
   }

   public Dependency getRoot() {
      return this.root;
   }

   public CollectRequest setRoot(Dependency var1) {
      this.root = var1;
      return this;
   }

   public List getDependencies() {
      return this.dependencies;
   }

   public CollectRequest setDependencies(List var1) {
      if (var1 == null) {
         this.dependencies = Collections.emptyList();
      } else {
         this.dependencies = var1;
      }

      return this;
   }

   public CollectRequest addDependency(Dependency var1) {
      if (var1 != null) {
         if (this.dependencies.isEmpty()) {
            this.dependencies = new ArrayList();
         }

         this.dependencies.add(var1);
      }

      return this;
   }

   public List getManagedDependencies() {
      return this.managedDependencies;
   }

   public CollectRequest setManagedDependencies(List var1) {
      if (var1 == null) {
         this.managedDependencies = Collections.emptyList();
      } else {
         this.managedDependencies = var1;
      }

      return this;
   }

   public CollectRequest addManagedDependency(Dependency var1) {
      if (var1 != null) {
         if (this.managedDependencies.isEmpty()) {
            this.managedDependencies = new ArrayList();
         }

         this.managedDependencies.add(var1);
      }

      return this;
   }

   public List getRepositories() {
      return this.repositories;
   }

   public CollectRequest setRepositories(List var1) {
      if (var1 == null) {
         this.repositories = Collections.emptyList();
      } else {
         this.repositories = var1;
      }

      return this;
   }

   public CollectRequest addRepository(RemoteRepository var1) {
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

   public CollectRequest setRequestContext(String var1) {
      this.context = var1 != null ? var1 : "";
      return this;
   }

   public RequestTrace getTrace() {
      return this.trace;
   }

   public CollectRequest setTrace(RequestTrace var1) {
      this.trace = var1;
      return this;
   }

   public String toString() {
      return this.getRoot() + " -> " + this.getDependencies() + " < " + this.getRepositories();
   }
}
