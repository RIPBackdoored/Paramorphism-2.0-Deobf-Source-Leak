package com.fasterxml.jackson.core;

public final class JsonEncoding extends Enum {
   public static final JsonEncoding UTF8 = new JsonEncoding("UTF8", 0, "UTF-8", false, 8);
   public static final JsonEncoding UTF16_BE = new JsonEncoding("UTF16_BE", 1, "UTF-16BE", true, 16);
   public static final JsonEncoding UTF16_LE = new JsonEncoding("UTF16_LE", 2, "UTF-16LE", false, 16);
   public static final JsonEncoding UTF32_BE = new JsonEncoding("UTF32_BE", 3, "UTF-32BE", true, 32);
   public static final JsonEncoding UTF32_LE = new JsonEncoding("UTF32_LE", 4, "UTF-32LE", false, 32);
   private final String _javaName;
   private final boolean _bigEndian;
   private final int _bits;
   private static final JsonEncoding[] $VALUES = new JsonEncoding[]{UTF8, UTF16_BE, UTF16_LE, UTF32_BE, UTF32_LE};

   public static JsonEncoding[] values() {
      return (JsonEncoding[])$VALUES.clone();
   }

   public static JsonEncoding valueOf(String var0) {
      return (JsonEncoding)Enum.valueOf(JsonEncoding.class, var0);
   }

   private JsonEncoding(String var1, int var2, String var3, boolean var4, int var5) {
      super(var1, var2);
      this._javaName = var3;
      this._bigEndian = var4;
      this._bits = var5;
   }

   public String getJavaName() {
      return this._javaName;
   }

   public boolean isBigEndian() {
      return this._bigEndian;
   }

   public int bits() {
      return this._bits;
   }
}
