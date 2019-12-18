package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;

public class NullifyingDeserializer extends StdDeserializer {
   private static final long serialVersionUID = 1L;
   public static final NullifyingDeserializer instance = new NullifyingDeserializer();

   public NullifyingDeserializer() {
      super(Object.class);
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return Boolean.FALSE;
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (var1.hasToken(JsonToken.FIELD_NAME)) {
         while(true) {
            JsonToken var3 = var1.nextToken();
            if (var3 == null || var3 == JsonToken.END_OBJECT) {
               break;
            }

            var1.skipChildren();
         }
      } else {
         var1.skipChildren();
      }

      return null;
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 1:
      case 3:
      case 5:
         return var3.deserializeTypedFromAny(var1, var2);
      case 2:
      case 4:
      default:
         return null;
      }
   }
}
