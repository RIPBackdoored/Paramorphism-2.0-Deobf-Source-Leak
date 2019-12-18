package com.fasterxml.jackson.dataformat.yaml;

import com.fasterxml.jackson.core.FormatFeature;

public final class YAMLGenerator$Feature extends Enum implements FormatFeature {
   public static final YAMLGenerator$Feature WRITE_DOC_START_MARKER = new YAMLGenerator$Feature("WRITE_DOC_START_MARKER", 0, true);
   public static final YAMLGenerator$Feature USE_NATIVE_OBJECT_ID = new YAMLGenerator$Feature("USE_NATIVE_OBJECT_ID", 1, true);
   public static final YAMLGenerator$Feature USE_NATIVE_TYPE_ID = new YAMLGenerator$Feature("USE_NATIVE_TYPE_ID", 2, true);
   public static final YAMLGenerator$Feature CANONICAL_OUTPUT = new YAMLGenerator$Feature("CANONICAL_OUTPUT", 3, false);
   public static final YAMLGenerator$Feature SPLIT_LINES = new YAMLGenerator$Feature("SPLIT_LINES", 4, true);
   public static final YAMLGenerator$Feature MINIMIZE_QUOTES = new YAMLGenerator$Feature("MINIMIZE_QUOTES", 5, false);
   public static final YAMLGenerator$Feature ALWAYS_QUOTE_NUMBERS_AS_STRINGS = new YAMLGenerator$Feature("ALWAYS_QUOTE_NUMBERS_AS_STRINGS", 6, false);
   public static final YAMLGenerator$Feature LITERAL_BLOCK_STYLE = new YAMLGenerator$Feature("LITERAL_BLOCK_STYLE", 7, false);
   public static final YAMLGenerator$Feature INDENT_ARRAYS = new YAMLGenerator$Feature("INDENT_ARRAYS", 8, false);
   public static final YAMLGenerator$Feature USE_PLATFORM_LINE_BREAKS = new YAMLGenerator$Feature("USE_PLATFORM_LINE_BREAKS", 9, false);
   protected final boolean _defaultState;
   protected final int _mask;
   private static final YAMLGenerator$Feature[] $VALUES = new YAMLGenerator$Feature[]{WRITE_DOC_START_MARKER, USE_NATIVE_OBJECT_ID, USE_NATIVE_TYPE_ID, CANONICAL_OUTPUT, SPLIT_LINES, MINIMIZE_QUOTES, ALWAYS_QUOTE_NUMBERS_AS_STRINGS, LITERAL_BLOCK_STYLE, INDENT_ARRAYS, USE_PLATFORM_LINE_BREAKS};

   public static YAMLGenerator$Feature[] values() {
      return (YAMLGenerator$Feature[])$VALUES.clone();
   }

   public static YAMLGenerator$Feature valueOf(String var0) {
      return (YAMLGenerator$Feature)Enum.valueOf(YAMLGenerator$Feature.class, var0);
   }

   public static int collectDefaults() {
      int var0 = 0;
      YAMLGenerator$Feature[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         YAMLGenerator$Feature var4 = var1[var3];
         if (var4.enabledByDefault()) {
            var0 |= var4.getMask();
         }
      }

      return var0;
   }

   private YAMLGenerator$Feature(String var1, int var2, boolean var3) {
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
