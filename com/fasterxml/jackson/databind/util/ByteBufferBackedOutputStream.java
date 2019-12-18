package com.fasterxml.jackson.databind.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ByteBufferBackedOutputStream extends OutputStream {
   protected final ByteBuffer _b;

   public ByteBufferBackedOutputStream(ByteBuffer var1) {
      super();
      this._b = var1;
   }

   public void write(int var1) throws IOException {
      this._b.put((byte)var1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this._b.put(var1, var2, var3);
   }
}
