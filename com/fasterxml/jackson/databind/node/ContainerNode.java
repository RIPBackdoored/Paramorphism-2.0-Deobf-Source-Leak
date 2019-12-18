package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.RawValue;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class ContainerNode extends BaseJsonNode implements JsonNodeCreator {
   protected final JsonNodeFactory _nodeFactory;

   protected ContainerNode(JsonNodeFactory var1) {
      super();
      this._nodeFactory = var1;
   }

   public abstract JsonToken asToken();

   public String asText() {
      return "";
   }

   public abstract int size();

   public abstract JsonNode get(int var1);

   public abstract JsonNode get(String var1);

   public final ArrayNode arrayNode() {
      return this._nodeFactory.arrayNode();
   }

   public final ArrayNode arrayNode(int var1) {
      return this._nodeFactory.arrayNode(var1);
   }

   public final ObjectNode objectNode() {
      return this._nodeFactory.objectNode();
   }

   public final NullNode nullNode() {
      return this._nodeFactory.nullNode();
   }

   public final BooleanNode booleanNode(boolean var1) {
      return this._nodeFactory.booleanNode(var1);
   }

   public final NumericNode numberNode(byte var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(short var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(int var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(long var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(float var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(double var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final ValueNode numberNode(BigInteger var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final ValueNode numberNode(BigDecimal var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final ValueNode numberNode(Byte var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final ValueNode numberNode(Short var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final ValueNode numberNode(Integer var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final ValueNode numberNode(Long var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final ValueNode numberNode(Float var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final ValueNode numberNode(Double var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final TextNode textNode(String var1) {
      return this._nodeFactory.textNode(var1);
   }

   public final BinaryNode binaryNode(byte[] var1) {
      return this._nodeFactory.binaryNode(var1);
   }

   public final BinaryNode binaryNode(byte[] var1, int var2, int var3) {
      return this._nodeFactory.binaryNode(var1, var2, var3);
   }

   public final ValueNode pojoNode(Object var1) {
      return this._nodeFactory.pojoNode(var1);
   }

   public final ValueNode rawValueNode(RawValue var1) {
      return this._nodeFactory.rawValueNode(var1);
   }

   public abstract ContainerNode removeAll();

   public TreeNode get(int var1) {
      return this.get(var1);
   }

   public TreeNode get(String var1) {
      return this.get(var1);
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
}
