package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.EnumValues;
import java.io.IOException;

public class StdKeySerializers$EnumKeySerializer extends StdSerializer {
   protected final EnumValues _values;

   protected StdKeySerializers$EnumKeySerializer(Class var1, EnumValues var2) {
      super(var1, false);
      this._values = var2;
   }

   public static StdKeySerializers$EnumKeySerializer construct(Class var0, EnumValues var1) {
      return new StdKeySerializers$EnumKeySerializer(var0, var1);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (var3.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
         var2.writeFieldName(var1.toString());
      } else {
         Enum var4 = (Enum)var1;
         if (var3.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX)) {
            var2.writeFieldName(String.valueOf(var4.ordinal()));
         } else {
            var2.writeFieldName(this._values.serializedValueFor(var4));
         }
      }
   }
}
