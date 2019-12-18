package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class OptionalHandlerFactory implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String PACKAGE_PREFIX_JAVAX_XML = "javax.xml.";
   private static final String SERIALIZERS_FOR_JAVAX_XML = "com.fasterxml.jackson.databind.ext.CoreXMLSerializers";
   private static final String DESERIALIZERS_FOR_JAVAX_XML = "com.fasterxml.jackson.databind.ext.CoreXMLDeserializers";
   private static final String SERIALIZER_FOR_DOM_NODE = "com.fasterxml.jackson.databind.ext.DOMSerializer";
   private static final String DESERIALIZER_FOR_DOM_DOCUMENT = "com.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer";
   private static final String DESERIALIZER_FOR_DOM_NODE = "com.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer";
   private static final Class CLASS_DOM_NODE;
   private static final Class CLASS_DOM_DOCUMENT;
   private static final Java7Support _jdk7Helper;
   public static final OptionalHandlerFactory instance;

   protected OptionalHandlerFactory() {
      super();
   }

   public JsonSerializer findSerializer(SerializationConfig var1, JavaType var2, BeanDescription var3) {
      Class var4 = var2.getRawClass();
      if (_jdk7Helper != null) {
         JsonSerializer var5 = _jdk7Helper.getSerializerForJavaNioFilePath(var4);
         if (var5 != null) {
            return var5;
         }
      }

      if (CLASS_DOM_NODE != null && CLASS_DOM_NODE.isAssignableFrom(var4)) {
         return (JsonSerializer)this.instantiate("com.fasterxml.jackson.databind.ext.DOMSerializer");
      } else {
         String var8 = var4.getName();
         if (!var8.startsWith("javax.xml.") && !this.hasSuperClassStartingWith(var4, "javax.xml.")) {
            return null;
         } else {
            String var6 = "com.fasterxml.jackson.databind.ext.CoreXMLSerializers";
            Object var7 = this.instantiate(var6);
            return var7 == null ? null : ((Serializers)var7).findSerializer(var1, var2, var3);
         }
      }
   }

   public JsonDeserializer findDeserializer(JavaType var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      Class var4 = var1.getRawClass();
      if (_jdk7Helper != null) {
         JsonDeserializer var5 = _jdk7Helper.getDeserializerForJavaNioFilePath(var4);
         if (var5 != null) {
            return var5;
         }
      }

      if (CLASS_DOM_NODE != null && CLASS_DOM_NODE.isAssignableFrom(var4)) {
         return (JsonDeserializer)this.instantiate("com.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer");
      } else if (CLASS_DOM_DOCUMENT != null && CLASS_DOM_DOCUMENT.isAssignableFrom(var4)) {
         return (JsonDeserializer)this.instantiate("com.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer");
      } else {
         String var8 = var4.getName();
         if (!var8.startsWith("javax.xml.") && !this.hasSuperClassStartingWith(var4, "javax.xml.")) {
            return null;
         } else {
            String var6 = "com.fasterxml.jackson.databind.ext.CoreXMLDeserializers";
            Object var7 = this.instantiate(var6);
            return var7 == null ? null : ((Deserializers)var7).findBeanDeserializer(var1, var2, var3);
         }
      }
   }

   private Object instantiate(String var1) {
      try {
         Object var10000 = ClassUtil.createInstance(Class.forName(var1), false);
         return var10000;
      } catch (LinkageError var3) {
      } catch (Exception var4) {
      }

      return null;
   }

   private boolean hasSuperClassStartingWith(Class var1, String var2) {
      for(Class var3 = var1.getSuperclass(); var3 != null; var3 = var3.getSuperclass()) {
         if (var3 == Object.class) {
            return false;
         }

         if (var3.getName().startsWith(var2)) {
            return true;
         }
      }

      return false;
   }

   static {
      Class var0 = null;
      Class var1 = null;

      try {
         var1 = Node.class;
         var0 = Document.class;
      } catch (Exception var4) {
         Logger.getLogger(OptionalHandlerFactory.class.getName()).log(Level.INFO, "Could not load DOM `Node` and/or `Document` classes: no DOM support");
      }

      CLASS_DOM_NODE = var1;
      CLASS_DOM_DOCUMENT = var0;
      Java7Support var5 = null;

      try {
         var5 = Java7Support.instance();
      } catch (Throwable var3) {
      }

      _jdk7Helper = var5;
      instance = new OptionalHandlerFactory();
   }
}
