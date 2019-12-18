package org.eclipse.aether.util.graph.traverser;

import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencyTraverser;
import org.eclipse.aether.graph.Dependency;

public final class StaticDependencyTraverser implements DependencyTraverser {
   private final boolean traverse;

   public StaticDependencyTraverser(boolean var1) {
      super();
      this.traverse = var1;
   }

   public boolean traverseDependency(Dependency var1) {
      return this.traverse;
   }

   public DependencyTraverser deriveChildTraverser(DependencyCollectionContext var1) {
      return this;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (null != var1 && this.getClass().equals(var1.getClass())) {
         StaticDependencyTraverser var2 = (StaticDependencyTraverser)var1;
         return this.traverse == var2.traverse;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.getClass().hashCode();
      var1 = var1 * 31 + (this.traverse ? 1 : 0);
      return var1;
   }
}
