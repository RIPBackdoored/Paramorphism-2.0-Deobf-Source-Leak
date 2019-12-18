package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DoubleNode extends NumericNode {
   protected final double _value;

   public DoubleNode(double var1) {
      super();
      this._value = var1;
   }

   public static DoubleNode valueOf(double var0) {
      return new DoubleNode(var0);
   }

   public JsonToken asToken() {
      return JsonToken.VALUE_NUMBER_FLOAT;
   }

   public JsonParser$NumberType numberType() {
      return JsonParser$NumberType.DOUBLE;
   }

   public boolean isFloatingPointNumber() {
      return true;
   }

   public boolean isDouble() {
      return true;
   }

   public boolean canConvertToInt() {
      return this._value >= -2.147483648E9D && this._value <= 2.147483647E9D;
   }

   public boolean canConvertToLong() {
      return this._value >= -9.223372036854776E18D && this._value <= 9.223372036854776E18D;
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
      return (float)this._value;
   }

   public double doubleValue() {
      return this._value;
   }

   public BigDecimal decimalValue() {
      return BigDecimal.valueOf(this._value);
   }

   public BigInteger bigIntegerValue() {
      return this.decimalValue().toBigInteger();
   }

   public String asText() {
      return NumberOutput.toString(this._value);
   }

   public boolean isNaN() {
      return Double.isNaN(this._value) || Double.isInfinite(this._value);
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException {
      var1.writeNumber(this._value);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1 instanceof DoubleNode) {
         double var2 = ((DoubleNode)var1)._value;
         return Double.compare(this._value, var2) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      long var1 = Double.doubleToLongBits(this._value);
      return (int)var1 ^ (int)(var1 >> 32);
   }
}
