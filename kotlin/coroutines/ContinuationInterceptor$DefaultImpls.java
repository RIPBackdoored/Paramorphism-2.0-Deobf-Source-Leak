package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class ContinuationInterceptor$DefaultImpls {
   public static void releaseInterceptedContinuation(ContinuationInterceptor var0, @NotNull Continuation var1) {
   }

   @Nullable
   public static CoroutineContext$Element get(ContinuationInterceptor var0, @NotNull CoroutineContext$Key var1) {
      CoroutineContext$Element var10000;
      if (var1 == ContinuationInterceptor.Key) {
         if (var0 == null) {
            throw new TypeCastException("null cannot be cast to non-null type E");
         }

         var10000 = (CoroutineContext$Element)var0;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   @NotNull
   public static CoroutineContext minusKey(ContinuationInterceptor var0, @NotNull CoroutineContext$Key var1) {
      return var1 == ContinuationInterceptor.Key ? (CoroutineContext)EmptyCoroutineContext.INSTANCE : (CoroutineContext)var0;
   }

   public static Object fold(ContinuationInterceptor var0, Object var1, @NotNull Function2 var2) {
      return CoroutineContext$Element$DefaultImpls.fold((CoroutineContext$Element)var0, var1, var2);
   }

   @NotNull
   public static CoroutineContext plus(ContinuationInterceptor var0, @NotNull CoroutineContext var1) {
      return CoroutineContext$Element$DefaultImpls.plus((CoroutineContext$Element)var0, var1);
   }
}
