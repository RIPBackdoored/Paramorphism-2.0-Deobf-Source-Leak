package com.fasterxml.jackson.databind.util;

final class PrimitiveArrayBuilder$Node {
   final Object _data;
   final int _dataLength;
   PrimitiveArrayBuilder$Node _next;

   public PrimitiveArrayBuilder$Node(Object var1, int var2) {
      super();
      this._data = var1;
      this._dataLength = var2;
   }

   public Object getData() {
      return this._data;
   }

   public int copyData(Object var1, int var2) {
      System.arraycopy(this._data, 0, var1, var2, this._dataLength);
      var2 += this._dataLength;
      return var2;
   }

   public PrimitiveArrayBuilder$Node next() {
      return this._next;
   }

   public void linkNext(PrimitiveArrayBuilder$Node var1) {
      if (this._next != null) {
         throw new IllegalStateException();
      } else {
         this._next = var1;
      }
   }
}
