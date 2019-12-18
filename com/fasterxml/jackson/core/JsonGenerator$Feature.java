package com.fasterxml.jackson.core;

public final class JsonGenerator$Feature extends Enum {
   public static final JsonGenerator$Feature AUTO_CLOSE_TARGET = new JsonGenerator$Feature("AUTO_CLOSE_TARGET", 0, true);
   public static final JsonGenerator$Feature AUTO_CLOSE_JSON_CONTENT = new JsonGenerator$Feature("AUTO_CLOSE_JSON_CONTENT", 1, true);
   public static final JsonGenerator$Feature FLUSH_PASSED_TO_STREAM = new JsonGenerator$Feature("FLUSH_PASSED_TO_STREAM", 2, true);
   public static final JsonGenerator$Feature QUOTE_FIELD_NAMES = new JsonGenerator$Feature("QUOTE_FIELD_NAMES", 3, true);
   public static final JsonGenerator$Feature QUOTE_NON_NUMERIC_NUMBERS = new JsonGenerator$Feature("QUOTE_NON_NUMERIC_NUMBERS", 4, true);
   public static final JsonGenerator$Feature WRITE_NUMBERS_AS_STRINGS = new JsonGenerator$Feature("WRITE_NUMBERS_AS_STRINGS", 5, false);
   public static final JsonGenerator$Feature WRITE_BIGDECIMAL_AS_PLAIN = new JsonGenerator$Feature("WRITE_BIGDECIMAL_AS_PLAIN", 6, false);
   public static final JsonGenerator$Feature ESCAPE_NON_ASCII = new JsonGenerator$Feature("ESCAPE_NON_ASCII", 7, false);
   public static final JsonGenerator$Feature STRICT_DUPLICATE_DETECTION = new JsonGenerator$Feature("STRICT_DUPLICATE_DETECTION", 8, false);
   public static final JsonGenerator$Feature IGNORE_UNKNOWN = new JsonGenerator$Feature("IGNORE_UNKNOWN", 9, false);
   private final boolean _defaultState;
   private final int _mask;
   private static final JsonGenerator$Feature[] $VALUES = new JsonGenerator$Feature[]{AUTO_CLOSE_TARGET, AUTO_CLOSE_JSON_CONTENT, FLUSH_PASSED_TO_STREAM, QUOTE_FIELD_NAMES, QUOTE_NON_NUMERIC_NUMBERS, WRITE_NUMBERS_AS_STRINGS, WRITE_BIGDECIMAL_AS_PLAIN, ESCAPE_NON_ASCII, STRICT_DUPLICATE_DETECTION, IGNORE_UNKNOWN};

   public static JsonGenerator$Feature[] values() {
      return (JsonGenerator$Feature[])$VALUES.clone();
   }

   public static JsonGenerator$Feature valueOf(String var0) {
      return (JsonGenerator$Feature)Enum.valueOf(JsonGenerator$Feature.class, var0);
   }

   public static int collectDefaults() {
      int var0 = 0;
      JsonGenerator$Feature[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         JsonGenerator$Feature var4 = var1[var3];
         if (var4.enabledByDefault()) {
            var0 |= var4.getMask();
         }
      }

      return var0;
   }

   private JsonGenerator$Feature(String var1, int var2, boolean var3) {
      super(var1, var2);
      this._defaultState = var3;
      this._mask = 1 << this.ordinal();
   }

   public boolean enabledByDefault() {
      return this._defaultState;
   }

   public boolean enabledIn(int var1) {
      return (var1 & this._mask) != 0;
   }

   public int getMask() {
      return this._mask;
   }
}
