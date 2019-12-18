package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanDeserializer extends StdScalarDeserializer {
   private static final long serialVersionUID = 1L;

   public AtomicBooleanDeserializer() {
      super(AtomicBoolean.class);
   }

   public AtomicBoolean deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return new AtomicBoolean(this._parseBooleanPrimitive(var1, var2));
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
