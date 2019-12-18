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

public class ShortNode extends NumericNode {
   protected final short _value;

   public ShortNode(short var1) {
      super();
      this._value = var1;
   }

   public static ShortNode valueOf(short var0) {
      return new ShortNode(var0);
   }

   public JsonToken asToken() {
      return JsonToken.VALUE_NUMBER_INT;
   }

   public JsonParser$NumberType numberType() {
      return JsonParser$NumberType.INT;
   }

   public boolean isIntegralNumber() {
      return true;
   }

   public boolean isShort() {
      return true;
   }

   public boolean canConvertToInt() {
      return true;
   }

   public boolean canConvertToLong() {
      return true;
   }

   public Number numberValue() {
      return this._value;
   }

   public short shortValue() {
      return this._value;
   }

   public int intValue() {
      return this._value;
   }

   public long longValue() {
      return (long)this._value;
   }

   public float floatValue() {
      return (float)this._value;
   }

   public double doubleValue() {
      return (double)this._value;
   }

   public BigDecimal decimalValue() {
      return BigDecimal.valueOf((long)this._value);
   }

   public BigInteger bigIntegerValue() {
      return BigInteger.valueOf((long)this._value);
   }

   public String asText() {
      return NumberOutput.toString(this._value);
   }

   public boolean asBoolean(boolean var1) {
      return this._value != 0;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      var1.writeNumber(this._value);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1 instanceof ShortNode) {
         return ((ShortNode)var1)._value == this._value;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this._value;
   }
}
