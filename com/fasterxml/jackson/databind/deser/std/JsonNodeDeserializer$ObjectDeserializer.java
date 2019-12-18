package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

final class JsonNodeDeserializer$ObjectDeserializer extends BaseNodeDeserializer {
   private static final long serialVersionUID = 1L;
   protected static final JsonNodeDeserializer$ObjectDeserializer _instance = new JsonNodeDeserializer$ObjectDeserializer();

   protected JsonNodeDeserializer$ObjectDeserializer() {
      super(ObjectNode.class, true);
   }

   public static JsonNodeDeserializer$ObjectDeserializer getInstance() {
      return _instance;
   }

   public ObjectNode deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (var1.isExpectedStartObjectToken()) {
         return this.deserializeObject(var1, var2, var2.getNodeFactory());
      } else if (var1.hasToken(JsonToken.FIELD_NAME)) {
         return this.deserializeObjectAtName(var1, var2, var2.getNodeFactory());
      } else {
         return var1.hasToken(JsonToken.END_OBJECT) ? var2.getNodeFactory().objectNode() : (ObjectNode)var2.handleUnexpectedToken(ObjectNode.class, var1);
      }
   }

   public ObjectNode deserialize(JsonParser var1, DeserializationContext var2, ObjectNode var3) throws IOException {
      return !var1.isExpectedStartObjectToken() && !var1.hasToken(JsonToken.FIELD_NAME) ? (ObjectNode)var2.handleUnexpectedToken(ObjectNode.class, var1) : (ObjectNode)this.updateObject(var1, var2, var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (ObjectNode)var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
