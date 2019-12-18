package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@JacksonStdImpl
public class NumberDeserializers$NumberDeserializer extends StdScalarDeserializer {
   public static final NumberDeserializers$NumberDeserializer instance = new NumberDeserializers$NumberDeserializer();

   public NumberDeserializers$NumberDeserializer() {
      super(Number.class);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 3:
         return this._deserializeFromArray(var1, var2);
      case 4:
      case 5:
      default:
         return var2.handleUnexpectedToken(this._valueClass, var1);
      case 6:
         String var3 = var1.getText().trim();
         if (var3.length() == 0) {
            return this.getNullValue(var2);
         } else if (this._hasTextualNull(var3)) {
            return this.getNullValue(var2);
         } else if (this._isPosInf(var3)) {
            return Double.POSITIVE_INFINITY;
         } else if (this._isNegInf(var3)) {
            return Double.NEGATIVE_INFINITY;
         } else if (this._isNaN(var3)) {
            return Double.NaN;
         } else {
            this._verifyStringForScalarCoercion(var2, var3);

            BigInteger var10000;
            try {
               if (!this._isIntNumber(var3)) {
                  if (var2.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                     BigDecimal var10 = new BigDecimal(var3);
                     return var10;
                  }

                  Double var9 = Double.valueOf(var3);
                  return var9;
               }

               if (!var2.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                  long var4 = Long.parseLong(var3);
                  if (!var2.isEnabled(DeserializationFeature.USE_LONG_FOR_INTS) && var4 <= 0L && var4 >= -2147483648L) {
                     Integer var8 = (int)var4;
                     return var8;
                  }

                  Long var7 = var4;
                  return var7;
               }

               var10000 = new BigInteger(var3);
            } catch (IllegalArgumentException var6) {
               return var2.handleWeirdStringValue(this._valueClass, var3, "not a valid number");
            }

            return var10000;
         }
      case 7:
         if (var2.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
            return this._coerceIntegral(var1, var2);
         }

         return var1.getNumberValue();
      case 8:
         if (var2.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS) && !var1.isNaN()) {
            return var1.getDecimalValue();
         } else {
            return var1.getNumberValue();
         }
      }
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 6:
      case 7:
      case 8:
         return this.deserialize(var1, var2);
      default:
         return var3.deserializeTypedFromScalar(var1, var2);
      }
   }
}
