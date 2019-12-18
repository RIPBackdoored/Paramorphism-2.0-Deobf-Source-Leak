package com.fasterxml.jackson.annotation;

public final class JsonFormat$Shape extends Enum {
   public static final JsonFormat$Shape ANY = new JsonFormat$Shape("ANY", 0);
   public static final JsonFormat$Shape NATURAL = new JsonFormat$Shape("NATURAL", 1);
   public static final JsonFormat$Shape SCALAR = new JsonFormat$Shape("SCALAR", 2);
   public static final JsonFormat$Shape ARRAY = new JsonFormat$Shape("ARRAY", 3);
   public static final JsonFormat$Shape OBJECT = new JsonFormat$Shape("OBJECT", 4);
   public static final JsonFormat$Shape NUMBER = new JsonFormat$Shape("NUMBER", 5);
   public static final JsonFormat$Shape NUMBER_FLOAT = new JsonFormat$Shape("NUMBER_FLOAT", 6);
   public static final JsonFormat$Shape NUMBER_INT = new JsonFormat$Shape("NUMBER_INT", 7);
   public static final JsonFormat$Shape STRING = new JsonFormat$Shape("STRING", 8);
   public static final JsonFormat$Shape BOOLEAN = new JsonFormat$Shape("BOOLEAN", 9);
   private static final JsonFormat$Shape[] $VALUES = new JsonFormat$Shape[]{ANY, NATURAL, SCALAR, ARRAY, OBJECT, NUMBER, NUMBER_FLOAT, NUMBER_INT, STRING, BOOLEAN};

   public static JsonFormat$Shape[] values() {
      return (JsonFormat$Shape[])$VALUES.clone();
   }

   public static JsonFormat$Shape valueOf(String var0) {
      return (JsonFormat$Shape)Enum.valueOf(JsonFormat$Shape.class, var0);
   }

   private JsonFormat$Shape(String var1, int var2) {
      super(var1, var2);
   }

   public boolean isNumeric() {
      return this == NUMBER || this == NUMBER_INT || this == NUMBER_FLOAT;
   }

   public boolean isStructured() {
      return this == OBJECT || this == ARRAY;
   }
}
