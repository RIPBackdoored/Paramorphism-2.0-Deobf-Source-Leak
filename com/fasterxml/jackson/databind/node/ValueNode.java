package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.List;

public abstract class ValueNode extends BaseJsonNode {
   protected ValueNode() {
      super();
   }

   protected JsonNode _at(JsonPointer var1) {
      return MissingNode.getInstance();
   }

   public JsonNode deepCopy() {
      return this;
   }

   public abstract JsonToken asToken();

   public void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException {
      WritableTypeId var4 = var3.writeTypePrefix(var1, var3.typeId(this, this.asToken()));
      this.serialize(var1, var2);
      var3.writeTypeSuffix(var1, var4);
   }

   public String toString() {
      return this.asText();
   }

   public final JsonNode get(int var1) {
      return null;
   }

   public final JsonNode path(int var1) {
      return MissingNode.getInstance();
   }

   public final boolean has(int var1) {
      return false;
   }

   public final boolean hasNonNull(int var1) {
      return false;
   }

   public final JsonNode get(String var1) {
      return null;
   }

   public final JsonNode path(String var1) {
      return MissingNode.getInstance();
   }

   public final boolean has(String var1) {
      return false;
   }

   public final boolean hasNonNull(String var1) {
      return false;
   }

   public final JsonNode findValue(String var1) {
      return null;
   }

   public final ObjectNode findParent(String var1) {
      return null;
   }

   public final List findValues(String var1, List var2) {
      return var2;
   }

   public final List findValuesAsText(String var1, List var2) {
      return var2;
   }

   public final List findParents(String var1, List var2) {
      return var2;
   }

   public JsonNode findParent(String var1) {
      return this.findParent(var1);
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
