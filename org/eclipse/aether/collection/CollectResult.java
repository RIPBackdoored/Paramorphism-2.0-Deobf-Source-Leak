package org.eclipse.aether.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.eclipse.aether.graph.DependencyCycle;
import org.eclipse.aether.graph.DependencyNode;

public final class CollectResult {
   private final CollectRequest request;
   private List exceptions;
   private List cycles;
   private DependencyNode root;

   public CollectResult(CollectRequest var1) {
      super();
      this.request = (CollectRequest)Objects.requireNonNull(var1, "dependency collection request cannot be null");
      this.exceptions = Collections.emptyList();
      this.cycles = Collections.emptyList();
   }

   public CollectRequest getRequest() {
      return this.request;
   }

   public List getExceptions() {
      return this.exceptions;
   }

   public CollectResult addException(Exception var1) {
      if (var1 != null) {
         if (this.exceptions.isEmpty()) {
            this.exceptions = new ArrayList();
         }

         this.exceptions.add(var1);
      }

      return this;
   }

   public List getCycles() {
      return this.cycles;
   }

   public CollectResult addCycle(DependencyCycle var1) {
      if (var1 != null) {
         if (this.cycles.isEmpty()) {
            this.cycles = new ArrayList();
         }

         this.cycles.add(var1);
      }

      return this;
   }

   public DependencyNode getRoot() {
      return this.root;
   }

   public CollectResult setRoot(DependencyNode var1) {
      this.root = var1;
      return this;
   }

   public String toString() {
      return String.valueOf(this.getRoot());
   }
}
