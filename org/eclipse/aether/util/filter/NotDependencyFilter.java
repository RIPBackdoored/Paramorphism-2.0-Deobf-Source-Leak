package org.eclipse.aether.util.filter;

import java.util.List;
import java.util.Objects;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;

public final class NotDependencyFilter implements DependencyFilter {
   private final DependencyFilter filter;

   public NotDependencyFilter(DependencyFilter var1) {
      super();
      this.filter = (DependencyFilter)Objects.requireNonNull(var1, "dependency filter cannot be null");
   }

   public boolean accept(DependencyNode var1, List var2) {
      return !this.filter.accept(var1, var2);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         NotDependencyFilter var2 = (NotDependencyFilter)var1;
         return this.filter.equals(var2.filter);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.getClass().hashCode();
      var1 = var1 * 31 + this.filter.hashCode();
      return var1;
   }
}
