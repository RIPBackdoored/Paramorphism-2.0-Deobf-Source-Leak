package com.fasterxml.jackson.core.util;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;

public final class ByteArrayBuilder extends OutputStream {
   public static final byte[] NO_BYTES = new byte[0];
   private static final int INITIAL_BLOCK_SIZE = 500;
   private static final int MAX_BLOCK_SIZE = 262144;
   static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
   private final BufferRecycler _bufferRecycler;
   private final LinkedList _pastBlocks;
   private int _pastLen;
   private byte[] _currBlock;
   private int _currBlockPtr;

   public ByteArrayBuilder() {
      this((BufferRecycler)null);
   }

   public ByteArrayBuilder(BufferRecycler var1) {
      this(var1, 500);
   }

   public ByteArrayBuilder(int var1) {
      this((BufferRecycler)null, var1);
   }

   public ByteArrayBuilder(BufferRecycler var1, int var2) {
      super();
      this._pastBlocks = new LinkedList();
      this._bufferRecycler = var1;
      this._currBlock = var1 == null ? new byte[var2] : var1.allocByteBuffer(2);
   }

   public void reset() {
      this._pastLen = 0;
      this._currBlockPtr = 0;
      if (!this._pastBlocks.isEmpty()) {
         this._pastBlocks.clear();
      }

   }

   public int size() {
      return this._pastLen + this._currBlockPtr;
   }

   public void release() {
      this.reset();
      if (this._bufferRecycler != null && this._currBlock != null) {
         this._bufferRecycler.releaseByteBuffer(2, this._currBlock);
         this._currBlock = null;
      }

   }

   public void append(int var1) {
      if (this._currBlockPtr >= this._currBlock.length) {
         this._allocMore();
      }

      this._currBlock[this._currBlockPtr++] = (byte)var1;
   }

   public void appendTwoBytes(int var1) {
      if (this._currBlockPtr + 1 < this._currBlock.length) {
         this._currBlock[this._currBlockPtr++] = (byte)(var1 >> 8);
         this._currBlock[this._currBlockPtr++] = (byte)var1;
      } else {
         this.append(var1 >> 8);
         this.append(var1);
      }

   }

   public void appendThreeBytes(int var1) {
      if (this._currBlockPtr + 2 < this._currBlock.length) {
         this._currBlock[this._currBlockPtr++] = (byte)(var1 >> 16);
         this._currBlock[this._currBlockPtr++] = (byte)(var1 >> 8);
         this._currBlock[this._currBlockPtr++] = (byte)var1;
      } else {
         this.append(var1 >> 16);
         this.append(var1 >> 8);
         this.append(var1);
      }

   }

   public void appendFourBytes(int var1) {
      if (this._currBlockPtr + 3 < this._currBlock.length) {
         this._currBlock[this._currBlockPtr++] = (byte)(var1 >> 24);
         this._currBlock[this._currBlockPtr++] = (byte)(var1 >> 16);
         this._currBlock[this._currBlockPtr++] = (byte)(var1 >> 8);
         this._currBlock[this._currBlockPtr++] = (byte)var1;
      } else {
         this.append(var1 >> 24);
         this.append(var1 >> 16);
         this.append(var1 >> 8);
         this.append(var1);
      }

   }

   public byte[] toByteArray() {
      int var1 = this._pastLen + this._currBlockPtr;
      if (var1 == 0) {
         return NO_BYTES;
      } else {
         byte[] var2 = new byte[var1];
         int var3 = 0;

         int var6;
         for(Iterator var4 = this._pastBlocks.iterator(); var4.hasNext(); var3 += var6) {
            byte[] var5 = (byte[])var4.next();
            var6 = var5.length;
            System.arraycopy(var5, 0, var2, var3, var6);
         }

         System.arraycopy(this._currBlock, 0, var2, var3, this._currBlockPtr);
         var3 += this._currBlockPtr;
         if (var3 != var1) {
            throw new RuntimeException("Internal error: total len assumed to be " + var1 + ", copied " + var3 + " bytes");
         } else {
            if (!this._pastBlocks.isEmpty()) {
               this.reset();
            }

            return var2;
         }
      }
   }

   public byte[] resetAndGetFirstSegment() {
      this.reset();
      return this._currBlock;
   }

   public byte[] finishCurrentSegment() {
      this._allocMore();
      return this._currBlock;
   }

   public byte[] completeAndCoalesce(int var1) {
      this._currBlockPtr = var1;
      return this.toByteArray();
   }

   public byte[] getCurrentSegment() {
      return this._currBlock;
   }

   public void setCurrentSegmentLength(int var1) {
      this._currBlockPtr = var1;
   }

   public int getCurrentSegmentLength() {
      return this._currBlockPtr;
   }

   public void write(byte[] var1) {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) {
      while(true) {
         int var4 = this._currBlock.length - this._currBlockPtr;
         int var5 = Math.min(var4, var3);
         if (var5 > 0) {
            System.arraycopy(var1, var2, this._currBlock, this._currBlockPtr, var5);
            var2 += var5;
            this._currBlockPtr += var5;
            var3 -= var5;
         }

         if (var3 <= 0) {
            return;
         }

         this._allocMore();
      }
   }

   public void write(int var1) {
      this.append(var1);
   }

   public void close() {
   }

   public void flush() {
   }

   private void _allocMore() {
      int var1 = this._pastLen + this._currBlock.length;
      if (var1 < 0) {
         throw new IllegalStateException("Maximum Java array size (2GB) exceeded by `ByteArrayBuilder`");
      } else {
         this._pastLen = var1;
         int var2 = Math.max(this._pastLen >> 1, 1000);
         if (var2 > 262144) {
            var2 = 262144;
         }

         this._pastBlocks.add(this._currBlock);
         this._currBlock = new byte[var2];
         this._currBlockPtr = 0;
      }
   }
}
