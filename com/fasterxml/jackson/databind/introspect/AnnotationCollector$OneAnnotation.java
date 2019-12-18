package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.io.Serializable;
import java.lang.annotation.Annotation;

public class AnnotationCollector$OneAnnotation implements Annotations, Serializable {
   private static final long serialVersionUID = 1L;
   private final Class _type;
   private final Annotation _value;

   public AnnotationCollector$OneAnnotation(Class var1, Annotation var2) {
      super();
      this._type = var1;
      this._value = var2;
   }

   public Annotation get(Class var1) {
      return this._type == var1 ? this._value : null;
   }

   public boolean has(Class var1) {
      return this._type == var1;
   }

   public boolean hasOneOf(Class[] var1) {
      Class[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class var5 = var2[var4];
         if (var5 == this._type) {
            return true;
         }
      }

      return false;
   }

   public int size() {
      return 1;
   }
}
