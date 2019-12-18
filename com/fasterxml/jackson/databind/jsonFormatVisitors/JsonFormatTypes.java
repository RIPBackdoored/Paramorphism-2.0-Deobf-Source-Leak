package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public final class JsonFormatTypes extends Enum {
   public static final JsonFormatTypes STRING = new JsonFormatTypes("STRING", 0);
   public static final JsonFormatTypes NUMBER = new JsonFormatTypes("NUMBER", 1);
   public static final JsonFormatTypes INTEGER = new JsonFormatTypes("INTEGER", 2);
   public static final JsonFormatTypes BOOLEAN = new JsonFormatTypes("BOOLEAN", 3);
   public static final JsonFormatTypes OBJECT = new JsonFormatTypes("OBJECT", 4);
   public static final JsonFormatTypes ARRAY = new JsonFormatTypes("ARRAY", 5);
   public static final JsonFormatTypes NULL = new JsonFormatTypes("NULL", 6);
   public static final JsonFormatTypes ANY = new JsonFormatTypes("ANY", 7);
   private static final Map _byLCName = new HashMap();
   private static final JsonFormatTypes[] $VALUES = new JsonFormatTypes[]{STRING, NUMBER, INTEGER, BOOLEAN, OBJECT, ARRAY, NULL, ANY};

   public static JsonFormatTypes[] values() {
      return (JsonFormatTypes[])$VALUES.clone();
   }

   public static JsonFormatTypes valueOf(String var0) {
      return (JsonFormatTypes)Enum.valueOf(JsonFormatTypes.class, var0);
   }

   private JsonFormatTypes(String var1, int var2) {
      super(var1, var2);
   }

   @JsonValue
   public String value() {
      return this.name().toLowerCase();
   }

   @JsonCreator
   public static JsonFormatTypes forValue(String var0) {
      return (JsonFormatTypes)_byLCName.get(var0);
   }

   static {
      JsonFormatTypes[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         JsonFormatTypes var3 = var0[var2];
         _byLCName.put(var3.name().toLowerCase(), var3);
      }

   }
}
