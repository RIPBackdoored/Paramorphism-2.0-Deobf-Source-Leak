package kotlin.jvm.internal;

import java.util.Arrays;
import kotlin.KotlinNullPointerException;
import kotlin.SinceKotlin;
import kotlin.UninitializedPropertyAccessException;

public class Intrinsics {
   private Intrinsics() {
      super();
   }

   public static String stringPlus(String var0, Object var1) {
      return var0 + var1;
   }

   public static void checkNotNull(Object var0) {
      if (var0 == null) {
         throwNpe();
      }

   }

   public static void checkNotNull(Object var0, String var1) {
      if (var0 == null) {
         throwNpe(var1);
      }

   }

   public static void throwNpe() {
      throw (KotlinNullPointerException)sanitizeStackTrace(new KotlinNullPointerException());
   }

   public static void throwNpe(String var0) {
      throw (KotlinNullPointerException)sanitizeStackTrace(new KotlinNullPointerException(var0));
   }

   public static void throwUninitializedProperty(String var0) {
      throw (UninitializedPropertyAccessException)sanitizeStackTrace(new UninitializedPropertyAccessException(var0));
   }

   public static void throwUninitializedPropertyAccessException(String var0) {
      throwUninitializedProperty("lateinit property " + var0 + " has not been initialized");
   }

   public static void throwAssert() {
      throw (AssertionError)sanitizeStackTrace(new AssertionError());
   }

   public static void throwAssert(String var0) {
      throw (AssertionError)sanitizeStackTrace(new AssertionError(var0));
   }

   public static void throwIllegalArgument() {
      throw (IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException());
   }

   public static void throwIllegalArgument(String var0) {
      throw (IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException(var0));
   }

   public static void throwIllegalState() {
      throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException());
   }

   public static void throwIllegalState(String var0) {
      throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var0));
   }

   public static void checkExpressionValueIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var1 + " must not be null"));
      }
   }

   public static void checkNotNullExpressionValue(Object var0, String var1) {
      if (var0 == null) {
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var1));
      }
   }

   public static void checkReturnedValueIsNotNull(Object var0, String var1, String var2) {
      if (var0 == null) {
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException("Method specified as non-null returned null: " + var1 + "." + var2));
      }
   }

   public static void checkReturnedValueIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var1));
      }
   }

   public static void checkFieldIsNotNull(Object var0, String var1, String var2) {
      if (var0 == null) {
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException("Field specified as non-null is null: " + var1 + "." + var2));
      }
   }

   public static void checkFieldIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var1));
      }
   }

   public static void checkParameterIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         throwParameterIsNullException(var1);
      }

   }

   public static void checkNotNullParameter(Object var0, String var1) {
      if (var0 == null) {
         throw (IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException(var1));
      }
   }

   private static void throwParameterIsNullException(String var0) {
      StackTraceElement[] var1 = Thread.currentThread().getStackTrace();
      StackTraceElement var2 = var1[3];
      String var3 = var2.getClassName();
      String var4 = var2.getMethodName();
      IllegalArgumentException var5 = new IllegalArgumentException("Parameter specified as non-null is null: method " + var3 + "." + var4 + ", parameter " + var0);
      throw (IllegalArgumentException)sanitizeStackTrace(var5);
   }

   public static int compare(long var0, long var2) {
      return var0 < var2 ? -1 : (var0 == var2 ? 0 : 1);
   }

   public static int compare(int var0, int var1) {
      return var0 < var1 ? -1 : (var0 == var1 ? 0 : 1);
   }

   public static boolean areEqual(Object var0, Object var1) {
      return var0 == null ? var1 == null : var0.equals(var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static boolean areEqual(Double var0, Double var1) {
      return var0 == null ? var1 == null : var1 != null && var0 == var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static boolean areEqual(Double var0, double var1) {
      return var0 != null && var0 == var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static boolean areEqual(double var0, Double var2) {
      return var2 != null && var0 == var2;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static boolean areEqual(Float var0, Float var1) {
      return var0 == null ? var1 == null : var1 != null && var0 == var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static boolean areEqual(Float var0, float var1) {
      return var0 != null && var0 == var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static boolean areEqual(float var0, Float var1) {
      return var1 != null && var0 == var1;
   }

   public static void throwUndefinedForReified() {
      throwUndefinedForReified("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
   }

   public static void throwUndefinedForReified(String var0) {
      throw new UnsupportedOperationException(var0);
   }

   public static void reifiedOperationMarker(int var0, String var1) {
      throwUndefinedForReified();
   }

   public static void reifiedOperationMarker(int var0, String var1, String var2) {
      throwUndefinedForReified(var2);
   }

   public static void needClassReification() {
      throwUndefinedForReified();
   }

   public static void needClassReification(String var0) {
      throwUndefinedForReified(var0);
   }

   public static void checkHasClass(String var0) throws ClassNotFoundException {
      String var1 = var0.replace('/', '.');

      try {
         Class.forName(var1);
      } catch (ClassNotFoundException var3) {
         throw (ClassNotFoundException)sanitizeStackTrace(new ClassNotFoundException("Class " + var1 + " is not found. Please update the Kotlin runtime to the latest version", var3));
      }

   }

   public static void checkHasClass(String var0, String var1) throws ClassNotFoundException {
      String var2 = var0.replace('/', '.');

      try {
         Class.forName(var2);
      } catch (ClassNotFoundException var4) {
         throw (ClassNotFoundException)sanitizeStackTrace(new ClassNotFoundException("Class " + var2 + " is not found: this code requires the Kotlin runtime of version at least " + var1, var4));
      }

   }

   private static Throwable sanitizeStackTrace(Throwable var0) {
      return sanitizeStackTrace(var0, Intrinsics.class.getName());
   }

   static Throwable sanitizeStackTrace(Throwable var0, String var1) {
      StackTraceElement[] var2 = var0.getStackTrace();
      int var3 = var2.length;
      int var4 = -1;

      for(int var5 = 0; var5 < var3; ++var5) {
         if (var1.equals(var2[var5].getClassName())) {
            var4 = var5;
         }
      }

      StackTraceElement[] var6 = (StackTraceElement[])Arrays.copyOfRange(var2, var4 + 1, var3);
      var0.setStackTrace(var6);
      return var0;
   }
}
