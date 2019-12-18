package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StaticListSerializerBase;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@JacksonStdImpl
public class StringCollectionSerializer extends StaticListSerializerBase {
   public static final StringCollectionSerializer instance = new StringCollectionSerializer();

   protected StringCollectionSerializer() {
      super(Collection.class);
   }

   protected StringCollectionSerializer(StringCollectionSerializer var1, Boolean var2) {
      super(var1, var2);
   }

   public JsonSerializer _withResolved(BeanProperty var1, Boolean var2) {
      return new StringCollectionSerializer(this, var2);
   }

   protected JsonNode contentSchema() {
      return this.createSchemaNode("string", true);
   }

   protected void acceptContentVisitor(JsonArrayFormatVisitor var1) throws JsonMappingException {
      var1.itemsFormat(JsonFormatTypes.STRING);
   }

   public void serialize(Collection var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.setCurrentValue(var1);
      int var4 = var1.size();
      if (var4 != 1 || (this._unwrapSingle != null || !var3.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) && this._unwrapSingle != Boolean.TRUE) {
         var2.writeStartArray(var4);
         this.serializeContents(var1, var2, var3);
         var2.writeEndArray();
      } else {
         this.serializeContents(var1, var2, var3);
      }
   }

   public void serializeWithType(Collection var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      var2.setCurrentValue(var1);
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, JsonToken.START_ARRAY));
      this.serializeContents(var1, var2, var3);
      var4.writeTypeSuffix(var2, var5);
   }

   private final void serializeContents(Collection var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      int var4 = 0;

      try {
         for(Iterator var5 = var1.iterator(); var5.hasNext(); ++var4) {
            String var6 = (String)var5.next();
            if (var6 == null) {
               var3.defaultSerializeNull(var2);
            } else {
               var2.writeString(var6);
            }
         }
      } catch (Exception var7) {
         this.wrapAndThrow(var3, var7, var1, var4);
      }

   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Collection)var1, var2, var3);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((Collection)var1, var2, var3, var4);
   }
}
