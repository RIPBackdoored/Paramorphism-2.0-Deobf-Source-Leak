package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.DeserializationContext;
import org.w3c.dom.Node;

public class DOMDeserializer$NodeDeserializer extends DOMDeserializer {
   private static final long serialVersionUID = 1L;

   public DOMDeserializer$NodeDeserializer() {
      super(Node.class);
   }

   public Node _deserialize(String var1, DeserializationContext var2) throws IllegalArgumentException {
      return this.parse(var1);
   }

   public Object _deserialize(String var1, DeserializationContext var2) {
      return this._deserialize(var1, var2);
   }
}
