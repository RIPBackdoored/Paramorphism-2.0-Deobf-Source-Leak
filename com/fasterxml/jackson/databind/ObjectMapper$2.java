package com.fasterxml.jackson.databind;

import java.security.PrivilegedAction;
import java.util.ServiceLoader;

final class ObjectMapper$2 implements PrivilegedAction {
   final ClassLoader val$classLoader;
   final Class val$clazz;

   ObjectMapper$2(ClassLoader var1, Class var2) {
      super();
      this.val$classLoader = var1;
      this.val$clazz = var2;
   }

   public ServiceLoader run() {
      return this.val$classLoader == null ? ServiceLoader.load(this.val$clazz) : ServiceLoader.load(this.val$clazz, this.val$classLoader);
   }

   public Object run() {
      return this.run();
   }
}
