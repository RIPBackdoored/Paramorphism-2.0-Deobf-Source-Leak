package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NioPathDeserializer extends StdScalarDeserializer {
   private static final long serialVersionUID = 1L;

   public NioPathDeserializer() {
      super(Path.class);
   }

   public Path deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (!var1.hasToken(JsonToken.VALUE_STRING)) {
         return (Path)var2.handleUnexpectedToken(Path.class, var1);
      } else {
         String var3 = var1.getText();
         if (var3.indexOf(58) < 0) {
            return Paths.get(var3);
         } else {
            Path var10000;
            try {
               URI var4 = new URI(var3);
               var10000 = Paths.get(var4);
            } catch (URISyntaxException var5) {
               return (Path)var2.handleInstantiationProblem(this.handledType(), var3, var5);
            }

            return var10000;
         }
      }
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
