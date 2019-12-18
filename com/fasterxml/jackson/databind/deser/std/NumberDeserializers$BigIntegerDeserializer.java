package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import java.io.IOException;
import java.math.BigInteger;

@JacksonStdImpl
public class NumberDeserializers$BigIntegerDeserializer extends StdScalarDeserializer {
   public static final NumberDeserializers$BigIntegerDeserializer instance = new NumberDeserializers$BigIntegerDeserializer();

   public NumberDeserializers$BigIntegerDeserializer() {
      super(BigInteger.class);
   }

   public Object getEmptyValue(DeserializationContext var1) {
      return BigInteger.ZERO;
   }

   public BigInteger deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
