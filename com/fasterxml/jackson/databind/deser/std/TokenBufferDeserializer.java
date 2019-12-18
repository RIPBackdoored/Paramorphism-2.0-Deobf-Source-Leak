package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;

@JacksonStdImpl
public class TokenBufferDeserializer extends StdScalarDeserializer {
   private static final long serialVersionUID = 1L;

   public TokenBufferDeserializer() {
      super(TokenBuffer.class);
   }

   public TokenBuffer deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return this.createBufferInstance(var1).deserialize(var1, var2);
   }

   protected TokenBuffer createBufferInstance(JsonParser var1) {
      return new TokenBuffer(var1);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
