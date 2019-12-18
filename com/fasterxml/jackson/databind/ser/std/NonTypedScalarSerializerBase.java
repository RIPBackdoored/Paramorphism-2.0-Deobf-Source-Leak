package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

/** @deprecated */
@Deprecated
public abstract class NonTypedScalarSerializerBase extends StdScalarSerializer {
   protected NonTypedScalarSerializerBase(Class var1) {
      super(var1);
   }

   protected NonTypedScalarSerializerBase(Class var1, boolean var2) {
      super(var1, var2);
   }

   public final void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serialize(var1, var2, var3);
   }
}
