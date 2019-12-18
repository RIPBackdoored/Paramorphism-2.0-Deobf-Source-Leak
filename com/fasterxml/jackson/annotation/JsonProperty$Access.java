package com.fasterxml.jackson.annotation;

public final class JsonProperty$Access extends Enum {
   public static final JsonProperty$Access AUTO = new JsonProperty$Access("AUTO", 0);
   public static final JsonProperty$Access READ_ONLY = new JsonProperty$Access("READ_ONLY", 1);
   public static final JsonProperty$Access WRITE_ONLY = new JsonProperty$Access("WRITE_ONLY", 2);
   public static final JsonProperty$Access READ_WRITE = new JsonProperty$Access("READ_WRITE", 3);
   private static final JsonProperty$Access[] $VALUES = new JsonProperty$Access[]{AUTO, READ_ONLY, WRITE_ONLY, READ_WRITE};

   public static JsonProperty$Access[] values() {
      return (JsonProperty$Access[])$VALUES.clone();
   }

   public static JsonProperty$Access valueOf(String var0) {
      return (JsonProperty$Access)Enum.valueOf(JsonProperty$Access.class, var0);
   }

   private JsonProperty$Access(String var1, int var2) {
      super(var1, var2);
   }
}
