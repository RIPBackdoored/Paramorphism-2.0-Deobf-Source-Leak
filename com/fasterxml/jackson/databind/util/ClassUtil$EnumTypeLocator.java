package com.fasterxml.jackson.databind.util;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.EnumSet;

class ClassUtil$EnumTypeLocator {
   static final ClassUtil$EnumTypeLocator instance = new ClassUtil$EnumTypeLocator();
   private final Field enumSetTypeField = locateField(EnumSet.class, "elementType", Class.class);
   private final Field enumMapTypeField = locateField(EnumMap.class, "elementType", Class.class);

   private ClassUtil$EnumTypeLocator() {
      super();
   }

   public Class enumTypeFor(EnumSet var1) {
      if (this.enumSetTypeField != null) {
         return (Class)this.get(var1, this.enumSetTypeField);
      } else {
         throw new IllegalStateException("Cannot figure out type for EnumSet (odd JDK platform?)");
      }
   }

   public Class enumTypeFor(EnumMap var1) {
      if (this.enumMapTypeField != null) {
         return (Class)this.get(var1, this.enumMapTypeField);
      } else {
         throw new IllegalStateException("Cannot figure out type for EnumMap (odd JDK platform?)");
      }
   }

   private Object get(Object var1, Field var2) {
      Object var10000;
      try {
         var10000 = var2.get(var1);
      } catch (Exception var4) {
         throw new IllegalArgumentException(var4);
      }

      return var10000;
   }

   private static Field locateField(Class var0, String var1, Class var2) {
      Field var3 = null;
      Field[] var4 = ClassUtil.getDeclaredFields(var0);
      Field[] var5 = var4;
      int var6 = var4.length;

      int var7;
      Field var8;
      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var5[var7];
         if (var1.equals(var8.getName()) && var8.getType() == var2) {
            var3 = var8;
            break;
         }
      }

      if (var3 == null) {
         var5 = var4;
         var6 = var4.length;

         for(var7 = 0; var7 < var6; ++var7) {
            var8 = var5[var7];
            if (var8.getType() == var2) {
               if (var3 != null) {
                  return null;
               }

               var3 = var8;
            }
         }
      }

      if (var3 != null) {
         try {
            var3.setAccessible(true);
         } catch (Throwable var9) {
         }
      }

      return var3;
   }
}
