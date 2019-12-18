package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class ErrorThrowingDeserializer extends JsonDeserializer {
   private final Error _cause;

   public ErrorThrowingDeserializer(NoClassDefFoundError var1) {
      super();
      this._cause = var1;
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      throw this._cause;
   }
}
