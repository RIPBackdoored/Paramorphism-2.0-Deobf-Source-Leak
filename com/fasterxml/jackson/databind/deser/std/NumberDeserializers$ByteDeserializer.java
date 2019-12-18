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
public class NumberDeserializers$ByteDeserializer extends NumberDeserializers$PrimitiveOrWrapperDeserializer {
   private static final long serialVersionUID = 1L;
   static final NumberDeserializers$ByteDeserializer primitiveInstance;
   static final NumberDeserializers$ByteDeserializer wrapperInstance;

   public NumberDeserializers$ByteDeserializer(Class var1, Byte var2) {
      super(var1, var2, (byte)0);
   }

   public Byte deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return var1.hasToken(JsonToken.VALUE_NUMBER_INT) ? var1.getByteValue() : this._parseByte(var1, var2);
   }

   protected Byte _parseByte(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 == JsonToken.VALUE_STRING) {
         String var4 = var1.getText().trim();
         if (this._hasTextualNull(var4)) {
            return (Byte)this._coerceTextualNull(var2, this._primitive);
         } else {
            int var5 = var4.length();
            if (var5 == 0) {
               return (Byte)this._coerceEmptyString(var2, this._primitive);
            } else {
               this._verifyStringForScalarCoercion(var2, var4);

               int var6;
               try {
                  var6 = NumberInput.parseInt(var4);
               } catch (IllegalArgumentException var8) {
                  return (Byte)var2.handleWeirdStringValue(this._valueClass, var4, "not a valid Byte value");
               }

               return this._byteOverflow(var6) ? (Byte)var2.handleWeirdStringValue(this._valueClass, var4, "overflow, value cannot be represented as 8-bit value") : (byte)var6;
            }
         }
      } else if (var3 == JsonToken.VALUE_NUMBER_FLOAT) {
         if (!var2.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
            this._failDoubleToIntCoercion(var1, var2, "Byte");
         }

         return var1.getByteValue();
      } else if (var3 == JsonToken.VALUE_NULL) {
         return (Byte)this._coerceNullToken(var2, this._primitive);
      } else if (var3 == JsonToken.START_ARRAY) {
         return (Byte)this._deserializeFromArray(var1, var2);
      } else {
         return var3 == JsonToken.VALUE_NUMBER_INT ? var1.getByteValue() : (Byte)var2.handleUnexpectedToken(this._valueClass, var1);
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
      primitiveInstance = new NumberDeserializers$ByteDeserializer(Byte.TYPE, (byte)0);
      wrapperInstance = new NumberDeserializers$ByteDeserializer(Byte.class, (Byte)null);
   }
}
