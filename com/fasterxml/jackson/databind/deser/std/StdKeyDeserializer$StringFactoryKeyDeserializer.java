package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.lang.reflect.Method;

final class StdKeyDeserializer$StringFactoryKeyDeserializer extends StdKeyDeserializer {
   private static final long serialVersionUID = 1L;
   final Method _factoryMethod;

   public StdKeyDeserializer$StringFactoryKeyDeserializer(Method var1) {
      super(-1, var1.getDeclaringClass());
      this._factoryMethod = var1;
   }

   public Object _parse(String var1, DeserializationContext var2) throws Exception {
      return this._factoryMethod.invoke((Object)null, var1);
   }
}
