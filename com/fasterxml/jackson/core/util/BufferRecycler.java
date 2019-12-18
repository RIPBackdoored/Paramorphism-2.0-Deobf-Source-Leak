package com.fasterxml.jackson.core.util;

public class BufferRecycler {
   public static final int BYTE_READ_IO_BUFFER = 0;
   public static final int BYTE_WRITE_ENCODING_BUFFER = 1;
   public static final int BYTE_WRITE_CONCAT_BUFFER = 2;
   public static final int BYTE_BASE64_CODEC_BUFFER = 3;
   public static final int CHAR_TOKEN_BUFFER = 0;
   public static final int CHAR_CONCAT_BUFFER = 1;
   public static final int CHAR_TEXT_BUFFER = 2;
   public static final int CHAR_NAME_COPY_BUFFER = 3;
   private static final int[] BYTE_BUFFER_LENGTHS = new int[]{8000, 8000, 2000, 2000};
   private static final int[] CHAR_BUFFER_LENGTHS = new int[]{4000, 4000, 200, 200};
   protected final byte[][] _byteBuffers;
   protected final char[][] _charBuffers;

   public BufferRecycler() {
      this(4, 4);
   }

   protected BufferRecycler(int var1, int var2) {
      super();
      this._byteBuffers = new byte[var1][];
      this._charBuffers = new char[var2][];
   }

   public final byte[] allocByteBuffer(int var1) {
      return this.allocByteBuffer(var1, 0);
   }

   public byte[] allocByteBuffer(int var1, int var2) {
      int var3 = this.byteBufferLength(var1);
      if (var2 < var3) {
         var2 = var3;
      }

      byte[] var4 = this._byteBuffers[var1];
      if (var4 != null && var4.length >= var2) {
         this._byteBuffers[var1] = null;
      } else {
         var4 = this.balloc(var2);
      }

      return var4;
   }

   public void releaseByteBuffer(int var1, byte[] var2) {
      this._byteBuffers[var1] = var2;
   }

   public final char[] allocCharBuffer(int var1) {
      return this.allocCharBuffer(var1, 0);
   }

   public char[] allocCharBuffer(int var1, int var2) {
      int var3 = this.charBufferLength(var1);
      if (var2 < var3) {
         var2 = var3;
      }

      char[] var4 = this._charBuffers[var1];
      if (var4 != null && var4.length >= var2) {
         this._charBuffers[var1] = null;
      } else {
         var4 = this.calloc(var2);
      }

      return var4;
   }

   public void releaseCharBuffer(int var1, char[] var2) {
      this._charBuffers[var1] = var2;
   }

   protected int byteBufferLength(int var1) {
      return BYTE_BUFFER_LENGTHS[var1];
   }

   protected int charBufferLength(int var1) {
      return CHAR_BUFFER_LENGTHS[var1];
   }

   protected byte[] balloc(int var1) {
      return new byte[var1];
   }

   protected char[] calloc(int var1) {
      return new char[var1];
   }
}
