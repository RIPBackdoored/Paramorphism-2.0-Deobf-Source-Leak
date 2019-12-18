package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializable$Base;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

@JacksonStdImpl
public class SerializableSerializer extends StdSerializer {
   public static final SerializableSerializer instance = new SerializableSerializer();

   protected SerializableSerializer() {
      super(JsonSerializable.class);
   }

   public boolean isEmpty(SerializerProvider var1, JsonSerializable var2) {
      return var2 instanceof JsonSerializable$Base ? ((JsonSerializable$Base)var2).isEmpty(var1) : false;
   }

   public void serialize(JsonSerializable var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var1.serialize(var2, var3);
   }

   public final void serializeWithType(JsonSerializable var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      var1.serializeWithType(var2, var3, var4);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      var1.expectAnyFormat(var2);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((JsonSerializable)var1, var2, var3);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (JsonSerializable)var2);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((JsonSerializable)var1, var2, var3, var4);
   }
}
