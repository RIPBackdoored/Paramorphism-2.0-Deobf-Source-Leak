package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"},
   d2 = {"<anonymous>", "", "<anonymous parameter 0>", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke", "(Lkotlin/Unit;Lkotlin/coroutines/CoroutineContext$Element;)V"}
)
final class CombinedContext$writeReplace$1 extends Lambda implements Function2 {
   final CoroutineContext[] $elements;
   final Ref$IntRef $index;

   public Object invoke(Object var1, Object var2) {
      this.invoke((Unit)var1, (CoroutineContext$Element)var2);
      return Unit.INSTANCE;
   }

   public final void invoke(@NotNull Unit var1, @NotNull CoroutineContext$Element var2) {
      CoroutineContext[] var10000 = this.$elements;
      Ref$IntRef var10001 = this.$index;
      int var3;
      var10001.element = (var3 = var10001.element) + 1;
      var10000[var3] = (CoroutineContext)var2;
   }

   CombinedContext$writeReplace$1(CoroutineContext[] var1, Ref$IntRef var2) {
      super(2);
      this.$elements = var1;
      this.$index = var2;
   }
}
