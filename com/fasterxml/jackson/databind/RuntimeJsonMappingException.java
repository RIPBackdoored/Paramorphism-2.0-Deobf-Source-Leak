package com.fasterxml.jackson.databind;

public class RuntimeJsonMappingException extends RuntimeException {
   public RuntimeJsonMappingException(JsonMappingException var1) {
      super(var1);
   }

   public RuntimeJsonMappingException(String var1) {
      super(var1);
   }

   public RuntimeJsonMappingException(String var1, JsonMappingException var2) {
      super(var1, var2);
   }
}
