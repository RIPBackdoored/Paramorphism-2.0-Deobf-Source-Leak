package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.UUID;

public class UUIDSerializer extends StdScalarSerializer {
   static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

   public UUIDSerializer() {
      super(UUID.class);
   }

   public boolean isEmpty(SerializerProvider var1, UUID var2) {
      return var2.getLeastSignificantBits() == 0L && var2.getMostSignificantBits() == 0L;
   }

   public void serialize(UUID var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (var2.canWriteBinaryNatively() && !(var2 instanceof TokenBuffer)) {
         var2.writeBinary(_asBytes(var1));
      } else {
         char[] var4 = new char[36];
         long var5 = var1.getMostSignificantBits();
         _appendInt((int)(var5 >> 32), (char[])var4, 0);
         var4[8] = '-';
         int var7 = (int)var5;
         _appendShort(var7 >>> 16, var4, 9);
         var4[13] = '-';
         _appendShort(var7, var4, 14);
         var4[18] = '-';
         long var8 = var1.getLeastSignificantBits();
         _appendShort((int)(var8 >>> 48), var4, 19);
         var4[23] = '-';
         _appendShort((int)(var8 >>> 32), var4, 24);
         _appendInt((int)var8, (char[])var4, 28);
         var2.writeString(var4, 0, 36);
      }
   }

   private static void _appendInt(int var0, char[] var1, int var2) {
      _appendShort(var0 >> 16, var1, var2);
      _appendShort(var0, var1, var2 + 4);
   }

   private static void _appendShort(int var0, char[] var1, int var2) {
      var1[var2] = HEX_CHARS[var0 >> 12 & 15];
      ++var2;
      var1[var2] = HEX_CHARS[var0 >> 8 & 15];
      ++var2;
      var1[var2] = HEX_CHARS[var0 >> 4 & 15];
      ++var2;
      var1[var2] = HEX_CHARS[var0 & 15];
   }

   private static final byte[] _asBytes(UUID var0) {
      byte[] var1 = new byte[16];
      long var2 = var0.getMostSignificantBits();
      long var4 = var0.getLeastSignificantBits();
      _appendInt((int)(var2 >> 32), (byte[])var1, 0);
      _appendInt((int)var2, (byte[])var1, 4);
      _appendInt((int)(var4 >> 32), (byte[])var1, 8);
      _appendInt((int)var4, (byte[])var1, 12);
      return var1;
   }

   private static final void _appendInt(int var0, byte[] var1, int var2) {
      var1[var2] = (byte)(var0 >> 24);
      ++var2;
      var1[var2] = (byte)(var0 >> 16);
      ++var2;
      var1[var2] = (byte)(var0 >> 8);
      ++var2;
      var1[var2] = (byte)var0;
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((UUID)var1, var2, var3);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (UUID)var2);
   }
}
