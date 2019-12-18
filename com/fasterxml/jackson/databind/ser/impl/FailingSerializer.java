package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

public class FailingSerializer extends StdSerializer {
   protected final String _msg;

   public FailingSerializer(String var1) {
      super(Object.class);
      this._msg = var1;
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var3.reportMappingProblem(this._msg);
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) throws JsonMappingException {
      return null;
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) {
   }
}
