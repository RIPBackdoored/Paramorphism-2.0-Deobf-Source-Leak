package org.jline.reader;

import java.util.Iterator;
import java.util.ListIterator;

class History$1 implements Iterator {
   private final ListIterator it;
   final int val$index;
   final History this$0;

   History$1(History var1, int var2) {
      super();
      this.this$0 = var1;
      this.val$index = var2;
      this.it = this.this$0.iterator(this.val$index + 1);
   }

   public boolean hasNext() {
      return this.it.hasPrevious();
   }

   public History$Entry next() {
      return (History$Entry)this.it.previous();
   }

   public Object next() {
      return this.next();
   }
}
