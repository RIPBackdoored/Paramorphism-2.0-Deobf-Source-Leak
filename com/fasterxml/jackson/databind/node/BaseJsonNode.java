package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public abstract class BaseJsonNode extends JsonNode implements JsonSerializable {
   protected BaseJsonNode() {
      super();
   }

   public final JsonNode findPath(String var1) {
      JsonNode var2 = this.findValue(var1);
      return (JsonNode)(var2 == null ? MissingNode.getInstance() : var2);
   }

   public abstract int hashCode();

   public JsonParser traverse() {
      return new TreeTraversingParser(this);
   }

   public JsonParser traverse(ObjectCodec var1) {
      return new TreeTraversingParser(this, var1);
   }

   public abstract JsonToken asToken();

   public JsonParser$NumberType numberType() {
      return null;
   }

   public abstract void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException;

   public abstract void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException, JsonProcessingException;
}
