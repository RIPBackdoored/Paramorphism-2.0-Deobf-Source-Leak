package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo$Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;

public class ClassNameIdResolver extends TypeIdResolverBase {
   private static final String JAVA_UTIL_PKG = "java.util.";

   public ClassNameIdResolver(JavaType var1, TypeFactory var2) {
      super(var1, var2);
   }

   public JsonTypeInfo$Id getMechanism() {
      return JsonTypeInfo$Id.CLASS;
   }

   public void registerSubtype(Class var1, String var2) {
   }

   public String idFromValue(Object var1) {
      return this._idFrom(var1, var1.getClass(), this._typeFactory);
   }

   public String idFromValueAndType(Object var1, Class var2) {
      return this._idFrom(var1, var2, this._typeFactory);
   }

   public JavaType typeFromId(DatabindContext var1, String var2) throws IOException {
      return this._typeFromId(var2, var1);
   }

   protected JavaType _typeFromId(String var1, DatabindContext var2) throws IOException {
      JavaType var3 = var2.resolveSubType(this._baseType, var1);
      return var3 == null && var2 instanceof DeserializationContext ? ((DeserializationContext)var2).handleUnknownTypeId(this._baseType, var1, this, "no such class found") : var3;
   }

   protected String _idFrom(Object var1, Class var2, TypeFactory var3) {
      if (Enum.class.isAssignableFrom(var2) && !var2.isEnum()) {
         var2 = var2.getSuperclass();
      }

      String var4 = var2.getName();
      Class var5;
      Class var6;
      if (var4.startsWith("java.util.")) {
         if (var1 instanceof EnumSet) {
            var5 = ClassUtil.findEnumType((EnumSet)var1);
            var4 = var3.constructCollectionType(EnumSet.class, var5).toCanonical();
         } else if (var1 instanceof EnumMap) {
            var5 = ClassUtil.findEnumType((EnumMap)var1);
            var6 = Object.class;
            var4 = var3.constructMapType(EnumMap.class, var5, var6).toCanonical();
         }
      } else if (var4.indexOf(36) >= 0) {
         var5 = ClassUtil.getOuterClass(var2);
         if (var5 != null) {
            var6 = this._baseType.getRawClass();
            if (ClassUtil.getOuterClass(var6) == null) {
               var2 = this._baseType.getRawClass();
               var4 = var2.getName();
            }
         }
      }

      return var4;
   }

   public String getDescForKnownTypeIds() {
      return "class name used as type id";
   }
}
