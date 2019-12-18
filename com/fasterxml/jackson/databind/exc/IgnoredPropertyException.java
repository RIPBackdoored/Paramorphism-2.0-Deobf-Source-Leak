package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import java.util.Collection;

public class IgnoredPropertyException extends PropertyBindingException {
   private static final long serialVersionUID = 1L;

   public IgnoredPropertyException(JsonParser var1, String var2, JsonLocation var3, Class var4, String var5, Collection var6) {
      super(var1, var2, var3, var4, var5, var6);
   }

   /** @deprecated */
   @Deprecated
   public IgnoredPropertyException(String var1, JsonLocation var2, Class var3, String var4, Collection var5) {
      super(var1, var2, var3, var4, var5);
   }

   public static IgnoredPropertyException from(JsonParser var0, Object var1, String var2, Collection var3) {
      Class var4;
      if (var1 instanceof Class) {
         var4 = (Class)var1;
      } else {
         var4 = var1.getClass();
      }

      String var5 = String.format("Ignored field \"%s\" (class %s) encountered; mapper configured not to allow this", var2, var4.getName());
      IgnoredPropertyException var6 = new IgnoredPropertyException(var0, var5, var0.getCurrentLocation(), var4, var2, var3);
      var6.prependPath(var1, var2);
      return var6;
   }
}
