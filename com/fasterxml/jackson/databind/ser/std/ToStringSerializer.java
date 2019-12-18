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
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class ToStringSerializer extends StdSerializer {
   public static final ToStringSerializer instance = new ToStringSerializer();

   public ToStringSerializer() {
      super(Object.class);
   }

   public ToStringSerializer(Class var1) {
      super(var1, false);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return var2.toString().isEmpty();
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeString(var1.toString());
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, JsonToken.VALUE_STRING));
      this.serialize(var1, var2, var3);
      var4.writeTypeSuffix(var2, var5);
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) throws JsonMappingException {
      return this.createSchemaNode("string", true);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      this.visitStringFormat(var1, var2);
   }
}
