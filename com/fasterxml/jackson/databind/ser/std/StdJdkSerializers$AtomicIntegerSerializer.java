package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicInteger;

public class StdJdkSerializers$AtomicIntegerSerializer extends StdScalarSerializer {
   public StdJdkSerializers$AtomicIntegerSerializer() {
      super(AtomicInteger.class, false);
   }

   public void serialize(AtomicInteger var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException {
      var2.writeNumber(var1.get());
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode("integer", true);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      this.visitIntFormat(var1, var2, JsonParser$NumberType.INT);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((AtomicInteger)var1, var2, var3);
   }
}
