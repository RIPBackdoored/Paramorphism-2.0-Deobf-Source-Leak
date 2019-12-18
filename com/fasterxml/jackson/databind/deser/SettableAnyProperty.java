package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class SettableAnyProperty implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final BeanProperty _property;
   protected final AnnotatedMember _setter;
   final boolean _setterIsField;
   protected final JavaType _type;
   protected JsonDeserializer _valueDeserializer;
   protected final TypeDeserializer _valueTypeDeserializer;
   protected final KeyDeserializer _keyDeserializer;

   public SettableAnyProperty(BeanProperty var1, AnnotatedMember var2, JavaType var3, KeyDeserializer var4, JsonDeserializer var5, TypeDeserializer var6) {
      super();
      this._property = var1;
      this._setter = var2;
      this._type = var3;
      this._valueDeserializer = var5;
      this._valueTypeDeserializer = var6;
      this._keyDeserializer = var4;
      this._setterIsField = var2 instanceof AnnotatedField;
   }

   /** @deprecated */
   @Deprecated
   public SettableAnyProperty(BeanProperty var1, AnnotatedMember var2, JavaType var3, JsonDeserializer var4, TypeDeserializer var5) {
      this(var1, var2, var3, (KeyDeserializer)null, var4, var5);
   }

   public SettableAnyProperty withValueDeserializer(JsonDeserializer var1) {
      return new SettableAnyProperty(this._property, this._setter, this._type, this._keyDeserializer, var1, this._valueTypeDeserializer);
   }

   public void fixAccess(DeserializationConfig var1) {
      this._setter.fixAccess(var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
   }

   Object readResolve() {
      if (this._setter != null && this._setter.getAnnotated() != null) {
         return this;
      } else {
         throw new IllegalArgumentException("Missing method (broken JDK (de)serialization?)");
      }
   }

   public BeanProperty getProperty() {
      return this._property;
   }

   public boolean hasValueDeserializer() {
      return this._valueDeserializer != null;
   }

   public JavaType getType() {
      return this._type;
   }

   public final void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3, String var4) throws IOException {
      try {
         Object var5 = this._keyDeserializer == null ? var4 : this._keyDeserializer.deserializeKey(var4, var2);
         this.set(var3, var5, this.deserialize(var1, var2));
      } catch (UnresolvedForwardReference var7) {
         if (this._valueDeserializer.getObjectIdReader() == null) {
            throw JsonMappingException.from((JsonParser)var1, "Unresolved forward reference but no identity info.", var7);
         }

         SettableAnyProperty$AnySetterReferring var6 = new SettableAnyProperty$AnySetterReferring(this, var7, this._type.getRawClass(), var3, var4);
         var7.getRoid().appendReferring(var6);
      }

   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 == JsonToken.VALUE_NULL) {
         return this._valueDeserializer.getNullValue(var2);
      } else {
         return this._valueTypeDeserializer != null ? this._valueDeserializer.deserializeWithType(var1, var2, this._valueTypeDeserializer) : this._valueDeserializer.deserialize(var1, var2);
      }
   }

   public void set(Object var1, Object var2, Object var3) throws IOException {
      try {
         if (this._setterIsField) {
            AnnotatedField var4 = (AnnotatedField)this._setter;
            Map var5 = (Map)var4.getValue(var1);
            if (var5 != null) {
               var5.put(var2, var3);
            }
         } else {
            ((AnnotatedMethod)this._setter).callOnWith(var1, var2, var3);
         }
      } catch (Exception var6) {
         this._throwAsIOE(var6, var2, var3);
      }

   }

   protected void _throwAsIOE(Exception var1, Object var2, Object var3) throws IOException {
      if (var1 instanceof IllegalArgumentException) {
         String var7 = ClassUtil.classNameOf(var3);
         StringBuilder var5 = (new StringBuilder("Problem deserializing \"any\" property '")).append(var2);
         var5.append("' of class " + this.getClassName() + " (expected type: ").append(this._type);
         var5.append("; actual type: ").append(var7).append(")");
         String var6 = var1.getMessage();
         if (var6 != null) {
            var5.append(", problem: ").append(var6);
         } else {
            var5.append(" (no error message provided)");
         }

         throw new JsonMappingException((Closeable)null, var5.toString(), var1);
      } else {
         ClassUtil.throwIfIOE(var1);
         ClassUtil.throwIfRTE(var1);
         Throwable var4 = ClassUtil.getRootCause(var1);
         throw new JsonMappingException((Closeable)null, var4.getMessage(), var4);
      }
   }

   private String getClassName() {
      return this._setter.getDeclaringClass().getName();
   }

   public String toString() {
      return "[any property on class " + this.getClassName() + "]";
   }
}
