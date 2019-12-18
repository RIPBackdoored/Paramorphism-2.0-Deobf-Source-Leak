package org.eclipse.aether.util.graph.selector;

import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.graph.Dependency;

public final class OptionalDependencySelector implements DependencySelector {
   private final int depth;

   public OptionalDependencySelector() {
      super();
      this.depth = 0;
   }

   private OptionalDependencySelector(int var1) {
      super();
      this.depth = var1;
   }

   public boolean selectDependency(Dependency var1) {
      return this.depth < 2 || !var1.isOptional();
   }

   public DependencySelector deriveChildSelector(DependencyCollectionContext var1) {
      return this.depth >= 2 ? this : new OptionalDependencySelector(this.depth + 1);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (null != var1 && this.getClass().equals(var1.getClass())) {
         OptionalDependencySelector var2 = (OptionalDependencySelector)var1;
         return this.depth == var2.depth;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.getClass().hashCode();
      var1 = var1 * 31 + this.depth;
      return var1;
   }
}
