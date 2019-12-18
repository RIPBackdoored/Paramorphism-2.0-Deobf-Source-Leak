package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;

final class JsonNodeDeserializer$ArrayDeserializer extends BaseNodeDeserializer {
   private static final long serialVersionUID = 1L;
   protected static final JsonNodeDeserializer$ArrayDeserializer _instance = new JsonNodeDeserializer$ArrayDeserializer();

   protected JsonNodeDeserializer$ArrayDeserializer() {
      super(ArrayNode.class, true);
   }

   public static JsonNodeDeserializer$ArrayDeserializer getInstance() {
      return _instance;
   }

   public ArrayNode deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return var1.isExpectedStartArrayToken() ? this.deserializeArray(var1, var2, var2.getNodeFactory()) : (ArrayNode)var2.handleUnexpectedToken(ArrayNode.class, var1);
   }

   public ArrayNode deserialize(JsonParser var1, DeserializationContext var2, ArrayNode var3) throws IOException {
      return var1.isExpectedStartArrayToken() ? (ArrayNode)this.updateArray(var1, var2, var3) : (ArrayNode)var2.handleUnexpectedToken(ArrayNode.class, var1);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (ArrayNode)var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
