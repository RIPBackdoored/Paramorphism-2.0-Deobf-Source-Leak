package org.eclipse.aether.util.graph.selector;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.graph.Dependency;

public final class AndDependencySelector implements DependencySelector {
   private final Set selectors;
   private int hashCode;

   public AndDependencySelector(DependencySelector... var1) {
      super();
      if (var1 != null && var1.length > 0) {
         this.selectors = new LinkedHashSet(Arrays.asList(var1));
      } else {
         this.selectors = Collections.emptySet();
      }

   }

   public AndDependencySelector(Collection var1) {
      super();
      if (var1 != null && !var1.isEmpty()) {
         this.selectors = new LinkedHashSet(var1);
      } else {
         this.selectors = Collections.emptySet();
      }

   }

   private AndDependencySelector(Set var1) {
      super();
      if (var1 != null && !var1.isEmpty()) {
         this.selectors = var1;
      } else {
         this.selectors = Collections.emptySet();
      }

   }

   public static DependencySelector newInstance(DependencySelector var0, DependencySelector var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (DependencySelector)(var1 != null && !var1.equals(var0) ? new AndDependencySelector(new DependencySelector[]{var0, var1}) : var0);
      }
   }

   public boolean selectDependency(Dependency var1) {
      Iterator var2 = this.selectors.iterator();

      DependencySelector var3;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         var3 = (DependencySelector)var2.next();
      } while(var3.selectDependency(var1));

      return false;
   }

   public DependencySelector deriveChildSelector(DependencyCollectionContext var1) {
      int var2 = 0;
      LinkedHashSet var3 = null;
      Iterator var4 = this.selectors.iterator();

      while(true) {
         while(var4.hasNext()) {
            DependencySelector var5 = (DependencySelector)var4.next();
            DependencySelector var6 = var5.deriveChildSelector(var1);
            if (var3 != null) {
               if (var6 != null) {
                  var3.add(var6);
               }
            } else if (var5 == var6) {
               ++var2;
            } else {
               var3 = new LinkedHashSet();
               if (var2 > 0) {
                  Iterator var7 = this.selectors.iterator();

                  while(var7.hasNext()) {
                     DependencySelector var8 = (DependencySelector)var7.next();
                     if (var3.size() >= var2) {
                        break;
                     }

                     var3.add(var8);
                  }
               }

               if (var6 != null) {
                  var3.add(var6);
               }
            }
         }

         if (var3 == null) {
            return this;
         }

         if (var3.size() <= 1) {
            if (var3.isEmpty()) {
               return null;
            }

            return (DependencySelector)var3.iterator().next();
         }

         return new AndDependencySelector(var3);
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (null != var1 && this.getClass().equals(var1.getClass())) {
         AndDependencySelector var2 = (AndDependencySelector)var1;
         return this.selectors.equals(var2.selectors);
      } else {
         return false;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         byte var1 = 17;
         int var2 = var1 * 31 + this.selectors.hashCode();
         this.hashCode = var2;
      }

      return this.hashCode;
   }
}
