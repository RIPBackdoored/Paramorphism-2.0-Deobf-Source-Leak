package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0005\u0010\u0006"},
   d2 = {"<anonymous>", "Lkotlin/Pair;", "T", "a", "b", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;"}
)
final class SequencesKt___SequencesKt$zipWithNext$1 extends Lambda implements Function2 {
   public static final SequencesKt___SequencesKt$zipWithNext$1 INSTANCE = new SequencesKt___SequencesKt$zipWithNext$1();

   public Object invoke(Object var1, Object var2) {
      return this.invoke(var1, var2);
   }

   @NotNull
   public final Pair invoke(Object var1, Object var2) {
      return TuplesKt.to(var1, var2);
   }

   SequencesKt___SequencesKt$zipWithNext$1() {
      super(2);
   }
}
