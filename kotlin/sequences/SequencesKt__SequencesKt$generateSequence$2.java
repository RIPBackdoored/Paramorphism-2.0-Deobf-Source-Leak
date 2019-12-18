package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"},
   d2 = {"<anonymous>", "T", "", "invoke", "()Ljava/lang/Object;"}
)
final class SequencesKt__SequencesKt$generateSequence$2 extends Lambda implements Function0 {
   final Object $seed;

   @Nullable
   public final Object invoke() {
      return this.$seed;
   }

   SequencesKt__SequencesKt$generateSequence$2(Object var1) {
      super(0);
      this.$seed = var1;
   }
}
