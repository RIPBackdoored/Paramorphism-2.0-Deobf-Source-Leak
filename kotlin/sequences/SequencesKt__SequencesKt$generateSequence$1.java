package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u0002H\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"},
   d2 = {"<anonymous>", "T", "", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;"}
)
final class SequencesKt__SequencesKt$generateSequence$1 extends Lambda implements Function1 {
   final Function0 $nextFunction;

   @Nullable
   public final Object invoke(@NotNull Object var1) {
      return this.$nextFunction.invoke();
   }

   SequencesKt__SequencesKt$generateSequence$1(Function0 var1) {
      super(1);
      this.$nextFunction = var1;
   }
}
