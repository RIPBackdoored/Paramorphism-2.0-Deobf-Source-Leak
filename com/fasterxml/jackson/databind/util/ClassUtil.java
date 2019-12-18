package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator$Feature;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.annotation.NoClass;
import java.io.Closeable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ClassUtil {
   private static final Class CLS_OBJECT = Object.class;
   private static final Annotation[] NO_ANNOTATIONS = new Annotation[0];
   private static final ClassUtil$Ctor[] NO_CTORS = new ClassUtil$Ctor[0];
   private static final Iterator EMPTY_ITERATOR = Collections.emptyIterator();

   public ClassUtil() {
      super();
   }

   public static Iterator emptyIterator() {
      return EMPTY_ITERATOR;
   }

   public static List findSuperTypes(JavaType var0, Class var1, boolean var2) {
      if (var0 != null && !var0.hasRawClass(var1) && !var0.hasRawClass(Object.class)) {
         ArrayList var3 = new ArrayList(8);
         _addSuperTypes(var0, var1, var3, var2);
         return var3;
      } else {
         return Collections.emptyList();
      }
   }

   public static List findRawSuperTypes(Class var0, Class var1, boolean var2) {
      if (var0 != null && var0 != var1 && var0 != Object.class) {
         ArrayList var3 = new ArrayList(8);
         _addRawSuperTypes(var0, var1, var3, var2);
         return var3;
      } else {
         return Collections.emptyList();
      }
   }

   public static List findSuperClasses(Class var0, Class var1, boolean var2) {
      LinkedList var3 = new LinkedList();
      if (var0 != null && var0 != var1) {
         if (var2) {
            var3.add(var0);
         }

         while((var0 = var0.getSuperclass()) != null && var0 != var1) {
            var3.add(var0);
         }
      }

      return var3;
   }

   /** @deprecated */
   @Deprecated
   public static List findSuperTypes(Class var0, Class var1) {
      return findSuperTypes(var0, var1, new ArrayList(8));
   }

   /** @deprecated */
   @Deprecated
   public static List findSuperTypes(Class var0, Class var1, List var2) {
      _addRawSuperTypes(var0, var1, var2, false);
      return var2;
   }

   private static void _addSuperTypes(JavaType var0, Class var1, Collection var2, boolean var3) {
      if (var0 != null) {
         Class var4 = var0.getRawClass();
         if (var4 != var1 && var4 != Object.class) {
            if (var3) {
               if (var2.contains(var0)) {
                  return;
               }

               var2.add(var0);
            }

            Iterator var5 = var0.getInterfaces().iterator();

            while(var5.hasNext()) {
               JavaType var6 = (JavaType)var5.next();
               _addSuperTypes(var6, var1, var2, true);
            }

            _addSuperTypes(var0.getSuperClass(), var1, var2, true);
         }
      }
   }

   private static void _addRawSuperTypes(Class var0, Class var1, Collection var2, boolean var3) {
      if (var0 != var1 && var0 != null && var0 != Object.class) {
         if (var3) {
            if (var2.contains(var0)) {
               return;
            }

            var2.add(var0);
         }

         Class[] var4 = _interfaces(var0);
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class var7 = var4[var6];
            _addRawSuperTypes(var7, var1, var2, true);
         }

         _addRawSuperTypes(var0.getSuperclass(), var1, var2, true);
      }
   }

   public static String canBeABeanType(Class var0) {
      if (var0.isAnnotation()) {
         return "annotation";
      } else if (var0.isArray()) {
         return "array";
      } else if (var0.isEnum()) {
         return "enum";
      } else {
         return var0.isPrimitive() ? "primitive" : null;
      }
   }

   public static String isLocalType(Class var0, boolean var1) {
      try {
         String var10000;
         if (hasEnclosingMethod(var0)) {
            var10000 = "local/anonymous";
            return var10000;
         }

         if (!var1 && !Modifier.isStatic(var0.getModifiers()) && getEnclosingClass(var0) != null) {
            var10000 = "non-static member class";
            return var10000;
         }
      } catch (SecurityException var3) {
      } catch (NullPointerException var4) {
      }

      return null;
   }

   public static Class getOuterClass(Class var0) {
      try {
         Class var10000;
         if (hasEnclosingMethod(var0)) {
            var10000 = null;
            return var10000;
         }

         if (!Modifier.isStatic(var0.getModifiers())) {
            var10000 = getEnclosingClass(var0);
            return var10000;
         }
      } catch (SecurityException var2) {
      }

      return null;
   }

   public static boolean isProxyType(Class var0) {
      String var1 = var0.getName();
      return var1.startsWith("net.sf.cglib.proxy.") || var1.startsWith("org.hibernate.proxy.");
   }

   public static boolean isConcrete(Class var0) {
      int var1 = var0.getModifiers();
      return (var1 & 1536) == 0;
   }

   public static boolean isConcrete(Member var0) {
      int var1 = var0.getModifiers();
      return (var1 & 1536) == 0;
   }

   public static boolean isCollectionMapOrArray(Class var0) {
      if (var0.isArray()) {
         return true;
      } else if (Collection.class.isAssignableFrom(var0)) {
         return true;
      } else {
         return Map.class.isAssignableFrom(var0);
      }
   }

   public static boolean isBogusClass(Class var0) {
      return var0 == Void.class || var0 == Void.TYPE || var0 == NoClass.class;
   }

   public static boolean isNonStaticInnerClass(Class var0) {
      return !Modifier.isStatic(var0.getModifiers()) && getEnclosingClass(var0) != null;
   }

   public static boolean isObjectOrPrimitive(Class var0) {
      return var0 == CLS_OBJECT || var0.isPrimitive();
   }

   public static boolean hasClass(Object var0, Class var1) {
      return var0 != null && var0.getClass() == var1;
   }

   public static void verifyMustOverride(Class var0, Object var1, String var2) {
      if (var1.getClass() != var0) {
         throw new IllegalStateException(String.format("Sub-class %s (of class %s) must override method '%s'", var1.getClass().getName(), var0.getName(), var2));
      }
   }

   /** @deprecated */
   @Deprecated
   public static boolean hasGetterSignature(Method var0) {
      if (Modifier.isStatic(var0.getModifiers())) {
         return false;
      } else {
         Class[] var1 = var0.getParameterTypes();
         if (var1 != null && var1.length != 0) {
            return false;
         } else {
            return Void.TYPE != var0.getReturnType();
         }
      }
   }

   public static Throwable throwIfError(Throwable var0) {
      if (var0 instanceof Error) {
         throw (Error)var0;
      } else {
         return var0;
      }
   }

   public static Throwable throwIfRTE(Throwable var0) {
      if (var0 instanceof RuntimeException) {
         throw (RuntimeException)var0;
      } else {
         return var0;
      }
   }

   public static Throwable throwIfIOE(Throwable var0) throws IOException {
      if (var0 instanceof IOException) {
         throw (IOException)var0;
      } else {
         return var0;
      }
   }

   public static Throwable getRootCause(Throwable var0) {
      while(var0.getCause() != null) {
         var0 = var0.getCause();
      }

      return var0;
   }

   public static Throwable throwRootCauseIfIOE(Throwable var0) throws IOException {
      return throwIfIOE(getRootCause(var0));
   }

   public static void throwAsIAE(Throwable var0) {
      throwAsIAE(var0, var0.getMessage());
   }

   public static void throwAsIAE(Throwable var0, String var1) {
      throwIfRTE(var0);
      throwIfError(var0);
      throw new IllegalArgumentException(var1, var0);
   }

   public static Object throwAsMappingException(DeserializationContext var0, IOException var1) throws JsonMappingException {
      if (var1 instanceof JsonMappingException) {
         throw (JsonMappingException)var1;
      } else {
         JsonMappingException var2 = JsonMappingException.from(var0, var1.getMessage());
         var2.initCause(var1);
         throw var2;
      }
   }

   public static void unwrapAndThrowAsIAE(Throwable var0) {
      throwAsIAE(getRootCause(var0));
   }

   public static void unwrapAndThrowAsIAE(Throwable var0, String var1) {
      throwAsIAE(getRootCause(var0), var1);
   }

   public static void closeOnFailAndThrowAsIOE(JsonGenerator var0, Exception var1) throws IOException {
      var0.disable(JsonGenerator$Feature.AUTO_CLOSE_JSON_CONTENT);

      try {
         var0.close();
      } catch (Exception var3) {
         var1.addSuppressed(var3);
      }

      throwIfIOE(var1);
      throwIfRTE(var1);
      throw new RuntimeException(var1);
   }

   public static void closeOnFailAndThrowAsIOE(JsonGenerator var0, Closeable var1, Exception var2) throws IOException {
      if (var0 != null) {
         var0.disable(JsonGenerator$Feature.AUTO_CLOSE_JSON_CONTENT);

         try {
            var0.close();
         } catch (Exception var5) {
            var2.addSuppressed(var5);
         }
      }

      if (var1 != null) {
         try {
            var1.close();
         } catch (Exception var4) {
            var2.addSuppressed(var4);
         }
      }

      throwIfIOE(var2);
      throwIfRTE(var2);
      throw new RuntimeException(var2);
   }

   public static Object createInstance(Class var0, boolean var1) throws IllegalArgumentException {
      Constructor var2 = findConstructor(var0, var1);
      if (var2 == null) {
         throw new IllegalArgumentException("Class " + var0.getName() + " has no default (no arg) constructor");
      } else {
         Object var10000;
         try {
            var10000 = var2.newInstance();
         } catch (Exception var4) {
            unwrapAndThrowAsIAE(var4, "Failed to instantiate class " + var0.getName() + ", problem: " + var4.getMessage());
            return null;
         }

         return var10000;
      }
   }

   public static Constructor findConstructor(Class var0, boolean var1) throws IllegalArgumentException {
      try {
         Constructor var2 = var0.getDeclaredConstructor();
         if (var1) {
            checkAndFixAccess(var2, var1);
         } else if (!Modifier.isPublic(var2.getModifiers())) {
            throw new IllegalArgumentException("Default constructor for " + var0.getName() + " is not accessible (non-public?): not allowed to try modify access via Reflection: cannot instantiate type");
         }

         Constructor var10000 = var2;
         return var10000;
      } catch (NoSuchMethodException var3) {
      } catch (Exception var4) {
         unwrapAndThrowAsIAE(var4, "Failed to find default constructor of class " + var0.getName() + ", problem: " + var4.getMessage());
      }

      return null;
   }

   public static Class classOf(Object var0) {
      return var0 == null ? null : var0.getClass();
   }

   public static Class rawClass(JavaType var0) {
      return var0 == null ? null : var0.getRawClass();
   }

   public static Object nonNull(Object var0, Object var1) {
      return var0 == null ? var1 : var0;
   }

   public static String nullOrToString(Object var0) {
      return var0 == null ? null : var0.toString();
   }

   public static String nonNullString(String var0) {
      return var0 == null ? "" : var0;
   }

   public static String quotedOr(Object var0, String var1) {
      return var0 == null ? var1 : String.format("\"%s\"", var0);
   }

   public static String getClassDescription(Object var0) {
      if (var0 == null) {
         return "unknown";
      } else {
         Class var1 = var0 instanceof Class ? (Class)var0 : var0.getClass();
         return nameOf(var1);
      }
   }

   public static String classNameOf(Object var0) {
      return var0 == null ? "[null]" : nameOf(var0.getClass());
   }

   public static String nameOf(Class var0) {
      if (var0 == null) {
         return "[null]";
      } else {
         int var1;
         for(var1 = 0; var0.isArray(); var0 = var0.getComponentType()) {
            ++var1;
         }

         String var2 = var0.isPrimitive() ? var0.getSimpleName() : var0.getName();
         if (var1 > 0) {
            StringBuilder var3 = new StringBuilder(var2);

            do {
               var3.append("[]");
               --var1;
            } while(var1 > 0);

            var2 = var3.toString();
         }

         return backticked(var2);
      }
   }

   public static String nameOf(Named var0) {
      return var0 == null ? "[null]" : backticked(var0.getName());
   }

   public static String backticked(String var0) {
      return var0 == null ? "[null]" : (new StringBuilder(var0.length() + 2)).append('`').append(var0).append('`').toString();
   }

   public static Object defaultValue(Class var0) {
      if (var0 == Integer.TYPE) {
         return 0;
      } else if (var0 == Long.TYPE) {
         return 0L;
      } else if (var0 == Boolean.TYPE) {
         return Boolean.FALSE;
      } else if (var0 == Double.TYPE) {
         return 0.0D;
      } else if (var0 == Float.TYPE) {
         return 0.0F;
      } else if (var0 == Byte.TYPE) {
         return 0;
      } else if (var0 == Short.TYPE) {
         return Short.valueOf((short)0);
      } else if (var0 == Character.TYPE) {
         return '\u0000';
      } else {
         throw new IllegalArgumentException("Class " + var0.getName() + " is not a primitive type");
      }
   }

   public static Class wrapperType(Class var0) {
      if (var0 == Integer.TYPE) {
         return Integer.class;
      } else if (var0 == Long.TYPE) {
         return Long.class;
      } else if (var0 == Boolean.TYPE) {
         return Boolean.class;
      } else if (var0 == Double.TYPE) {
         return Double.class;
      } else if (var0 == Float.TYPE) {
         return Float.class;
      } else if (var0 == Byte.TYPE) {
         return Byte.class;
      } else if (var0 == Short.TYPE) {
         return Short.class;
      } else if (var0 == Character.TYPE) {
         return Character.class;
      } else {
         throw new IllegalArgumentException("Class " + var0.getName() + " is not a primitive type");
      }
   }

   public static Class primitiveType(Class var0) {
      if (var0.isPrimitive()) {
         return var0;
      } else if (var0 == Integer.class) {
         return Integer.TYPE;
      } else if (var0 == Long.class) {
         return Long.TYPE;
      } else if (var0 == Boolean.class) {
         return Boolean.TYPE;
      } else if (var0 == Double.class) {
         return Double.TYPE;
      } else if (var0 == Float.class) {
         return Float.TYPE;
      } else if (var0 == Byte.class) {
         return Byte.TYPE;
      } else if (var0 == Short.class) {
         return Short.TYPE;
      } else {
         return var0 == Character.class ? Character.TYPE : null;
      }
   }

   /** @deprecated */
   @Deprecated
   public static void checkAndFixAccess(Member var0) {
      checkAndFixAccess(var0, false);
   }

   public static void checkAndFixAccess(Member var0, boolean var1) {
      AccessibleObject var2 = (AccessibleObject)var0;

      try {
         if (var1 || !Modifier.isPublic(var0.getModifiers()) || !Modifier.isPublic(var0.getDeclaringClass().getModifiers())) {
            var2.setAccessible(true);
         }
      } catch (SecurityException var5) {
         if (!var2.isAccessible()) {
            Class var4 = var0.getDeclaringClass();
            throw new IllegalArgumentException("Cannot access " + var0 + " (from class " + var4.getName() + "; failed to set access: " + var5.getMessage());
         }
      }

   }

   public static Class findEnumType(EnumSet var0) {
      return !var0.isEmpty() ? findEnumType((Enum)var0.iterator().next()) : ClassUtil$EnumTypeLocator.instance.enumTypeFor(var0);
   }

   public static Class findEnumType(EnumMap var0) {
      return !var0.isEmpty() ? findEnumType((Enum)var0.keySet().iterator().next()) : ClassUtil$EnumTypeLocator.instance.enumTypeFor(var0);
   }

   public static Class findEnumType(Enum var0) {
      Class var1 = var0.getClass();
      if (var1.getSuperclass() != Enum.class) {
         var1 = var1.getSuperclass();
      }

      return var1;
   }

   public static Class findEnumType(Class var0) {
      if (var0.getSuperclass() != Enum.class) {
         var0 = var0.getSuperclass();
      }

      return var0;
   }

   public static Enum findFirstAnnotatedEnumValue(Class var0, Class var1) {
      Field[] var2 = getDeclaredFields(var0);
      Field[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field var6 = var3[var5];
         if (var6.isEnumConstant()) {
            Annotation var7 = var6.getAnnotation(var1);
            if (var7 != null) {
               String var8 = var6.getName();
               Enum[] var9 = (Enum[])var0.getEnumConstants();
               int var10 = var9.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  Enum var12 = var9[var11];
                  if (var8.equals(var12.name())) {
                     return var12;
                  }
               }
            }
         }
      }

      return null;
   }

   public static boolean isJacksonStdImpl(Object var0) {
      return var0 == null || isJacksonStdImpl(var0.getClass());
   }

   public static boolean isJacksonStdImpl(Class var0) {
      return var0.getAnnotation(JacksonStdImpl.class) != null;
   }

   public static String getPackageName(Class var0) {
      Package var1 = var0.getPackage();
      return var1 == null ? null : var1.getName();
   }

   public static boolean hasEnclosingMethod(Class var0) {
      return !isObjectOrPrimitive(var0) && var0.getEnclosingMethod() != null;
   }

   public static Field[] getDeclaredFields(Class var0) {
      return var0.getDeclaredFields();
   }

   public static Method[] getDeclaredMethods(Class var0) {
      return var0.getDeclaredMethods();
   }

   public static Annotation[] findClassAnnotations(Class var0) {
      return isObjectOrPrimitive(var0) ? NO_ANNOTATIONS : var0.getDeclaredAnnotations();
   }

   public static Method[] getClassMethods(Class var0) {
      Method[] var10000;
      try {
         var10000 = getDeclaredMethods(var0);
      } catch (NoClassDefFoundError var6) {
         ClassLoader var2 = Thread.currentThread().getContextClassLoader();
         if (var2 == null) {
            throw var6;
         }

         Class var3;
         try {
            var3 = var2.loadClass(var0.getName());
         } catch (ClassNotFoundException var5) {
            var6.addSuppressed(var5);
            throw var6;
         }

         return var3.getDeclaredMethods();
      }

      return var10000;
   }

   public static ClassUtil$Ctor[] getConstructors(Class var0) {
      if (!var0.isInterface() && !isObjectOrPrimitive(var0)) {
         Constructor[] var1 = var0.getDeclaredConstructors();
         int var2 = var1.length;
         ClassUtil$Ctor[] var3 = new ClassUtil$Ctor[var2];

         for(int var4 = 0; var4 < var2; ++var4) {
            var3[var4] = new ClassUtil$Ctor(var1[var4]);
         }

         return var3;
      } else {
         return NO_CTORS;
      }
   }

   public static Class getDeclaringClass(Class var0) {
      return isObjectOrPrimitive(var0) ? null : var0.getDeclaringClass();
   }

   public static Type getGenericSuperclass(Class var0) {
      return var0.getGenericSuperclass();
   }

   public static Type[] getGenericInterfaces(Class var0) {
      return var0.getGenericInterfaces();
   }

   public static Class getEnclosingClass(Class var0) {
      return isObjectOrPrimitive(var0) ? null : var0.getEnclosingClass();
   }

   private static Class[] _interfaces(Class var0) {
      return var0.getInterfaces();
   }
}
