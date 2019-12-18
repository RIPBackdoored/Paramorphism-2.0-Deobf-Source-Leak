package org.objectweb.asm.tree.analysis;

import java.util.Iterator;
import java.util.NoSuchElementException;

class SmallSet$IteratorImpl implements Iterator {
   private Object firstElement;
   private Object secondElement;

   SmallSet$IteratorImpl(Object var1, Object var2) {
      super();
      this.firstElement = var1;
      this.secondElement = var2;
   }

   public boolean hasNext() {
      return this.firstElement != null;
   }

   public Object next() {
      if (this.firstElement == null) {
         throw new NoSuchElementException();
      } else {
         Object var1 = this.firstElement;
         this.firstElement = this.secondElement;
         this.secondElement = null;
         return var1;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
