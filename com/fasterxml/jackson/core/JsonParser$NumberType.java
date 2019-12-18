package com.fasterxml.jackson.core;

public final class JsonParser$NumberType extends Enum {
   public static final JsonParser$NumberType INT = new JsonParser$NumberType("INT", 0);
   public static final JsonParser$NumberType LONG = new JsonParser$NumberType("LONG", 1);
   public static final JsonParser$NumberType BIG_INTEGER = new JsonParser$NumberType("BIG_INTEGER", 2);
   public static final JsonParser$NumberType FLOAT = new JsonParser$NumberType("FLOAT", 3);
   public static final JsonParser$NumberType DOUBLE = new JsonParser$NumberType("DOUBLE", 4);
   public static final JsonParser$NumberType BIG_DECIMAL = new JsonParser$NumberType("BIG_DECIMAL", 5);
   private static final JsonParser$NumberType[] $VALUES = new JsonParser$NumberType[]{INT, LONG, BIG_INTEGER, FLOAT, DOUBLE, BIG_DECIMAL};

   public static JsonParser$NumberType[] values() {
      return (JsonParser$NumberType[])$VALUES.clone();
   }

   public static JsonParser$NumberType valueOf(String var0) {
      return (JsonParser$NumberType)Enum.valueOf(JsonParser$NumberType.class, var0);
   }

   private JsonParser$NumberType(String var1, int var2) {
      super(var1, var2);
   }
}
