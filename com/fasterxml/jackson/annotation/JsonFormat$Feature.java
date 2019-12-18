package com.fasterxml.jackson.annotation;

public final class JsonFormat$Feature extends Enum {
   public static final JsonFormat$Feature ACCEPT_SINGLE_VALUE_AS_ARRAY = new JsonFormat$Feature("ACCEPT_SINGLE_VALUE_AS_ARRAY", 0);
   public static final JsonFormat$Feature ACCEPT_CASE_INSENSITIVE_PROPERTIES = new JsonFormat$Feature("ACCEPT_CASE_INSENSITIVE_PROPERTIES", 1);
   public static final JsonFormat$Feature WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS = new JsonFormat$Feature("WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS", 2);
   public static final JsonFormat$Feature WRITE_DATES_WITH_ZONE_ID = new JsonFormat$Feature("WRITE_DATES_WITH_ZONE_ID", 3);
   public static final JsonFormat$Feature WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED = new JsonFormat$Feature("WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED", 4);
   public static final JsonFormat$Feature WRITE_SORTED_MAP_ENTRIES = new JsonFormat$Feature("WRITE_SORTED_MAP_ENTRIES", 5);
   public static final JsonFormat$Feature ADJUST_DATES_TO_CONTEXT_TIME_ZONE = new JsonFormat$Feature("ADJUST_DATES_TO_CONTEXT_TIME_ZONE", 6);
   private static final JsonFormat$Feature[] $VALUES = new JsonFormat$Feature[]{ACCEPT_SINGLE_VALUE_AS_ARRAY, ACCEPT_CASE_INSENSITIVE_PROPERTIES, WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, WRITE_DATES_WITH_ZONE_ID, WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, WRITE_SORTED_MAP_ENTRIES, ADJUST_DATES_TO_CONTEXT_TIME_ZONE};

   public static JsonFormat$Feature[] values() {
      return (JsonFormat$Feature[])$VALUES.clone();
   }

   public static JsonFormat$Feature valueOf(String var0) {
      return (JsonFormat$Feature)Enum.valueOf(JsonFormat$Feature.class, var0);
   }

   private JsonFormat$Feature(String var1, int var2) {
      super(var1, var2);
   }
}
