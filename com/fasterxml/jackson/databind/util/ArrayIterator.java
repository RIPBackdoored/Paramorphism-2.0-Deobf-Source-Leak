package com.fasterxml.jackson.databind.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator implements Iterator, Iterable {
   private final Object[] _a;
   private int _index;

   public ArrayIterator(Object[] var1) {
      super();
      this._a = var1;
      this._index = 0;
   }

   public boolean hasNext() {
      return this._index < this._a.length;
   }

   public Object next() {
      if (this._index >= this._a.length) {
         throw new NoSuchElementException();
      } else {
         return this._a[this._index++];
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public Iterator iterator() {
      return this;
   }
}
