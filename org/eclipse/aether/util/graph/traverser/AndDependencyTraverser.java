package org.eclipse.aether.util.graph.traverser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencyTraverser;
import org.eclipse.aether.graph.Dependency;

public final class AndDependencyTraverser implements DependencyTraverser {
   private final Set traversers;
   private int hashCode;

   public AndDependencyTraverser(DependencyTraverser... var1) {
      super();
      if (var1 != null && var1.length > 0) {
         this.traversers = new LinkedHashSet(Arrays.asList(var1));
      } else {
         this.traversers = Collections.emptySet();
      }

   }

   public AndDependencyTraverser(Collection var1) {
      super();
      if (var1 != null && !var1.isEmpty()) {
         this.traversers = new LinkedHashSet(var1);
      } else {
         this.traversers = Collections.emptySet();
      }

   }

   private AndDependencyTraverser(Set var1) {
      super();
      if (var1 != null && !var1.isEmpty()) {
         this.traversers = var1;
      } else {
         this.traversers = Collections.emptySet();
      }

   }

   public static DependencyTraverser newInstance(DependencyTraverser var0, DependencyTraverser var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (DependencyTraverser)(var1 != null && !var1.equals(var0) ? new AndDependencyTraverser(new DependencyTraverser[]{var0, var1}) : var0);
      }
   }

   public boolean traverseDependency(Dependency var1) {
      Iterator var2 = this.traversers.iterator();

      DependencyTraverser var3;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         var3 = (DependencyTraverser)var2.next();
      } while(var3.traverseDependency(var1));

      return false;
   }

   public DependencyTraverser deriveChildTraverser(DependencyCollectionContext var1) {
      int var2 = 0;
      LinkedHashSet var3 = null;
      Iterator var4 = this.traversers.iterator();

      while(true) {
         while(var4.hasNext()) {
            DependencyTraverser var5 = (DependencyTraverser)var4.next();
            DependencyTraverser var6 = var5.deriveChildTraverser(var1);
            if (var3 != null) {
               if (var6 != null) {
                  var3.add(var6);
               }
            } else if (var5 == var6) {
               ++var2;
            } else {
               var3 = new LinkedHashSet();
               if (var2 > 0) {
                  Iterator var7 = this.traversers.iterator();

                  while(var7.hasNext()) {
                     DependencyTraverser var8 = (DependencyTraverser)var7.next();
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

            return (DependencyTraverser)var3.iterator().next();
         }

         return new AndDependencyTraverser(var3);
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (null != var1 && this.getClass().equals(var1.getClass())) {
         AndDependencyTraverser var2 = (AndDependencyTraverser)var1;
         return this.traversers.equals(var2.traversers);
      } else {
         return false;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         byte var1 = 17;
         int var2 = var1 * 31 + this.traversers.hashCode();
         this.hashCode = var2;
      }

      return this.hashCode;
   }
}
