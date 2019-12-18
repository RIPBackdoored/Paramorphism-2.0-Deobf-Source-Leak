package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public final class StringSerializer extends StdScalarSerializer {
   private static final long serialVersionUID = 1L;

   public StringSerializer() {
      super(String.class, false);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      String var3 = (String)var2;
      return var3.length() == 0;
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeString((String)var1);
   }

   public final void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      var2.writeString((String)var1);
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode("string", true);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      this.visitStringFormat(var1, var2);
   }
}
