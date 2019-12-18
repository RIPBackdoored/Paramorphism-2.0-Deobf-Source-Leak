package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class StdArraySerializers$CharArraySerializer extends StdSerializer {
   public StdArraySerializers$CharArraySerializer() {
      super(char[].class);
   }

   public boolean isEmpty(SerializerProvider var1, char[] var2) {
      return var2.length == 0;
   }

   public void serialize(char[] var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (var3.isEnabled(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)) {
         var2.writeStartArray(var1.length);
         var2.setCurrentValue(var1);
         this._writeArrayContents(var2, var1);
         var2.writeEndArray();
      } else {
         var2.writeString(var1, 0, var1.length);
      }

   }

   public void serializeWithType(char[] var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      boolean var5 = var3.isEnabled(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS);
      WritableTypeId var6;
      if (var5) {
         var6 = var4.writeTypePrefix(var2, var4.typeId(var1, JsonToken.START_ARRAY));
         this._writeArrayContents(var2, var1);
      } else {
         var6 = var4.writeTypePrefix(var2, var4.typeId(var1, JsonToken.VALUE_STRING));
         var2.writeString(var1, 0, var1.length);
      }

      var4.writeTypeSuffix(var2, var6);
   }

   private final void _writeArrayContents(JsonGenerator var1, char[] var2) throws IOException {
      int var3 = 0;

      for(int var4 = var2.length; var3 < var4; ++var3) {
         var1.writeString(var2, var3, 1);
      }

   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      ObjectNode var3 = this.createSchemaNode("array", true);
      ObjectNode var4 = this.createSchemaNode("string");
      var4.put("type", "string");
      return var3.set("items", var4);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      this.visitArrayFormat(var1, var2, JsonFormatTypes.STRING);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((char[])var1, var2, var3);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (char[])var2);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((char[])var1, var2, var3, var4);
   }
}
