package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.util.ByteBufferBackedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteBufferDeserializer extends StdScalarDeserializer {
   private static final long serialVersionUID = 1L;

   protected ByteBufferDeserializer() {
      super(ByteBuffer.class);
   }

   public ByteBuffer deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      byte[] var3 = var1.getBinaryValue();
      return ByteBuffer.wrap(var3);
   }

   public ByteBuffer deserialize(JsonParser var1, DeserializationContext var2, ByteBuffer var3) throws IOException {
      ByteBufferBackedOutputStream var4 = new ByteBufferBackedOutputStream(var3);
      var1.readBinaryValue(var2.getBase64Variant(), var4);
      var4.close();
      return var3;
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (ByteBuffer)var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
