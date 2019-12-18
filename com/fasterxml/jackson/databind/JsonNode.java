package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class JsonNode extends JsonSerializable$Base implements TreeNode, Iterable {
   protected JsonNode() {
      super();
   }

   public abstract JsonNode deepCopy();

   public int size() {
      return 0;
   }

   public final boolean isValueNode() {
      // $FF: Couldn't be decompiled
   }

   public final boolean isContainerNode() {
      JsonNodeType var1 = this.getNodeType();
      return var1 == JsonNodeType.OBJECT || var1 == JsonNodeType.ARRAY;
   }

   public boolean isMissingNode() {
      return false;
   }

   public boolean isArray() {
      return false;
   }

   public boolean isObject() {
      return false;
   }

   public abstract JsonNode get(int var1);

   public JsonNode get(String var1) {
      return null;
   }

   public abstract JsonNode path(String var1);

   public abstract JsonNode path(int var1);

   public Iterator fieldNames() {
      return ClassUtil.emptyIterator();
   }

   public final JsonNode at(JsonPointer var1) {
      if (var1.matches()) {
         return this;
      } else {
         JsonNode var2 = this._at(var1);
         return (JsonNode)(var2 == null ? MissingNode.getInstance() : var2.at(var1.tail()));
      }
   }

   public final JsonNode at(String var1) {
      return this.at(JsonPointer.compile(var1));
   }

   protected abstract JsonNode _at(JsonPointer var1);

   public abstract JsonNodeType getNodeType();

   public final boolean isPojo() {
      return this.getNodeType() == JsonNodeType.POJO;
   }

   public final boolean isNumber() {
      return this.getNodeType() == JsonNodeType.NUMBER;
   }

   public boolean isIntegralNumber() {
      return false;
   }

   public boolean isFloatingPointNumber() {
      return false;
   }

   public boolean isShort() {
      return false;
   }

   public boolean isInt() {
      return false;
   }

   public boolean isLong() {
      return false;
   }

   public boolean isFloat() {
      return false;
   }

   public boolean isDouble() {
      return false;
   }

   public boolean isBigDecimal() {
      return false;
   }

   public boolean isBigInteger() {
      return false;
   }

   public final boolean isTextual() {
      return this.getNodeType() == JsonNodeType.STRING;
   }

   public final boolean isBoolean() {
      return this.getNodeType() == JsonNodeType.BOOLEAN;
   }

   public final boolean isNull() {
      return this.getNodeType() == JsonNodeType.NULL;
   }

   public final boolean isBinary() {
      return this.getNodeType() == JsonNodeType.BINARY;
   }

   public boolean canConvertToInt() {
      return false;
   }

   public boolean canConvertToLong() {
      return false;
   }

   public String textValue() {
      return null;
   }

   public byte[] binaryValue() throws IOException {
      return null;
   }

   public boolean booleanValue() {
      return false;
   }

   public Number numberValue() {
      return null;
   }

   public short shortValue() {
      return 0;
   }

   public int intValue() {
      return 0;
   }

   public long longValue() {
      return 0L;
   }

   public float floatValue() {
      return 0.0F;
   }

   public double doubleValue() {
      return 0.0D;
   }

   public BigDecimal decimalValue() {
      return BigDecimal.ZERO;
   }

   public BigInteger bigIntegerValue() {
      return BigInteger.ZERO;
   }

   public abstract String asText();

   public String asText(String var1) {
      String var2 = this.asText();
      return var2 == null ? var1 : var2;
   }

   public int asInt() {
      return this.asInt(0);
   }

   public int asInt(int var1) {
      return var1;
   }

   public long asLong() {
      return this.asLong(0L);
   }

   public long asLong(long var1) {
      return var1;
   }

   public double asDouble() {
      return this.asDouble(0.0D);
   }

   public double asDouble(double var1) {
      return var1;
   }

   public boolean asBoolean() {
      return this.asBoolean(false);
   }

   public boolean asBoolean(boolean var1) {
      return var1;
   }

   public boolean has(String var1) {
      return this.get(var1) != null;
   }

   public boolean has(int var1) {
      return this.get(var1) != null;
   }

   public boolean hasNonNull(String var1) {
      JsonNode var2 = this.get(var1);
      return var2 != null && !var2.isNull();
   }

   public boolean hasNonNull(int var1) {
      JsonNode var2 = this.get(var1);
      return var2 != null && !var2.isNull();
   }

   public final Iterator iterator() {
      return this.elements();
   }

   public Iterator elements() {
      return ClassUtil.emptyIterator();
   }

   public Iterator fields() {
      return ClassUtil.emptyIterator();
   }

   public abstract JsonNode findValue(String var1);

   public final List findValues(String var1) {
      List var2 = this.findValues(var1, (List)null);
      return var2 == null ? Collections.emptyList() : var2;
   }

   public final List findValuesAsText(String var1) {
      List var2 = this.findValuesAsText(var1, (List)null);
      return var2 == null ? Collections.emptyList() : var2;
   }

   public abstract JsonNode findPath(String var1);

   public abstract JsonNode findParent(String var1);

   public final List findParents(String var1) {
      List var2 = this.findParents(var1, (List)null);
      return var2 == null ? Collections.emptyList() : var2;
   }

   public abstract List findValues(String var1, List var2);

   public abstract List findValuesAsText(String var1, List var2);

   public abstract List findParents(String var1, List var2);

   public JsonNode with(String var1) {
      throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + this.getClass().getName() + "), cannot call with() on it");
   }

   public JsonNode withArray(String var1) {
      throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + this.getClass().getName() + "), cannot call withArray() on it");
   }

   public boolean equals(Comparator var1, JsonNode var2) {
      return var1.compare(this, var2) == 0;
   }

   public abstract String toString();

   public abstract boolean equals(Object var1);

   public TreeNode at(String var1) throws IllegalArgumentException {
      return this.at(var1);
   }

   public TreeNode at(JsonPointer var1) {
      return this.at(var1);
   }

   public TreeNode path(int var1) {
      return this.path(var1);
   }

   public TreeNode path(String var1) {
      return this.path(var1);
   }

   public TreeNode get(int var1) {
      return this.get(var1);
   }

   public TreeNode get(String var1) {
      return this.get(var1);
   }
}
