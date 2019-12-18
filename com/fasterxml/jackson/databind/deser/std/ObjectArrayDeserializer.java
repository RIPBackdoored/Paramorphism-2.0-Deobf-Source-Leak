package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.lang.reflect.Array;

@JacksonStdImpl
public class ObjectArrayDeserializer extends ContainerDeserializerBase implements ContextualDeserializer {
   private static final long serialVersionUID = 1L;
   protected static final Object[] NO_OBJECTS = new Object[0];
   protected final boolean _untyped;
   protected final Class _elementClass;
   protected JsonDeserializer _elementDeserializer;
   protected final TypeDeserializer _elementTypeDeserializer;

   public ObjectArrayDeserializer(JavaType var1, JsonDeserializer var2, TypeDeserializer var3) {
      super((JavaType)var1, (NullValueProvider)null, (Boolean)null);
      this._elementClass = var1.getContentType().getRawClass();
      this._untyped = this._elementClass == Object.class;
      this._elementDeserializer = var2;
      this._elementTypeDeserializer = var3;
   }

   protected ObjectArrayDeserializer(ObjectArrayDeserializer var1, JsonDeserializer var2, TypeDeserializer var3, NullValueProvider var4, Boolean var5) {
      super((ContainerDeserializerBase)var1, var4, var5);
      this._elementClass = var1._elementClass;
      this._untyped = var1._untyped;
      this._elementDeserializer = var2;
      this._elementTypeDeserializer = var3;
   }

   public ObjectArrayDeserializer withDeserializer(TypeDeserializer var1, JsonDeserializer var2) {
      return this.withResolved(var1, var2, this._nullProvider, this._unwrapSingle);
   }

   public ObjectArrayDeserializer withResolved(TypeDeserializer var1, JsonDeserializer var2, NullValueProvider var3, Boolean var4) {
      return var4 == this._unwrapSingle && var3 == this._nullProvider && var2 == this._elementDeserializer && var1 == this._elementTypeDeserializer ? this : new ObjectArrayDeserializer(this, var2, var1, var3, var4);
   }

   public boolean isCachable() {
      return this._elementDeserializer == null && this._elementTypeDeserializer == null;
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      JsonDeserializer var3 = this._elementDeserializer;
      Boolean var4 = this.findFormatFeature(var1, var2, this._containerType.getRawClass(), JsonFormat$Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      var3 = this.findConvertingContentDeserializer(var1, var2, var3);
      JavaType var5 = this._containerType.getContentType();
      if (var3 == null) {
         var3 = var1.findContextualValueDeserializer(var5, var2);
      } else {
         var3 = var1.handleSecondaryContextualization(var3, var2, var5);
      }

      TypeDeserializer var6 = this._elementTypeDeserializer;
      if (var6 != null) {
         var6 = var6.forProperty(var2);
      }

      NullValueProvider var7 = this.findContentNullProvider(var1, var2, var3);
      return this.withResolved(var6, var3, var7, var4);
   }

   public JsonDeserializer getContentDeserializer() {
      return this._elementDeserializer;
   }

   public AccessPattern getEmptyAccessPattern() {
      return AccessPattern.CONSTANT;
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return NO_OBJECTS;
   }

   public Object[] deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (!var1.isExpectedStartArrayToken()) {
         return this.handleNonArray(var1, var2);
      } else {
         ObjectBuffer var3 = var2.leaseObjectBuffer();
         Object[] var4 = var3.resetAndStart();
         int var5 = 0;
         TypeDeserializer var7 = this._elementTypeDeserializer;

         JsonToken var6;
         try {
            while((var6 = var1.nextToken()) != JsonToken.END_ARRAY) {
               Object var8;
               if (var6 == JsonToken.VALUE_NULL) {
                  if (this._skipNullValues) {
                     continue;
                  }

                  var8 = this._nullProvider.getNullValue(var2);
               } else if (var7 == null) {
                  var8 = this._elementDeserializer.deserialize(var1, var2);
               } else {
                  var8 = this._elementDeserializer.deserializeWithType(var1, var2, var7);
               }

               if (var5 >= var4.length) {
                  var4 = var3.appendCompletedChunk(var4);
                  var5 = 0;
               }

               var4[var5++] = var8;
            }
         } catch (Exception var9) {
            throw JsonMappingException.wrapWithPath(var9, var4, var3.bufferedSize() + var5);
         }

         Object[] var10;
         if (this._untyped) {
            var10 = var3.completeAndClearBuffer(var4, var5);
         } else {
            var10 = var3.completeAndClearBuffer(var4, var5, this._elementClass);
         }

         var2.returnObjectBuffer(var3);
         return var10;
      }
   }

   public Object[] deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return (Object[])((Object[])var3.deserializeTypedFromArray(var1, var2));
   }

   public Object[] deserialize(JsonParser var1, DeserializationContext var2, Object[] var3) throws IOException {
      int var5;
      Object[] var6;
      if (!var1.isExpectedStartArrayToken()) {
         Object[] var12 = this.handleNonArray(var1, var2);
         if (var12 == null) {
            return var3;
         } else {
            var5 = var3.length;
            var6 = new Object[var5 + var12.length];
            System.arraycopy(var3, 0, var6, 0, var5);
            System.arraycopy(var12, 0, var6, var5, var12.length);
            return var6;
         }
      } else {
         ObjectBuffer var4 = var2.leaseObjectBuffer();
         var5 = var3.length;
         var6 = var4.resetAndStart(var3, var5);
         TypeDeserializer var8 = this._elementTypeDeserializer;

         JsonToken var7;
         try {
            while((var7 = var1.nextToken()) != JsonToken.END_ARRAY) {
               Object var9;
               if (var7 == JsonToken.VALUE_NULL) {
                  if (this._skipNullValues) {
                     continue;
                  }

                  var9 = this._nullProvider.getNullValue(var2);
               } else if (var8 == null) {
                  var9 = this._elementDeserializer.deserialize(var1, var2);
               } else {
                  var9 = this._elementDeserializer.deserializeWithType(var1, var2, var8);
               }

               if (var5 >= var6.length) {
                  var6 = var4.appendCompletedChunk(var6);
                  var5 = 0;
               }

               var6[var5++] = var9;
            }
         } catch (Exception var10) {
            throw JsonMappingException.wrapWithPath(var10, var6, var4.bufferedSize() + var5);
         }

         Object[] var11;
         if (this._untyped) {
            var11 = var4.completeAndClearBuffer(var6, var5);
         } else {
            var11 = var4.completeAndClearBuffer(var6, var5, this._elementClass);
         }

         var2.returnObjectBuffer(var4);
         return var11;
      }
   }

   protected Byte[] deserializeFromBase64(JsonParser var1, DeserializationContext var2) throws IOException {
      byte[] var3 = var1.getBinaryValue(var2.getBase64Variant());
      Byte[] var4 = new Byte[var3.length];
      int var5 = 0;

      for(int var6 = var3.length; var5 < var6; ++var5) {
         var4[var5] = var3[var5];
      }

      return var4;
   }

   protected Object[] handleNonArray(JsonParser var1, DeserializationContext var2) throws IOException {
      if (var1.hasToken(JsonToken.VALUE_STRING) && var2.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
         String var3 = var1.getText();
         if (var3.length() == 0) {
            return null;
         }
      }

      boolean var7 = this._unwrapSingle == Boolean.TRUE || this._unwrapSingle == null && var2.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      JsonToken var4;
      if (!var7) {
         var4 = var1.getCurrentToken();
         return (Object[])(var4 == JsonToken.VALUE_STRING && this._elementClass == Byte.class ? this.deserializeFromBase64(var1, var2) : (Object[])((Object[])var2.handleUnexpectedToken(this._containerType.getRawClass(), var1)));
      } else {
         var4 = var1.getCurrentToken();
         Object var5;
         if (var4 == JsonToken.VALUE_NULL) {
            if (this._skipNullValues) {
               return NO_OBJECTS;
            }

            var5 = this._nullProvider.getNullValue(var2);
         } else if (this._elementTypeDeserializer == null) {
            var5 = this._elementDeserializer.deserialize(var1, var2);
         } else {
            var5 = this._elementDeserializer.deserializeWithType(var1, var2, this._elementTypeDeserializer);
         }

         Object[] var6;
         if (this._untyped) {
            var6 = new Object[1];
         } else {
            var6 = (Object[])((Object[])Array.newInstance(this._elementClass, 1));
         }

         var6[0] = var5;
         return var6;
      }
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return this.deserializeWithType(var1, var2, var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (Object[])var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
