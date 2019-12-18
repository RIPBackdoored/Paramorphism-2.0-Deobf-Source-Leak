package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;

public class AsPropertyTypeDeserializer extends AsArrayTypeDeserializer {
   private static final long serialVersionUID = 1L;
   protected final JsonTypeInfo$As _inclusion;

   public AsPropertyTypeDeserializer(JavaType var1, TypeIdResolver var2, String var3, boolean var4, JavaType var5) {
      this(var1, var2, var3, var4, var5, JsonTypeInfo$As.PROPERTY);
   }

   public AsPropertyTypeDeserializer(JavaType var1, TypeIdResolver var2, String var3, boolean var4, JavaType var5, JsonTypeInfo$As var6) {
      super(var1, var2, var3, var4, var5);
      this._inclusion = var6;
   }

   public AsPropertyTypeDeserializer(AsPropertyTypeDeserializer var1, BeanProperty var2) {
      super(var1, var2);
      this._inclusion = var1._inclusion;
   }

   public TypeDeserializer forProperty(BeanProperty var1) {
      return var1 == this._property ? this : new AsPropertyTypeDeserializer(this, var1);
   }

   public JsonTypeInfo$As getTypeInclusion() {
      return this._inclusion;
   }

   public Object deserializeTypedFromObject(JsonParser var1, DeserializationContext var2) throws IOException {
      if (var1.canReadTypeId()) {
         Object var3 = var1.getTypeId();
         if (var3 != null) {
            return this._deserializeWithNativeTypeId(var1, var2, var3);
         }
      }

      JsonToken var6 = var1.getCurrentToken();
      if (var6 == JsonToken.START_OBJECT) {
         var6 = var1.nextToken();
      } else if (var6 != JsonToken.FIELD_NAME) {
         return this._deserializeTypedUsingDefaultImpl(var1, var2, (TokenBuffer)null);
      }

      TokenBuffer var4;
      for(var4 = null; var6 == JsonToken.FIELD_NAME; var6 = var1.nextToken()) {
         String var5 = var1.getCurrentName();
         var1.nextToken();
         if (var5.equals(this._typePropertyName)) {
            return this._deserializeTypedForId(var1, var2, var4);
         }

         if (var4 == null) {
            var4 = new TokenBuffer(var1, var2);
         }

         var4.writeFieldName(var5);
         var4.copyCurrentStructure(var1);
      }

      return this._deserializeTypedUsingDefaultImpl(var1, var2, var4);
   }

   protected Object _deserializeTypedForId(JsonParser var1, DeserializationContext var2, TokenBuffer var3) throws IOException {
      String var4 = ((JsonParser)var1).getText();
      JsonDeserializer var5 = this._findDeserializer(var2, var4);
      if (this._typeIdVisible) {
         if (var3 == null) {
            var3 = new TokenBuffer((JsonParser)var1, var2);
         }

         var3.writeFieldName(((JsonParser)var1).getCurrentName());
         var3.writeString(var4);
      }

      if (var3 != null) {
         ((JsonParser)var1).clearCurrentToken();
         var1 = JsonParserSequence.createFlattened(false, var3.asParser((JsonParser)var1), (JsonParser)var1);
      }

      ((JsonParser)var1).nextToken();
      return var5.deserialize((JsonParser)var1, var2);
   }

   protected Object _deserializeTypedUsingDefaultImpl(JsonParser var1, DeserializationContext var2, TokenBuffer var3) throws IOException {
      JsonDeserializer var4 = this._findDefaultImplDeserializer(var2);
      if (var4 == null) {
         Object var5 = TypeDeserializer.deserializeIfNatural(var1, var2, this._baseType);
         if (var5 != null) {
            return var5;
         }

         if (var1.isExpectedStartArrayToken()) {
            return super.deserializeTypedFromAny(var1, var2);
         }

         String var6;
         if (var1.hasToken(JsonToken.VALUE_STRING) && var2.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
            var6 = var1.getText().trim();
            if (var6.isEmpty()) {
               return null;
            }
         }

         var6 = String.format("missing type id property '%s'", this._typePropertyName);
         if (this._property != null) {
            var6 = String.format("%s (for POJO property '%s')", var6, this._property.getName());
         }

         JavaType var7 = this._handleMissingTypeId(var2, var6);
         if (var7 == null) {
            return null;
         }

         var4 = var2.findContextualValueDeserializer(var7, this._property);
      }

      if (var3 != null) {
         var3.writeEndObject();
         var1 = var3.asParser(var1);
         var1.nextToken();
      }

      return var4.deserialize(var1, var2);
   }

   public Object deserializeTypedFromAny(JsonParser var1, DeserializationContext var2) throws IOException {
      return var1.getCurrentToken() == JsonToken.START_ARRAY ? super.deserializeTypedFromArray(var1, var2) : this.deserializeTypedFromObject(var1, var2);
   }
}
