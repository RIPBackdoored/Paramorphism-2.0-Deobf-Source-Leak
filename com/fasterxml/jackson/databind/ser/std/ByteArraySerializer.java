package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class ByteArraySerializer extends StdSerializer {
   private static final long serialVersionUID = 1L;

   public ByteArraySerializer() {
      super(byte[].class);
   }

   public boolean isEmpty(SerializerProvider var1, byte[] var2) {
      return var2.length == 0;
   }

   public void serialize(byte[] var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeBinary(var3.getConfig().getBase64Variant(), var1, 0, var1.length);
   }

   public void serializeWithType(byte[] var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, JsonToken.VALUE_EMBEDDED_OBJECT));
      var2.writeBinary(var3.getConfig().getBase64Variant(), var1, 0, var1.length);
      var4.writeTypeSuffix(var2, var5);
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      ObjectNode var3 = this.createSchemaNode("array", true);
      ObjectNode var4 = this.createSchemaNode("byte");
      return var3.set("items", var4);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      JsonArrayFormatVisitor var3 = var1.expectArrayFormat(var2);
      if (var3 != null) {
         var3.itemsFormat(JsonFormatTypes.INTEGER);
      }

   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((byte[])var1, var2, var3);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (byte[])var2);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((byte[])var1, var2, var3, var4);
   }
}
