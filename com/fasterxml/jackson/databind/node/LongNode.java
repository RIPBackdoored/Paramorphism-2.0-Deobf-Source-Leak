package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class LongNode extends NumericNode {
   protected final long _value;

   public LongNode(long var1) {
      super();
      this._value = var1;
   }

   public static LongNode valueOf(long var0) {
      return new LongNode(var0);
   }

   public JsonToken asToken() {
      return JsonToken.VALUE_NUMBER_INT;
   }

   public JsonParser$NumberType numberType() {
      return JsonParser$NumberType.LONG;
   }

   public boolean isIntegralNumber() {
      return true;
   }

   public boolean isLong() {
      return true;
   }

   public boolean canConvertToInt() {
      return this._value >= -2147483648L && this._value <= 0L;
   }

   public boolean canConvertToLong() {
      return true;
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
      return this._value;
   }

   public float floatValue() {
      return (float)this._value;
   }

   public double doubleValue() {
      return (double)this._value;
   }

   public BigDecimal decimalValue() {
      return BigDecimal.valueOf(this._value);
   }

   public BigInteger bigIntegerValue() {
      return BigInteger.valueOf(this._value);
   }

   public String asText() {
      return NumberOutput.toString(this._value);
   }

   public boolean asBoolean(boolean var1) {
      return this._value != 0L;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      var1.writeNumber(this._value);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1 instanceof LongNode) {
         return ((LongNode)var1)._value == this._value;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (int)this._value ^ (int)(this._value >> 32);
   }
}
