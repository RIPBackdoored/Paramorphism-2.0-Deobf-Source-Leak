package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import java.io.IOException;
import java.math.BigDecimal;

@JacksonStdImpl
public class NumberDeserializers$BigDecimalDeserializer extends StdScalarDeserializer {
   public static final NumberDeserializers$BigDecimalDeserializer instance = new NumberDeserializers$BigDecimalDeserializer();

   public NumberDeserializers$BigDecimalDeserializer() {
      super(BigDecimal.class);
   }

   public Object getEmptyValue(DeserializationContext var1) {
      return BigDecimal.ZERO;
   }

   public BigDecimal deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 3:
         return (BigDecimal)this._deserializeFromArray(var1, var2);
      case 4:
      case 5:
      default:
         return (BigDecimal)var2.handleUnexpectedToken(this._valueClass, var1);
      case 6:
         String var3 = var1.getText().trim();
         if (this._isEmptyOrTextualNull(var3)) {
            this._verifyNullForScalarCoercion(var2, var3);
            return (BigDecimal)this.getNullValue(var2);
         } else {
            this._verifyStringForScalarCoercion(var2, var3);

            BigDecimal var10000;
            try {
               var10000 = new BigDecimal(var3);
            } catch (IllegalArgumentException var5) {
               return (BigDecimal)var2.handleWeirdStringValue(this._valueClass, var3, "not a valid representation");
            }

            return var10000;
         }
      case 7:
      case 8:
         return var1.getDecimalValue();
      }
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
