package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001aF\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\u001c\b\u0004\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\u0083\b¢\u0006\u0002\b\b\u001aD\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a]\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001aA\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aZ\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"},
   d2 = {"createCoroutineFromSuspendFunction", "Lkotlin/coroutines/Continuation;", "", "T", "completion", "block", "Lkotlin/Function1;", "", "createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnintercepted", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "intercepted", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"},
   xs = "kotlin/coroutines/intrinsics/IntrinsicsKt"
)
class IntrinsicsKt__IntrinsicsJvmKt {
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object startCoroutineUninterceptedOrReturn(@NotNull Function1 var0, Continuation var1) {
      return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 1)).invoke(var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object startCoroutineUninterceptedOrReturn(@NotNull Function2 var0, Object var1, Continuation var2) {
      return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 2)).invoke(var1, var2);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Continuation createCoroutineUnintercepted(@NotNull Function1 var0, @NotNull Continuation var1) {
      Continuation var2 = DebugProbesKt.probeCoroutineCreated(var1);
      Continuation var10000;
      if (var0 instanceof BaseContinuationImpl) {
         var10000 = ((BaseContinuationImpl)var0).create(var2);
      } else {
         boolean var3 = false;
         CoroutineContext var4 = var2.getContext();
         if (var4 == EmptyCoroutineContext.INSTANCE) {
            IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$1 var5 = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$1;
            if (var2 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var5.<init>(var2, var2, var0);
            var10000 = (Continuation)var5;
         } else {
            IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2 var6 = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2;
            if (var2 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var6.<init>(var2, var4, var2, var4, var0);
            var10000 = (Continuation)var6;
         }
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Continuation createCoroutineUnintercepted(@NotNull Function2 var0, Object var1, @NotNull Continuation var2) {
      Continuation var3 = DebugProbesKt.probeCoroutineCreated(var2);
      Continuation var10000;
      if (var0 instanceof BaseContinuationImpl) {
         var10000 = ((BaseContinuationImpl)var0).create(var1, var3);
      } else {
         boolean var4 = false;
         CoroutineContext var5 = var3.getContext();
         if (var5 == EmptyCoroutineContext.INSTANCE) {
            IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3 var6 = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3;
            if (var3 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var6.<init>(var3, var3, var0, var1);
            var10000 = (Continuation)var6;
         } else {
            IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4 var7 = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4;
            if (var3 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var7.<init>(var3, var5, var3, var5, var0, var1);
            var10000 = (Continuation)var7;
         }
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Continuation intercepted(@NotNull Continuation var0) {
      Continuation var10000 = var0;
      if (!(var0 instanceof ContinuationImpl)) {
         var10000 = null;
      }

      ContinuationImpl var1 = (ContinuationImpl)var10000;
      if (var1 != null) {
         var10000 = var1.intercepted();
         if (var10000 != null) {
            return var10000;
         }
      }

      var10000 = var0;
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   private static final Continuation createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(Continuation var0, Function1 var1) {
      byte var2 = 0;
      CoroutineContext var3 = var0.getContext();
      Continuation var4;
      if (var3 == EmptyCoroutineContext.INSTANCE) {
         IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$1 var10000 = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$1;
         if (var0 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
         }

         var10000.<init>(var1, var0, var0);
         var4 = (Continuation)var10000;
      } else {
         IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$2 var5 = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$2;
         if (var0 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
         }

         var5.<init>(var1, var0, var3, var0, var3);
         var4 = (Continuation)var5;
      }

      return var4;
   }

   public IntrinsicsKt__IntrinsicsJvmKt() {
      super();
   }
}
