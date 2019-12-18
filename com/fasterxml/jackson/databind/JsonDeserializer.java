package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Collection;

public abstract class JsonDeserializer implements NullValueProvider {
   public JsonDeserializer() {
      super();
   }

   public abstract Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException;

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      if (var2.isEnabled(MapperFeature.IGNORE_MERGE_FOR_UNMERGEABLE)) {
         return this.deserialize(var1, var2);
      } else {
         throw new UnsupportedOperationException("Cannot update object of type " + var3.getClass().getName() + " (by deserializer of type " + this.getClass().getName() + ")");
      }
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var3.deserializeTypedFromAny(var1, var2);
   }

   public JsonDeserializer unwrappingDeserializer(NameTransformer var1) {
      return this;
   }

   public JsonDeserializer replaceDelegatee(JsonDeserializer var1) {
      throw new UnsupportedOperationException();
   }

   public Class handledType() {
      return null;
   }

   public boolean isCachable() {
      return false;
   }

   public JsonDeserializer getDelegatee() {
      return null;
   }

   public Collection getKnownPropertyNames() {
      return null;
   }

   public Object getNullValue(DeserializationContext var1) throws JsonMappingException {
      return this.getNullValue();
   }

   public AccessPattern getNullAccessPattern() {
      return AccessPattern.CONSTANT;
   }

   public AccessPattern getEmptyAccessPattern() {
      return AccessPattern.DYNAMIC;
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return this.getNullValue(var1);
   }

   public ObjectIdReader getObjectIdReader() {
      return null;
   }

   public SettableBeanProperty findBackReference(String var1) {
      throw new IllegalArgumentException("Cannot handle managed/back reference '" + var1 + "': type: value deserializer of type " + this.getClass().getName() + " does not support them");
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Object getNullValue() {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Object getEmptyValue() {
      return this.getNullValue();
   }
}
