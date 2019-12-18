package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.io.IOException;
import java.lang.annotation.Annotation;

public final class ObjectIdValueProperty extends SettableBeanProperty {
   private static final long serialVersionUID = 1L;
   protected final ObjectIdReader _objectIdReader;

   public ObjectIdValueProperty(ObjectIdReader var1, PropertyMetadata var2) {
      super(var1.propertyName, var1.getIdType(), var2, var1.getDeserializer());
      this._objectIdReader = var1;
   }

   protected ObjectIdValueProperty(ObjectIdValueProperty var1, JsonDeserializer var2, NullValueProvider var3) {
      super(var1, var2, var3);
      this._objectIdReader = var1._objectIdReader;
   }

   protected ObjectIdValueProperty(ObjectIdValueProperty var1, PropertyName var2) {
      super(var1, var2);
      this._objectIdReader = var1._objectIdReader;
   }

   public SettableBeanProperty withName(PropertyName var1) {
      return new ObjectIdValueProperty(this, var1);
   }

   public SettableBeanProperty withValueDeserializer(JsonDeserializer var1) {
      return this._valueDeserializer == var1 ? this : new ObjectIdValueProperty(this, var1, this._nullProvider);
   }

   public SettableBeanProperty withNullProvider(NullValueProvider var1) {
      return new ObjectIdValueProperty(this, this._valueDeserializer, var1);
   }

   public Annotation getAnnotation(Class var1) {
      return null;
   }

   public AnnotatedMember getMember() {
      return null;
   }

   public void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      this.deserializeSetAndReturn(var1, var2, var3);
   }

   public Object deserializeSetAndReturn(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      if (var1.hasToken(JsonToken.VALUE_NULL)) {
         return null;
      } else {
         Object var4 = this._valueDeserializer.deserialize(var1, var2);
         ReadableObjectId var5 = var2.findObjectId(var4, this._objectIdReader.generator, this._objectIdReader.resolver);
         var5.bindItem(var3);
         SettableBeanProperty var6 = this._objectIdReader.idProperty;
         return var6 != null ? var6.setAndReturn(var3, var4) : var3;
      }
   }

   public void set(Object var1, Object var2) throws IOException {
      this.setAndReturn(var1, var2);
   }

   public Object setAndReturn(Object var1, Object var2) throws IOException {
      SettableBeanProperty var3 = this._objectIdReader.idProperty;
      if (var3 == null) {
         throw new UnsupportedOperationException("Should not call set() on ObjectIdProperty that has no SettableBeanProperty");
      } else {
         return var3.setAndReturn(var1, var2);
      }
   }
}
