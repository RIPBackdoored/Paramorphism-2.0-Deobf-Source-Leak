package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.RawValue;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ArrayNode extends ContainerNode {
   private final List _children;

   public ArrayNode(JsonNodeFactory var1) {
      super(var1);
      this._children = new ArrayList();
   }

   public ArrayNode(JsonNodeFactory var1, int var2) {
      super(var1);
      this._children = new ArrayList(var2);
   }

   public ArrayNode(JsonNodeFactory var1, List var2) {
      super(var1);
      this._children = var2;
   }

   protected JsonNode _at(JsonPointer var1) {
      return this.get(var1.getMatchingIndex());
   }

   public ArrayNode deepCopy() {
      ArrayNode var1 = new ArrayNode(this._nodeFactory);
      Iterator var2 = this._children.iterator();

      while(var2.hasNext()) {
         JsonNode var3 = (JsonNode)var2.next();
         var1._children.add(var3.deepCopy());
      }

      return var1;
   }

   public boolean isEmpty(SerializerProvider var1) {
      return this._children.isEmpty();
   }

   public JsonNodeType getNodeType() {
      return JsonNodeType.ARRAY;
   }

   public boolean isArray() {
      return true;
   }

   public JsonToken asToken() {
      return JsonToken.START_ARRAY;
   }

   public int size() {
      return this._children.size();
   }

   public Iterator elements() {
      return this._children.iterator();
   }

   public JsonNode get(int var1) {
      return var1 >= 0 && var1 < this._children.size() ? (JsonNode)this._children.get(var1) : null;
   }

   public JsonNode get(String var1) {
      return null;
   }

   public JsonNode path(String var1) {
      return MissingNode.getInstance();
   }

   public JsonNode path(int var1) {
      return (JsonNode)(var1 >= 0 && var1 < this._children.size() ? (JsonNode)this._children.get(var1) : MissingNode.getInstance());
   }

   public boolean equals(Comparator var1, JsonNode var2) {
      if (!(var2 instanceof ArrayNode)) {
         return false;
      } else {
         ArrayNode var3 = (ArrayNode)var2;
         int var4 = this._children.size();
         if (var3.size() != var4) {
            return false;
         } else {
            List var5 = this._children;
            List var6 = var3._children;

            for(int var7 = 0; var7 < var4; ++var7) {
               if (!((JsonNode)var5.get(var7)).equals(var1, (JsonNode)var6.get(var7))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException {
      List var3 = this._children;
      int var4 = var3.size();
      var1.writeStartArray(var4);

      for(int var5 = 0; var5 < var4; ++var5) {
         JsonNode var6 = (JsonNode)var3.get(var5);
         ((BaseJsonNode)var6).serialize(var1, var2);
      }

      var1.writeEndArray();
   }

   public void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException {
      WritableTypeId var4 = var3.writeTypePrefix(var1, var3.typeId(this, JsonToken.START_ARRAY));
      Iterator var5 = this._children.iterator();

      while(var5.hasNext()) {
         JsonNode var6 = (JsonNode)var5.next();
         ((BaseJsonNode)var6).serialize(var1, var2);
      }

      var3.writeTypeSuffix(var1, var4);
   }

   public JsonNode findValue(String var1) {
      Iterator var2 = this._children.iterator();

      JsonNode var4;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         JsonNode var3 = (JsonNode)var2.next();
         var4 = var3.findValue(var1);
      } while(var4 == null);

      return var4;
   }

   public List findValues(String var1, List var2) {
      JsonNode var4;
      for(Iterator var3 = this._children.iterator(); var3.hasNext(); var2 = var4.findValues(var1, var2)) {
         var4 = (JsonNode)var3.next();
      }

      return var2;
   }

   public List findValuesAsText(String var1, List var2) {
      JsonNode var4;
      for(Iterator var3 = this._children.iterator(); var3.hasNext(); var2 = var4.findValuesAsText(var1, var2)) {
         var4 = (JsonNode)var3.next();
      }

      return var2;
   }

   public ObjectNode findParent(String var1) {
      Iterator var2 = this._children.iterator();

      JsonNode var4;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         JsonNode var3 = (JsonNode)var2.next();
         var4 = var3.findParent(var1);
      } while(var4 == null);

      return (ObjectNode)var4;
   }

   public List findParents(String var1, List var2) {
      JsonNode var4;
      for(Iterator var3 = this._children.iterator(); var3.hasNext(); var2 = var4.findParents(var1, var2)) {
         var4 = (JsonNode)var3.next();
      }

      return var2;
   }

   public JsonNode set(int var1, JsonNode var2) {
      if (var2 == null) {
         var2 = this.nullNode();
      }

      if (var1 >= 0 && var1 < this._children.size()) {
         return (JsonNode)this._children.set(var1, var2);
      } else {
         throw new IndexOutOfBoundsException("Illegal index " + var1 + ", array size " + this.size());
      }
   }

   public ArrayNode add(JsonNode var1) {
      if (var1 == null) {
         var1 = this.nullNode();
      }

      this._add((JsonNode)var1);
      return this;
   }

   public ArrayNode addAll(ArrayNode var1) {
      this._children.addAll(var1._children);
      return this;
   }

   public ArrayNode addAll(Collection var1) {
      this._children.addAll(var1);
      return this;
   }

   public ArrayNode insert(int var1, JsonNode var2) {
      if (var2 == null) {
         var2 = this.nullNode();
      }

      this._insert(var1, (JsonNode)var2);
      return this;
   }

   public JsonNode remove(int var1) {
      return var1 >= 0 && var1 < this._children.size() ? (JsonNode)this._children.remove(var1) : null;
   }

   public ArrayNode removeAll() {
      this._children.clear();
      return this;
   }

   public ArrayNode addArray() {
      ArrayNode var1 = this.arrayNode();
      this._add(var1);
      return var1;
   }

   public ObjectNode addObject() {
      ObjectNode var1 = this.objectNode();
      this._add(var1);
      return var1;
   }

   public ArrayNode addPOJO(Object var1) {
      if (var1 == null) {
         this.addNull();
      } else {
         this._add(this.pojoNode(var1));
      }

      return this;
   }

   public ArrayNode addRawValue(RawValue var1) {
      if (var1 == null) {
         this.addNull();
      } else {
         this._add(this.rawValueNode(var1));
      }

      return this;
   }

   public ArrayNode addNull() {
      this._add(this.nullNode());
      return this;
   }

   public ArrayNode add(int var1) {
      this._add(this.numberNode(var1));
      return this;
   }

   public ArrayNode add(Integer var1) {
      return var1 == null ? this.addNull() : this._add(this.numberNode(var1));
   }

   public ArrayNode add(long var1) {
      return this._add(this.numberNode(var1));
   }

   public ArrayNode add(Long var1) {
      return var1 == null ? this.addNull() : this._add(this.numberNode(var1));
   }

   public ArrayNode add(float var1) {
      return this._add(this.numberNode(var1));
   }

   public ArrayNode add(Float var1) {
      return var1 == null ? this.addNull() : this._add(this.numberNode(var1));
   }

   public ArrayNode add(double var1) {
      return this._add(this.numberNode(var1));
   }

   public ArrayNode add(Double var1) {
      return var1 == null ? this.addNull() : this._add(this.numberNode(var1));
   }

   public ArrayNode add(BigDecimal var1) {
      return var1 == null ? this.addNull() : this._add(this.numberNode(var1));
   }

   public ArrayNode add(BigInteger var1) {
      return var1 == null ? this.addNull() : this._add(this.numberNode(var1));
   }

   public ArrayNode add(String var1) {
      return var1 == null ? this.addNull() : this._add(this.textNode(var1));
   }

   public ArrayNode add(boolean var1) {
      return this._add(this.booleanNode(var1));
   }

   public ArrayNode add(Boolean var1) {
      return var1 == null ? this.addNull() : this._add(this.booleanNode(var1));
   }

   public ArrayNode add(byte[] var1) {
      return var1 == null ? this.addNull() : this._add(this.binaryNode(var1));
   }

   public ArrayNode insertArray(int var1) {
      ArrayNode var2 = this.arrayNode();
      this._insert(var1, var2);
      return var2;
   }

   public ObjectNode insertObject(int var1) {
      ObjectNode var2 = this.objectNode();
      this._insert(var1, var2);
      return var2;
   }

   public ArrayNode insertPOJO(int var1, Object var2) {
      return var2 == null ? this.insertNull(var1) : this._insert(var1, this.pojoNode(var2));
   }

   public ArrayNode insertNull(int var1) {
      this._insert(var1, this.nullNode());
      return this;
   }

   public ArrayNode insert(int var1, int var2) {
      this._insert(var1, this.numberNode(var2));
      return this;
   }

   public ArrayNode insert(int var1, Integer var2) {
      if (var2 == null) {
         this.insertNull(var1);
      } else {
         this._insert(var1, this.numberNode(var2));
      }

      return this;
   }

   public ArrayNode insert(int var1, long var2) {
      return this._insert(var1, this.numberNode(var2));
   }

   public ArrayNode insert(int var1, Long var2) {
      return var2 == null ? this.insertNull(var1) : this._insert(var1, this.numberNode(var2));
   }

   public ArrayNode insert(int var1, float var2) {
      return this._insert(var1, this.numberNode(var2));
   }

   public ArrayNode insert(int var1, Float var2) {
      return var2 == null ? this.insertNull(var1) : this._insert(var1, this.numberNode(var2));
   }

   public ArrayNode insert(int var1, double var2) {
      return this._insert(var1, this.numberNode(var2));
   }

   public ArrayNode insert(int var1, Double var2) {
      return var2 == null ? this.insertNull(var1) : this._insert(var1, this.numberNode(var2));
   }

   public ArrayNode insert(int var1, BigDecimal var2) {
      return var2 == null ? this.insertNull(var1) : this._insert(var1, this.numberNode(var2));
   }

   public ArrayNode insert(int var1, BigInteger var2) {
      return var2 == null ? this.insertNull(var1) : this._insert(var1, this.numberNode(var2));
   }

   public ArrayNode insert(int var1, String var2) {
      return var2 == null ? this.insertNull(var1) : this._insert(var1, this.textNode(var2));
   }

   public ArrayNode insert(int var1, boolean var2) {
      return this._insert(var1, this.booleanNode(var2));
   }

   public ArrayNode insert(int var1, Boolean var2) {
      return var2 == null ? this.insertNull(var1) : this._insert(var1, this.booleanNode(var2));
   }

   public ArrayNode insert(int var1, byte[] var2) {
      return var2 == null ? this.insertNull(var1) : this._insert(var1, this.binaryNode(var2));
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         return var1 instanceof ArrayNode ? this._children.equals(((ArrayNode)var1)._children) : false;
      }
   }

   protected boolean _childrenEqual(ArrayNode var1) {
      return this._children.equals(var1._children);
   }

   public int hashCode() {
      return this._children.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(16 + (this.size() << 4));
      var1.append('[');
      int var2 = 0;

      for(int var3 = this._children.size(); var2 < var3; ++var2) {
         if (var2 > 0) {
            var1.append(',');
         }

         var1.append(((JsonNode)this._children.get(var2)).toString());
      }

      var1.append(']');
      return var1.toString();
   }

   protected ArrayNode _add(JsonNode var1) {
      this._children.add(var1);
      return this;
   }

   protected ArrayNode _insert(int var1, JsonNode var2) {
      if (var1 < 0) {
         this._children.add(0, var2);
      } else if (var1 >= this._children.size()) {
         this._children.add(var2);
      } else {
         this._children.add(var1, var2);
      }

      return this;
   }

   public ContainerNode removeAll() {
      return this.removeAll();
   }

   public JsonNode findParent(String var1) {
      return this.findParent(var1);
   }

   public JsonNode deepCopy() {
      return this.deepCopy();
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
