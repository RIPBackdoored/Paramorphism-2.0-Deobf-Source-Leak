package com.fasterxml.jackson.databind.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ByteBufferBackedInputStream extends InputStream {
   protected final ByteBuffer _b;

   public ByteBufferBackedInputStream(ByteBuffer var1) {
      super();
      this._b = var1;
   }

   public int available() {
      return this._b.remaining();
   }

   public int read() throws IOException {
      return this._b.hasRemaining() ? this._b.get() & 255 : -1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (!this._b.hasRemaining()) {
         return -1;
      } else {
         var3 = Math.min(var3, this._b.remaining());
         this._b.get(var1, var2, var3);
         return var3;
      }
   }
}
