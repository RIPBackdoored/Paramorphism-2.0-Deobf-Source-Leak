package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class StdKeySerializers$Default extends StdSerializer {
   static final int TYPE_DATE = 1;
   static final int TYPE_CALENDAR = 2;
   static final int TYPE_CLASS = 3;
   static final int TYPE_ENUM = 4;
   static final int TYPE_INTEGER = 5;
   static final int TYPE_LONG = 6;
   static final int TYPE_BYTE_ARRAY = 7;
   static final int TYPE_TO_STRING = 8;
   protected final int _typeId;

   public StdKeySerializers$Default(int var1, Class var2) {
      super(var2, false);
      this._typeId = var1;
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      String var4;
      switch(this._typeId) {
      case 1:
         var3.defaultSerializeDateKey((Date)var1, var2);
         break;
      case 2:
         var3.defaultSerializeDateKey(((Calendar)var1).getTimeInMillis(), var2);
         break;
      case 3:
         var2.writeFieldName(((Class)var1).getName());
         break;
      case 4:
         if (var3.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
            var4 = var1.toString();
         } else {
            Enum var5 = (Enum)var1;
            if (var3.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX)) {
               var4 = String.valueOf(var5.ordinal());
            } else {
               var4 = var5.name();
            }
         }

         var2.writeFieldName(var4);
         break;
      case 5:
      case 6:
         var2.writeFieldId(((Number)var1).longValue());
         break;
      case 7:
         var4 = var3.getConfig().getBase64Variant().encode((byte[])((byte[])var1));
         var2.writeFieldName(var4);
         break;
      case 8:
      default:
         var2.writeFieldName(var1.toString());
      }

   }
}
