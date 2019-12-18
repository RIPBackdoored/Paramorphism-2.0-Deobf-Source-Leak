package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerNode extends NumericNode {
   private static final BigInteger MIN_INTEGER = BigInteger.valueOf(-2147483648L);
   private static final BigInteger MAX_INTEGER = BigInteger.valueOf(0L);
   private static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
   private static final BigInteger MAX_LONG = BigInteger.valueOf(4294967295L);
   protected final BigInteger _value;

   public BigIntegerNode(BigInteger var1) {
      super();
      this._value = var1;
   }

   public static BigIntegerNode valueOf(BigInteger var0) {
      return new BigIntegerNode(var0);
   }

   public JsonToken asToken() {
      return JsonToken.VALUE_NUMBER_INT;
   }

   public JsonParser$NumberType numberType() {
      return JsonParser$NumberType.BIG_INTEGER;
   }

   public boolean isIntegralNumber() {
      return true;
   }

   public boolean isBigInteger() {
      return true;
   }

   public boolean canConvertToInt() {
      return this._value.compareTo(MIN_INTEGER) >= 0 && this._value.compareTo(MAX_INTEGER) <= 0;
   }

   public boolean canConvertToLong() {
      return this._value.compareTo(MIN_LONG) >= 0 && this._value.compareTo(MAX_LONG) <= 0;
   }

   public Number numberValue() {
      return this._value;
   }

   public short shortValue() {
      return this._value.shortValue();
   }

   public int intValue() {
      return this._value.intValue();
   }

   public long longValue() {
      return this._value.longValue();
   }

   public BigInteger bigIntegerValue() {
      return this._value;
   }

   public float floatValue() {
      return this._value.floatValue();
   }

   public double doubleValue() {
      return this._value.doubleValue();
   }

   public BigDecimal decimalValue() {
      return new BigDecimal(this._value);
   }

   public String asText() {
      return this._value.toString();
   }

   public boolean asBoolean(boolean var1) {
      return !BigInteger.ZERO.equals(this._value);
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      var1.writeNumber(this._value);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         return !(var1 instanceof BigIntegerNode) ? false : ((BigIntegerNode)var1)._value.equals(this._value);
      }
   }

   public int hashCode() {
      return this._value.hashCode();
   }
}
