package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class UUIDDeserializer extends FromStringDeserializer {
   private static final long serialVersionUID = 1L;
   static final int[] HEX_DIGITS = new int[127];

   public UUIDDeserializer() {
      super(UUID.class);
   }

   protected UUID _deserialize(String var1, DeserializationContext var2) throws IOException {
      if (var1.length() != 36) {
         if (var1.length() == 24) {
            byte[] var12 = Base64Variants.getDefaultVariant().decode(var1);
            return this._fromBytes(var12, var2);
         } else {
            return this._badFormat(var1, var2);
         }
      } else {
         if (var1.charAt(8) != '-' || var1.charAt(13) != '-' || var1.charAt(18) != '-' || var1.charAt(23) != '-') {
            this._badFormat(var1, var2);
         }

         long var3 = (long)this.intFromChars(var1, 0, var2);
         var3 <<= 32;
         long var5 = (long)this.shortFromChars(var1, 9, var2) << 16;
         var5 |= (long)this.shortFromChars(var1, 14, var2);
         long var7 = var3 + var5;
         int var9 = this.shortFromChars(var1, 19, var2) << 16 | this.shortFromChars(var1, 24, var2);
         var3 = (long)var9;
         var3 <<= 32;
         var5 = (long)this.intFromChars(var1, 28, var2);
         var5 = var5 << 32 >>> 32;
         long var10 = var3 | var5;
         return new UUID(var7, var10);
      }
   }

   protected UUID _deserializeEmbedded(Object var1, DeserializationContext var2) throws IOException {
      if (var1 instanceof byte[]) {
         return this._fromBytes((byte[])((byte[])var1), var2);
      } else {
         super._deserializeEmbedded(var1, var2);
         return null;
      }
   }

   private UUID _badFormat(String var1, DeserializationContext var2) throws IOException {
      return (UUID)var2.handleWeirdStringValue(this.handledType(), var1, "UUID has to be represented by standard 36-char representation");
   }

   int intFromChars(String var1, int var2, DeserializationContext var3) throws JsonMappingException {
      return (this.byteFromChars(var1, var2, var3) << 24) + (this.byteFromChars(var1, var2 + 2, var3) << 16) + (this.byteFromChars(var1, var2 + 4, var3) << 8) + this.byteFromChars(var1, var2 + 6, var3);
   }

   int shortFromChars(String var1, int var2, DeserializationContext var3) throws JsonMappingException {
      return (this.byteFromChars(var1, var2, var3) << 8) + this.byteFromChars(var1, var2 + 2, var3);
   }

   int byteFromChars(String var1, int var2, DeserializationContext var3) throws JsonMappingException {
      char var4 = var1.charAt(var2);
      char var5 = var1.charAt(var2 + 1);
      if (var4 <= 127 && var5 <= 127) {
         int var6 = HEX_DIGITS[var4] << 4 | HEX_DIGITS[var5];
         if (var6 >= 0) {
            return var6;
         }
      }

      return var4 <= 127 && HEX_DIGITS[var4] >= 0 ? this._badChar(var1, var2 + 1, var3, var5) : this._badChar(var1, var2, var3, var4);
   }

   int _badChar(String var1, int var2, DeserializationContext var3, char var4) throws JsonMappingException {
      throw var3.weirdStringException(var1, this.handledType(), String.format("Non-hex character '%c' (value 0x%s), not valid for UUID String", var4, Integer.toHexString(var4)));
   }

   private UUID _fromBytes(byte[] var1, DeserializationContext var2) throws JsonMappingException {
      if (var1.length != 16) {
         throw InvalidFormatException.from(var2.getParser(), "Can only construct UUIDs from byte[16]; got " + var1.length + " bytes", var1, this.handledType());
      } else {
         return new UUID(_long(var1, 0), _long(var1, 8));
      }
   }

   private static long _long(byte[] var0, int var1) {
      long var2 = (long)_int(var0, var1) << 32;
      long var4 = (long)_int(var0, var1 + 4);
      var4 = var4 << 32 >>> 32;
      return var2 | var4;
   }

   private static int _int(byte[] var0, int var1) {
      return var0[var1] << 24 | (var0[var1 + 1] & 255) << 16 | (var0[var1 + 2] & 255) << 8 | var0[var1 + 3] & 255;
   }

   protected Object _deserializeEmbedded(Object var1, DeserializationContext var2) throws IOException {
      return this._deserializeEmbedded(var1, var2);
   }

   protected Object _deserialize(String var1, DeserializationContext var2) throws IOException {
      return this._deserialize(var1, var2);
   }

   static {
      Arrays.fill(HEX_DIGITS, -1);

      int var0;
      for(var0 = 0; var0 < 10; HEX_DIGITS[48 + var0] = var0++) {
      }

      for(var0 = 0; var0 < 6; ++var0) {
         HEX_DIGITS[97 + var0] = 10 + var0;
         HEX_DIGITS[65 + var0] = 10 + var0;
      }

   }
}
