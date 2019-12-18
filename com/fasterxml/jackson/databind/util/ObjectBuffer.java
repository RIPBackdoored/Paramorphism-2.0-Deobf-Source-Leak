package com.fasterxml.jackson.databind.util;

import java.lang.reflect.Array;
import java.util.List;

public final class ObjectBuffer {
   private static final int SMALL_CHUNK = 16384;
   private static final int MAX_CHUNK = 262144;
   private LinkedNode _head;
   private LinkedNode _tail;
   private int _size;
   private Object[] _freeBuffer;

   public ObjectBuffer() {
      super();
   }

   public Object[] resetAndStart() {
      this._reset();
      return this._freeBuffer == null ? (this._freeBuffer = new Object[12]) : this._freeBuffer;
   }

   public Object[] resetAndStart(Object[] var1, int var2) {
      this._reset();
      if (this._freeBuffer == null || this._freeBuffer.length < var2) {
         this._freeBuffer = new Object[Math.max(12, var2)];
      }

      System.arraycopy(var1, 0, this._freeBuffer, 0, var2);
      return this._freeBuffer;
   }

   public Object[] appendCompletedChunk(Object[] var1) {
      LinkedNode var2 = new LinkedNode(var1, (LinkedNode)null);
      if (this._head == null) {
         this._head = this._tail = var2;
      } else {
         this._tail.linkNext(var2);
         this._tail = var2;
      }

      int var3 = var1.length;
      this._size += var3;
      if (var3 < 16384) {
         var3 += var3;
      } else if (var3 < 262144) {
         var3 += var3 >> 2;
      }

      return new Object[var3];
   }

   public Object[] completeAndClearBuffer(Object[] var1, int var2) {
      int var3 = var2 + this._size;
      Object[] var4 = new Object[var3];
      this._copyTo(var4, var3, var1, var2);
      this._reset();
      return var4;
   }

   public Object[] completeAndClearBuffer(Object[] var1, int var2, Class var3) {
      int var4 = var2 + this._size;
      Object[] var5 = (Object[])((Object[])Array.newInstance(var3, var4));
      this._copyTo(var5, var4, var1, var2);
      this._reset();
      return var5;
   }

   public void completeAndClearBuffer(Object[] var1, int var2, List var3) {
      for(LinkedNode var4 = this._head; var4 != null; var4 = var4.next()) {
         Object[] var5 = (Object[])var4.value();
         int var6 = 0;

         for(int var7 = var5.length; var6 < var7; ++var6) {
            var3.add(var5[var6]);
         }
      }

      for(int var8 = 0; var8 < var2; ++var8) {
         var3.add(var1[var8]);
      }

      this._reset();
   }

   public int initialCapacity() {
      return this._freeBuffer == null ? 0 : this._freeBuffer.length;
   }

   public int bufferedSize() {
      return this._size;
   }

   protected void _reset() {
      if (this._tail != null) {
         this._freeBuffer = (Object[])this._tail.value();
      }

      this._head = this._tail = null;
      this._size = 0;
   }

   protected final void _copyTo(Object var1, int var2, Object[] var3, int var4) {
      int var5 = 0;

      for(LinkedNode var6 = this._head; var6 != null; var6 = var6.next()) {
         Object[] var7 = (Object[])var6.value();
         int var8 = var7.length;
         System.arraycopy(var7, 0, var1, var5, var8);
         var5 += var8;
      }

      System.arraycopy(var3, 0, var1, var5, var4);
      var5 += var4;
      if (var5 != var2) {
         throw new IllegalStateException("Should have gotten " + var2 + " entries, got " + var5);
      }
   }
}
