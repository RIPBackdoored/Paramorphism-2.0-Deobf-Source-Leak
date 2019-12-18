package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;

@JacksonStdImpl
public final class NumberDeserializers$LongDeserializer extends NumberDeserializers$PrimitiveOrWrapperDeserializer {
   private static final long serialVersionUID = 1L;
   static final NumberDeserializers$LongDeserializer primitiveInstance;
   static final NumberDeserializers$LongDeserializer wrapperInstance;

   public NumberDeserializers$LongDeserializer(Class var1, Long var2) {
      super(var1, var2, 0L);
   }

   public boolean isCachable() {
      return true;
   }

   public Long deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return var1.hasToken(JsonToken.VALUE_NUMBER_INT) ? var1.getLongValue() : this._parseLong(var1, var2);
   }

   protected final Long _parseLong(JsonParser var1, DeserializationContext var2) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 3:
         return (Long)this._deserializeFromArray(var1, var2);
      case 4:
      case 5:
      case 9:
      case 10:
      default:
         return (Long)var2.handleUnexpectedToken(this._valueClass, var1);
      case 6:
         String var3 = var1.getText().trim();
         if (var3.length() == 0) {
            return (Long)this._coerceEmptyString(var2, this._primitive);
         } else if (this._hasTextualNull(var3)) {
            return (Long)this._coerceTextualNull(var2, this._primitive);
         } else {
            this._verifyStringForScalarCoercion(var2, var3);

            Long var10000;
            try {
               var10000 = NumberInput.parseLong(var3);
            } catch (IllegalArgumentException var5) {
               return (Long)var2.handleWeirdStringValue(this._valueClass, var3, "not a valid Long value");
            }

            return var10000;
         }
      case 7:
         return var1.getLongValue();
      case 8:
         if (!var2.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
            this._failDoubleToIntCoercion(var1, var2, "Long");
         }

         return var1.getValueAsLong();
      case 11:
         return (Long)this._coerceNullToken(var2, this._primitive);
      }
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return super.getEmptyValue(var1);
   }

   public AccessPattern getNullAccessPattern() {
      return super.getNullAccessPattern();
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }

   static {
      primitiveInstance = new NumberDeserializers$LongDeserializer(Long.TYPE, 0L);
      wrapperInstance = new NumberDeserializers$LongDeserializer(Long.class, (Long)null);
   }
}
