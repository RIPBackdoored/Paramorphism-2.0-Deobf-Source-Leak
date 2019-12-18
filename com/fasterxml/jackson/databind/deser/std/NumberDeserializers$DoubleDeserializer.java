package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;

@JacksonStdImpl
public class NumberDeserializers$DoubleDeserializer extends NumberDeserializers$PrimitiveOrWrapperDeserializer {
   private static final long serialVersionUID = 1L;
   static final NumberDeserializers$DoubleDeserializer primitiveInstance;
   static final NumberDeserializers$DoubleDeserializer wrapperInstance;

   public NumberDeserializers$DoubleDeserializer(Class var1, Double var2) {
      super(var1, var2, 0.0D);
   }

   public Double deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._parseDouble(var1, var2);
   }

   public Double deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return this._parseDouble(var1, var2);
   }

   protected final Double _parseDouble(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 != JsonToken.VALUE_NUMBER_INT && var3 != JsonToken.VALUE_NUMBER_FLOAT) {
         if (var3 == JsonToken.VALUE_STRING) {
            String var4 = var1.getText().trim();
            if (var4.length() == 0) {
               return (Double)this._coerceEmptyString(var2, this._primitive);
            } else if (this._hasTextualNull(var4)) {
               return (Double)this._coerceTextualNull(var2, this._primitive);
            } else {
               switch(var4.charAt(0)) {
               case '-':
                  if (this._isNegInf(var4)) {
                     return Double.NEGATIVE_INFINITY;
                  }
                  break;
               case 'I':
                  if (this._isPosInf(var4)) {
                     return Double.POSITIVE_INFINITY;
                  }
                  break;
               case 'N':
                  if (this._isNaN(var4)) {
                     return Double.NaN;
                  }
               }

               this._verifyStringForScalarCoercion(var2, var4);

               Double var10000;
               try {
                  var10000 = parseDouble(var4);
               } catch (IllegalArgumentException var6) {
                  return (Double)var2.handleWeirdStringValue(this._valueClass, var4, "not a valid Double value");
               }

               return var10000;
            }
         } else if (var3 == JsonToken.VALUE_NULL) {
            return (Double)this._coerceNullToken(var2, this._primitive);
         } else {
            return var3 == JsonToken.START_ARRAY ? (Double)this._deserializeFromArray(var1, var2) : (Double)var2.handleUnexpectedToken(this._valueClass, var1);
         }
      } else {
         return var1.getDoubleValue();
      }
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return super.getEmptyValue(var1);
   }

   public AccessPattern getNullAccessPattern() {
      return super.getNullAccessPattern();
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return this.deserializeWithType(var1, var2, var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }

   static {
      primitiveInstance = new NumberDeserializers$DoubleDeserializer(Double.TYPE, 0.0D);
      wrapperInstance = new NumberDeserializers$DoubleDeserializer(Double.class, (Double)null);
   }
}
