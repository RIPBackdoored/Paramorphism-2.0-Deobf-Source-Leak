package com.fasterxml.jackson.databind.annotation;

public class JsonPOJOBuilder$Value {
   public final String buildMethodName;
   public final String withPrefix;

   public JsonPOJOBuilder$Value(JsonPOJOBuilder var1) {
      this(var1.buildMethodName(), var1.withPrefix());
   }

   public JsonPOJOBuilder$Value(String var1, String var2) {
      super();
      this.buildMethodName = var1;
      this.withPrefix = var2;
   }
}
