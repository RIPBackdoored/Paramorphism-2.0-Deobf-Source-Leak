package com.fasterxml.jackson.core.io;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class UTF32Reader extends Reader {
   protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
   protected static final char NC = '\u0000';
   protected final IOContext _context;
   protected InputStream _in;
   protected byte[] _buffer;
   protected int _ptr;
   protected int _length;
   protected final boolean _bigEndian;
   protected char _surrogate = 0;
   protected int _charCount;
   protected int _byteCount;
   protected final boolean _managedBuffers;
   protected char[] _tmpBuf;

   public UTF32Reader(IOContext var1, InputStream var2, byte[] var3, int var4, int var5, boolean var6) {
      super();
      this._context = var1;
      this._in = var2;
      this._buffer = var3;
      this._ptr = var4;
      this._length = var5;
      this._bigEndian = var6;
      this._managedBuffers = var2 != null;
   }

   public void close() throws IOException {
      InputStream var1 = this._in;
      if (var1 != null) {
         this._in = null;
         this.freeBuffers();
         var1.close();
      }

   }

   public int read() throws IOException {
      if (this._tmpBuf == null) {
         this._tmpBuf = new char[1];
      }

      return this.read(this._tmpBuf, 0, 1) < 1 ? -1 : this._tmpBuf[0];
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if (this._buffer == null) {
         return -1;
      } else if (var3 < 1) {
         return var3;
      } else {
         if (var2 < 0 || var2 + var3 > var1.length) {
            this.reportBounds(var1, var2, var3);
         }

         int var4 = var2;
         int var5 = var3 + var2;
         int var6;
         if (this._surrogate != 0) {
            var4 = var2 + 1;
            var1[var2] = this._surrogate;
            this._surrogate = 0;
         } else {
            var6 = this._length - this._ptr;
            if (var6 < 4 && !this.loadMore(var6)) {
               if (var6 == 0) {
                  return -1;
               }

               this.reportUnexpectedEOF(this._length - this._ptr, 4);
            }
         }

         var6 = this._length - 4;

         int var7;
         while(var4 < var5) {
            var7 = this._ptr;
            int var8;
            int var9;
            if (this._bigEndian) {
               var8 = this._buffer[var7] << 8 | this._buffer[var7 + 1] & 255;
               var9 = (this._buffer[var7 + 2] & 255) << 8 | this._buffer[var7 + 3] & 255;
            } else {
               var9 = this._buffer[var7] & 255 | (this._buffer[var7 + 1] & 255) << 8;
               var8 = this._buffer[var7 + 2] & 255 | this._buffer[var7 + 3] << 8;
            }

            this._ptr += 4;
            if (var8 != 0) {
               var8 &= 65535;
               int var10 = var8 - 1 << 16 | var9;
               if (var8 > 16) {
                  this.reportInvalid(var10, var4 - var2, String.format(" (above 0x%08x)", 1114111));
               }

               var1[var4++] = (char)('\ud800' + (var10 >> 10));
               var9 = '\udc00' | var10 & 1023;
               if (var4 >= var5) {
                  this._surrogate = (char)var10;
                  break;
               }
            }

            var1[var4++] = (char)var9;
            if (this._ptr > var6) {
               break;
            }
         }

         var7 = var4 - var2;
         this._charCount += var7;
         return var7;
      }
   }

   private void reportUnexpectedEOF(int var1, int var2) throws IOException {
      int var3 = this._byteCount + var1;
      int var4 = this._charCount;
      throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + var1 + ", needed " + var2 + ", at char #" + var4 + ", byte #" + var3 + ")");
   }

   private void reportInvalid(int var1, int var2, String var3) throws IOException {
      int var4 = this._byteCount + this._ptr - 1;
      int var5 = this._charCount + var2;
      throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString(var1) + var3 + " at char #" + var5 + ", byte #" + var4 + ")");
   }

   private boolean loadMore(int var1) throws IOException {
      this._byteCount += this._length - var1;
      int var2;
      if (var1 > 0) {
         if (this._ptr > 0) {
            System.arraycopy(this._buffer, this._ptr, this._buffer, 0, var1);
            this._ptr = 0;
         }

         this._length = var1;
      } else {
         this._ptr = 0;
         var2 = this._in == null ? -1 : this._in.read(this._buffer);
         if (var2 < 1) {
            this._length = 0;
            if (var2 < 0) {
               if (this._managedBuffers) {
                  this.freeBuffers();
               }

               return false;
            }

            this.reportStrangeStream();
         }

         this._length = var2;
      }

      for(; this._length < 4; this._length += var2) {
         var2 = this._in == null ? -1 : this._in.read(this._buffer, this._length, this._buffer.length - this._length);
         if (var2 < 1) {
            if (var2 < 0) {
               if (this._managedBuffers) {
                  this.freeBuffers();
               }

               this.reportUnexpectedEOF(this._length, 4);
            }

            this.reportStrangeStream();
         }
      }

      return true;
   }

   private void freeBuffers() {
      byte[] var1 = this._buffer;
      if (var1 != null) {
         this._buffer = null;
         this._context.releaseReadIOBuffer(var1);
      }

   }

   private void reportBounds(char[] var1, int var2, int var3) throws IOException {
      throw new ArrayIndexOutOfBoundsException("read(buf," + var2 + "," + var3 + "), cbuf[" + var1.length + "]");
   }

   private void reportStrangeStream() throws IOException {
      throw new IOException("Strange I/O stream, returned 0 bytes on read");
   }
}
