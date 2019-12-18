package org.eclipse.aether.graph;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

class Dependency$Exclusions extends AbstractSet {
   private final Exclusion[] exclusions;

   public static Set copy(Collection var0) {
      return (Set)(var0 != null && !var0.isEmpty() ? new Dependency$Exclusions(var0) : Collections.emptySet());
   }

   private Dependency$Exclusions(Collection var1) {
      super();
      if (((Collection)var1).size() > 1 && !(var1 instanceof Set)) {
         var1 = new LinkedHashSet((Collection)var1);
      }

      this.exclusions = (Exclusion[])((Collection)var1).toArray(new Exclusion[((Collection)var1).size()]);
   }

   public Iterator iterator() {
      return new Dependency$Exclusions$1(this);
   }

   public int size() {
      return this.exclusions.length;
   }

   static Exclusion[] access$000(Dependency$Exclusions var0) {
      return var0.exclusions;
   }
}
