package com.fasterxml.jackson.core.io;

import java.io.IOException;
import java.io.InputStream;

public final class MergedStream extends InputStream {
   private final IOContext _ctxt;
   private final InputStream _in;
   private byte[] _b;
   private int _ptr;
   private final int _end;

   public MergedStream(IOContext var1, InputStream var2, byte[] var3, int var4, int var5) {
      super();
      this._ctxt = var1;
      this._in = var2;
      this._b = var3;
      this._ptr = var4;
      this._end = var5;
   }

   public int available() throws IOException {
      return this._b != null ? this._end - this._ptr : this._in.available();
   }

   public void close() throws IOException {
      this._free();
      this._in.close();
   }

   public void mark(int var1) {
      if (this._b == null) {
         this._in.mark(var1);
      }

   }

   public boolean markSupported() {
      return this._b == null && this._in.markSupported();
   }

   public int read() throws IOException {
      if (this._b != null) {
         int var1 = this._b[this._ptr++] & 255;
         if (this._ptr >= this._end) {
            this._free();
         }

         return var1;
      } else {
         return this._in.read();
      }
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (this._b != null) {
         int var4 = this._end - this._ptr;
         if (var3 > var4) {
            var3 = var4;
         }

         System.arraycopy(this._b, this._ptr, var1, var2, var3);
         this._ptr += var3;
         if (this._ptr >= this._end) {
            this._free();
         }

         return var3;
      } else {
         return this._in.read(var1, var2, var3);
      }
   }

   public void reset() throws IOException {
      if (this._b == null) {
         this._in.reset();
      }

   }

   public long skip(long var1) throws IOException {
      long var3 = 0L;
      if (this._b != null) {
         int var5 = this._end - this._ptr;
         if ((long)var5 > var1) {
            this._ptr += (int)var1;
            return var1;
         }

         this._free();
         var3 += (long)var5;
         var1 -= (long)var5;
      }

      if (var1 > 0L) {
         var3 += this._in.skip(var1);
      }

      return var3;
   }

   private void _free() {
      byte[] var1 = this._b;
      if (var1 != null) {
         this._b = null;
         if (this._ctxt != null) {
            this._ctxt.releaseReadIOBuffer(var1);
         }
      }

   }
}
