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

public final class SetterlessProperty extends SettableBeanProperty {
   private static final long serialVersionUID = 1L;
   protected final AnnotatedMethod _annotated;
   protected final Method _getter;

   public SetterlessProperty(BeanPropertyDefinition var1, JavaType var2, TypeDeserializer var3, Annotations var4, AnnotatedMethod var5) {
      super(var1, var2, var3, var4);
      this._annotated = var5;
      this._getter = var5.getAnnotated();
   }

   protected SetterlessProperty(SetterlessProperty var1, JsonDeserializer var2, NullValueProvider var3) {
      super(var1, var2, var3);
      this._annotated = var1._annotated;
      this._getter = var1._getter;
   }

   protected SetterlessProperty(SetterlessProperty var1, PropertyName var2) {
      super(var1, var2);
      this._annotated = var1._annotated;
      this._getter = var1._getter;
   }

   public SettableBeanProperty withName(PropertyName var1) {
      return new SetterlessProperty(this, var1);
   }

   public SettableBeanProperty withValueDeserializer(JsonDeserializer var1) {
      return this._valueDeserializer == var1 ? this : new SetterlessProperty(this, var1, this._nullProvider);
   }

   public SettableBeanProperty withNullProvider(NullValueProvider var1) {
      return new SetterlessProperty(this, this._valueDeserializer, var1);
   }

   public void fixAccess(DeserializationConfig var1) {
      this._annotated.fixAccess(var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
   }

   public Annotation getAnnotation(Class var1) {
      return this._annotated.getAnnotation(var1);
   }

   public AnnotatedMember getMember() {
      return this._annotated;
   }

   public final void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      JsonToken var4 = var1.getCurrentToken();
      if (var4 != JsonToken.VALUE_NULL) {
         if (this._valueTypeDeserializer != null) {
            var2.reportBadDefinition(this.getType(), String.format("Problem deserializing 'setterless' property (\"%s\"): no way to handle typed deser with setterless yet", this.getName()));
         }

         Object var5;
         try {
            var5 = this._getter.invoke(var3, (Object[])null);
         } catch (Exception var7) {
            this._throwAsIOE(var1, var7);
            return;
         }

         if (var5 == null) {
            var2.reportBadDefinition(this.getType(), String.format("Problem deserializing 'setterless' property '%s': get method returned null", this.getName()));
         }

         this._valueDeserializer.deserialize(var1, var2, var5);
      }
   }

   public Object deserializeSetAndReturn(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      this.deserializeAndSet(var1, var2, var3);
      return var3;
   }

   public final void set(Object var1, Object var2) throws IOException {
      throw new UnsupportedOperationException("Should never call `set()` on setterless property ('" + this.getName() + "')");
   }

   public Object setAndReturn(Object var1, Object var2) throws IOException {
      this.set(var1, var2);
      return var1;
   }
}
