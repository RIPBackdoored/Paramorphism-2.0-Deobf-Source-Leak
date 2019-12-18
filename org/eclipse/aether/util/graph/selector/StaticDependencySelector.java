package org.eclipse.aether.util.graph.selector;

import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.graph.Dependency;

public final class StaticDependencySelector implements DependencySelector {
   private final boolean select;

   public StaticDependencySelector(boolean var1) {
      super();
      this.select = var1;
   }

   public boolean selectDependency(Dependency var1) {
      return this.select;
   }

   public DependencySelector deriveChildSelector(DependencyCollectionContext var1) {
      return this;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (null != var1 && this.getClass().equals(var1.getClass())) {
         StaticDependencySelector var2 = (StaticDependencySelector)var1;
         return this.select == var2.select;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.getClass().hashCode();
      var1 = var1 * 31 + (this.select ? 1 : 0);
      return var1;
   }
}
