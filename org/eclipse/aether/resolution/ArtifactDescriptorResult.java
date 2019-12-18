package org.eclipse.aether.resolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.ArtifactRepository;
import org.eclipse.aether.repository.RemoteRepository;

public final class ArtifactDescriptorResult {
   private final ArtifactDescriptorRequest request;
   private List exceptions;
   private List relocations;
   private Collection aliases;
   private Artifact artifact;
   private ArtifactRepository repository;
   private List dependencies;
   private List managedDependencies;
   private List repositories;
   private Map properties;

   public ArtifactDescriptorResult(ArtifactDescriptorRequest var1) {
      super();
      this.request = (ArtifactDescriptorRequest)Objects.requireNonNull(var1, "artifact descriptor request cannot be null");
      this.artifact = var1.getArtifact();
      this.exceptions = Collections.emptyList();
      this.relocations = Collections.emptyList();
      this.aliases = Collections.emptyList();
      this.dependencies = Collections.emptyList();
      this.managedDependencies = Collections.emptyList();
      this.repositories = Collections.emptyList();
      this.properties = Collections.emptyMap();
   }

   public ArtifactDescriptorRequest getRequest() {
      return this.request;
   }

   public List getExceptions() {
      return this.exceptions;
   }

   public ArtifactDescriptorResult setExceptions(List var1) {
      if (var1 == null) {
         this.exceptions = Collections.emptyList();
      } else {
         this.exceptions = var1;
      }

      return this;
   }

   public ArtifactDescriptorResult addException(Exception var1) {
      if (var1 != null) {
         if (this.exceptions.isEmpty()) {
            this.exceptions = new ArrayList();
         }

         this.exceptions.add(var1);
      }

      return this;
   }

   public List getRelocations() {
      return this.relocations;
   }

   public ArtifactDescriptorResult setRelocations(List var1) {
      if (var1 == null) {
         this.relocations = Collections.emptyList();
      } else {
         this.relocations = var1;
      }

      return this;
   }

   public ArtifactDescriptorResult addRelocation(Artifact var1) {
      if (var1 != null) {
         if (this.relocations.isEmpty()) {
            this.relocations = new ArrayList();
         }

         this.relocations.add(var1);
      }

      return this;
   }

   public Collection getAliases() {
      return this.aliases;
   }

   public ArtifactDescriptorResult setAliases(Collection var1) {
      if (var1 == null) {
         this.aliases = Collections.emptyList();
      } else {
         this.aliases = var1;
      }

      return this;
   }

   public ArtifactDescriptorResult addAlias(Artifact var1) {
      if (var1 != null) {
         if (this.aliases.isEmpty()) {
            this.aliases = new ArrayList();
         }

         this.aliases.add(var1);
      }

      return this;
   }

   public Artifact getArtifact() {
      return this.artifact;
   }

   public ArtifactDescriptorResult setArtifact(Artifact var1) {
      this.artifact = var1;
      return this;
   }

   public ArtifactRepository getRepository() {
      return this.repository;
   }

   public ArtifactDescriptorResult setRepository(ArtifactRepository var1) {
      this.repository = var1;
      return this;
   }

   public List getDependencies() {
      return this.dependencies;
   }

   public ArtifactDescriptorResult setDependencies(List var1) {
      if (var1 == null) {
         this.dependencies = Collections.emptyList();
      } else {
         this.dependencies = var1;
      }

      return this;
   }

   public ArtifactDescriptorResult addDependency(Dependency var1) {
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

   public ArtifactDescriptorResult setManagedDependencies(List var1) {
      if (var1 == null) {
         this.managedDependencies = Collections.emptyList();
      } else {
         this.managedDependencies = var1;
      }

      return this;
   }

   public ArtifactDescriptorResult addManagedDependency(Dependency var1) {
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

   public ArtifactDescriptorResult setRepositories(List var1) {
      if (var1 == null) {
         this.repositories = Collections.emptyList();
      } else {
         this.repositories = var1;
      }

      return this;
   }

   public ArtifactDescriptorResult addRepository(RemoteRepository var1) {
      if (var1 != null) {
         if (this.repositories.isEmpty()) {
            this.repositories = new ArrayList();
         }

         this.repositories.add(var1);
      }

      return this;
   }

   public Map getProperties() {
      return this.properties;
   }

   public ArtifactDescriptorResult setProperties(Map var1) {
      if (var1 == null) {
         this.properties = Collections.emptyMap();
      } else {
         this.properties = var1;
      }

      return this;
   }

   public String toString() {
      return this.getArtifact() + " -> " + this.getDependencies();
   }
}
