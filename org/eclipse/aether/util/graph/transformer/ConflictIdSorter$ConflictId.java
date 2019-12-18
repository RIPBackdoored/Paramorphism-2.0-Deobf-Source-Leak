package org.eclipse.aether.util.graph.transformer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

final class ConflictIdSorter$ConflictId {
   final Object key;
   Collection children = Collections.emptySet();
   int inDegree;
   int minDepth;

   ConflictIdSorter$ConflictId(Object var1, int var2) {
      super();
      this.key = var1;
      this.minDepth = var2;
   }

   public void add(ConflictIdSorter$ConflictId var1) {
      if (this.children.isEmpty()) {
         this.children = new HashSet();
      }

      if (this.children.add(var1)) {
         ++var1.inDegree;
      }

   }

   public void pullup(int var1) {
      if (var1 < this.minDepth) {
         this.minDepth = var1++;
         Iterator var2 = this.children.iterator();

         while(var2.hasNext()) {
            ConflictIdSorter$ConflictId var3 = (ConflictIdSorter$ConflictId)var2.next();
            var3.pullup(var1);
         }
      }

   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ConflictIdSorter$ConflictId)) {
         return false;
      } else {
         ConflictIdSorter$ConflictId var2 = (ConflictIdSorter$ConflictId)var1;
         return this.key.equals(var2.key);
      }
   }

   public int hashCode() {
      return this.key.hashCode();
   }

   public String toString() {
      return this.key + " @ " + this.minDepth + " <" + this.inDegree;
   }
}
