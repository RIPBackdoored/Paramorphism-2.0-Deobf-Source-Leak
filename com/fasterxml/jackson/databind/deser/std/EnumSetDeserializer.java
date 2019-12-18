package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.EnumSet;

public class EnumSetDeserializer extends StdDeserializer implements ContextualDeserializer {
   private static final long serialVersionUID = 1L;
   protected final JavaType _enumType;
   protected final Class _enumClass;
   protected JsonDeserializer _enumDeserializer;
   protected final Boolean _unwrapSingle;

   public EnumSetDeserializer(JavaType var1, JsonDeserializer var2) {
      super(EnumSet.class);
      this._enumType = var1;
      this._enumClass = var1.getRawClass();
      if (!this._enumClass.isEnum()) {
         throw new IllegalArgumentException("Type " + var1 + " not Java Enum type");
      } else {
         this._enumDeserializer = var2;
         this._unwrapSingle = null;
      }
   }

   protected EnumSetDeserializer(EnumSetDeserializer var1, JsonDeserializer var2, Boolean var3) {
      super((StdDeserializer)var1);
      this._enumType = var1._enumType;
      this._enumClass = var1._enumClass;
      this._enumDeserializer = var2;
      this._unwrapSingle = var3;
   }

   public EnumSetDeserializer withDeserializer(JsonDeserializer var1) {
      return this._enumDeserializer == var1 ? this : new EnumSetDeserializer(this, var1, this._unwrapSingle);
   }

   public EnumSetDeserializer withResolved(JsonDeserializer var1, Boolean var2) {
      return this._unwrapSingle == var2 && this._enumDeserializer == var1 ? this : new EnumSetDeserializer(this, var1, var2);
   }

   public boolean isCachable() {
      return this._enumType.getValueHandler() == null;
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return Boolean.TRUE;
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      Boolean var3 = this.findFormatFeature(var1, var2, EnumSet.class, JsonFormat$Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      JsonDeserializer var4 = this._enumDeserializer;
      if (var4 == null) {
         var4 = var1.findContextualValueDeserializer(this._enumType, var2);
      } else {
         var4 = var1.handleSecondaryContextualization(var4, var2, this._enumType);
      }

      return this.withResolved(var4, var3);
   }

   public EnumSet deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      EnumSet var3 = this.constructSet();
      return !var1.isExpectedStartArrayToken() ? this.handleNonArray(var1, var2, var3) : this._deserialize(var1, var2, var3);
   }

   public EnumSet deserialize(JsonParser var1, DeserializationContext var2, EnumSet var3) throws IOException {
      return !var1.isExpectedStartArrayToken() ? this.handleNonArray(var1, var2, var3) : this._deserialize(var1, var2, var3);
   }

   protected final EnumSet _deserialize(JsonParser var1, DeserializationContext var2, EnumSet var3) throws IOException {
      JsonToken var4;
      try {
         while((var4 = var1.nextToken()) != JsonToken.END_ARRAY) {
            if (var4 == JsonToken.VALUE_NULL) {
               EnumSet var10000 = (EnumSet)var2.handleUnexpectedToken(this._enumClass, var1);
               return var10000;
            }

            Enum var5 = (Enum)this._enumDeserializer.deserialize(var1, var2);
            if (var5 != null) {
               var3.add(var5);
            }
         }
      } catch (Exception var6) {
         throw JsonMappingException.wrapWithPath(var6, var3, var3.size());
      }

      return var3;
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException, JsonProcessingException {
      return var3.deserializeTypedFromArray(var1, var2);
   }

   private EnumSet constructSet() {
      return EnumSet.noneOf(this._enumClass);
   }

   protected EnumSet handleNonArray(JsonParser var1, DeserializationContext var2, EnumSet var3) throws IOException {
      boolean var4 = this._unwrapSingle == Boolean.TRUE || this._unwrapSingle == null && var2.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      if (!var4) {
         return (EnumSet)var2.handleUnexpectedToken(EnumSet.class, var1);
      } else if (var1.hasToken(JsonToken.VALUE_NULL)) {
         return (EnumSet)var2.handleUnexpectedToken(this._enumClass, var1);
      } else {
         try {
            Enum var5 = (Enum)this._enumDeserializer.deserialize(var1, var2);
            if (var5 != null) {
               var3.add(var5);
            }
         } catch (Exception var6) {
            throw JsonMappingException.wrapWithPath(var6, var3, var3.size());
         }

         return var3;
      }
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (EnumSet)var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
