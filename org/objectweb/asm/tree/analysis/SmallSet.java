package org.objectweb.asm.tree.analysis;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class SmallSet extends AbstractSet {
   private final Object element1;
   private final Object element2;

   SmallSet() {
      super();
      this.element1 = null;
      this.element2 = null;
   }

   SmallSet(Object var1) {
      super();
      this.element1 = var1;
      this.element2 = null;
   }

   private SmallSet(Object var1, Object var2) {
      super();
      this.element1 = var1;
      this.element2 = var2;
   }

   public Iterator iterator() {
      return new SmallSet$IteratorImpl(this.element1, this.element2);
   }

   public int size() {
      if (this.element1 == null) {
         return 0;
      } else {
         return this.element2 == null ? 1 : 2;
      }
   }

   Set union(SmallSet var1) {
      if ((var1.element1 != this.element1 || var1.element2 != this.element2) && (var1.element1 != this.element2 || var1.element2 != this.element1)) {
         if (var1.element1 == null) {
            return this;
         } else if (this.element1 == null) {
            return var1;
         } else {
            if (var1.element2 == null) {
               if (this.element2 == null) {
                  return new SmallSet(this.element1, var1.element1);
               }

               if (var1.element1 == this.element1 || var1.element1 == this.element2) {
                  return this;
               }
            }

            if (this.element2 != null || this.element1 != var1.element1 && this.element1 != var1.element2) {
               HashSet var2 = new HashSet(4);
               var2.add(this.element1);
               if (this.element2 != null) {
                  var2.add(this.element2);
               }

               var2.add(var1.element1);
               if (var1.element2 != null) {
                  var2.add(var1.element2);
               }

               return var2;
            } else {
               return var1;
            }
         }
      } else {
         return this;
      }
   }
}
