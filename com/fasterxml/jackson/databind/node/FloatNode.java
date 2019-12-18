package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class FloatNode extends NumericNode {
   protected final float _value;

   public FloatNode(float var1) {
      super();
      this._value = var1;
   }

   public static FloatNode valueOf(float var0) {
      return new FloatNode(var0);
   }

   public JsonToken asToken() {
      return JsonToken.VALUE_NUMBER_FLOAT;
   }

   public JsonParser$NumberType numberType() {
      return JsonParser$NumberType.FLOAT;
   }

   public boolean isFloatingPointNumber() {
      return true;
   }

   public boolean isFloat() {
      return true;
   }

   public boolean canConvertToInt() {
      return this._value >= -2.14748365E9F && this._value <= 2.14748365E9F;
   }

   public boolean canConvertToLong() {
      return this._value >= -9.223372E18F && this._value <= 9.223372E18F;
   }

   public Number numberValue() {
      return this._value;
   }

   public short shortValue() {
      return (short)((int)this._value);
   }

   public int intValue() {
      return (int)this._value;
   }

   public long longValue() {
      return (long)this._value;
   }

   public float floatValue() {
      return this._value;
   }

   public double doubleValue() {
      return (double)this._value;
   }

   public BigDecimal decimalValue() {
      return BigDecimal.valueOf((double)this._value);
   }

   public BigInteger bigIntegerValue() {
      return this.decimalValue().toBigInteger();
   }

   public String asText() {
      return NumberOutput.toString(this._value);
   }

   public boolean isNaN() {
      return Float.isNaN(this._value) || Float.isInfinite(this._value);
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException {
      var1.writeNumber(this._value);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1 instanceof FloatNode) {
         float var2 = ((FloatNode)var1)._value;
         return Float.compare(this._value, var2) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Float.floatToIntBits(this._value);
   }
}
