package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.lang.annotation.Annotation;

class AnnotationCollector$EmptyCollector extends AnnotationCollector {
   public static final AnnotationCollector$EmptyCollector instance = new AnnotationCollector$EmptyCollector((Object)null);

   AnnotationCollector$EmptyCollector(Object var1) {
      super(var1);
   }

   public Annotations asAnnotations() {
      return NO_ANNOTATIONS;
   }

   public AnnotationMap asAnnotationMap() {
      return new AnnotationMap();
   }

   public boolean isPresent(Annotation var1) {
      return false;
   }

   public AnnotationCollector addOrOverride(Annotation var1) {
      return new AnnotationCollector$OneCollector(this._data, var1.annotationType(), var1);
   }
}
