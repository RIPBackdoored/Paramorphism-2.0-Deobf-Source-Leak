package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;

public final class TypeWrappedSerializer extends JsonSerializer implements ContextualSerializer {
   protected final TypeSerializer _typeSerializer;
   protected final JsonSerializer _serializer;

   public TypeWrappedSerializer(TypeSerializer var1, JsonSerializer var2) {
      super();
      this._typeSerializer = var1;
      this._serializer = var2;
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this._serializer.serializeWithType(var1, var2, var3, this._typeSerializer);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this._serializer.serializeWithType(var1, var2, var3, var4);
   }

   public Class handledType() {
      return Object.class;
   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      JsonSerializer var3 = this._serializer;
      if (var3 instanceof ContextualSerializer) {
         var3 = var1.handleSecondaryContextualization(var3, var2);
      }

      return var3 == this._serializer ? this : new TypeWrappedSerializer(this._typeSerializer, var3);
   }

   public JsonSerializer valueSerializer() {
      return this._serializer;
   }

   public TypeSerializer typeSerializer() {
      return this._typeSerializer;
   }
}
