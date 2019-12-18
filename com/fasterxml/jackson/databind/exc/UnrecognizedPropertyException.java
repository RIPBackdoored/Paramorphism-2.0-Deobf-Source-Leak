package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import java.util.Collection;

public class UnrecognizedPropertyException extends PropertyBindingException {
   private static final long serialVersionUID = 1L;

   public UnrecognizedPropertyException(JsonParser var1, String var2, JsonLocation var3, Class var4, String var5, Collection var6) {
      super(var1, var2, var3, var4, var5, var6);
   }

   /** @deprecated */
   @Deprecated
   public UnrecognizedPropertyException(String var1, JsonLocation var2, Class var3, String var4, Collection var5) {
      super(var1, var2, var3, var4, var5);
   }

   public static UnrecognizedPropertyException from(JsonParser var0, Object var1, String var2, Collection var3) {
      Class var4;
      if (var1 instanceof Class) {
         var4 = (Class)var1;
      } else {
         var4 = var1.getClass();
      }

      String var5 = String.format("Unrecognized field \"%s\" (class %s), not marked as ignorable", var2, var4.getName());
      UnrecognizedPropertyException var6 = new UnrecognizedPropertyException(var0, var5, var0.getCurrentLocation(), var4, var2, var3);
      var6.prependPath(var1, var2);
      return var6;
   }
}
