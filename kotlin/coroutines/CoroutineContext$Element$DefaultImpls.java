package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class CoroutineContext$Element$DefaultImpls {
   @Nullable
   public static CoroutineContext$Element get(CoroutineContext$Element var0, @NotNull CoroutineContext$Key var1) {
      CoroutineContext$Element var10000;
      if (Intrinsics.areEqual((Object)var0.getKey(), (Object)var1)) {
         var10000 = var0;
         if (var0 == null) {
            throw new TypeCastException("null cannot be cast to non-null type E");
         }
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public static Object fold(CoroutineContext$Element var0, Object var1, @NotNull Function2 var2) {
      return var2.invoke(var1, var0);
   }

   @NotNull
   public static CoroutineContext minusKey(CoroutineContext$Element var0, @NotNull CoroutineContext$Key var1) {
      return Intrinsics.areEqual((Object)var0.getKey(), (Object)var1) ? (CoroutineContext)EmptyCoroutineContext.INSTANCE : (CoroutineContext)var0;
   }

   @NotNull
   public static CoroutineContext plus(CoroutineContext$Element var0, @NotNull CoroutineContext var1) {
      return CoroutineContext$DefaultImpls.plus((CoroutineContext)var0, var1);
   }
}
