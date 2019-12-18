package com.fasterxml.jackson.databind.util;

public abstract class PrimitiveArrayBuilder {
   static final int INITIAL_CHUNK_SIZE = 12;
   static final int SMALL_CHUNK_SIZE = 16384;
   static final int MAX_CHUNK_SIZE = 262144;
   protected Object _freeBuffer;
   protected PrimitiveArrayBuilder$Node _bufferHead;
   protected PrimitiveArrayBuilder$Node _bufferTail;
   protected int _bufferedEntryCount;

   protected PrimitiveArrayBuilder() {
      super();
   }

   public int bufferedSize() {
      return this._bufferedEntryCount;
   }

   public Object resetAndStart() {
      this._reset();
      return this._freeBuffer == null ? this._constructArray(12) : this._freeBuffer;
   }

   public final Object appendCompletedChunk(Object var1, int var2) {
      PrimitiveArrayBuilder$Node var3 = new PrimitiveArrayBuilder$Node(var1, var2);
      if (this._bufferHead == null) {
         this._bufferHead = this._bufferTail = var3;
      } else {
         this._bufferTail.linkNext(var3);
         this._bufferTail = var3;
      }

      this._bufferedEntryCount += var2;
      int var4;
      if (var2 < 16384) {
         var4 = var2 + var2;
      } else {
         var4 = var2 + (var2 >> 2);
      }

      return this._constructArray(var4);
   }

   public Object completeAndClearBuffer(Object var1, int var2) {
      int var3 = var2 + this._bufferedEntryCount;
      Object var4 = this._constructArray(var3);
      int var5 = 0;

      for(PrimitiveArrayBuilder$Node var6 = this._bufferHead; var6 != null; var6 = var6.next()) {
         var5 = var6.copyData(var4, var5);
      }

      System.arraycopy(var1, 0, var4, var5, var2);
      var5 += var2;
      if (var5 != var3) {
         throw new IllegalStateException("Should have gotten " + var3 + " entries, got " + var5);
      } else {
         return var4;
      }
   }

   protected abstract Object _constructArray(int var1);

   protected void _reset() {
      if (this._bufferTail != null) {
         this._freeBuffer = this._bufferTail.getData();
      }

      this._bufferHead = this._bufferTail = null;
      this._bufferedEntryCount = 0;
   }
}
