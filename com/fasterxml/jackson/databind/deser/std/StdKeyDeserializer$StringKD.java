package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import java.io.IOException;

@JacksonStdImpl
final class StdKeyDeserializer$StringKD extends StdKeyDeserializer {
   private static final long serialVersionUID = 1L;
   private static final StdKeyDeserializer$StringKD sString = new StdKeyDeserializer$StringKD(String.class);
   private static final StdKeyDeserializer$StringKD sObject = new StdKeyDeserializer$StringKD(Object.class);

   private StdKeyDeserializer$StringKD(Class var1) {
      super(-1, var1);
   }

   public static StdKeyDeserializer$StringKD forType(Class var0) {
      if (var0 == String.class) {
         return sString;
      } else {
         return var0 == Object.class ? sObject : new StdKeyDeserializer$StringKD(var0);
      }
   }

   public Object deserializeKey(String var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return var1;
   }
}
