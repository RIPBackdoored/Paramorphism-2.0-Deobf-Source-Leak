package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

public class CoreXMLDeserializers$Std extends FromStringDeserializer {
   private static final long serialVersionUID = 1L;
   protected final int _kind;

   public CoreXMLDeserializers$Std(Class var1, int var2) {
      super(var1);
      this._kind = var2;
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._kind == 2 && var1.hasToken(JsonToken.VALUE_NUMBER_INT) ? this._gregorianFromDate(var2, this._parseDate(var1, var2)) : super.deserialize(var1, var2);
   }

   protected Object _deserialize(String var1, DeserializationContext var2) throws IOException {
      switch(this._kind) {
      case 1:
         return CoreXMLDeserializers._dataTypeFactory.newDuration(var1);
      case 2:
         Date var3;
         try {
            var3 = this._parseDate(var1, var2);
         } catch (JsonMappingException var5) {
            return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar(var1);
         }

         return this._gregorianFromDate(var2, var3);
      case 3:
         return QName.valueOf(var1);
      default:
         throw new IllegalStateException();
      }
   }

   protected XMLGregorianCalendar _gregorianFromDate(DeserializationContext var1, Date var2) {
      if (var2 == null) {
         return null;
      } else {
         GregorianCalendar var3 = new GregorianCalendar();
         var3.setTime(var2);
         TimeZone var4 = var1.getTimeZone();
         if (var4 != null) {
            var3.setTimeZone(var4);
         }

         return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar(var3);
      }
   }
}
