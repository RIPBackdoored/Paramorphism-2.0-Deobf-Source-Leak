package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/** @deprecated */
@Deprecated
public class StdKeySerializer extends StdSerializer {
   public StdKeySerializer() {
      super(Object.class);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeFieldName(var1.toString());
   }
}
