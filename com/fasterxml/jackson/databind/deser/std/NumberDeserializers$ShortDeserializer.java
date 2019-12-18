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
public class NumberDeserializers$ShortDeserializer extends NumberDeserializers$PrimitiveOrWrapperDeserializer {
   private static final long serialVersionUID = 1L;
   static final NumberDeserializers$ShortDeserializer primitiveInstance;
   static final NumberDeserializers$ShortDeserializer wrapperInstance;

   public NumberDeserializers$ShortDeserializer(Class var1, Short var2) {
      super(var1, var2, Short.valueOf((short)0));
   }

   public Short deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._parseShort(var1, var2);
   }

   protected Short _parseShort(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 == JsonToken.VALUE_NUMBER_INT) {
         return var1.getShortValue();
      } else if (var3 == JsonToken.VALUE_STRING) {
         String var4 = var1.getText().trim();
         int var5 = var4.length();
         if (var5 == 0) {
            return (Short)this._coerceEmptyString(var2, this._primitive);
         } else if (this._hasTextualNull(var4)) {
            return (Short)this._coerceTextualNull(var2, this._primitive);
         } else {
            this._verifyStringForScalarCoercion(var2, var4);

            int var6;
            try {
               var6 = NumberInput.parseInt(var4);
            } catch (IllegalArgumentException var8) {
               return (Short)var2.handleWeirdStringValue(this._valueClass, var4, "not a valid Short value");
            }

            return this._shortOverflow(var6) ? (Short)var2.handleWeirdStringValue(this._valueClass, var4, "overflow, value cannot be represented as 16-bit value") : (short)var6;
         }
      } else if (var3 == JsonToken.VALUE_NUMBER_FLOAT) {
         if (!var2.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
            this._failDoubleToIntCoercion(var1, var2, "Short");
         }

         return var1.getShortValue();
      } else if (var3 == JsonToken.VALUE_NULL) {
         return (Short)this._coerceNullToken(var2, this._primitive);
      } else {
         return var3 == JsonToken.START_ARRAY ? (Short)this._deserializeFromArray(var1, var2) : (Short)var2.handleUnexpectedToken(this._valueClass, var1);
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
      primitiveInstance = new NumberDeserializers$ShortDeserializer(Short.TYPE, Short.valueOf((short)0));
      wrapperInstance = new NumberDeserializers$ShortDeserializer(Short.class, (Short)null);
   }
}
