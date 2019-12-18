package com.fasterxml.jackson.databind.introspect;

import java.io.Serializable;
import java.lang.reflect.Field;

final class AnnotatedField$Serialization implements Serializable {
   private static final long serialVersionUID = 1L;
   protected Class clazz;
   protected String name;

   public AnnotatedField$Serialization(Field var1) {
      super();
      this.clazz = var1.getDeclaringClass();
      this.name = var1.getName();
   }
}
