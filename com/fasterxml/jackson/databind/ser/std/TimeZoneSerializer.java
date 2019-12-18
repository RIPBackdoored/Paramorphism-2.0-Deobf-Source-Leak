package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.TimeZone;

public class TimeZoneSerializer extends StdScalarSerializer {
   public TimeZoneSerializer() {
      super(TimeZone.class);
   }

   public void serialize(TimeZone var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeString(var1.getID());
   }

   public void serializeWithType(TimeZone var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, (Class)TimeZone.class, (JsonToken)JsonToken.VALUE_STRING));
      this.serialize(var1, var2, var3);
      var4.writeTypeSuffix(var2, var5);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((TimeZone)var1, var2, var3, var4);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((TimeZone)var1, var2, var3);
   }
}
