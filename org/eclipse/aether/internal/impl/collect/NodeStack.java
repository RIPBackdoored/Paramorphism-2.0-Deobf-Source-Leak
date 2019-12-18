package org.eclipse.aether.internal.impl.collect;

import java.util.Arrays;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.DependencyNode;

final class NodeStack {
   private DependencyNode[] nodes = new DependencyNode[96];
   private int size;

   NodeStack() {
      super();
   }

   public DependencyNode top() {
      if (this.size <= 0) {
         throw new IllegalStateException("stack empty");
      } else {
         return this.nodes[this.size - 1];
      }
   }

   public void push(DependencyNode var1) {
      if (this.size >= this.nodes.length) {
         DependencyNode[] var2 = new DependencyNode[this.size + 64];
         System.arraycopy(this.nodes, 0, var2, 0, this.nodes.length);
         this.nodes = var2;
      }

      this.nodes[this.size++] = var1;
   }

   public void pop() {
      if (this.size <= 0) {
         throw new IllegalStateException("stack empty");
      } else {
         --this.size;
      }
   }

   public int find(Artifact var1) {
      for(int var2 = this.size - 1; var2 >= 0; --var2) {
         DependencyNode var3 = this.nodes[var2];
         Artifact var4 = var3.getArtifact();
         if (var4 == null) {
            break;
         }

         if (var4.getArtifactId().equals(var1.getArtifactId()) && var4.getGroupId().equals(var1.getGroupId()) && var4.getExtension().equals(var1.getExtension()) && var4.getClassifier().equals(var1.getClassifier())) {
            return var2;
         }
      }

      return -1;
   }

   public int size() {
      return this.size;
   }

   public DependencyNode get(int var1) {
      return this.nodes[var1];
   }

   public String toString() {
      return Arrays.toString(this.nodes);
   }
}
