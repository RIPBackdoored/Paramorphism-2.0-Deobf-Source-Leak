package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class ContinuationInterceptor$DefaultImpls {
   @Nullable
   public static CoroutineContext$Element get(ContinuationInterceptor var0, @NotNull CoroutineContext$Key var1) {
      return CoroutineContext$Element$DefaultImpls.get((CoroutineContext$Element)var0, var1);
   }

   public static Object fold(ContinuationInterceptor var0, Object var1, @NotNull Function2 var2) {
      return CoroutineContext$Element$DefaultImpls.fold((CoroutineContext$Element)var0, var1, var2);
   }

   @NotNull
   public static CoroutineContext minusKey(ContinuationInterceptor var0, @NotNull CoroutineContext$Key var1) {
      return CoroutineContext$Element$DefaultImpls.minusKey((CoroutineContext$Element)var0, var1);
   }

   @NotNull
   public static CoroutineContext plus(ContinuationInterceptor var0, @NotNull CoroutineContext var1) {
      return CoroutineContext$Element$DefaultImpls.plus((CoroutineContext$Element)var0, var1);
   }
}
