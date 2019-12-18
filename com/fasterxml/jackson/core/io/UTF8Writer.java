package com.fasterxml.jackson.core.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public final class UTF8Writer extends Writer {
   static final int SURR1_FIRST = 55296;
   static final int SURR1_LAST = 56319;
   static final int SURR2_FIRST = 56320;
   static final int SURR2_LAST = 57343;
   private final IOContext _context;
   private OutputStream _out;
   private byte[] _outBuffer;
   private final int _outBufferEnd;
   private int _outPtr;
   private int _surrogate;

   public UTF8Writer(IOContext var1, OutputStream var2) {
      super();
      this._context = var1;
      this._out = var2;
      this._outBuffer = var1.allocWriteEncodingBuffer();
      this._outBufferEnd = this._outBuffer.length - 4;
      this._outPtr = 0;
   }

   public Writer append(char var1) throws IOException {
      this.write(var1);
      return this;
   }

   public void close() throws IOException {
      if (this._out != null) {
         if (this._outPtr > 0) {
            this._out.write(this._outBuffer, 0, this._outPtr);
            this._outPtr = 0;
         }

         OutputStream var1 = this._out;
         this._out = null;
         byte[] var2 = this._outBuffer;
         if (var2 != null) {
            this._outBuffer = null;
            this._context.releaseWriteEncodingBuffer(var2);
         }

         var1.close();
         int var3 = this._surrogate;
         this._surrogate = 0;
         if (var3 > 0) {
            illegalSurrogate(var3);
         }
      }

   }

   public void flush() throws IOException {
      if (this._out != null) {
         if (this._outPtr > 0) {
            this._out.write(this._outBuffer, 0, this._outPtr);
            this._outPtr = 0;
         }

         this._out.flush();
      }

   }

   public void write(char[] var1) throws IOException {
      this.write((char[])var1, 0, var1.length);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      if (var3 < 2) {
         if (var3 == 1) {
            this.write(var1[var2]);
         }

      } else {
         if (this._surrogate > 0) {
            char var4 = var1[var2++];
            --var3;
            this.write(this.convertSurrogate(var4));
         }

         int var10 = this._outPtr;
         byte[] var5 = this._outBuffer;
         int var6 = this._outBufferEnd;
         var3 += var2;

         label70:
         while(var2 < var3) {
            if (var10 >= var6) {
               this._out.write(var5, 0, var10);
               var10 = 0;
            }

            char var7 = var1[var2++];
            if (var7 < 128) {
               var5[var10++] = (byte)var7;
               int var8 = var3 - var2;
               int var9 = var6 - var10;
               if (var8 > var9) {
                  var8 = var9;
               }

               var8 += var2;

               while(true) {
                  if (var2 >= var8) {
                     continue label70;
                  }

                  var7 = var1[var2++];
                  if (var7 >= 128) {
                     break;
                  }

                  var5[var10++] = (byte)var7;
               }
            }

            if (var7 < 2048) {
               var5[var10++] = (byte)(192 | var7 >> 6);
               var5[var10++] = (byte)(128 | var7 & 63);
            } else if (var7 >= '\ud800' && var7 <= '\udfff') {
               if (var7 > '\udbff') {
                  this._outPtr = var10;
                  illegalSurrogate(var7);
               }

               this._surrogate = var7;
               if (var2 >= var3) {
                  break;
               }

               int var11 = this.convertSurrogate(var1[var2++]);
               if (var11 > 1114111) {
                  this._outPtr = var10;
                  illegalSurrogate(var11);
               }

               var5[var10++] = (byte)(240 | var11 >> 18);
               var5[var10++] = (byte)(128 | var11 >> 12 & 63);
               var5[var10++] = (byte)(128 | var11 >> 6 & 63);
               var5[var10++] = (byte)(128 | var11 & 63);
            } else {
               var5[var10++] = (byte)(224 | var7 >> 12);
               var5[var10++] = (byte)(128 | var7 >> 6 & 63);
               var5[var10++] = (byte)(128 | var7 & 63);
            }
         }

         this._outPtr = var10;
      }
   }

   public void write(int var1) throws IOException {
      if (this._surrogate > 0) {
         var1 = this.convertSurrogate(var1);
      } else if (var1 >= 55296 && var1 <= 57343) {
         if (var1 > 56319) {
            illegalSurrogate(var1);
         }

         this._surrogate = var1;
         return;
      }

      if (this._outPtr >= this._outBufferEnd) {
         this._out.write(this._outBuffer, 0, this._outPtr);
         this._outPtr = 0;
      }

      if (var1 < 128) {
         this._outBuffer[this._outPtr++] = (byte)var1;
      } else {
         int var2 = this._outPtr;
         if (var1 < 2048) {
            this._outBuffer[var2++] = (byte)(192 | var1 >> 6);
            this._outBuffer[var2++] = (byte)(128 | var1 & 63);
         } else if (var1 <= 65535) {
            this._outBuffer[var2++] = (byte)(224 | var1 >> 12);
            this._outBuffer[var2++] = (byte)(128 | var1 >> 6 & 63);
            this._outBuffer[var2++] = (byte)(128 | var1 & 63);
         } else {
            if (var1 > 1114111) {
               illegalSurrogate(var1);
            }

            this._outBuffer[var2++] = (byte)(240 | var1 >> 18);
            this._outBuffer[var2++] = (byte)(128 | var1 >> 12 & 63);
            this._outBuffer[var2++] = (byte)(128 | var1 >> 6 & 63);
            this._outBuffer[var2++] = (byte)(128 | var1 & 63);
         }

         this._outPtr = var2;
      }

   }

   public void write(String var1) throws IOException {
      this.write((String)var1, 0, var1.length());
   }

   public void write(String var1, int var2, int var3) throws IOException {
      if (var3 < 2) {
         if (var3 == 1) {
            this.write(var1.charAt(var2));
         }

      } else {
         if (this._surrogate > 0) {
            char var4 = var1.charAt(var2++);
            --var3;
            this.write(this.convertSurrogate(var4));
         }

         int var10 = this._outPtr;
         byte[] var5 = this._outBuffer;
         int var6 = this._outBufferEnd;
         var3 += var2;

         label70:
         while(var2 < var3) {
            if (var10 >= var6) {
               this._out.write(var5, 0, var10);
               var10 = 0;
            }

            char var7 = var1.charAt(var2++);
            if (var7 < 128) {
               var5[var10++] = (byte)var7;
               int var8 = var3 - var2;
               int var9 = var6 - var10;
               if (var8 > var9) {
                  var8 = var9;
               }

               var8 += var2;

               while(true) {
                  if (var2 >= var8) {
                     continue label70;
                  }

                  var7 = var1.charAt(var2++);
                  if (var7 >= 128) {
                     break;
                  }

                  var5[var10++] = (byte)var7;
               }
            }

            if (var7 < 2048) {
               var5[var10++] = (byte)(192 | var7 >> 6);
               var5[var10++] = (byte)(128 | var7 & 63);
            } else if (var7 >= '\ud800' && var7 <= '\udfff') {
               if (var7 > '\udbff') {
                  this._outPtr = var10;
                  illegalSurrogate(var7);
               }

               this._surrogate = var7;
               if (var2 >= var3) {
                  break;
               }

               int var11 = this.convertSurrogate(var1.charAt(var2++));
               if (var11 > 1114111) {
                  this._outPtr = var10;
                  illegalSurrogate(var11);
               }

               var5[var10++] = (byte)(240 | var11 >> 18);
               var5[var10++] = (byte)(128 | var11 >> 12 & 63);
               var5[var10++] = (byte)(128 | var11 >> 6 & 63);
               var5[var10++] = (byte)(128 | var11 & 63);
            } else {
               var5[var10++] = (byte)(224 | var7 >> 12);
               var5[var10++] = (byte)(128 | var7 >> 6 & 63);
               var5[var10++] = (byte)(128 | var7 & 63);
            }
         }

         this._outPtr = var10;
      }
   }

   protected int convertSurrogate(int var1) throws IOException {
      int var2 = this._surrogate;
      this._surrogate = 0;
      if (var1 >= 56320 && var1 <= 57343) {
         return 65536 + (var2 - '\ud800' << 10) + (var1 - '\udc00');
      } else {
         throw new IOException("Broken surrogate pair: first char 0x" + Integer.toHexString(var2) + ", second 0x" + Integer.toHexString(var1) + "; illegal combination");
      }
   }

   protected static void illegalSurrogate(int var0) throws IOException {
      throw new IOException(illegalSurrogateDesc(var0));
   }

   protected static String illegalSurrogateDesc(int var0) {
      if (var0 > 1114111) {
         return "Illegal character point (0x" + Integer.toHexString(var0) + ") to output; max is 0x10FFFF as per RFC 4627";
      } else if (var0 >= 55296) {
         return var0 <= 56319 ? "Unmatched first part of surrogate pair (0x" + Integer.toHexString(var0) + ")" : "Unmatched second part of surrogate pair (0x" + Integer.toHexString(var0) + ")";
      } else {
         return "Illegal character point (0x" + Integer.toHexString(var0) + ") to output";
      }
   }

   public Appendable append(char var1) throws IOException {
      return this.append(var1);
   }
}
