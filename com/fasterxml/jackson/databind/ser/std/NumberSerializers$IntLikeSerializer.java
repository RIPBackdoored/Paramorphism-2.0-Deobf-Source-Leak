package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public final class NumberSerializers$IntLikeSerializer extends NumberSerializers$Base {
   static final NumberSerializers$IntLikeSerializer instance = new NumberSerializers$IntLikeSerializer();

   public NumberSerializers$IntLikeSerializer() {
      super(Number.class, JsonParser$NumberType.INT, "integer");
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeNumber(((Number)var1).intValue());
   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      return super.createContextual(var1, var2);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      super.acceptJsonFormatVisitor(var1, var2);
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return super.getSchema(var1, var2);
   }
}
