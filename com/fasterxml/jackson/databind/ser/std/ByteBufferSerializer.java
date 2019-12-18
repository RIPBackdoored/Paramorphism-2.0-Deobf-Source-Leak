package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteBufferSerializer extends StdScalarSerializer {
   public ByteBufferSerializer() {
      super(ByteBuffer.class);
   }

   public void serialize(ByteBuffer var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (var1.hasArray()) {
         var2.writeBinary(var1.array(), 0, var1.limit());
      } else {
         ByteBuffer var4 = var1.asReadOnlyBuffer();
         if (var4.position() > 0) {
            var4.rewind();
         }

         ByteBufferBackedInputStream var5 = new ByteBufferBackedInputStream(var4);
         var2.writeBinary(var5, var4.remaining());
         var5.close();
      }
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      JsonArrayFormatVisitor var3 = var1.expectArrayFormat(var2);
      if (var3 != null) {
         var3.itemsFormat(JsonFormatTypes.INTEGER);
      }

   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((ByteBuffer)var1, var2, var3);
   }
}
