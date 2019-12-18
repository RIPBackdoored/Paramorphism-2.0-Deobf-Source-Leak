package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

public class JsonNodeDeserializer extends BaseNodeDeserializer {
   private static final JsonNodeDeserializer instance = new JsonNodeDeserializer();

   protected JsonNodeDeserializer() {
      super(JsonNode.class, (Boolean)null);
   }

   public static JsonDeserializer getDeserializer(Class var0) {
      if (var0 == ObjectNode.class) {
         return JsonNodeDeserializer$ObjectDeserializer.getInstance();
      } else {
         return (JsonDeserializer)(var0 == ArrayNode.class ? JsonNodeDeserializer$ArrayDeserializer.getInstance() : instance);
      }
   }

   public JsonNode getNullValue(DeserializationContext var1) {
      return NullNode.getInstance();
   }

   public JsonNode deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 1:
         return this.deserializeObject(var1, var2, var2.getNodeFactory());
      case 3:
         return this.deserializeArray(var1, var2, var2.getNodeFactory());
      default:
         return this.deserializeAny(var1, var2, var2.getNodeFactory());
      }
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return super.supportsUpdate(var1);
   }

   public boolean isCachable() {
      return super.isCachable();
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return super.deserializeWithType(var1, var2, var3);
   }

   public Object getNullValue(DeserializationContext var1) throws JsonMappingException {
      return this.getNullValue(var1);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
