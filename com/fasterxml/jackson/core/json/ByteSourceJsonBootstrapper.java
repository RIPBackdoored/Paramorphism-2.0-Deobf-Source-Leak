package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory$Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import com.fasterxml.jackson.core.io.UTF32Reader;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import java.io.ByteArrayInputStream;
import java.io.CharConversionException;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class ByteSourceJsonBootstrapper {
   public static final byte UTF8_BOM_1 = -17;
   public static final byte UTF8_BOM_2 = -69;
   public static final byte UTF8_BOM_3 = -65;
   private final IOContext _context;
   private final InputStream _in;
   private final byte[] _inputBuffer;
   private int _inputPtr;
   private int _inputEnd;
   private final boolean _bufferRecyclable;
   private boolean _bigEndian = true;
   private int _bytesPerChar;

   public ByteSourceJsonBootstrapper(IOContext var1, InputStream var2) {
      super();
      this._context = var1;
      this._in = var2;
      this._inputBuffer = var1.allocReadIOBuffer();
      this._inputEnd = this._inputPtr = 0;
      this._bufferRecyclable = true;
   }

   public ByteSourceJsonBootstrapper(IOContext var1, byte[] var2, int var3, int var4) {
      super();
      this._context = var1;
      this._in = null;
      this._inputBuffer = var2;
      this._inputPtr = var3;
      this._inputEnd = var3 + var4;
      this._bufferRecyclable = false;
   }

   public JsonEncoding detectEncoding() throws IOException {
      boolean var1 = false;
      int var2;
      if (this.ensureLoaded(4)) {
         var2 = this._inputBuffer[this._inputPtr] << 24 | (this._inputBuffer[this._inputPtr + 1] & 255) << 16 | (this._inputBuffer[this._inputPtr + 2] & 255) << 8 | this._inputBuffer[this._inputPtr + 3] & 255;
         if (this.handleBOM(var2)) {
            var1 = true;
         } else if (this.checkUTF32(var2)) {
            var1 = true;
         } else if (this.checkUTF16(var2 >>> 16)) {
            var1 = true;
         }
      } else if (this.ensureLoaded(2)) {
         var2 = (this._inputBuffer[this._inputPtr] & 255) << 8 | this._inputBuffer[this._inputPtr + 1] & 255;
         if (this.checkUTF16(var2)) {
            var1 = true;
         }
      }

      JsonEncoding var3;
      if (!var1) {
         var3 = JsonEncoding.UTF8;
      } else {
         switch(this._bytesPerChar) {
         case 1:
            var3 = JsonEncoding.UTF8;
            break;
         case 2:
            var3 = this._bigEndian ? JsonEncoding.UTF16_BE : JsonEncoding.UTF16_LE;
            break;
         case 3:
         default:
            throw new RuntimeException("Internal error");
         case 4:
            var3 = this._bigEndian ? JsonEncoding.UTF32_BE : JsonEncoding.UTF32_LE;
         }
      }

      this._context.setEncoding(var3);
      return var3;
   }

   public static int skipUTF8BOM(DataInput var0) throws IOException {
      int var1 = var0.readUnsignedByte();
      if (var1 != 239) {
         return var1;
      } else {
         var1 = var0.readUnsignedByte();
         if (var1 != 187) {
            throw new IOException("Unexpected byte 0x" + Integer.toHexString(var1) + " following 0xEF; should get 0xBB as part of UTF-8 BOM");
         } else {
            var1 = var0.readUnsignedByte();
            if (var1 != 191) {
               throw new IOException("Unexpected byte 0x" + Integer.toHexString(var1) + " following 0xEF 0xBB; should get 0xBF as part of UTF-8 BOM");
            } else {
               return var0.readUnsignedByte();
            }
         }
      }
   }

   public Reader constructReader() throws IOException {
      JsonEncoding var1 = this._context.getEncoding();
      switch(var1.bits()) {
      case 8:
      case 16:
         Object var2 = this._in;
         if (var2 == null) {
            var2 = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
         } else if (this._inputPtr < this._inputEnd) {
            var2 = new MergedStream(this._context, (InputStream)var2, this._inputBuffer, this._inputPtr, this._inputEnd);
         }

         return new InputStreamReader((InputStream)var2, var1.getJavaName());
      case 32:
         return new UTF32Reader(this._context, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, this._context.getEncoding().isBigEndian());
      default:
         throw new RuntimeException("Internal error");
      }
   }

   public JsonParser constructParser(int var1, ObjectCodec var2, ByteQuadsCanonicalizer var3, CharsToNameCanonicalizer var4, int var5) throws IOException {
      JsonEncoding var6 = this.detectEncoding();
      if (var6 == JsonEncoding.UTF8 && JsonFactory$Feature.CANONICALIZE_FIELD_NAMES.enabledIn(var5)) {
         ByteQuadsCanonicalizer var7 = var3.makeChild(var5);
         return new UTF8StreamJsonParser(this._context, var1, this._in, var2, var7, this._inputBuffer, this._inputPtr, this._inputEnd, this._bufferRecyclable);
      } else {
         return new ReaderBasedJsonParser(this._context, var1, this.constructReader(), var2, var4.makeChild(var5));
      }
   }

   public static MatchStrength hasJSONFormat(InputAccessor var0) throws IOException {
      if (!var0.hasMoreBytes()) {
         return MatchStrength.INCONCLUSIVE;
      } else {
         byte var1 = var0.nextByte();
         if (var1 == -17) {
            if (!var0.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            if (var0.nextByte() != -69) {
               return MatchStrength.NO_MATCH;
            }

            if (!var0.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            if (var0.nextByte() != -65) {
               return MatchStrength.NO_MATCH;
            }

            if (!var0.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            var1 = var0.nextByte();
         }

         int var2 = skipSpace(var0, var1);
         if (var2 < 0) {
            return MatchStrength.INCONCLUSIVE;
         } else if (var2 == 123) {
            var2 = skipSpace(var0);
            if (var2 < 0) {
               return MatchStrength.INCONCLUSIVE;
            } else {
               return var2 != 34 && var2 != 125 ? MatchStrength.NO_MATCH : MatchStrength.SOLID_MATCH;
            }
         } else if (var2 == 91) {
            var2 = skipSpace(var0);
            if (var2 < 0) {
               return MatchStrength.INCONCLUSIVE;
            } else {
               return var2 != 93 && var2 != 91 ? MatchStrength.SOLID_MATCH : MatchStrength.SOLID_MATCH;
            }
         } else {
            MatchStrength var3 = MatchStrength.WEAK_MATCH;
            if (var2 == 34) {
               return var3;
            } else if (var2 <= 57 && var2 >= 48) {
               return var3;
            } else if (var2 == 45) {
               var2 = skipSpace(var0);
               if (var2 < 0) {
                  return MatchStrength.INCONCLUSIVE;
               } else {
                  return var2 <= 57 && var2 >= 48 ? var3 : MatchStrength.NO_MATCH;
               }
            } else if (var2 == 110) {
               return tryMatch(var0, "ull", var3);
            } else if (var2 == 116) {
               return tryMatch(var0, "rue", var3);
            } else {
               return var2 == 102 ? tryMatch(var0, "alse", var3) : MatchStrength.NO_MATCH;
            }
         }
      }
   }

   private static MatchStrength tryMatch(InputAccessor var0, String var1, MatchStrength var2) throws IOException {
      int var3 = 0;

      for(int var4 = var1.length(); var3 < var4; ++var3) {
         if (!var0.hasMoreBytes()) {
            return MatchStrength.INCONCLUSIVE;
         }

         if (var0.nextByte() != var1.charAt(var3)) {
            return MatchStrength.NO_MATCH;
         }
      }

      return var2;
   }

   private static int skipSpace(InputAccessor var0) throws IOException {
      return !var0.hasMoreBytes() ? -1 : skipSpace(var0, var0.nextByte());
   }

   private static int skipSpace(InputAccessor var0, byte var1) throws IOException {
      while(true) {
         int var2 = var1 & 255;
         if (var2 != 32 && var2 != 13 && var2 != 10 && var2 != 9) {
            return var2;
         }

         if (!var0.hasMoreBytes()) {
            return -1;
         }

         var1 = var0.nextByte();
      }
   }

   private boolean handleBOM(int var1) throws IOException {
      switch(var1) {
      case -16842752:
         this.reportWeirdUCS4("3412");
         break;
      case -131072:
         this._inputPtr += 4;
         this._bytesPerChar = 4;
         this._bigEndian = false;
         return true;
      case 65279:
         this._bigEndian = true;
         this._inputPtr += 4;
         this._bytesPerChar = 4;
         return true;
      case 65534:
         this.reportWeirdUCS4("2143");
      }

      int var2 = var1 >>> 16;
      if (var2 == 65279) {
         this._inputPtr += 2;
         this._bytesPerChar = 2;
         this._bigEndian = true;
         return true;
      } else if (var2 == 65534) {
         this._inputPtr += 2;
         this._bytesPerChar = 2;
         this._bigEndian = false;
         return true;
      } else if (var1 >>> 8 == 15711167) {
         this._inputPtr += 3;
         this._bytesPerChar = 1;
         this._bigEndian = true;
         return true;
      } else {
         return false;
      }
   }

   private boolean checkUTF32(int var1) throws IOException {
      if (var1 >> 8 == 0) {
         this._bigEndian = true;
      } else if ((var1 & 16777215) == 0) {
         this._bigEndian = false;
      } else if ((var1 & -16711681) == 0) {
         this.reportWeirdUCS4("3412");
      } else {
         if ((var1 & -65281) != 0) {
            return false;
         }

         this.reportWeirdUCS4("2143");
      }

      this._bytesPerChar = 4;
      return true;
   }

   private boolean checkUTF16(int var1) {
      if ((var1 & '\uff00') == 0) {
         this._bigEndian = true;
      } else {
         if ((var1 & 255) != 0) {
            return false;
         }

         this._bigEndian = false;
      }

      this._bytesPerChar = 2;
      return true;
   }

   private void reportWeirdUCS4(String var1) throws IOException {
      throw new CharConversionException("Unsupported UCS-4 endianness (" + var1 + ") detected");
   }

   protected boolean ensureLoaded(int var1) throws IOException {
      int var3;
      for(int var2 = this._inputEnd - this._inputPtr; var2 < var1; var2 += var3) {
         if (this._in == null) {
            var3 = -1;
         } else {
            var3 = this._in.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
         }

         if (var3 < 1) {
            return false;
         }

         this._inputEnd += var3;
      }

      return true;
   }
}
