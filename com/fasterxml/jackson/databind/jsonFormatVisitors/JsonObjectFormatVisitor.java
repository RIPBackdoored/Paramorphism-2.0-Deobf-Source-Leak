package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface JsonObjectFormatVisitor extends JsonFormatVisitorWithSerializerProvider {
   void property(BeanProperty var1) throws JsonMappingException;

   void property(String var1, JsonFormatVisitable var2, JavaType var3) throws JsonMappingException;

   void optionalProperty(BeanProperty var1) throws JsonMappingException;

   void optionalProperty(String var1, JsonFormatVisitable var2, JavaType var3) throws JsonMappingException;
}
