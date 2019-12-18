package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo$Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;

public class MinimalClassNameIdResolver extends ClassNameIdResolver {
   protected final String _basePackageName;
   protected final String _basePackagePrefix;

   protected MinimalClassNameIdResolver(JavaType var1, TypeFactory var2) {
      super(var1, var2);
      String var3 = var1.getRawClass().getName();
      int var4 = var3.lastIndexOf(46);
      if (var4 < 0) {
         this._basePackageName = "";
         this._basePackagePrefix = ".";
      } else {
         this._basePackagePrefix = var3.substring(0, var4 + 1);
         this._basePackageName = var3.substring(0, var4);
      }

   }

   public JsonTypeInfo$Id getMechanism() {
      return JsonTypeInfo$Id.MINIMAL_CLASS;
   }

   public String idFromValue(Object var1) {
      String var2 = var1.getClass().getName();
      return var2.startsWith(this._basePackagePrefix) ? var2.substring(this._basePackagePrefix.length() - 1) : var2;
   }

   protected JavaType _typeFromId(String var1, DatabindContext var2) throws IOException {
      if (var1.startsWith(".")) {
         StringBuilder var3 = new StringBuilder(var1.length() + this._basePackageName.length());
         if (this._basePackageName.length() == 0) {
            var3.append(var1.substring(1));
         } else {
            var3.append(this._basePackageName).append(var1);
         }

         var1 = var3.toString();
      }

      return super._typeFromId(var1, var2);
   }
}
