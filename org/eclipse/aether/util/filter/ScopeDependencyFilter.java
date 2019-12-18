package org.eclipse.aether.util.filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;

public final class ScopeDependencyFilter implements DependencyFilter {
   private final Set included = new HashSet();
   private final Set excluded = new HashSet();

   public ScopeDependencyFilter(Collection var1, Collection var2) {
      super();
      if (var1 != null) {
         this.included.addAll(var1);
      }

      if (var2 != null) {
         this.excluded.addAll(var2);
      }

   }

   public ScopeDependencyFilter(String... var1) {
      super();
      if (var1 != null) {
         this.excluded.addAll(Arrays.asList(var1));
      }

   }

   public boolean accept(DependencyNode var1, List var2) {
      Dependency var3 = var1.getDependency();
      if (var3 == null) {
         return true;
      } else {
         String var4 = var1.getDependency().getScope();
         return (this.included.isEmpty() || this.included.contains(var4)) && (this.excluded.isEmpty() || !this.excluded.contains(var4));
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         ScopeDependencyFilter var2 = (ScopeDependencyFilter)var1;
         return this.included.equals(var2.included) && this.excluded.equals(var2.excluded);
      } else {
         return false;
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + this.included.hashCode();
      var2 = var2 * 31 + this.excluded.hashCode();
      return var2;
   }
}
