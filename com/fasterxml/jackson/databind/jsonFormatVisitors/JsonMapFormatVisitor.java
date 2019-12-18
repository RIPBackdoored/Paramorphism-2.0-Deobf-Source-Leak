package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface JsonMapFormatVisitor extends JsonFormatVisitorWithSerializerProvider {
   void keyFormat(JsonFormatVisitable var1, JavaType var2) throws JsonMappingException;

   void valueFormat(JsonFormatVisitable var1, JavaType var2) throws JsonMappingException;
}
