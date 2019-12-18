package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"},
   d2 = {"<anonymous>", "Lkotlin/coroutines/experimental/CoroutineContext;", "acc", "element", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "invoke"}
)
final class CoroutineContext$plus$1 extends Lambda implements Function2 {
   public static final CoroutineContext$plus$1 INSTANCE = new CoroutineContext$plus$1();

   public Object invoke(Object var1, Object var2) {
      return this.invoke((CoroutineContext)var1, (CoroutineContext$Element)var2);
   }

   @NotNull
   public final CoroutineContext invoke(@NotNull CoroutineContext var1, @NotNull CoroutineContext$Element var2) {
      CoroutineContext var3 = var1.minusKey(var2.getKey());
      CoroutineContext var10000;
      if (var3 == EmptyCoroutineContext.INSTANCE) {
         var10000 = (CoroutineContext)var2;
      } else {
         ContinuationInterceptor var4 = (ContinuationInterceptor)var3.get((CoroutineContext$Key)ContinuationInterceptor.Key);
         CombinedContext var6;
         if (var4 == null) {
            var6 = new CombinedContext(var3, var2);
         } else {
            CoroutineContext var5 = var3.minusKey((CoroutineContext$Key)ContinuationInterceptor.Key);
            var6 = var5 == EmptyCoroutineContext.INSTANCE ? new CombinedContext((CoroutineContext)var2, (CoroutineContext$Element)var4) : new CombinedContext((CoroutineContext)(new CombinedContext(var5, var2)), (CoroutineContext$Element)var4);
         }

         var10000 = (CoroutineContext)var6;
      }

      return var10000;
   }

   CoroutineContext$plus$1() {
      super(2);
   }
}
