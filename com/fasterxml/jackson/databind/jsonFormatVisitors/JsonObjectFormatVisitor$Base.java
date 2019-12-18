package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonObjectFormatVisitor$Base implements JsonObjectFormatVisitor {
   protected SerializerProvider _provider;

   public JsonObjectFormatVisitor$Base() {
      super();
   }

   public JsonObjectFormatVisitor$Base(SerializerProvider var1) {
      super();
      this._provider = var1;
   }

   public SerializerProvider getProvider() {
      return this._provider;
   }

   public void setProvider(SerializerProvider var1) {
      this._provider = var1;
   }

   public void property(BeanProperty var1) throws JsonMappingException {
   }

   public void property(String var1, JsonFormatVisitable var2, JavaType var3) throws JsonMappingException {
   }

   public void optionalProperty(BeanProperty var1) throws JsonMappingException {
   }

   public void optionalProperty(String var1, JsonFormatVisitable var2, JavaType var3) throws JsonMappingException {
   }
}
