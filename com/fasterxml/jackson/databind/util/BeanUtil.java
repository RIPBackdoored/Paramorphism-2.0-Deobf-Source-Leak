package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BeanUtil {
   public BeanUtil() {
      super();
   }

   public static String okNameForGetter(AnnotatedMethod var0, boolean var1) {
      String var2 = var0.getName();
      String var3 = okNameForIsGetter(var0, var2, var1);
      if (var3 == null) {
         var3 = okNameForRegularGetter(var0, var2, var1);
      }

      return var3;
   }

   public static String okNameForRegularGetter(AnnotatedMethod var0, String var1, boolean var2) {
      if (var1.startsWith("get")) {
         if ("getCallbacks".equals(var1)) {
            if (isCglibGetCallbacks(var0)) {
               return null;
            }
         } else if ("getMetaClass".equals(var1) && isGroovyMetaClassGetter(var0)) {
            return null;
         }

         return var2 ? stdManglePropertyName(var1, 3) : legacyManglePropertyName(var1, 3);
      } else {
         return null;
      }
   }

   public static String okNameForIsGetter(AnnotatedMethod var0, String var1, boolean var2) {
      if (var1.startsWith("is")) {
         Class var3 = var0.getRawType();
         if (var3 == Boolean.class || var3 == Boolean.TYPE) {
            return var2 ? stdManglePropertyName(var1, 2) : legacyManglePropertyName(var1, 2);
         }
      }

      return null;
   }

   /** @deprecated */
   @Deprecated
   public static String okNameForSetter(AnnotatedMethod var0, boolean var1) {
      String var2 = okNameForMutator(var0, "set", var1);
      return var2 == null || "metaClass".equals(var2) && isGroovyMetaClassSetter(var0) ? null : var2;
   }

   public static String okNameForMutator(AnnotatedMethod var0, String var1, boolean var2) {
      String var3 = var0.getName();
      if (var3.startsWith(var1)) {
         return var2 ? stdManglePropertyName(var3, var1.length()) : legacyManglePropertyName(var3, var1.length());
      } else {
         return null;
      }
   }

   public static Object getDefaultValue(JavaType var0) {
      Class var1 = var0.getRawClass();
      Class var2 = ClassUtil.primitiveType(var1);
      if (var2 != null) {
         return ClassUtil.defaultValue(var2);
      } else if (!var0.isContainerType() && !var0.isReferenceType()) {
         if (var1 == String.class) {
            return "";
         } else if (var0.isTypeOrSubTypeOf(Date.class)) {
            return new Date(0L);
         } else if (var0.isTypeOrSubTypeOf(Calendar.class)) {
            GregorianCalendar var3 = new GregorianCalendar();
            var3.setTimeInMillis(0L);
            return var3;
         } else {
            return null;
         }
      } else {
         return JsonInclude$Include.NON_EMPTY;
      }
   }

   protected static boolean isCglibGetCallbacks(AnnotatedMethod var0) {
      Class var1 = var0.getRawType();
      if (var1.isArray()) {
         Class var2 = var1.getComponentType();
         String var3 = ClassUtil.getPackageName(var2);
         if (var3 != null && var3.contains(".cglib")) {
            return var3.startsWith("net.sf.cglib") || var3.startsWith("org.hibernate.repackage.cglib") || var3.startsWith("org.springframework.cglib");
         }
      }

      return false;
   }

   protected static boolean isGroovyMetaClassSetter(AnnotatedMethod var0) {
      Class var1 = var0.getRawParameterType(0);
      String var2 = ClassUtil.getPackageName(var1);
      return var2 != null && var2.startsWith("groovy.lang");
   }

   protected static boolean isGroovyMetaClassGetter(AnnotatedMethod var0) {
      String var1 = ClassUtil.getPackageName(var0.getRawType());
      return var1 != null && var1.startsWith("groovy.lang");
   }

   protected static String legacyManglePropertyName(String var0, int var1) {
      int var2 = var0.length();
      if (var2 == var1) {
         return null;
      } else {
         char var3 = var0.charAt(var1);
         char var4 = Character.toLowerCase(var3);
         if (var3 == var4) {
            return var0.substring(var1);
         } else {
            StringBuilder var5 = new StringBuilder(var2 - var1);
            var5.append(var4);

            for(int var6 = var1 + 1; var6 < var2; ++var6) {
               var3 = var0.charAt(var6);
               var4 = Character.toLowerCase(var3);
               if (var3 == var4) {
                  var5.append(var0, var6, var2);
                  break;
               }

               var5.append(var4);
            }

            return var5.toString();
         }
      }
   }

   protected static String stdManglePropertyName(String var0, int var1) {
      int var2 = var0.length();
      if (var2 == var1) {
         return null;
      } else {
         char var3 = var0.charAt(var1);
         char var4 = Character.toLowerCase(var3);
         if (var3 == var4) {
            return var0.substring(var1);
         } else if (var1 + 1 < var2 && Character.isUpperCase(var0.charAt(var1 + 1))) {
            return var0.substring(var1);
         } else {
            StringBuilder var5 = new StringBuilder(var2 - var1);
            var5.append(var4);
            var5.append(var0, var1 + 1, var2);
            return var5.toString();
         }
      }
   }
}
