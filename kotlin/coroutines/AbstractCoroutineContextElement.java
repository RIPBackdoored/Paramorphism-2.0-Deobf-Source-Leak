package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b'\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)V", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
public abstract class AbstractCoroutineContextElement implements CoroutineContext$Element {
   @NotNull
   private final CoroutineContext$Key key;

   @NotNull
   public CoroutineContext$Key getKey() {
      return this.key;
   }

   public AbstractCoroutineContextElement(@NotNull CoroutineContext$Key var1) {
      super();
      this.key = var1;
   }

   @Nullable
   public CoroutineContext$Element get(@NotNull CoroutineContext$Key var1) {
      return CoroutineContext$Element$DefaultImpls.get(this, var1);
   }

   public Object fold(Object var1, @NotNull Function2 var2) {
      return CoroutineContext$Element$DefaultImpls.fold(this, var1, var2);
   }

   @NotNull
   public CoroutineContext minusKey(@NotNull CoroutineContext$Key var1) {
      return CoroutineContext$Element$DefaultImpls.minusKey(this, var1);
   }

   @NotNull
   public CoroutineContext plus(@NotNull CoroutineContext var1) {
      return CoroutineContext$Element$DefaultImpls.plus(this, var1);
   }
}
