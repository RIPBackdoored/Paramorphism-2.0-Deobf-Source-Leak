package org.eclipse.aether.util.graph.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;
import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.graph.Dependency;

public final class ScopeDependencySelector implements DependencySelector {
   private final boolean transitive;
   private final Collection included;
   private final Collection excluded;

   public ScopeDependencySelector(Collection var1, Collection var2) {
      super();
      this.transitive = false;
      this.included = clone(var1);
      this.excluded = clone(var2);
   }

   private static Collection clone(Collection var0) {
      Object var1;
      if (var0 != null && !var0.isEmpty()) {
         var1 = new HashSet(var0);
         if (((Collection)var1).size() <= 2) {
            var1 = new ArrayList(new TreeSet((Collection)var1));
         }
      } else {
         var1 = null;
      }

      return (Collection)var1;
   }

   public ScopeDependencySelector(String... var1) {
      this((Collection)null, var1 != null ? Arrays.asList(var1) : null);
   }

   private ScopeDependencySelector(boolean var1, Collection var2, Collection var3) {
      super();
      this.transitive = var1;
      this.included = var2;
      this.excluded = var3;
   }

   public boolean selectDependency(Dependency var1) {
      if (!this.transitive) {
         return true;
      } else {
         String var2 = var1.getScope();
         return (this.included == null || this.included.contains(var2)) && (this.excluded == null || !this.excluded.contains(var2));
      }
   }

   public DependencySelector deriveChildSelector(DependencyCollectionContext var1) {
      return !this.transitive && var1.getDependency() != null ? new ScopeDependencySelector(true, this.included, this.excluded) : this;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (null != var1 && this.getClass().equals(var1.getClass())) {
         ScopeDependencySelector var2 = (ScopeDependencySelector)var1;
         return this.transitive == var2.transitive && Objects.equals(this.included, var2.included) && Objects.equals(this.excluded, var2.excluded);
      } else {
         return false;
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + (this.transitive ? 1 : 0);
      var2 = var2 * 31 + (this.included != null ? this.included.hashCode() : 0);
      var2 = var2 * 31 + (this.excluded != null ? this.excluded.hashCode() : 0);
      return var2;
   }
}
