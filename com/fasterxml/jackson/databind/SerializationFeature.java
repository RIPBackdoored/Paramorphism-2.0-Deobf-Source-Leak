package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.cfg.ConfigFeature;

public final class SerializationFeature extends Enum implements ConfigFeature {
   public static final SerializationFeature WRAP_ROOT_VALUE = new SerializationFeature("WRAP_ROOT_VALUE", 0, false);
   public static final SerializationFeature INDENT_OUTPUT = new SerializationFeature("INDENT_OUTPUT", 1, false);
   public static final SerializationFeature FAIL_ON_EMPTY_BEANS = new SerializationFeature("FAIL_ON_EMPTY_BEANS", 2, true);
   public static final SerializationFeature FAIL_ON_SELF_REFERENCES = new SerializationFeature("FAIL_ON_SELF_REFERENCES", 3, true);
   public static final SerializationFeature WRAP_EXCEPTIONS = new SerializationFeature("WRAP_EXCEPTIONS", 4, true);
   public static final SerializationFeature FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS = new SerializationFeature("FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS", 5, true);
   public static final SerializationFeature CLOSE_CLOSEABLE = new SerializationFeature("CLOSE_CLOSEABLE", 6, false);
   public static final SerializationFeature FLUSH_AFTER_WRITE_VALUE = new SerializationFeature("FLUSH_AFTER_WRITE_VALUE", 7, true);
   public static final SerializationFeature WRITE_DATES_AS_TIMESTAMPS = new SerializationFeature("WRITE_DATES_AS_TIMESTAMPS", 8, true);
   public static final SerializationFeature WRITE_DATE_KEYS_AS_TIMESTAMPS = new SerializationFeature("WRITE_DATE_KEYS_AS_TIMESTAMPS", 9, false);
   public static final SerializationFeature WRITE_DATES_WITH_ZONE_ID = new SerializationFeature("WRITE_DATES_WITH_ZONE_ID", 10, false);
   public static final SerializationFeature WRITE_DURATIONS_AS_TIMESTAMPS = new SerializationFeature("WRITE_DURATIONS_AS_TIMESTAMPS", 11, true);
   public static final SerializationFeature WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS = new SerializationFeature("WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS", 12, false);
   public static final SerializationFeature WRITE_ENUMS_USING_TO_STRING = new SerializationFeature("WRITE_ENUMS_USING_TO_STRING", 13, false);
   public static final SerializationFeature WRITE_ENUMS_USING_INDEX = new SerializationFeature("WRITE_ENUMS_USING_INDEX", 14, false);
   /** @deprecated */
   @Deprecated
   public static final SerializationFeature WRITE_NULL_MAP_VALUES = new SerializationFeature("WRITE_NULL_MAP_VALUES", 15, true);
   /** @deprecated */
   @Deprecated
   public static final SerializationFeature WRITE_EMPTY_JSON_ARRAYS = new SerializationFeature("WRITE_EMPTY_JSON_ARRAYS", 16, true);
   public static final SerializationFeature WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED = new SerializationFeature("WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED", 17, false);
   /** @deprecated */
   @Deprecated
   public static final SerializationFeature WRITE_BIGDECIMAL_AS_PLAIN = new SerializationFeature("WRITE_BIGDECIMAL_AS_PLAIN", 18, false);
   public static final SerializationFeature WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS = new SerializationFeature("WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS", 19, true);
   public static final SerializationFeature ORDER_MAP_ENTRIES_BY_KEYS = new SerializationFeature("ORDER_MAP_ENTRIES_BY_KEYS", 20, false);
   public static final SerializationFeature EAGER_SERIALIZER_FETCH = new SerializationFeature("EAGER_SERIALIZER_FETCH", 21, true);
   public static final SerializationFeature USE_EQUALITY_FOR_OBJECT_ID = new SerializationFeature("USE_EQUALITY_FOR_OBJECT_ID", 22, false);
   private final boolean _defaultState;
   private final int _mask;
   private static final SerializationFeature[] $VALUES = new SerializationFeature[]{WRAP_ROOT_VALUE, INDENT_OUTPUT, FAIL_ON_EMPTY_BEANS, FAIL_ON_SELF_REFERENCES, WRAP_EXCEPTIONS, FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, CLOSE_CLOSEABLE, FLUSH_AFTER_WRITE_VALUE, WRITE_DATES_AS_TIMESTAMPS, WRITE_DATE_KEYS_AS_TIMESTAMPS, WRITE_DATES_WITH_ZONE_ID, WRITE_DURATIONS_AS_TIMESTAMPS, WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, WRITE_ENUMS_USING_TO_STRING, WRITE_ENUMS_USING_INDEX, WRITE_NULL_MAP_VALUES, WRITE_EMPTY_JSON_ARRAYS, WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, WRITE_BIGDECIMAL_AS_PLAIN, WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, ORDER_MAP_ENTRIES_BY_KEYS, EAGER_SERIALIZER_FETCH, USE_EQUALITY_FOR_OBJECT_ID};

   public static SerializationFeature[] values() {
      return (SerializationFeature[])$VALUES.clone();
   }

   public static SerializationFeature valueOf(String var0) {
      return (SerializationFeature)Enum.valueOf(SerializationFeature.class, var0);
   }

   private SerializationFeature(String var1, int var2, boolean var3) {
      super(var1, var2);
      this._defaultState = var3;
      this._mask = 1 << this.ordinal();
   }

   public boolean enabledByDefault() {
      return this._defaultState;
   }

   public int getMask() {
      return this._mask;
   }

   public boolean enabledIn(int var1) {
      return (var1 & this._mask) != 0;
   }
}
