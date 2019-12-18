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
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public final class FieldProperty extends SettableBeanProperty {
   private static final long serialVersionUID = 1L;
   protected final AnnotatedField _annotated;
   protected final transient Field _field;
   protected final boolean _skipNulls;

   public FieldProperty(BeanPropertyDefinition var1, JavaType var2, TypeDeserializer var3, Annotations var4, AnnotatedField var5) {
      super(var1, var2, var3, var4);
      this._annotated = var5;
      this._field = var5.getAnnotated();
      this._skipNulls = NullsConstantProvider.isSkipper(this._nullProvider);
   }

   protected FieldProperty(FieldProperty var1, JsonDeserializer var2, NullValueProvider var3) {
      super(var1, var2, var3);
      this._annotated = var1._annotated;
      this._field = var1._field;
      this._skipNulls = NullsConstantProvider.isSkipper(var3);
   }

   protected FieldProperty(FieldProperty var1, PropertyName var2) {
      super(var1, var2);
      this._annotated = var1._annotated;
      this._field = var1._field;
      this._skipNulls = var1._skipNulls;
   }

   protected FieldProperty(FieldProperty var1) {
      super(var1);
      this._annotated = var1._annotated;
      Field var2 = this._annotated.getAnnotated();
      if (var2 == null) {
         throw new IllegalArgumentException("Missing field (broken JDK (de)serialization?)");
      } else {
         this._field = var2;
         this._skipNulls = var1._skipNulls;
      }
   }

   public SettableBeanProperty withName(PropertyName var1) {
      return new FieldProperty(this, var1);
   }

   public SettableBeanProperty withValueDeserializer(JsonDeserializer var1) {
      return this._valueDeserializer == var1 ? this : new FieldProperty(this, var1, this._nullProvider);
   }

   public SettableBeanProperty withNullProvider(NullValueProvider var1) {
      return new FieldProperty(this, this._valueDeserializer, var1);
   }

   public void fixAccess(DeserializationConfig var1) {
      ClassUtil.checkAndFixAccess(this._field, var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
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
         this._field.set(var3, var4);
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

      try {
         this._field.set(var3, var4);
      } catch (Exception var6) {
         this._throwAsIOE(var1, var6, var4);
      }

      return var3;
   }

   public void set(Object var1, Object var2) throws IOException {
      try {
         this._field.set(var1, var2);
      } catch (Exception var4) {
         this._throwAsIOE(var4, var2);
      }

   }

   public Object setAndReturn(Object var1, Object var2) throws IOException {
      try {
         this._field.set(var1, var2);
      } catch (Exception var4) {
         this._throwAsIOE(var4, var2);
      }

      return var1;
   }

   Object readResolve() {
      return new FieldProperty(this);
   }
}
