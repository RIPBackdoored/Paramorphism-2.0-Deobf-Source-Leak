package org.eclipse.aether.util.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;

public final class AndDependencyFilter implements DependencyFilter {
   private final Set filters = new LinkedHashSet();

   public AndDependencyFilter(DependencyFilter... var1) {
      super();
      if (var1 != null) {
         Collections.addAll(this.filters, var1);
      }

   }

   public AndDependencyFilter(Collection var1) {
      super();
      if (var1 != null) {
         this.filters.addAll(var1);
      }

   }

   public static DependencyFilter newInstance(DependencyFilter var0, DependencyFilter var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (DependencyFilter)(var1 == null ? var0 : new AndDependencyFilter(new DependencyFilter[]{var0, var1}));
      }
   }

   public boolean accept(DependencyNode var1, List var2) {
      Iterator var3 = this.filters.iterator();

      DependencyFilter var4;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         var4 = (DependencyFilter)var3.next();
      } while(var4.accept(var1, var2));

      return false;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         AndDependencyFilter var2 = (AndDependencyFilter)var1;
         return this.filters.equals(var2.filters);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.getClass().hashCode();
      var1 = var1 * 31 + this.filters.hashCode();
      return var1;
   }
}
