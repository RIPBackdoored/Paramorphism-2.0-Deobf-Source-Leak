package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers$Base;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

public class CoreXMLDeserializers extends Deserializers$Base {
   static final DatatypeFactory _dataTypeFactory;
   protected static final int TYPE_DURATION = 1;
   protected static final int TYPE_G_CALENDAR = 2;
   protected static final int TYPE_QNAME = 3;

   public CoreXMLDeserializers() {
      super();
   }

   public JsonDeserializer findBeanDeserializer(JavaType var1, DeserializationConfig var2, BeanDescription var3) {
      Class var4 = var1.getRawClass();
      if (var4 == QName.class) {
         return new CoreXMLDeserializers$Std(var4, 3);
      } else if (var4 == XMLGregorianCalendar.class) {
         return new CoreXMLDeserializers$Std(var4, 2);
      } else {
         return var4 == Duration.class ? new CoreXMLDeserializers$Std(var4, 1) : null;
      }
   }

   static {
      try {
         _dataTypeFactory = DatatypeFactory.newInstance();
      } catch (DatatypeConfigurationException var1) {
         throw new RuntimeException(var1);
      }

   }
}
