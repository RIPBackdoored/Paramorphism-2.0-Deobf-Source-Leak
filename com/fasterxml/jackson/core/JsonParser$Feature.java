package com.fasterxml.jackson.core;

public final class JsonParser$Feature extends Enum {
   public static final JsonParser$Feature AUTO_CLOSE_SOURCE = new JsonParser$Feature("AUTO_CLOSE_SOURCE", 0, true);
   public static final JsonParser$Feature ALLOW_COMMENTS = new JsonParser$Feature("ALLOW_COMMENTS", 1, false);
   public static final JsonParser$Feature ALLOW_YAML_COMMENTS = new JsonParser$Feature("ALLOW_YAML_COMMENTS", 2, false);
   public static final JsonParser$Feature ALLOW_UNQUOTED_FIELD_NAMES = new JsonParser$Feature("ALLOW_UNQUOTED_FIELD_NAMES", 3, false);
   public static final JsonParser$Feature ALLOW_SINGLE_QUOTES = new JsonParser$Feature("ALLOW_SINGLE_QUOTES", 4, false);
   public static final JsonParser$Feature ALLOW_UNQUOTED_CONTROL_CHARS = new JsonParser$Feature("ALLOW_UNQUOTED_CONTROL_CHARS", 5, false);
   public static final JsonParser$Feature ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER = new JsonParser$Feature("ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER", 6, false);
   public static final JsonParser$Feature ALLOW_NUMERIC_LEADING_ZEROS = new JsonParser$Feature("ALLOW_NUMERIC_LEADING_ZEROS", 7, false);
   public static final JsonParser$Feature ALLOW_NON_NUMERIC_NUMBERS = new JsonParser$Feature("ALLOW_NON_NUMERIC_NUMBERS", 8, false);
   public static final JsonParser$Feature ALLOW_MISSING_VALUES = new JsonParser$Feature("ALLOW_MISSING_VALUES", 9, false);
   public static final JsonParser$Feature ALLOW_TRAILING_COMMA = new JsonParser$Feature("ALLOW_TRAILING_COMMA", 10, false);
   public static final JsonParser$Feature STRICT_DUPLICATE_DETECTION = new JsonParser$Feature("STRICT_DUPLICATE_DETECTION", 11, false);
   public static final JsonParser$Feature IGNORE_UNDEFINED = new JsonParser$Feature("IGNORE_UNDEFINED", 12, false);
   public static final JsonParser$Feature INCLUDE_SOURCE_IN_LOCATION = new JsonParser$Feature("INCLUDE_SOURCE_IN_LOCATION", 13, true);
   private final boolean _defaultState;
   private final int _mask = 1 << this.ordinal();
   private static final JsonParser$Feature[] $VALUES = new JsonParser$Feature[]{AUTO_CLOSE_SOURCE, ALLOW_COMMENTS, ALLOW_YAML_COMMENTS, ALLOW_UNQUOTED_FIELD_NAMES, ALLOW_SINGLE_QUOTES, ALLOW_UNQUOTED_CONTROL_CHARS, ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, ALLOW_NUMERIC_LEADING_ZEROS, ALLOW_NON_NUMERIC_NUMBERS, ALLOW_MISSING_VALUES, ALLOW_TRAILING_COMMA, STRICT_DUPLICATE_DETECTION, IGNORE_UNDEFINED, INCLUDE_SOURCE_IN_LOCATION};

   public static JsonParser$Feature[] values() {
      return (JsonParser$Feature[])$VALUES.clone();
   }

   public static JsonParser$Feature valueOf(String var0) {
      return (JsonParser$Feature)Enum.valueOf(JsonParser$Feature.class, var0);
   }

   public static int collectDefaults() {
      int var0 = 0;
      JsonParser$Feature[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         JsonParser$Feature var4 = var1[var3];
         if (var4.enabledByDefault()) {
            var0 |= var4.getMask();
         }
      }

      return var0;
   }

   private JsonParser$Feature(String var1, int var2, boolean var3) {
      super(var1, var2);
      this._defaultState = var3;
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
