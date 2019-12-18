package com.fasterxml.jackson.dataformat.yaml;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.ref.SoftReference;

public final class UTF8Reader extends Reader {
   private static final int DEFAULT_BUFFER_SIZE = 8000;
   protected static final ThreadLocal _bufferRecycler = new ThreadLocal();
   protected final byte[][] _bufferHolder;
   private InputStream _inputSource;
   private final boolean _autoClose;
   protected byte[] _inputBuffer;
   protected int _inputPtr;
   protected int _inputEnd;
   protected int _surrogate = -1;
   int _charCount = 0;
   int _byteCount = 0;
   private char[] _tmpBuffer = null;

   public UTF8Reader(InputStream var1, boolean var2) {
      super(var1 == null ? new Object() : var1);
      this._inputSource = var1;
      this._bufferHolder = _findBufferHolder();
      byte[] var3 = this._bufferHolder[0];
      if (var3 == null) {
         var3 = new byte[8000];
      } else {
         this._bufferHolder[0] = null;
      }

      this._inputBuffer = var3;
      this._inputPtr = 0;
      this._inputEnd = 0;
      this._autoClose = var2;
   }

   public UTF8Reader(byte[] var1, int var2, int var3, boolean var4) {
      super(new Object());
      this._inputSource = null;
      this._inputBuffer = var1;
      this._inputPtr = var2;
      this._inputEnd = var2 + var3;
      this._autoClose = var4;
      this._bufferHolder = (byte[][])null;
   }

   private static byte[][] _findBufferHolder() {
      byte[][] var0 = (byte[][])null;
      SoftReference var1 = (SoftReference)_bufferRecycler.get();
      if (var1 != null) {
         var0 = (byte[][])var1.get();
      }

      if (var0 == null) {
         var0 = new byte[1][];
         _bufferRecycler.set(new SoftReference(var0));
      }

      return var0;
   }

   public void close() throws IOException {
      InputStream var1 = this._inputSource;
      if (var1 != null) {
         this._inputSource = null;
         if (this._autoClose) {
            var1.close();
         }
      }

      this.freeBuffers();
   }

   public int read() throws IOException {
      if (this._tmpBuffer == null) {
         this._tmpBuffer = new char[1];
      }

      return this.read(this._tmpBuffer, 0, 1) < 1 ? -1 : this._tmpBuffer[0];
   }

   public int read(char[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if (this._inputBuffer == null) {
         return -1;
      } else {
         var3 += var2;
         int var4 = var2;
         if (this._surrogate >= 0) {
            var4 = var2 + 1;
            var1[var2] = (char)this._surrogate;
            this._surrogate = -1;
         } else {
            int var5 = this._inputEnd - this._inputPtr;
            if (var5 < 4 && (var5 < 1 || this._inputBuffer[this._inputPtr] < 0) && !this.loadMore(var5)) {
               return -1;
            }
         }

         byte[] var12 = this._inputBuffer;
         int var6 = this._inputPtr;
         int var7 = this._inputEnd;

         label81:
         while(var4 < var3) {
            int var8 = var12[var6++];
            if (var8 >= 0) {
               var1[var4++] = (char)var8;
               int var9 = var3 - var4;
               int var10 = var7 - var6;
               int var11 = var6 + (var10 < var9 ? var10 : var9);

               while(true) {
                  if (var6 >= var11) {
                     break label81;
                  }

                  var8 = var12[var6++];
                  if (var8 < 0) {
                     break;
                  }

                  var1[var4++] = (char)var8;
               }
            }

            byte var13;
            if ((var8 & 224) == 192) {
               var8 &= 31;
               var13 = 1;
            } else if ((var8 & 240) == 224) {
               var8 &= 15;
               var13 = 2;
            } else if ((var8 & 248) == 240) {
               var8 &= 15;
               var13 = 3;
            } else {
               this.reportInvalidInitial(var8 & 255, var4 - var2);
               var13 = 1;
            }

            if (var7 - var6 < var13) {
               --var6;
               break;
            }

            byte var14 = var12[var6++];
            if ((var14 & 192) != 128) {
               this.reportInvalidOther(var14 & 255, var4 - var2);
            }

            var8 = var8 << 6 | var14 & 63;
            if (var13 > 1) {
               var14 = var12[var6++];
               if ((var14 & 192) != 128) {
                  this.reportInvalidOther(var14 & 255, var4 - var2);
               }

               var8 = var8 << 6 | var14 & 63;
               if (var13 > 2) {
                  var14 = var12[var6++];
                  if ((var14 & 192) != 128) {
                     this.reportInvalidOther(var14 & 255, var4 - var2);
                  }

                  var8 = var8 << 6 | var14 & 63;
                  var8 -= 65536;
                  var1[var4++] = (char)('\ud800' + (var8 >> 10));
                  var8 = '\udc00' | var8 & 1023;
                  if (var4 >= var3) {
                     this._surrogate = var8;
                     break;
                  }
               }
            }

            var1[var4++] = (char)var8;
            if (var6 >= var7) {
               break;
            }
         }

         this._inputPtr = var6;
         var3 = var4 - var2;
         this._charCount += var3;
         return var3;
      }
   }

   protected final InputStream getStream() {
      return this._inputSource;
   }

   protected final int readBytes() throws IOException {
      this._inputPtr = 0;
      this._inputEnd = 0;
      if (this._inputSource != null) {
         int var1 = this._inputSource.read(this._inputBuffer, 0, this._inputBuffer.length);
         if (var1 > 0) {
            this._inputEnd = var1;
         }

         return var1;
      } else {
         return -1;
      }
   }

   protected final int readBytesAt(int var1) throws IOException {
      if (this._inputSource != null) {
         int var2 = this._inputSource.read(this._inputBuffer, var1, this._inputBuffer.length - var1);
         if (var2 > 0) {
            this._inputEnd += var2;
         }

         return var2;
      } else {
         return -1;
      }
   }

   public final void freeBuffers() {
      if (this._bufferHolder != null) {
         byte[] var1 = this._inputBuffer;
         if (var1 != null) {
            this._inputBuffer = null;
            this._bufferHolder[0] = var1;
         }
      }

   }

   private void reportInvalidInitial(int var1, int var2) throws IOException {
      int var3 = this._byteCount + this._inputPtr - 1;
      int var4 = this._charCount + var2 + 1;
      throw new CharConversionException("Invalid UTF-8 start byte 0x" + Integer.toHexString(var1) + " (at char #" + var4 + ", byte #" + var3 + ")");
   }

   private void reportInvalidOther(int var1, int var2) throws IOException {
      int var3 = this._byteCount + this._inputPtr - 1;
      int var4 = this._charCount + var2;
      throw new CharConversionException("Invalid UTF-8 middle byte 0x" + Integer.toHexString(var1) + " (at char #" + var4 + ", byte #" + var3 + ")");
   }

   private void reportUnexpectedEOF(int var1, int var2) throws IOException {
      int var3 = this._byteCount + var1;
      int var4 = this._charCount;
      throw new CharConversionException("Unexpected EOF in the middle of a multi-byte char: got " + var1 + ", needed " + var2 + ", at char #" + var4 + ", byte #" + var3 + ")");
   }

   private boolean loadMore(int var1) throws IOException {
      this._byteCount += this._inputEnd - var1;
      int var2;
      if (var1 > 0) {
         if (this._inputPtr > 0) {
            if (this._bufferHolder == null) {
               throw new IllegalStateException("Internal error: need to move partially decoded character; buffer not modifiable");
            }

            for(var2 = 0; var2 < var1; ++var2) {
               this._inputBuffer[var2] = this._inputBuffer[this._inputPtr + var2];
            }

            this._inputPtr = 0;
            this._inputEnd = var1;
         }
      } else {
         var2 = this.readBytes();
         if (var2 < 1) {
            this.freeBuffers();
            if (var2 < 0) {
               return false;
            }

            this.reportStrangeStream();
         }
      }

      byte var5 = this._inputBuffer[this._inputPtr];
      if (var5 >= 0) {
         return true;
      } else {
         byte var3;
         if ((var5 & 224) == 192) {
            var3 = 2;
         } else if ((var5 & 240) == 224) {
            var3 = 3;
         } else if ((var5 & 248) == 240) {
            var3 = 4;
         } else {
            this.reportInvalidInitial(var5 & 255, 0);
            var3 = 1;
         }

         while(this._inputPtr + var3 > this._inputEnd) {
            int var4 = this.readBytesAt(this._inputEnd);
            if (var4 < 1) {
               if (var4 < 0) {
                  this.freeBuffers();
                  this.reportUnexpectedEOF(this._inputEnd, var3);
               }

               this.reportStrangeStream();
            }
         }

         return true;
      }
   }

   protected void reportBounds(char[] var1, int var2, int var3) throws IOException {
      throw new ArrayIndexOutOfBoundsException("read(buf," + var2 + "," + var3 + "), cbuf[" + var1.length + "]");
   }

   protected void reportStrangeStream() throws IOException {
      throw new IOException("Strange I/O stream, returned 0 bytes on read");
   }
}
