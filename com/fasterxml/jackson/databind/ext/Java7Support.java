package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.util.logging.Logger;

public abstract class Java7Support {
   private static final Java7Support IMPL;

   public Java7Support() {
      super();
   }

   public static Java7Support instance() {
      return IMPL;
   }

   public abstract Boolean findTransient(Annotated var1);

   public abstract Boolean hasCreatorAnnotation(Annotated var1);

   public abstract PropertyName findConstructorName(AnnotatedParameter var1);

   public abstract Class getClassJavaNioFilePath();

   public abstract JsonDeserializer getDeserializerForJavaNioFilePath(Class var1);

   public abstract JsonSerializer getSerializerForJavaNioFilePath(Class var1);

   static {
      Java7Support var0 = null;

      try {
         Class var1 = Class.forName("com.fasterxml.jackson.databind.ext.Java7SupportImpl");
         var0 = (Java7Support)ClassUtil.createInstance(var1, false);
      } catch (Throwable var2) {
         Logger.getLogger(Java7Support.class.getName()).warning("Unable to load JDK7 types (annotations, java.nio.file.Path): no Java7 support added");
      }

      IMPL = var0;
   }
}
