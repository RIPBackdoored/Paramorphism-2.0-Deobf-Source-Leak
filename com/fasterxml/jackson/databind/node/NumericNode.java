package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonParser$NumberType;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class NumericNode extends ValueNode {
   protected NumericNode() {
      super();
   }

   public final JsonNodeType getNodeType() {
      return JsonNodeType.NUMBER;
   }

   public abstract JsonParser$NumberType numberType();

   public abstract Number numberValue();

   public abstract int intValue();

   public abstract long longValue();

   public abstract double doubleValue();

   public abstract BigDecimal decimalValue();

   public abstract BigInteger bigIntegerValue();

   public abstract boolean canConvertToInt();

   public abstract boolean canConvertToLong();

   public abstract String asText();

   public final int asInt() {
      return this.intValue();
   }

   public final int asInt(int var1) {
      return this.intValue();
   }

   public final long asLong() {
      return this.longValue();
   }

   public final long asLong(long var1) {
      return this.longValue();
   }

   public final double asDouble() {
      return this.doubleValue();
   }

   public final double asDouble(double var1) {
      return this.doubleValue();
   }

   public boolean isNaN() {
      return false;
   }
}
