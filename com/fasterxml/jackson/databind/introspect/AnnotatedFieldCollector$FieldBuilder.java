package com.fasterxml.jackson.databind.introspect;

import java.lang.reflect.Field;

final class AnnotatedFieldCollector$FieldBuilder {
   public final TypeResolutionContext typeContext;
   public final Field field;
   public AnnotationCollector annotations;

   public AnnotatedFieldCollector$FieldBuilder(TypeResolutionContext var1, Field var2) {
      super();
      this.typeContext = var1;
      this.field = var2;
      this.annotations = AnnotationCollector.emptyCollector();
   }

   public AnnotatedField build() {
      return new AnnotatedField(this.typeContext, this.field, this.annotations.asAnnotationMap());
   }
}
