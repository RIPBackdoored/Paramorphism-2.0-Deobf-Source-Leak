package com.fasterxml.jackson.annotation;

public final class JsonCreator$Mode extends Enum {
   public static final JsonCreator$Mode DEFAULT = new JsonCreator$Mode("DEFAULT", 0);
   public static final JsonCreator$Mode DELEGATING = new JsonCreator$Mode("DELEGATING", 1);
   public static final JsonCreator$Mode PROPERTIES = new JsonCreator$Mode("PROPERTIES", 2);
   public static final JsonCreator$Mode DISABLED = new JsonCreator$Mode("DISABLED", 3);
   private static final JsonCreator$Mode[] $VALUES = new JsonCreator$Mode[]{DEFAULT, DELEGATING, PROPERTIES, DISABLED};

   public static JsonCreator$Mode[] values() {
      return (JsonCreator$Mode[])$VALUES.clone();
   }

   public static JsonCreator$Mode valueOf(String var0) {
      return (JsonCreator$Mode)Enum.valueOf(JsonCreator$Mode.class, var0);
   }

   private JsonCreator$Mode(String var1, int var2) {
      super(var1, var2);
   }
}
