package com.fasterxml.jackson.databind.introspect;

import java.io.Serializable;
import java.lang.reflect.Constructor;

final class AnnotatedConstructor$Serialization implements Serializable {
   private static final long serialVersionUID = 1L;
   protected Class clazz;
   protected Class[] args;

   public AnnotatedConstructor$Serialization(Constructor var1) {
      super();
      this.clazz = var1.getDeclaringClass();
      this.args = var1.getParameterTypes();
   }
}
