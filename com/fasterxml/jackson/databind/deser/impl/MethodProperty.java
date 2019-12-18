package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class MethodProperty extends SettableBeanProperty {
   private static final long serialVersionUID = 1L;
   protected final AnnotatedMethod _annotated;
   protected final transient Method _setter;
   protected final boolean _skipNulls;

   public MethodProperty(BeanPropertyDefinition var1, JavaType var2, TypeDeserializer var3, Annotations var4, AnnotatedMethod var5) {
      super(var1, var2, var3, var4);
      this._annotated = var5;
      this._setter = var5.getAnnotated();
      this._skipNulls = NullsConstantProvider.isSkipper(this._nullProvider);
   }

   protected MethodProperty(MethodProperty var1, JsonDeserializer var2, NullValueProvider var3) {
      super(var1, var2, var3);
      this._annotated = var1._annotated;
      this._setter = var1._setter;
      this._skipNulls = NullsConstantProvider.isSkipper(var3);
   }

   protected MethodProperty(MethodProperty var1, PropertyName var2) {
      super(var1, var2);
      this._annotated = var1._annotated;
      this._setter = var1._setter;
      this._skipNulls = var1._skipNulls;
   }

   protected MethodProperty(MethodProperty var1, Method var2) {
      super(var1);
      this._annotated = var1._annotated;
      this._setter = var2;
      this._skipNulls = var1._skipNulls;
   }

   public SettableBeanProperty withName(PropertyName var1) {
      return new MethodProperty(this, var1);
   }

   public SettableBeanProperty withValueDeserializer(JsonDeserializer var1) {
      return this._valueDeserializer == var1 ? this : new MethodProperty(this, var1, this._nullProvider);
   }

   public SettableBeanProperty withNullProvider(NullValueProvider var1) {
      return new MethodProperty(this, this._valueDeserializer, var1);
   }

   public void fixAccess(DeserializationConfig var1) {
      this._annotated.fixAccess(var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
   }

   public Annotation getAnnotation(Class var1) {
      return this._annotated == null ? null : this._annotated.getAnnotation(var1);
   }

   public AnnotatedMember getMember() {
      return this._annotated;
   }

   public void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      Object var4;
      if (var1.hasToken(JsonToken.VALUE_NULL)) {
         if (this._skipNulls) {
            return;
         }

         var4 = this._nullProvider.getNullValue(var2);
      } else if (this._valueTypeDeserializer == null) {
         var4 = this._valueDeserializer.deserialize(var1, var2);
         if (var4 == null) {
            if (this._skipNulls) {
               return;
            }

            var4 = this._nullProvider.getNullValue(var2);
         }
      } else {
         var4 = this._valueDeserializer.deserializeWithType(var1, var2, this._valueTypeDeserializer);
      }

      try {
         this._setter.invoke(var3, var4);
      } catch (Exception var6) {
         this._throwAsIOE(var1, var6, var4);
      }

   }

   public Object deserializeSetAndReturn(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      Object var4;
      if (var1.hasToken(JsonToken.VALUE_NULL)) {
         if (this._skipNulls) {
            return var3;
         }

         var4 = this._nullProvider.getNullValue(var2);
      } else if (this._valueTypeDeserializer == null) {
         var4 = this._valueDeserializer.deserialize(var1, var2);
         if (var4 == null) {
            if (this._skipNulls) {
               return var3;
            }

            var4 = this._nullProvider.getNullValue(var2);
         }
      } else {
         var4 = this._valueDeserializer.deserializeWithType(var1, var2, this._valueTypeDeserializer);
      }

      Object var10000;
      try {
         Object var5 = this._setter.invoke(var3, var4);
         var10000 = var5 == null ? var3 : var5;
      } catch (Exception var6) {
         this._throwAsIOE(var1, var6, var4);
         return null;
      }

      return var10000;
   }

   public final void set(Object var1, Object var2) throws IOException {
      try {
         this._setter.invoke(var1, var2);
      } catch (Exception var4) {
         this._throwAsIOE(var4, var2);
      }

   }

   public Object setAndReturn(Object var1, Object var2) throws IOException {
      Object var10000;
      try {
         Object var3 = this._setter.invoke(var1, var2);
         var10000 = var3 == null ? var1 : var3;
      } catch (Exception var4) {
         this._throwAsIOE(var4, var2);
         return null;
      }

      return var10000;
   }

   Object readResolve() {
      return new MethodProperty(this, this._annotated.getAnnotated());
   }
}
