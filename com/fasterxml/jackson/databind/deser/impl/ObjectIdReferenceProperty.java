package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class ObjectIdReferenceProperty extends SettableBeanProperty {
   private static final long serialVersionUID = 1L;
   private final SettableBeanProperty _forward;

   public ObjectIdReferenceProperty(SettableBeanProperty var1, ObjectIdInfo var2) {
      super(var1);
      this._forward = var1;
      this._objectIdInfo = var2;
   }

   public ObjectIdReferenceProperty(ObjectIdReferenceProperty var1, JsonDeserializer var2, NullValueProvider var3) {
      super(var1, var2, var3);
      this._forward = var1._forward;
      this._objectIdInfo = var1._objectIdInfo;
   }

   public ObjectIdReferenceProperty(ObjectIdReferenceProperty var1, PropertyName var2) {
      super(var1, var2);
      this._forward = var1._forward;
      this._objectIdInfo = var1._objectIdInfo;
   }

   public SettableBeanProperty withName(PropertyName var1) {
      return new ObjectIdReferenceProperty(this, var1);
   }

   public SettableBeanProperty withValueDeserializer(JsonDeserializer var1) {
      return this._valueDeserializer == var1 ? this : new ObjectIdReferenceProperty(this, var1, this._nullProvider);
   }

   public SettableBeanProperty withNullProvider(NullValueProvider var1) {
      return new ObjectIdReferenceProperty(this, this._valueDeserializer, var1);
   }

   public void fixAccess(DeserializationConfig var1) {
      if (this._forward != null) {
         this._forward.fixAccess(var1);
      }

   }

   public Annotation getAnnotation(Class var1) {
      return this._forward.getAnnotation(var1);
   }

   public AnnotatedMember getMember() {
      return this._forward.getMember();
   }

   public int getCreatorIndex() {
      return this._forward.getCreatorIndex();
   }

   public void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      this.deserializeSetAndReturn(var1, var2, var3);
   }

   public Object deserializeSetAndReturn(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      Object var10000;
      try {
         var10000 = this.setAndReturn(var3, this.deserialize(var1, var2));
      } catch (UnresolvedForwardReference var6) {
         boolean var5 = this._objectIdInfo != null || this._valueDeserializer.getObjectIdReader() != null;
         if (!var5) {
            throw JsonMappingException.from((JsonParser)var1, "Unresolved forward reference but no identity info", var6);
         }

         var6.getRoid().appendReferring(new ObjectIdReferenceProperty$PropertyReferring(this, var6, this._type.getRawClass(), var3));
         return null;
      }

      return var10000;
   }

   public void set(Object var1, Object var2) throws IOException {
      this._forward.set(var1, var2);
   }

   public Object setAndReturn(Object var1, Object var2) throws IOException {
      return this._forward.setAndReturn(var1, var2);
   }
}
