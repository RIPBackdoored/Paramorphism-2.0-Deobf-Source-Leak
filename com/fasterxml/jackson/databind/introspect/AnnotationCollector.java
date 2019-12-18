package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.lang.annotation.Annotation;

public abstract class AnnotationCollector {
   protected static final Annotations NO_ANNOTATIONS = new AnnotationCollector$NoAnnotations();
   protected final Object _data;

   protected AnnotationCollector(Object var1) {
      super();
      this._data = var1;
   }

   public static Annotations emptyAnnotations() {
      return NO_ANNOTATIONS;
   }

   public static AnnotationCollector emptyCollector() {
      return AnnotationCollector$EmptyCollector.instance;
   }

   public static AnnotationCollector emptyCollector(Object var0) {
      return new AnnotationCollector$EmptyCollector(var0);
   }

   public abstract Annotations asAnnotations();

   public abstract AnnotationMap asAnnotationMap();

   public Object getData() {
      return this._data;
   }

   public abstract boolean isPresent(Annotation var1);

   public abstract AnnotationCollector addOrOverride(Annotation var1);
}
