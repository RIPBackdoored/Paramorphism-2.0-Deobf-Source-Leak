package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class TokenBufferSerializer extends StdSerializer {
   public TokenBufferSerializer() {
      super(TokenBuffer.class);
   }

   public void serialize(TokenBuffer var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var1.serialize(var2);
   }

   public final void serializeWithType(TokenBuffer var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, JsonToken.VALUE_EMBEDDED_OBJECT));
      this.serialize(var1, var2, var3);
      var4.writeTypeSuffix(var2, var5);
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode("any", true);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      var1.expectAnyFormat(var2);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((TokenBuffer)var1, var2, var3);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((TokenBuffer)var1, var2, var3, var4);
   }
}
