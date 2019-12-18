package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.BufferRecyclers;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;

public final class JsonStringEncoder {
   private static final char[] HC = CharTypes.copyHexChars();
   private static final byte[] HB = CharTypes.copyHexBytes();
   private static final int SURR1_FIRST = 55296;
   private static final int SURR1_LAST = 56319;
   private static final int SURR2_FIRST = 56320;
   private static final int SURR2_LAST = 57343;
   protected TextBuffer _text;
   protected ByteArrayBuilder _bytes;
   protected final char[] _qbuf = new char[6];

   public JsonStringEncoder() {
      super();
      this._qbuf[0] = '\\';
      this._qbuf[2] = '0';
      this._qbuf[3] = '0';
   }

   /** @deprecated */
   @Deprecated
   public static JsonStringEncoder getInstance() {
      return BufferRecyclers.getJsonStringEncoder();
   }

   public char[] quoteAsString(String var1) {
      TextBuffer var2 = this._text;
      if (var2 == null) {
         this._text = var2 = new TextBuffer((BufferRecycler)null);
      }

      char[] var3 = var2.emptyAndGetCurrentSegment();
      int[] var4 = CharTypes.get7BitOutputEscapes();
      int var5 = var4.length;
      int var6 = 0;
      int var7 = var1.length();
      int var8 = 0;

      label45:
      while(var6 < var7) {
         while(true) {
            char var9 = var1.charAt(var6);
            if (var9 < var5 && var4[var9] != 0) {
               var9 = var1.charAt(var6++);
               int var10 = var4[var9];
               int var11 = var10 < 0 ? this._appendNumeric(var9, this._qbuf) : this._appendNamed(var10, this._qbuf);
               if (var8 + var11 > var3.length) {
                  int var12 = var3.length - var8;
                  if (var12 > 0) {
                     System.arraycopy(this._qbuf, 0, var3, var8, var12);
                  }

                  var3 = var2.finishCurrentSegment();
                  int var13 = var11 - var12;
                  System.arraycopy(this._qbuf, var12, var3, 0, var13);
                  var8 = var13;
               } else {
                  System.arraycopy(this._qbuf, 0, var3, var8, var11);
                  var8 += var11;
               }
               break;
            }

            if (var8 >= var3.length) {
               var3 = var2.finishCurrentSegment();
               var8 = 0;
            }

            var3[var8++] = var9;
            ++var6;
            if (var6 >= var7) {
               break label45;
            }
         }
      }

      var2.setCurrentLength(var8);
      return var2.contentsAsArray();
   }

   public void quoteAsString(CharSequence var1, StringBuilder var2) {
      int[] var3 = CharTypes.get7BitOutputEscapes();
      int var4 = var3.length;
      int var5 = 0;
      int var6 = var1.length();

      while(var5 < var6) {
         while(true) {
            char var7 = var1.charAt(var5);
            if (var7 < var4 && var3[var7] != 0) {
               var7 = var1.charAt(var5++);
               int var8 = var3[var7];
               int var9 = var8 < 0 ? this._appendNumeric(var7, this._qbuf) : this._appendNamed(var8, this._qbuf);
               var2.append(this._qbuf, 0, var9);
            } else {
               var2.append(var7);
               ++var5;
               if (var5 >= var6) {
                  return;
               }
            }
         }
      }

   }

   public byte[] quoteAsUTF8(String var1) {
      ByteArrayBuilder var2 = this._bytes;
      if (var2 == null) {
         this._bytes = var2 = new ByteArrayBuilder((BufferRecycler)null);
      }

      int var3 = 0;
      int var4 = var1.length();
      int var5 = 0;
      byte[] var6 = var2.resetAndGetFirstSegment();

      label83:
      while(var3 < var4) {
         int[] var7 = CharTypes.get7BitOutputEscapes();

         do {
            char var8 = var1.charAt(var3);
            if (var8 > 127 || var7[var8] != 0) {
               if (var5 >= var6.length) {
                  var6 = var2.finishCurrentSegment();
                  var5 = 0;
               }

               var8 = var1.charAt(var3++);
               if (var8 <= 127) {
                  int var9 = var7[var8];
                  var5 = this._appendByte(var8, var9, var2, var5);
                  var6 = var2.getCurrentSegment();
               } else {
                  int var10;
                  if (var8 <= 2047) {
                     var6[var5++] = (byte)(192 | var8 >> 6);
                     var10 = 128 | var8 & 63;
                  } else if (var8 >= '\ud800' && var8 <= '\udfff') {
                     if (var8 > '\udbff') {
                        _illegal(var8);
                     }

                     if (var3 >= var4) {
                        _illegal(var8);
                     }

                     var10 = _convert(var8, var1.charAt(var3++));
                     if (var10 > 1114111) {
                        _illegal(var10);
                     }

                     var6[var5++] = (byte)(240 | var10 >> 18);
                     if (var5 >= var6.length) {
                        var6 = var2.finishCurrentSegment();
                        var5 = 0;
                     }

                     var6[var5++] = (byte)(128 | var10 >> 12 & 63);
                     if (var5 >= var6.length) {
                        var6 = var2.finishCurrentSegment();
                        var5 = 0;
                     }

                     var6[var5++] = (byte)(128 | var10 >> 6 & 63);
                     var10 = 128 | var10 & 63;
                  } else {
                     var6[var5++] = (byte)(224 | var8 >> 12);
                     if (var5 >= var6.length) {
                        var6 = var2.finishCurrentSegment();
                        var5 = 0;
                     }

                     var6[var5++] = (byte)(128 | var8 >> 6 & 63);
                     var10 = 128 | var8 & 63;
                  }

                  if (var5 >= var6.length) {
                     var6 = var2.finishCurrentSegment();
                     var5 = 0;
                  }

                  var6[var5++] = (byte)var10;
               }
               continue label83;
            }

            if (var5 >= var6.length) {
               var6 = var2.finishCurrentSegment();
               var5 = 0;
            }

            var6[var5++] = (byte)var8;
            ++var3;
         } while(var3 < var4);

         return this._bytes.completeAndCoalesce(var5);
      }

      return this._bytes.completeAndCoalesce(var5);
   }

   public byte[] encodeAsUTF8(String var1) {
      ByteArrayBuilder var2 = this._bytes;
      if (var2 == null) {
         this._bytes = var2 = new ByteArrayBuilder((BufferRecycler)null);
      }

      int var3 = 0;
      int var4 = var1.length();
      int var5 = 0;
      byte[] var6 = var2.resetAndGetFirstSegment();

      int var8;
      for(int var7 = var6.length; var3 < var4; var6[var5++] = (byte)(128 | var8 & 63)) {
         for(var8 = var1.charAt(var3++); var8 <= 127; var8 = var1.charAt(var3++)) {
            if (var5 >= var7) {
               var6 = var2.finishCurrentSegment();
               var7 = var6.length;
               var5 = 0;
            }

            var6[var5++] = (byte)var8;
            if (var3 >= var4) {
               return this._bytes.completeAndCoalesce(var5);
            }
         }

         if (var5 >= var7) {
            var6 = var2.finishCurrentSegment();
            var7 = var6.length;
            var5 = 0;
         }

         if (var8 < 2048) {
            var6[var5++] = (byte)(192 | var8 >> 6);
         } else if (var8 >= 55296 && var8 <= 57343) {
            if (var8 > 56319) {
               _illegal(var8);
            }

            if (var3 >= var4) {
               _illegal(var8);
            }

            var8 = _convert(var8, var1.charAt(var3++));
            if (var8 > 1114111) {
               _illegal(var8);
            }

            var6[var5++] = (byte)(240 | var8 >> 18);
            if (var5 >= var7) {
               var6 = var2.finishCurrentSegment();
               var7 = var6.length;
               var5 = 0;
            }

            var6[var5++] = (byte)(128 | var8 >> 12 & 63);
            if (var5 >= var7) {
               var6 = var2.finishCurrentSegment();
               var7 = var6.length;
               var5 = 0;
            }

            var6[var5++] = (byte)(128 | var8 >> 6 & 63);
         } else {
            var6[var5++] = (byte)(224 | var8 >> 12);
            if (var5 >= var7) {
               var6 = var2.finishCurrentSegment();
               var7 = var6.length;
               var5 = 0;
            }

            var6[var5++] = (byte)(128 | var8 >> 6 & 63);
         }

         if (var5 >= var7) {
            var6 = var2.finishCurrentSegment();
            var7 = var6.length;
            var5 = 0;
         }
      }

      return this._bytes.completeAndCoalesce(var5);
   }

   private int _appendNumeric(int var1, char[] var2) {
      var2[1] = 'u';
      var2[4] = HC[var1 >> 4];
      var2[5] = HC[var1 & 15];
      return 6;
   }

   private int _appendNamed(int var1, char[] var2) {
      var2[1] = (char)var1;
      return 2;
   }

   private int _appendByte(int var1, int var2, ByteArrayBuilder var3, int var4) {
      var3.setCurrentSegmentLength(var4);
      var3.append(92);
      if (var2 < 0) {
         var3.append(117);
         if (var1 > 255) {
            int var5 = var1 >> 8;
            var3.append(HB[var5 >> 4]);
            var3.append(HB[var5 & 15]);
            var1 &= 255;
         } else {
            var3.append(48);
            var3.append(48);
         }

         var3.append(HB[var1 >> 4]);
         var3.append(HB[var1 & 15]);
      } else {
         var3.append((byte)var2);
      }

      return var3.getCurrentSegmentLength();
   }

   private static int _convert(int var0, int var1) {
      if (var1 >= 56320 && var1 <= 57343) {
         return 65536 + (var0 - '\ud800' << 10) + (var1 - '\udc00');
      } else {
         throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(var0) + ", second 0x" + Integer.toHexString(var1) + "; illegal combination");
      }
   }

   private static void _illegal(int var0) {
      throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(var0));
   }
}
