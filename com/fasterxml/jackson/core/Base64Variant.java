package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import java.io.Serializable;
import java.util.Arrays;

public final class Base64Variant implements Serializable {
   private static final int INT_SPACE = 32;
   private static final long serialVersionUID = 1L;
   static final char PADDING_CHAR_NONE = '\u0000';
   public static final int BASE64_VALUE_INVALID = -1;
   public static final int BASE64_VALUE_PADDING = -2;
   private final transient int[] _asciiToBase64;
   private final transient char[] _base64ToAsciiC;
   private final transient byte[] _base64ToAsciiB;
   final String _name;
   private final transient boolean _usesPadding;
   private final transient char _paddingChar;
   private final transient int _maxLineLength;

   public Base64Variant(String var1, String var2, boolean var3, char var4, int var5) {
      super();
      this._asciiToBase64 = new int[128];
      this._base64ToAsciiC = new char[64];
      this._base64ToAsciiB = new byte[64];
      this._name = var1;
      this._usesPadding = var3;
      this._paddingChar = var4;
      this._maxLineLength = var5;
      int var6 = var2.length();
      if (var6 != 64) {
         throw new IllegalArgumentException("Base64Alphabet length must be exactly 64 (was " + var6 + ")");
      } else {
         var2.getChars(0, var6, this._base64ToAsciiC, 0);
         Arrays.fill(this._asciiToBase64, -1);

         char var8;
         for(int var7 = 0; var7 < var6; this._asciiToBase64[var8] = var7++) {
            var8 = this._base64ToAsciiC[var7];
            this._base64ToAsciiB[var7] = (byte)var8;
         }

         if (var3) {
            this._asciiToBase64[var4] = -2;
         }

      }
   }

   public Base64Variant(Base64Variant var1, String var2, int var3) {
      this(var1, var2, var1._usesPadding, var1._paddingChar, var3);
   }

   public Base64Variant(Base64Variant var1, String var2, boolean var3, char var4, int var5) {
      super();
      this._asciiToBase64 = new int[128];
      this._base64ToAsciiC = new char[64];
      this._base64ToAsciiB = new byte[64];
      this._name = var2;
      byte[] var6 = var1._base64ToAsciiB;
      System.arraycopy(var6, 0, this._base64ToAsciiB, 0, var6.length);
      char[] var7 = var1._base64ToAsciiC;
      System.arraycopy(var7, 0, this._base64ToAsciiC, 0, var7.length);
      int[] var8 = var1._asciiToBase64;
      System.arraycopy(var8, 0, this._asciiToBase64, 0, var8.length);
      this._usesPadding = var3;
      this._paddingChar = var4;
      this._maxLineLength = var5;
   }

   protected Object readResolve() {
      return Base64Variants.valueOf(this._name);
   }

   public String getName() {
      return this._name;
   }

   public boolean usesPadding() {
      return this._usesPadding;
   }

   public boolean usesPaddingChar(char var1) {
      return var1 == this._paddingChar;
   }

   public boolean usesPaddingChar(int var1) {
      return var1 == this._paddingChar;
   }

   public char getPaddingChar() {
      return this._paddingChar;
   }

   public byte getPaddingByte() {
      return (byte)this._paddingChar;
   }

   public int getMaxLineLength() {
      return this._maxLineLength;
   }

   public int decodeBase64Char(char var1) {
      return var1 <= 127 ? this._asciiToBase64[var1] : -1;
   }

   public int decodeBase64Char(int var1) {
      return var1 <= 127 ? this._asciiToBase64[var1] : -1;
   }

   public int decodeBase64Byte(byte var1) {
      return var1 < 0 ? -1 : this._asciiToBase64[var1];
   }

   public char encodeBase64BitsAsChar(int var1) {
      return this._base64ToAsciiC[var1];
   }

   public int encodeBase64Chunk(int var1, char[] var2, int var3) {
      var2[var3++] = this._base64ToAsciiC[var1 >> 18 & 63];
      var2[var3++] = this._base64ToAsciiC[var1 >> 12 & 63];
      var2[var3++] = this._base64ToAsciiC[var1 >> 6 & 63];
      var2[var3++] = this._base64ToAsciiC[var1 & 63];
      return var3;
   }

   public void encodeBase64Chunk(StringBuilder var1, int var2) {
      var1.append(this._base64ToAsciiC[var2 >> 18 & 63]);
      var1.append(this._base64ToAsciiC[var2 >> 12 & 63]);
      var1.append(this._base64ToAsciiC[var2 >> 6 & 63]);
      var1.append(this._base64ToAsciiC[var2 & 63]);
   }

   public int encodeBase64Partial(int var1, int var2, char[] var3, int var4) {
      var3[var4++] = this._base64ToAsciiC[var1 >> 18 & 63];
      var3[var4++] = this._base64ToAsciiC[var1 >> 12 & 63];
      if (this._usesPadding) {
         var3[var4++] = var2 == 2 ? this._base64ToAsciiC[var1 >> 6 & 63] : this._paddingChar;
         var3[var4++] = this._paddingChar;
      } else if (var2 == 2) {
         var3[var4++] = this._base64ToAsciiC[var1 >> 6 & 63];
      }

      return var4;
   }

   public void encodeBase64Partial(StringBuilder var1, int var2, int var3) {
      var1.append(this._base64ToAsciiC[var2 >> 18 & 63]);
      var1.append(this._base64ToAsciiC[var2 >> 12 & 63]);
      if (this._usesPadding) {
         var1.append(var3 == 2 ? this._base64ToAsciiC[var2 >> 6 & 63] : this._paddingChar);
         var1.append(this._paddingChar);
      } else if (var3 == 2) {
         var1.append(this._base64ToAsciiC[var2 >> 6 & 63]);
      }

   }

   public byte encodeBase64BitsAsByte(int var1) {
      return this._base64ToAsciiB[var1];
   }

   public int encodeBase64Chunk(int var1, byte[] var2, int var3) {
      var2[var3++] = this._base64ToAsciiB[var1 >> 18 & 63];
      var2[var3++] = this._base64ToAsciiB[var1 >> 12 & 63];
      var2[var3++] = this._base64ToAsciiB[var1 >> 6 & 63];
      var2[var3++] = this._base64ToAsciiB[var1 & 63];
      return var3;
   }

   public int encodeBase64Partial(int var1, int var2, byte[] var3, int var4) {
      var3[var4++] = this._base64ToAsciiB[var1 >> 18 & 63];
      var3[var4++] = this._base64ToAsciiB[var1 >> 12 & 63];
      if (this._usesPadding) {
         byte var5 = (byte)this._paddingChar;
         var3[var4++] = var2 == 2 ? this._base64ToAsciiB[var1 >> 6 & 63] : var5;
         var3[var4++] = var5;
      } else if (var2 == 2) {
         var3[var4++] = this._base64ToAsciiB[var1 >> 6 & 63];
      }

      return var4;
   }

   public String encode(byte[] var1) {
      return this.encode(var1, false);
   }

   public String encode(byte[] var1, boolean var2) {
      int var3 = var1.length;
      int var5 = var3 + (var3 >> 2) + (var3 >> 3);
      StringBuilder var4 = new StringBuilder(var5);
      if (var2) {
         var4.append('"');
      }

      var5 = this.getMaxLineLength() >> 2;
      int var6 = 0;
      int var7 = var3 - 3;

      int var8;
      while(var6 <= var7) {
         var8 = var1[var6++] << 8;
         var8 |= var1[var6++] & 255;
         var8 = var8 << 8 | var1[var6++] & 255;
         this.encodeBase64Chunk(var4, var8);
         --var5;
         if (var5 <= 0) {
            var4.append('\\');
            var4.append('n');
            var5 = this.getMaxLineLength() >> 2;
         }
      }

      var8 = var3 - var6;
      if (var8 > 0) {
         int var9 = var1[var6++] << 16;
         if (var8 == 2) {
            var9 |= (var1[var6++] & 255) << 8;
         }

         this.encodeBase64Partial(var4, var9, var8);
      }

      if (var2) {
         var4.append('"');
      }

      return var4.toString();
   }

   public byte[] decode(String var1) throws IllegalArgumentException {
      ByteArrayBuilder var2 = new ByteArrayBuilder();
      this.decode(var1, var2);
      return var2.toByteArray();
   }

   public void decode(String var1, ByteArrayBuilder var2) throws IllegalArgumentException {
      int var3 = 0;
      int var4 = var1.length();

      while(var3 < var4) {
         char var5 = var1.charAt(var3++);
         if (var5 > ' ') {
            int var6 = this.decodeBase64Char(var5);
            if (var6 < 0) {
               this._reportInvalidBase64(var5, 0, (String)null);
            }

            int var7 = var6;
            if (var3 >= var4) {
               this._reportBase64EOF();
            }

            var5 = var1.charAt(var3++);
            var6 = this.decodeBase64Char(var5);
            if (var6 < 0) {
               this._reportInvalidBase64(var5, 1, (String)null);
            }

            var7 = var7 << 6 | var6;
            if (var3 >= var4) {
               if (!this.usesPadding()) {
                  var7 >>= 4;
                  var2.append(var7);
                  break;
               }

               this._reportBase64EOF();
            }

            var5 = var1.charAt(var3++);
            var6 = this.decodeBase64Char(var5);
            if (var6 < 0) {
               if (var6 != -2) {
                  this._reportInvalidBase64(var5, 2, (String)null);
               }

               if (var3 >= var4) {
                  this._reportBase64EOF();
               }

               var5 = var1.charAt(var3++);
               if (!this.usesPaddingChar(var5)) {
                  this._reportInvalidBase64(var5, 3, "expected padding character '" + this.getPaddingChar() + "'");
               }

               var7 >>= 4;
               var2.append(var7);
            } else {
               var7 = var7 << 6 | var6;
               if (var3 >= var4) {
                  if (!this.usesPadding()) {
                     var7 >>= 2;
                     var2.appendTwoBytes(var7);
                     break;
                  }

                  this._reportBase64EOF();
               }

               var5 = var1.charAt(var3++);
               var6 = this.decodeBase64Char(var5);
               if (var6 < 0) {
                  if (var6 != -2) {
                     this._reportInvalidBase64(var5, 3, (String)null);
                  }

                  var7 >>= 2;
                  var2.appendTwoBytes(var7);
               } else {
                  var7 = var7 << 6 | var6;
                  var2.appendThreeBytes(var7);
               }
            }
         }
      }

   }

   public String toString() {
      return this._name;
   }

   public boolean equals(Object var1) {
      return var1 == this;
   }

   public int hashCode() {
      return this._name.hashCode();
   }

   protected void _reportInvalidBase64(char var1, int var2, String var3) throws IllegalArgumentException {
      String var4;
      if (var1 <= ' ') {
         var4 = "Illegal white space character (code 0x" + Integer.toHexString(var1) + ") as character #" + (var2 + 1) + " of 4-char base64 unit: can only used between units";
      } else if (this.usesPaddingChar(var1)) {
         var4 = "Unexpected padding character ('" + this.getPaddingChar() + "') as character #" + (var2 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
      } else if (Character.isDefined(var1) && !Character.isISOControl(var1)) {
         var4 = "Illegal character '" + var1 + "' (code 0x" + Integer.toHexString(var1) + ") in base64 content";
      } else {
         var4 = "Illegal character (code 0x" + Integer.toHexString(var1) + ") in base64 content";
      }

      if (var3 != null) {
         var4 = var4 + ": " + var3;
      }

      throw new IllegalArgumentException(var4);
   }

   protected void _reportBase64EOF() throws IllegalArgumentException {
      throw new IllegalArgumentException("Unexpected end-of-String in base64 content");
   }
}
