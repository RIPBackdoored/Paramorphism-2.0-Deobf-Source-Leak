package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class StdArraySerializers$IntArraySerializer extends ArraySerializerBase {
   private static final JavaType VALUE_TYPE;

   public StdArraySerializers$IntArraySerializer() {
      super(int[].class);
   }

   protected StdArraySerializers$IntArraySerializer(StdArraySerializers$IntArraySerializer var1, BeanProperty var2, Boolean var3) {
      super(var1, var2, var3);
   }

   public JsonSerializer _withResolved(BeanProperty var1, Boolean var2) {
      return new StdArraySerializers$IntArraySerializer(this, var1, var2);
   }

   public ContainerSerializer _withValueTypeSerializer(TypeSerializer var1) {
      return this;
   }

   public JavaType getContentType() {
      return VALUE_TYPE;
   }

   public JsonSerializer getContentSerializer() {
      return null;
   }

   public boolean isEmpty(SerializerProvider var1, int[] var2) {
      return var2.length == 0;
   }

   public boolean hasSingleElement(int[] var1) {
      return var1.length == 1;
   }

   public final void serialize(int[] var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      int var4 = var1.length;
      if (var4 == 1 && this._shouldUnwrapSingle(var3)) {
         this.serializeContents(var1, var2, var3);
      } else {
         var2.setCurrentValue(var1);
         var2.writeArray((int[])var1, 0, var1.length);
      }
   }

   public void serializeContents(int[] var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      int var4 = 0;

      for(int var5 = var1.length; var4 < var5; ++var4) {
         var2.writeNumber(var1[var4]);
      }

   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode("array", true).set("items", this.createSchemaNode("integer"));
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      this.visitArrayFormat(var1, var2, JsonFormatTypes.INTEGER);
   }

   public void serializeContents(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serializeContents((int[])var1, var2, var3);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((int[])var1, var2, var3);
   }

   public boolean hasSingleElement(Object var1) {
      return this.hasSingleElement((int[])var1);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (int[])var2);
   }

   static {
      VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Integer.TYPE);
   }
}
