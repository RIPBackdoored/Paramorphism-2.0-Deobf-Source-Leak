package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.Serializers$Base;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

public class CoreXMLSerializers extends Serializers$Base {
   public CoreXMLSerializers() {
      super();
   }

   public JsonSerializer findSerializer(SerializationConfig var1, JavaType var2, BeanDescription var3) {
      Class var4 = var2.getRawClass();
      if (!Duration.class.isAssignableFrom(var4) && !QName.class.isAssignableFrom(var4)) {
         return XMLGregorianCalendar.class.isAssignableFrom(var4) ? CoreXMLSerializers$XMLGregorianCalendarSerializer.instance : null;
      } else {
         return ToStringSerializer.instance;
      }
   }
}
