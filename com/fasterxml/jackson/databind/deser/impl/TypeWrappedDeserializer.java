package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public final class TypeWrappedDeserializer extends JsonDeserializer implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final TypeDeserializer _typeDeserializer;
   protected final JsonDeserializer _deserializer;

   public TypeWrappedDeserializer(TypeDeserializer var1, JsonDeserializer var2) {
      super();
      this._typeDeserializer = var1;
      this._deserializer = var2;
   }

   public Class handledType() {
      return this._deserializer.handledType();
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return this._deserializer.supportsUpdate(var1);
   }

   public JsonDeserializer getDelegatee() {
      return this._deserializer.getDelegatee();
   }

   public Collection getKnownPropertyNames() {
      return this._deserializer.getKnownPropertyNames();
   }

   public Object getNullValue(DeserializationContext var1) throws JsonMappingException {
      return this._deserializer.getNullValue(var1);
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return this._deserializer.getEmptyValue(var1);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._deserializer.deserializeWithType(var1, var2, this._typeDeserializer);
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      throw new IllegalStateException("Type-wrapped deserializer's deserializeWithType should never get called");
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this._deserializer.deserialize(var1, var2, var3);
   }
}
