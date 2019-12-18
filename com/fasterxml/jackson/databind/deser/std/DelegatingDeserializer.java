package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;
import java.util.Collection;

public abstract class DelegatingDeserializer extends StdDeserializer implements ContextualDeserializer, ResolvableDeserializer {
   private static final long serialVersionUID = 1L;
   protected final JsonDeserializer _delegatee;

   public DelegatingDeserializer(JsonDeserializer var1) {
      super(var1.handledType());
      this._delegatee = var1;
   }

   protected abstract JsonDeserializer newDelegatingInstance(JsonDeserializer var1);

   public void resolve(DeserializationContext var1) throws JsonMappingException {
      if (this._delegatee instanceof ResolvableDeserializer) {
         ((ResolvableDeserializer)this._delegatee).resolve(var1);
      }

   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      JavaType var3 = var1.constructType(this._delegatee.handledType());
      JsonDeserializer var4 = var1.handleSecondaryContextualization(this._delegatee, var2, var3);
      return (JsonDeserializer)(var4 == this._delegatee ? this : this.newDelegatingInstance(var4));
   }

   public JsonDeserializer replaceDelegatee(JsonDeserializer var1) {
      return (JsonDeserializer)(var1 == this._delegatee ? this : this.newDelegatingInstance(var1));
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._delegatee.deserialize(var1, var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this._delegatee.deserialize(var1, var2, var3);
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return this._delegatee.deserializeWithType(var1, var2, var3);
   }

   public boolean isCachable() {
      return this._delegatee.isCachable();
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return this._delegatee.supportsUpdate(var1);
   }

   public JsonDeserializer getDelegatee() {
      return this._delegatee;
   }

   public SettableBeanProperty findBackReference(String var1) {
      return this._delegatee.findBackReference(var1);
   }

   public AccessPattern getNullAccessPattern() {
      return this._delegatee.getNullAccessPattern();
   }

   public Object getNullValue(DeserializationContext var1) throws JsonMappingException {
      return this._delegatee.getNullValue(var1);
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return this._delegatee.getEmptyValue(var1);
   }

   public Collection getKnownPropertyNames() {
      return this._delegatee.getKnownPropertyNames();
   }

   public ObjectIdReader getObjectIdReader() {
      return this._delegatee.getObjectIdReader();
   }
}
