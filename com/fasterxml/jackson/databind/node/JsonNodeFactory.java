package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.databind.util.RawValue;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNodeFactory implements Serializable, JsonNodeCreator {
   private static final long serialVersionUID = 1L;
   private final boolean _cfgBigDecimalExact;
   private static final JsonNodeFactory decimalsNormalized = new JsonNodeFactory(false);
   private static final JsonNodeFactory decimalsAsIs = new JsonNodeFactory(true);
   public static final JsonNodeFactory instance;

   public JsonNodeFactory(boolean var1) {
      super();
      this._cfgBigDecimalExact = var1;
   }

   protected JsonNodeFactory() {
      this(false);
   }

   public static JsonNodeFactory withExactBigDecimals(boolean var0) {
      return var0 ? decimalsAsIs : decimalsNormalized;
   }

   public BooleanNode booleanNode(boolean var1) {
      return var1 ? BooleanNode.getTrue() : BooleanNode.getFalse();
   }

   public NullNode nullNode() {
      return NullNode.getInstance();
   }

   public NumericNode numberNode(byte var1) {
      return IntNode.valueOf(var1);
   }

   public ValueNode numberNode(Byte var1) {
      return (ValueNode)(var1 == null ? this.nullNode() : IntNode.valueOf(var1.intValue()));
   }

   public NumericNode numberNode(short var1) {
      return ShortNode.valueOf(var1);
   }

   public ValueNode numberNode(Short var1) {
      return (ValueNode)(var1 == null ? this.nullNode() : ShortNode.valueOf(var1));
   }

   public NumericNode numberNode(int var1) {
      return IntNode.valueOf(var1);
   }

   public ValueNode numberNode(Integer var1) {
      return (ValueNode)(var1 == null ? this.nullNode() : IntNode.valueOf(var1));
   }

   public NumericNode numberNode(long var1) {
      return LongNode.valueOf(var1);
   }

   public ValueNode numberNode(Long var1) {
      return (ValueNode)(var1 == null ? this.nullNode() : LongNode.valueOf(var1));
   }

   public ValueNode numberNode(BigInteger var1) {
      return (ValueNode)(var1 == null ? this.nullNode() : BigIntegerNode.valueOf(var1));
   }

   public NumericNode numberNode(float var1) {
      return FloatNode.valueOf(var1);
   }

   public ValueNode numberNode(Float var1) {
      return (ValueNode)(var1 == null ? this.nullNode() : FloatNode.valueOf(var1));
   }

   public NumericNode numberNode(double var1) {
      return DoubleNode.valueOf(var1);
   }

   public ValueNode numberNode(Double var1) {
      return (ValueNode)(var1 == null ? this.nullNode() : DoubleNode.valueOf(var1));
   }

   public ValueNode numberNode(BigDecimal var1) {
      if (var1 == null) {
         return this.nullNode();
      } else if (this._cfgBigDecimalExact) {
         return DecimalNode.valueOf(var1);
      } else {
         return var1.compareTo(BigDecimal.ZERO) == 0 ? DecimalNode.ZERO : DecimalNode.valueOf(var1.stripTrailingZeros());
      }
   }

   public TextNode textNode(String var1) {
      return TextNode.valueOf(var1);
   }

   public BinaryNode binaryNode(byte[] var1) {
      return BinaryNode.valueOf(var1);
   }

   public BinaryNode binaryNode(byte[] var1, int var2, int var3) {
      return BinaryNode.valueOf(var1, var2, var3);
   }

   public ArrayNode arrayNode() {
      return new ArrayNode(this);
   }

   public ArrayNode arrayNode(int var1) {
      return new ArrayNode(this, var1);
   }

   public ObjectNode objectNode() {
      return new ObjectNode(this);
   }

   public ValueNode pojoNode(Object var1) {
      return new POJONode(var1);
   }

   public ValueNode rawValueNode(RawValue var1) {
      return new POJONode(var1);
   }

   protected boolean _inIntRange(long var1) {
      int var3 = (int)var1;
      long var4 = (long)var3;
      return var4 == var1;
   }

   public ValueNode binaryNode(byte[] var1, int var2, int var3) {
      return this.binaryNode(var1, var2, var3);
   }

   public ValueNode binaryNode(byte[] var1) {
      return this.binaryNode(var1);
   }

   public ValueNode textNode(String var1) {
      return this.textNode(var1);
   }

   public ValueNode numberNode(double var1) {
      return this.numberNode(var1);
   }

   public ValueNode numberNode(float var1) {
      return this.numberNode(var1);
   }

   public ValueNode numberNode(long var1) {
      return this.numberNode(var1);
   }

   public ValueNode numberNode(int var1) {
      return this.numberNode(var1);
   }

   public ValueNode numberNode(short var1) {
      return this.numberNode(var1);
   }

   public ValueNode numberNode(byte var1) {
      return this.numberNode(var1);
   }

   public ValueNode nullNode() {
      return this.nullNode();
   }

   public ValueNode booleanNode(boolean var1) {
      return this.booleanNode(var1);
   }

   static {
      instance = decimalsNormalized;
   }
}
