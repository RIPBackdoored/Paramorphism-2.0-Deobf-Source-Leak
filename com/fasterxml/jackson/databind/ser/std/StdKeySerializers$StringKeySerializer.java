package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class StdKeySerializers$StringKeySerializer extends StdSerializer {
   public StdKeySerializers$StringKeySerializer() {
      super(String.class, false);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeFieldName((String)var1);
   }
}
