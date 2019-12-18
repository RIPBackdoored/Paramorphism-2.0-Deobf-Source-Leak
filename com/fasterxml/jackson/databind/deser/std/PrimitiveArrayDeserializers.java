package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.fasterxml.jackson.databind.deser.impl.NullsFailProvider;
import com.fasterxml.jackson.databind.exc.InvalidNullException;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;
import java.lang.reflect.Array;

public abstract class PrimitiveArrayDeserializers extends StdDeserializer implements ContextualDeserializer {
   protected final Boolean _unwrapSingle;
   private transient Object _emptyValue;
   protected final NullValueProvider _nuller;

   protected PrimitiveArrayDeserializers(Class var1) {
      super(var1);
      this._unwrapSingle = null;
      this._nuller = null;
   }

   protected PrimitiveArrayDeserializers(PrimitiveArrayDeserializers var1, NullValueProvider var2, Boolean var3) {
      super(var1._valueClass);
      this._unwrapSingle = var3;
      this._nuller = var2;
   }

   public static JsonDeserializer forType(Class var0) {
      if (var0 == Integer.TYPE) {
         return PrimitiveArrayDeserializers$IntDeser.instance;
      } else if (var0 == Long.TYPE) {
         return PrimitiveArrayDeserializers$LongDeser.instance;
      } else if (var0 == Byte.TYPE) {
         return new PrimitiveArrayDeserializers$ByteDeser();
      } else if (var0 == Short.TYPE) {
         return new PrimitiveArrayDeserializers$ShortDeser();
      } else if (var0 == Float.TYPE) {
         return new PrimitiveArrayDeserializers$FloatDeser();
      } else if (var0 == Double.TYPE) {
         return new PrimitiveArrayDeserializers$DoubleDeser();
      } else if (var0 == Boolean.TYPE) {
         return new PrimitiveArrayDeserializers$BooleanDeser();
      } else if (var0 == Character.TYPE) {
         return new PrimitiveArrayDeserializers$CharDeser();
      } else {
         throw new IllegalStateException();
      }
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      Boolean var3 = this.findFormatFeature(var1, var2, this._valueClass, JsonFormat$Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      Object var4 = null;
      Nulls var5 = this.findContentNullStyle(var1, var2);
      if (var5 == Nulls.SKIP) {
         var4 = NullsConstantProvider.skipper();
      } else if (var5 == Nulls.FAIL) {
         if (var2 == null) {
            var4 = NullsFailProvider.constructForRootValue(var1.constructType(this._valueClass));
         } else {
            var4 = NullsFailProvider.constructForProperty(var2);
         }
      }

      return var3 == this._unwrapSingle && var4 == this._nuller ? this : this.withResolved((NullValueProvider)var4, var3);
   }

   protected abstract Object _concat(Object var1, Object var2);

   protected abstract Object handleSingleElementUnwrapped(JsonParser var1, DeserializationContext var2) throws IOException;

   protected abstract PrimitiveArrayDeserializers withResolved(NullValueProvider var1, Boolean var2);

   protected abstract Object _constructEmpty();

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return Boolean.TRUE;
   }

   public AccessPattern getEmptyAccessPattern() {
      return AccessPattern.CONSTANT;
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      Object var2 = this._emptyValue;
      if (var2 == null) {
         this._emptyValue = var2 = this._constructEmpty();
      }

      return var2;
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var3.deserializeTypedFromArray(var1, var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      Object var4 = this.deserialize(var1, var2);
      if (var3 == null) {
         return var4;
      } else {
         int var5 = Array.getLength(var3);
         return var5 == 0 ? var4 : this._concat(var3, var4);
      }
   }

   protected Object handleNonArray(JsonParser var1, DeserializationContext var2) throws IOException {
      if (var1.hasToken(JsonToken.VALUE_STRING) && var2.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && var1.getText().length() == 0) {
         return null;
      } else {
         boolean var3 = this._unwrapSingle == Boolean.TRUE || this._unwrapSingle == null && var2.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
         return var3 ? this.handleSingleElementUnwrapped(var1, var2) : var2.handleUnexpectedToken(this._valueClass, var1);
      }
   }

   protected void _failOnNull(DeserializationContext var1) throws IOException {
      throw InvalidNullException.from(var1, (PropertyName)null, var1.constructType(this._valueClass));
   }
}
