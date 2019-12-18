package com.fasterxml.jackson.annotation;

public final class JsonTypeInfo$As extends Enum {
   public static final JsonTypeInfo$As PROPERTY = new JsonTypeInfo$As("PROPERTY", 0);
   public static final JsonTypeInfo$As WRAPPER_OBJECT = new JsonTypeInfo$As("WRAPPER_OBJECT", 1);
   public static final JsonTypeInfo$As WRAPPER_ARRAY = new JsonTypeInfo$As("WRAPPER_ARRAY", 2);
   public static final JsonTypeInfo$As EXTERNAL_PROPERTY = new JsonTypeInfo$As("EXTERNAL_PROPERTY", 3);
   public static final JsonTypeInfo$As EXISTING_PROPERTY = new JsonTypeInfo$As("EXISTING_PROPERTY", 4);
   private static final JsonTypeInfo$As[] $VALUES = new JsonTypeInfo$As[]{PROPERTY, WRAPPER_OBJECT, WRAPPER_ARRAY, EXTERNAL_PROPERTY, EXISTING_PROPERTY};

   public static JsonTypeInfo$As[] values() {
      return (JsonTypeInfo$As[])$VALUES.clone();
   }

   public static JsonTypeInfo$As valueOf(String var0) {
      return (JsonTypeInfo$As)Enum.valueOf(JsonTypeInfo$As.class, var0);
   }

   private JsonTypeInfo$As(String var1, int var2) {
      super(var1, var2);
   }
}
