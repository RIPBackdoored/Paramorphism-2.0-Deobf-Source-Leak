package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;
import java.nio.file.Path;

public class NioPathSerializer extends StdScalarSerializer {
   private static final long serialVersionUID = 1L;

   public NioPathSerializer() {
      super(Path.class);
   }

   public void serialize(Path var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeString(var1.toUri().toString());
   }

   public void serializeWithType(Path var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, (Class)Path.class, (JsonToken)JsonToken.VALUE_STRING));
      this.serialize(var1, var2, var3);
      var4.writeTypeSuffix(var2, var5);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((Path)var1, var2, var3, var4);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Path)var1, var2, var3);
   }
}
