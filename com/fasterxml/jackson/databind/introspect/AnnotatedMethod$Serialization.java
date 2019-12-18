package com.fasterxml.jackson.databind.introspect;

import java.io.Serializable;
import java.lang.reflect.Method;

final class AnnotatedMethod$Serialization implements Serializable {
   private static final long serialVersionUID = 1L;
   protected Class clazz;
   protected String name;
   protected Class[] args;

   public AnnotatedMethod$Serialization(Method var1) {
      super();
      this.clazz = var1.getDeclaringClass();
      this.name = var1.getName();
      this.args = var1.getParameterTypes();
   }
}
