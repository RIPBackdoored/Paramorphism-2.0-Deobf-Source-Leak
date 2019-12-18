package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import java.beans.ConstructorProperties;
import java.beans.Transient;
import java.nio.file.Path;

public class Java7SupportImpl extends Java7Support {
   private final Class _bogus;

   public Java7SupportImpl() {
      super();
      Class var1 = Transient.class;
      var1 = ConstructorProperties.class;
      this._bogus = var1;
   }

   public Class getClassJavaNioFilePath() {
      return Path.class;
   }

   public JsonDeserializer getDeserializerForJavaNioFilePath(Class var1) {
      return var1 == Path.class ? new NioPathDeserializer() : null;
   }

   public JsonSerializer getSerializerForJavaNioFilePath(Class var1) {
      return Path.class.isAssignableFrom(var1) ? new NioPathSerializer() : null;
   }

   public Boolean findTransient(Annotated var1) {
      Transient var2 = (Transient)var1.getAnnotation(Transient.class);
      return var2 != null ? var2.value() : null;
   }

   public Boolean hasCreatorAnnotation(Annotated var1) {
      ConstructorProperties var2 = (ConstructorProperties)var1.getAnnotation(ConstructorProperties.class);
      return var2 != null ? Boolean.TRUE : null;
   }

   public PropertyName findConstructorName(AnnotatedParameter var1) {
      AnnotatedWithParams var2 = var1.getOwner();
      if (var2 != null) {
         ConstructorProperties var3 = (ConstructorProperties)var2.getAnnotation(ConstructorProperties.class);
         if (var3 != null) {
            String[] var4 = var3.value();
            int var5 = var1.getIndex();
            if (var5 < var4.length) {
               return PropertyName.construct(var4[var5]);
            }
         }
      }

      return null;
   }
}
