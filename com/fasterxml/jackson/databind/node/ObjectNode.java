package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.RawValue;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ObjectNode extends ContainerNode {
   protected final Map _children;

   public ObjectNode(JsonNodeFactory var1) {
      super(var1);
      this._children = new LinkedHashMap();
   }

   public ObjectNode(JsonNodeFactory var1, Map var2) {
      super(var1);
      this._children = var2;
   }

   protected JsonNode _at(JsonPointer var1) {
      return this.get(var1.getMatchingProperty());
   }

   public ObjectNode deepCopy() {
      ObjectNode var1 = new ObjectNode(this._nodeFactory);
      Iterator var2 = this._children.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         var1._children.put(var3.getKey(), ((JsonNode)var3.getValue()).deepCopy());
      }

      return var1;
   }

   public boolean isEmpty(SerializerProvider var1) {
      return this._children.isEmpty();
   }

   public JsonNodeType getNodeType() {
      return JsonNodeType.OBJECT;
   }

   public final boolean isObject() {
      return true;
   }

   public JsonToken asToken() {
      return JsonToken.START_OBJECT;
   }

   public int size() {
      return this._children.size();
   }

   public Iterator elements() {
      return this._children.values().iterator();
   }

   public JsonNode get(int var1) {
      return null;
   }

   public JsonNode get(String var1) {
      return (JsonNode)this._children.get(var1);
   }

   public Iterator fieldNames() {
      return this._children.keySet().iterator();
   }

   public JsonNode path(int var1) {
      return MissingNode.getInstance();
   }

   public JsonNode path(String var1) {
      JsonNode var2 = (JsonNode)this._children.get(var1);
      return (JsonNode)(var2 != null ? var2 : MissingNode.getInstance());
   }

   public Iterator fields() {
      return this._children.entrySet().iterator();
   }

   public ObjectNode with(String var1) {
      JsonNode var2 = (JsonNode)this._children.get(var1);
      if (var2 != null) {
         if (var2 instanceof ObjectNode) {
            return (ObjectNode)var2;
         } else {
            throw new UnsupportedOperationException("Property '" + var1 + "' has value that is not of type ObjectNode (but " + var2.getClass().getName() + ")");
         }
      } else {
         ObjectNode var3 = this.objectNode();
         this._children.put(var1, var3);
         return var3;
      }
   }

   public ArrayNode withArray(String var1) {
      JsonNode var2 = (JsonNode)this._children.get(var1);
      if (var2 != null) {
         if (var2 instanceof ArrayNode) {
            return (ArrayNode)var2;
         } else {
            throw new UnsupportedOperationException("Property '" + var1 + "' has value that is not of type ArrayNode (but " + var2.getClass().getName() + ")");
         }
      } else {
         ArrayNode var3 = this.arrayNode();
         this._children.put(var1, var3);
         return var3;
      }
   }

   public boolean equals(Comparator var1, JsonNode var2) {
      if (!(var2 instanceof ObjectNode)) {
         return false;
      } else {
         ObjectNode var3 = (ObjectNode)var2;
         Map var4 = this._children;
         Map var5 = var3._children;
         int var6 = var4.size();
         if (var5.size() != var6) {
            return false;
         } else {
            Iterator var7 = var4.entrySet().iterator();

            Entry var8;
            JsonNode var9;
            do {
               if (!var7.hasNext()) {
                  return true;
               }

               var8 = (Entry)var7.next();
               var9 = (JsonNode)var5.get(var8.getKey());
            } while(var9 != null && ((JsonNode)var8.getValue()).equals(var1, var9));

            return false;
         }
      }
   }

   public JsonNode findValue(String var1) {
      Iterator var2 = this._children.entrySet().iterator();

      JsonNode var4;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         Entry var3 = (Entry)var2.next();
         if (var1.equals(var3.getKey())) {
            return (JsonNode)var3.getValue();
         }

         var4 = ((JsonNode)var3.getValue()).findValue(var1);
      } while(var4 == null);

      return var4;
   }

   public List findValues(String var1, List var2) {
      Iterator var3 = this._children.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if (var1.equals(var4.getKey())) {
            if (var2 == null) {
               var2 = new ArrayList();
            }

            ((List)var2).add(var4.getValue());
         } else {
            var2 = ((JsonNode)var4.getValue()).findValues(var1, (List)var2);
         }
      }

      return (List)var2;
   }

   public List findValuesAsText(String var1, List var2) {
      Iterator var3 = this._children.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if (var1.equals(var4.getKey())) {
            if (var2 == null) {
               var2 = new ArrayList();
            }

            ((List)var2).add(((JsonNode)var4.getValue()).asText());
         } else {
            var2 = ((JsonNode)var4.getValue()).findValuesAsText(var1, (List)var2);
         }
      }

      return (List)var2;
   }

   public ObjectNode findParent(String var1) {
      Iterator var2 = this._children.entrySet().iterator();

      JsonNode var4;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         Entry var3 = (Entry)var2.next();
         if (var1.equals(var3.getKey())) {
            return this;
         }

         var4 = ((JsonNode)var3.getValue()).findParent(var1);
      } while(var4 == null);

      return (ObjectNode)var4;
   }

   public List findParents(String var1, List var2) {
      Iterator var3 = this._children.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if (var1.equals(var4.getKey())) {
            if (var2 == null) {
               var2 = new ArrayList();
            }

            ((List)var2).add(this);
         } else {
            var2 = ((JsonNode)var4.getValue()).findParents(var1, (List)var2);
         }
      }

      return (List)var2;
   }

   public void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException {
      boolean var3 = var2 != null && !var2.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
      var1.writeStartObject(this);
      Iterator var4 = this._children.entrySet().iterator();

      while(true) {
         Entry var5;
         BaseJsonNode var6;
         do {
            if (!var4.hasNext()) {
               var1.writeEndObject();
               return;
            }

            var5 = (Entry)var4.next();
            var6 = (BaseJsonNode)var5.getValue();
         } while(var3 && var6.isArray() && var6.isEmpty(var2));

         var1.writeFieldName((String)var5.getKey());
         var6.serialize(var1, var2);
      }
   }

   public void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException {
      boolean var4 = var2 != null && !var2.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
      WritableTypeId var5 = var3.writeTypePrefix(var1, var3.typeId(this, JsonToken.START_OBJECT));
      Iterator var6 = this._children.entrySet().iterator();

      while(true) {
         Entry var7;
         BaseJsonNode var8;
         do {
            if (!var6.hasNext()) {
               var3.writeTypeSuffix(var1, var5);
               return;
            }

            var7 = (Entry)var6.next();
            var8 = (BaseJsonNode)var7.getValue();
         } while(var4 && var8.isArray() && var8.isEmpty(var2));

         var1.writeFieldName((String)var7.getKey());
         var8.serialize(var1, var2);
      }
   }

   public JsonNode set(String var1, JsonNode var2) {
      if (var2 == null) {
         var2 = this.nullNode();
      }

      this._children.put(var1, var2);
      return this;
   }

   public JsonNode setAll(Map var1) {
      Entry var3;
      Object var4;
      for(Iterator var2 = var1.entrySet().iterator(); var2.hasNext(); this._children.put(var3.getKey(), var4)) {
         var3 = (Entry)var2.next();
         var4 = (JsonNode)var3.getValue();
         if (var4 == null) {
            var4 = this.nullNode();
         }
      }

      return this;
   }

   public JsonNode setAll(ObjectNode var1) {
      this._children.putAll(var1._children);
      return this;
   }

   public JsonNode replace(String var1, JsonNode var2) {
      if (var2 == null) {
         var2 = this.nullNode();
      }

      return (JsonNode)this._children.put(var1, var2);
   }

   public JsonNode without(String var1) {
      this._children.remove(var1);
      return this;
   }

   public ObjectNode without(Collection var1) {
      this._children.keySet().removeAll(var1);
      return this;
   }

   /** @deprecated */
   @Deprecated
   public JsonNode put(String var1, JsonNode var2) {
      if (var2 == null) {
         var2 = this.nullNode();
      }

      return (JsonNode)this._children.put(var1, var2);
   }

   public JsonNode remove(String var1) {
      return (JsonNode)this._children.remove(var1);
   }

   public ObjectNode remove(Collection var1) {
      this._children.keySet().removeAll(var1);
      return this;
   }

   public ObjectNode removeAll() {
      this._children.clear();
      return this;
   }

   /** @deprecated */
   @Deprecated
   public JsonNode putAll(Map var1) {
      return this.setAll(var1);
   }

   /** @deprecated */
   @Deprecated
   public JsonNode putAll(ObjectNode var1) {
      return this.setAll(var1);
   }

   public ObjectNode retain(Collection var1) {
      this._children.keySet().retainAll(var1);
      return this;
   }

   public ObjectNode retain(String... var1) {
      return this.retain((Collection)Arrays.asList(var1));
   }

   public ArrayNode putArray(String var1) {
      ArrayNode var2 = this.arrayNode();
      this._put(var1, var2);
      return var2;
   }

   public ObjectNode putObject(String var1) {
      ObjectNode var2 = this.objectNode();
      this._put(var1, var2);
      return var2;
   }

   public ObjectNode putPOJO(String var1, Object var2) {
      return this._put(var1, this.pojoNode(var2));
   }

   public ObjectNode putRawValue(String var1, RawValue var2) {
      return this._put(var1, this.rawValueNode(var2));
   }

   public ObjectNode putNull(String var1) {
      this._children.put(var1, this.nullNode());
      return this;
   }

   public ObjectNode put(String var1, short var2) {
      return this._put(var1, this.numberNode(var2));
   }

   public ObjectNode put(String var1, Short var2) {
      return this._put(var1, (JsonNode)(var2 == null ? this.nullNode() : this.numberNode(var2)));
   }

   public ObjectNode put(String var1, int var2) {
      return this._put(var1, this.numberNode(var2));
   }

   public ObjectNode put(String var1, Integer var2) {
      return this._put(var1, (JsonNode)(var2 == null ? this.nullNode() : this.numberNode(var2)));
   }

   public ObjectNode put(String var1, long var2) {
      return this._put(var1, this.numberNode(var2));
   }

   public ObjectNode put(String var1, Long var2) {
      return this._put(var1, (JsonNode)(var2 == null ? this.nullNode() : this.numberNode(var2)));
   }

   public ObjectNode put(String var1, float var2) {
      return this._put(var1, this.numberNode(var2));
   }

   public ObjectNode put(String var1, Float var2) {
      return this._put(var1, (JsonNode)(var2 == null ? this.nullNode() : this.numberNode(var2)));
   }

   public ObjectNode put(String var1, double var2) {
      return this._put(var1, this.numberNode(var2));
   }

   public ObjectNode put(String var1, Double var2) {
      return this._put(var1, (JsonNode)(var2 == null ? this.nullNode() : this.numberNode(var2)));
   }

   public ObjectNode put(String var1, BigDecimal var2) {
      return this._put(var1, (JsonNode)(var2 == null ? this.nullNode() : this.numberNode(var2)));
   }

   public ObjectNode put(String var1, BigInteger var2) {
      return this._put(var1, (JsonNode)(var2 == null ? this.nullNode() : this.numberNode(var2)));
   }

   public ObjectNode put(String var1, String var2) {
      return this._put(var1, (JsonNode)(var2 == null ? this.nullNode() : this.textNode(var2)));
   }

   public ObjectNode put(String var1, boolean var2) {
      return this._put(var1, this.booleanNode(var2));
   }

   public ObjectNode put(String var1, Boolean var2) {
      return this._put(var1, (JsonNode)(var2 == null ? this.nullNode() : this.booleanNode(var2)));
   }

   public ObjectNode put(String var1, byte[] var2) {
      return this._put(var1, (JsonNode)(var2 == null ? this.nullNode() : this.binaryNode(var2)));
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         return var1 instanceof ObjectNode ? this._childrenEqual((ObjectNode)var1) : false;
      }
   }

   protected boolean _childrenEqual(ObjectNode var1) {
      return this._children.equals(var1._children);
   }

   public int hashCode() {
      return this._children.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(32 + (this.size() << 4));
      var1.append("{");
      int var2 = 0;
      Iterator var3 = this._children.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if (var2 > 0) {
            var1.append(",");
         }

         ++var2;
         TextNode.appendQuoted(var1, (String)var4.getKey());
         var1.append(':');
         var1.append(((JsonNode)var4.getValue()).toString());
      }

      var1.append("}");
      return var1.toString();
   }

   protected ObjectNode _put(String var1, JsonNode var2) {
      this._children.put(var1, var2);
      return this;
   }

   public ContainerNode removeAll() {
      return this.removeAll();
   }

   public JsonNode withArray(String var1) {
      return this.withArray(var1);
   }

   public JsonNode with(String var1) {
      return this.with(var1);
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
