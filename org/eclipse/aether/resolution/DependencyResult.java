package org.eclipse.aether.resolution;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.eclipse.aether.graph.DependencyNode;

public final class DependencyResult {
   private final DependencyRequest request;
   private DependencyNode root;
   private List cycles;
   private List collectExceptions;
   private List artifactResults;

   public DependencyResult(DependencyRequest var1) {
      super();
      this.request = (DependencyRequest)Objects.requireNonNull(var1, "dependency request cannot be null");
      this.root = var1.getRoot();
      this.cycles = Collections.emptyList();
      this.collectExceptions = Collections.emptyList();
      this.artifactResults = Collections.emptyList();
   }

   public DependencyRequest getRequest() {
      return this.request;
   }

   public DependencyNode getRoot() {
      return this.root;
   }

   public DependencyResult setRoot(DependencyNode var1) {
      this.root = var1;
      return this;
   }

   public List getCycles() {
      return this.cycles;
   }

   public DependencyResult setCycles(List var1) {
      if (var1 == null) {
         this.cycles = Collections.emptyList();
      } else {
         this.cycles = var1;
      }

      return this;
   }

   public List getCollectExceptions() {
      return this.collectExceptions;
   }

   public DependencyResult setCollectExceptions(List var1) {
      if (var1 == null) {
         this.collectExceptions = Collections.emptyList();
      } else {
         this.collectExceptions = var1;
      }

      return this;
   }

   public List getArtifactResults() {
      return this.artifactResults;
   }

   public DependencyResult setArtifactResults(List var1) {
      if (var1 == null) {
         this.artifactResults = Collections.emptyList();
      } else {
         this.artifactResults = var1;
      }

      return this;
   }

   public String toString() {
      return String.valueOf(this.artifactResults);
   }
}
