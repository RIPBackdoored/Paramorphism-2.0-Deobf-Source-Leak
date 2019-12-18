package org.eclipse.aether.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Dependency$Exclusions$1 implements Iterator {
   private int cursor;
   final Dependency$Exclusions this$0;

   Dependency$Exclusions$1(Dependency$Exclusions var1) {
      super();
      this.this$0 = var1;
      this.cursor = 0;
   }

   public boolean hasNext() {
      return this.cursor < Dependency$Exclusions.access$000(this.this$0).length;
   }

   public Exclusion next() {
      Exclusion var10000;
      try {
         Exclusion var1 = Dependency$Exclusions.access$000(this.this$0)[this.cursor];
         ++this.cursor;
         var10000 = var1;
      } catch (IndexOutOfBoundsException var2) {
         throw new NoSuchElementException();
      }

      return var10000;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public Object next() {
      return this.next();
   }
}
