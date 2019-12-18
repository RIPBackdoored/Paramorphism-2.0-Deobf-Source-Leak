package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import java.io.IOException;
import java.lang.reflect.Type;

public class ClassSerializer extends StdScalarSerializer {
   public ClassSerializer() {
      super(Class.class, false);
   }

   public void serialize(Class var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeString(var1.getName());
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode("string", true);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      this.visitStringFormat(var1, var2);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Class)var1, var2, var3);
   }
}
