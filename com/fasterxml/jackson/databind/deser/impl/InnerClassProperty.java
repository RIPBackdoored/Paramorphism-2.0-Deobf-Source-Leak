package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty$Delegating;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.lang.reflect.Constructor;

public final class InnerClassProperty extends SettableBeanProperty$Delegating {
   private static final long serialVersionUID = 1L;
   protected final transient Constructor _creator;
   protected AnnotatedConstructor _annotated;

   public InnerClassProperty(SettableBeanProperty var1, Constructor var2) {
      super(var1);
      this._creator = var2;
   }

   protected InnerClassProperty(SettableBeanProperty var1, AnnotatedConstructor var2) {
      super(var1);
      this._annotated = var2;
      this._creator = this._annotated == null ? null : this._annotated.getAnnotated();
      if (this._creator == null) {
         throw new IllegalArgumentException("Missing constructor (broken JDK (de)serialization?)");
      }
   }

   protected SettableBeanProperty withDelegate(SettableBeanProperty var1) {
      return var1 == this.delegate ? this : new InnerClassProperty(var1, this._creator);
   }

   public void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      JsonToken var4 = var1.getCurrentToken();
      Object var5;
      if (var4 == JsonToken.VALUE_NULL) {
         var5 = this._valueDeserializer.getNullValue(var2);
      } else if (this._valueTypeDeserializer != null) {
         var5 = this._valueDeserializer.deserializeWithType(var1, var2, this._valueTypeDeserializer);
      } else {
         try {
            var5 = this._creator.newInstance(var3);
         } catch (Exception var7) {
            ClassUtil.unwrapAndThrowAsIAE(var7, String.format("Failed to instantiate class %s, problem: %s", this._creator.getDeclaringClass().getName(), var7.getMessage()));
            var5 = null;
         }

         this._valueDeserializer.deserialize(var1, var2, var5);
      }

      this.set(var3, var5);
   }

   public Object deserializeSetAndReturn(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.setAndReturn(var3, this.deserialize(var1, var2));
   }

   Object readResolve() {
      return new InnerClassProperty(this, this._annotated);
   }

   Object writeReplace() {
      return this._annotated == null ? new InnerClassProperty(this, new AnnotatedConstructor((TypeResolutionContext)null, this._creator, (AnnotationMap)null, (AnnotationMap[])null)) : this;
   }
}
