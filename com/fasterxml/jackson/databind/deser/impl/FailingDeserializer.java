package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class FailingDeserializer extends StdDeserializer {
   private static final long serialVersionUID = 1L;
   protected final String _message;

   public FailingDeserializer(String var1) {
      super(Object.class);
      this._message = var1;
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      var2.reportInputMismatch((JsonDeserializer)this, this._message);
      return null;
   }
}
