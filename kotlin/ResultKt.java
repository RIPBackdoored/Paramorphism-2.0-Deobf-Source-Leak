package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a+\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\bH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\t\u001a\u0084\u0001\u0010\n\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\f\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\r2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000\u0082\u0002\u0014\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0000¢\u0006\u0002\u0010\u0012\u001a3\u0010\u0013\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052\u0006\u0010\u0014\u001a\u0002H\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a[\u0010\u0016\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001a!\u0010\u0018\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u0005H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a]\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aP\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001aW\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\u001e0\rH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aW\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u001e0\rH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aa\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aT\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a@\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0006*\u0002H\u000b2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00060\r¢\u0006\u0002\b!H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a\u0018\u0010\"\u001a\u00020\u001e*\u0006\u0012\u0002\b\u00030\u0005H\u0001ø\u0001\u0000¢\u0006\u0002\u0010#\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006$"},
   d2 = {"createFailure", "", "exception", "", "runCatching", "Lkotlin/Result;", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "fold", "T", "onSuccess", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "value", "onFailure", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrDefault", "defaultValue", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrThrow", "(Ljava/lang/Object;)Ljava/lang/Object;", "map", "transform", "mapCatching", "action", "", "recover", "recoverCatching", "Lkotlin/ExtensionFunctionType;", "throwOnFailure", "(Ljava/lang/Object;)V", "kotlin-stdlib"}
)
public final class ResultKt {
   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Object createFailure(@NotNull Throwable var0) {
      return new Result$Failure(var0);
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final void throwOnFailure(@NotNull Object var0) {
      if (var0 instanceof Result$Failure) {
         throw ((Result$Failure)var0).exception;
      }
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object runCatching(Function0 var0) {
      byte var1 = 0;

      Object var2;
      try {
         Result$Companion var7 = Result.Companion;
         Object var3 = var0.invoke();
         boolean var8 = false;
         var2 = Result.constructor-impl(var3);
      } catch (Throwable var6) {
         Result$Companion var4 = Result.Companion;
         boolean var5 = false;
         var2 = Result.constructor-impl(createFailure(var6));
      }

      return var2;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object runCatching(Object var0, Function1 var1) {
      byte var2 = 0;

      Object var3;
      try {
         Result$Companion var8 = Result.Companion;
         Object var4 = var1.invoke(var0);
         boolean var9 = false;
         var3 = Result.constructor-impl(var4);
      } catch (Throwable var7) {
         Result$Companion var5 = Result.Companion;
         boolean var6 = false;
         var3 = Result.constructor-impl(createFailure(var7));
      }

      return var3;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object getOrThrow(@NotNull Object var0) {
      byte var1 = 0;
      throwOnFailure(var0);
      return var0;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object getOrElse(@NotNull Object var0, Function1 var1) {
      byte var2 = 0;
      boolean var3 = false;
      Throwable var4 = Result.exceptionOrNull-impl(var0);
      return var4 == null ? var0 : var1.invoke(var4);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object getOrDefault(@NotNull Object var0, Object var1) {
      byte var2 = 0;
      return Result.isFailure-impl(var0) ? var1 : var0;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object fold(@NotNull Object var0, Function1 var1, Function1 var2) {
      byte var3 = 0;
      boolean var4 = false;
      Throwable var5 = Result.exceptionOrNull-impl(var0);
      return var5 == null ? var1.invoke(var0) : var2.invoke(var5);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object map(@NotNull Object var0, Function1 var1) {
      byte var2 = 0;
      boolean var3 = false;
      Object var10000;
      if (Result.isSuccess-impl(var0)) {
         Result$Companion var6 = Result.Companion;
         Object var4 = var1.invoke(var0);
         boolean var5 = false;
         var10000 = Result.constructor-impl(var4);
      } else {
         var10000 = Result.constructor-impl(var0);
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object mapCatching(@NotNull Object var0, Function1 var1) {
      byte var2 = 0;
      Object var10000;
      if (Result.isSuccess-impl(var0)) {
         Object var3 = var0;
         boolean var4 = false;

         Object var5;
         try {
            Result$Companion var10 = Result.Companion;
            boolean var11 = false;
            Object var6 = var1.invoke(var3);
            var11 = false;
            var5 = Result.constructor-impl(var6);
         } catch (Throwable var9) {
            Result$Companion var7 = Result.Companion;
            boolean var8 = false;
            var5 = Result.constructor-impl(createFailure(var9));
         }

         var10000 = var5;
      } else {
         var10000 = Result.constructor-impl(var0);
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object recover(@NotNull Object var0, Function1 var1) {
      byte var2 = 0;
      boolean var3 = false;
      Throwable var7 = Result.exceptionOrNull-impl(var0);
      Object var10000;
      if (var7 == null) {
         var10000 = var0;
      } else {
         Result$Companion var4 = Result.Companion;
         Object var5 = var1.invoke(var7);
         boolean var6 = false;
         var10000 = Result.constructor-impl(var5);
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object recoverCatching(@NotNull Object var0, Function1 var1) {
      byte var2 = 0;
      Throwable var4 = Result.exceptionOrNull-impl(var0);
      Object var10000;
      if (var4 == null) {
         var10000 = var0;
      } else {
         boolean var6 = false;

         Object var7;
         try {
            Result$Companion var12 = Result.Companion;
            boolean var13 = false;
            Object var8 = var1.invoke(var4);
            var13 = false;
            var7 = Result.constructor-impl(var8);
         } catch (Throwable var11) {
            Result$Companion var9 = Result.Companion;
            boolean var10 = false;
            var7 = Result.constructor-impl(createFailure(var11));
         }

         var10000 = var7;
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object onFailure(@NotNull Object var0, Function1 var1) {
      byte var2 = 0;
      boolean var3 = false;
      Throwable var10000 = Result.exceptionOrNull-impl(var0);
      if (var10000 != null) {
         Throwable var8 = var10000;
         boolean var4 = false;
         boolean var5 = false;
         boolean var7 = false;
         var1.invoke(var8);
      }

      return var0;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object onSuccess(@NotNull Object var0, Function1 var1) {
      byte var2 = 0;
      boolean var3 = false;
      if (Result.isSuccess-impl(var0)) {
         var1.invoke(var0);
      }

      return var0;
   }
}
