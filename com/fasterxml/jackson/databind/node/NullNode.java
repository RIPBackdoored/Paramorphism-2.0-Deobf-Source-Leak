package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public final class NullNode extends ValueNode {
   public static final NullNode instance = new NullNode();

   protected NullNode() {
      super();
   }

   public static NullNode getInstance() {
      return instance;
   }

   public JsonNodeType getNodeType() {
      return JsonNodeType.NULL;
   }

   public JsonToken asToken() {
      return JsonToken.VALUE_NULL;
   }

   public String asText(String var1) {
      return var1;
   }

   public String asText() {
      return "null";
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException {
      var2.defaultSerializeNull(var1);
   }

   public boolean equals(Object var1) {
      return var1 == this;
   }

   public int hashCode() {
      return JsonNodeType.NULL.ordinal();
   }
}
