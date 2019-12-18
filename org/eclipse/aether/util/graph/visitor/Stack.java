package org.eclipse.aether.util.graph.visitor;

import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

class Stack extends AbstractList implements RandomAccess {
   private Object[] elements = (Object[])(new Object[96]);
   private int size;

   Stack() {
      super();
   }

   public void push(Object var1) {
      if (this.size >= this.elements.length) {
         Object[] var2 = (Object[])(new Object[this.size + 64]);
         System.arraycopy(this.elements, 0, var2, 0, this.elements.length);
         this.elements = var2;
      }

      this.elements[this.size++] = var1;
   }

   public Object pop() {
      if (this.size <= 0) {
         throw new NoSuchElementException();
      } else {
         return this.elements[--this.size];
      }
   }

   public Object peek() {
      return this.size <= 0 ? null : this.elements[this.size - 1];
   }

   public Object get(int var1) {
      if (var1 >= 0 && var1 < this.size) {
         return this.elements[this.size - var1 - 1];
      } else {
         throw new IndexOutOfBoundsException("Index: " + var1 + ", Size: " + this.size);
      }
   }

   public int size() {
      return this.size;
   }
}
