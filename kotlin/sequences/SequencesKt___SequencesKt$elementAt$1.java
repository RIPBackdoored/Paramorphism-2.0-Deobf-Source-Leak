package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"},
   d2 = {"<anonymous>", "", "T", "it", "", "invoke"}
)
final class SequencesKt___SequencesKt$elementAt$1 extends Lambda implements Function1 {
   final int $index;

   public Object invoke(Object var1) {
      return this.invoke(((Number)var1).intValue());
   }

   @NotNull
   public final Void invoke(int var1) {
      throw (Throwable)(new IndexOutOfBoundsException("Sequence doesn't contain element at index " + this.$index + '.'));
   }

   SequencesKt___SequencesKt$elementAt$1(int var1) {
      super(1);
      this.$index = var1;
   }
}
