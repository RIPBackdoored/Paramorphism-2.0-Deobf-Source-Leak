package com.fasterxml.jackson.core;

public final class JsonFactory$Feature extends Enum {
   public static final JsonFactory$Feature INTERN_FIELD_NAMES = new JsonFactory$Feature("INTERN_FIELD_NAMES", 0, true);
   public static final JsonFactory$Feature CANONICALIZE_FIELD_NAMES = new JsonFactory$Feature("CANONICALIZE_FIELD_NAMES", 1, true);
   public static final JsonFactory$Feature FAIL_ON_SYMBOL_HASH_OVERFLOW = new JsonFactory$Feature("FAIL_ON_SYMBOL_HASH_OVERFLOW", 2, true);
   public static final JsonFactory$Feature USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING = new JsonFactory$Feature("USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING", 3, true);
   private final boolean _defaultState;
   private static final JsonFactory$Feature[] $VALUES = new JsonFactory$Feature[]{INTERN_FIELD_NAMES, CANONICALIZE_FIELD_NAMES, FAIL_ON_SYMBOL_HASH_OVERFLOW, USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING};

   public static JsonFactory$Feature[] values() {
      return (JsonFactory$Feature[])$VALUES.clone();
   }

   public static JsonFactory$Feature valueOf(String var0) {
      return (JsonFactory$Feature)Enum.valueOf(JsonFactory$Feature.class, var0);
   }

   public static int collectDefaults() {
      int var0 = 0;
      JsonFactory$Feature[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         JsonFactory$Feature var4 = var1[var3];
         if (var4.enabledByDefault()) {
            var0 |= var4.getMask();
         }
      }

      return var0;
   }

   private JsonFactory$Feature(String var1, int var2, boolean var3) {
      super(var1, var2);
      this._defaultState = var3;
   }

   public boolean enabledByDefault() {
      return this._defaultState;
   }

   public boolean enabledIn(int var1) {
      return (var1 & this.getMask()) != 0;
   }

   public int getMask() {
      return 1 << this.ordinal();
   }
}
