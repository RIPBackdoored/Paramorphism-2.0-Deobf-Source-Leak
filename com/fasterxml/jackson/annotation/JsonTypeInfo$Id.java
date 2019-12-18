package com.fasterxml.jackson.annotation;

public final class JsonTypeInfo$Id extends Enum {
   public static final JsonTypeInfo$Id NONE = new JsonTypeInfo$Id("NONE", 0, (String)null);
   public static final JsonTypeInfo$Id CLASS = new JsonTypeInfo$Id("CLASS", 1, "@class");
   public static final JsonTypeInfo$Id MINIMAL_CLASS = new JsonTypeInfo$Id("MINIMAL_CLASS", 2, "@c");
   public static final JsonTypeInfo$Id NAME = new JsonTypeInfo$Id("NAME", 3, "@type");
   public static final JsonTypeInfo$Id CUSTOM = new JsonTypeInfo$Id("CUSTOM", 4, (String)null);
   private final String _defaultPropertyName;
   private static final JsonTypeInfo$Id[] $VALUES = new JsonTypeInfo$Id[]{NONE, CLASS, MINIMAL_CLASS, NAME, CUSTOM};

   public static JsonTypeInfo$Id[] values() {
      return (JsonTypeInfo$Id[])$VALUES.clone();
   }

   public static JsonTypeInfo$Id valueOf(String var0) {
      return (JsonTypeInfo$Id)Enum.valueOf(JsonTypeInfo$Id.class, var0);
   }

   private JsonTypeInfo$Id(String var1, int var2, String var3) {
      super(var1, var2);
      this._defaultPropertyName = var3;
   }

   public String getDefaultPropertyName() {
      return this._defaultPropertyName;
   }
}
