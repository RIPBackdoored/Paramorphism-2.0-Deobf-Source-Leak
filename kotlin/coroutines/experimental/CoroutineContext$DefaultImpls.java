package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class CoroutineContext$DefaultImpls {
   @NotNull
   public static CoroutineContext plus(CoroutineContext var0, @NotNull CoroutineContext var1) {
      return var1 == EmptyCoroutineContext.INSTANCE ? var0 : (CoroutineContext)var1.fold(var0, (Function2)CoroutineContext$plus$1.INSTANCE);
   }
}
