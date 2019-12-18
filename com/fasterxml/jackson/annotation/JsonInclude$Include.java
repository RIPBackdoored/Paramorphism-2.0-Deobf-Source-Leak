package com.fasterxml.jackson.annotation;

public final class JsonInclude$Include extends Enum {
   public static final JsonInclude$Include ALWAYS = new JsonInclude$Include("ALWAYS", 0);
   public static final JsonInclude$Include NON_NULL = new JsonInclude$Include("NON_NULL", 1);
   public static final JsonInclude$Include NON_ABSENT = new JsonInclude$Include("NON_ABSENT", 2);
   public static final JsonInclude$Include NON_EMPTY = new JsonInclude$Include("NON_EMPTY", 3);
   public static final JsonInclude$Include NON_DEFAULT = new JsonInclude$Include("NON_DEFAULT", 4);
   public static final JsonInclude$Include CUSTOM = new JsonInclude$Include("CUSTOM", 5);
   public static final JsonInclude$Include USE_DEFAULTS = new JsonInclude$Include("USE_DEFAULTS", 6);
   private static final JsonInclude$Include[] $VALUES = new JsonInclude$Include[]{ALWAYS, NON_NULL, NON_ABSENT, NON_EMPTY, NON_DEFAULT, CUSTOM, USE_DEFAULTS};

   public static JsonInclude$Include[] values() {
      return (JsonInclude$Include[])$VALUES.clone();
   }

   public static JsonInclude$Include valueOf(String var0) {
      return (JsonInclude$Include)Enum.valueOf(JsonInclude$Include.class, var0);
   }

   private JsonInclude$Include(String var1, int var2) {
      super(var1, var2);
   }
}
