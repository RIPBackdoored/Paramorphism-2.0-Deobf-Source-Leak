package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.io.Serializable;
import java.lang.annotation.Annotation;

public class AnnotationCollector$NoAnnotations implements Annotations, Serializable {
   private static final long serialVersionUID = 1L;

   AnnotationCollector$NoAnnotations() {
      super();
   }

   public Annotation get(Class var1) {
      return null;
   }

   public boolean has(Class var1) {
      return false;
   }

   public boolean hasOneOf(Class[] var1) {
      return false;
   }

   public int size() {
      return 0;
   }
}
