package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\n¢\u0006\u0002\b\u0005"},
   d2 = {"<anonymous>", "", "R", "it", "", "invoke"}
)
final class SequencesKt___SequencesJvmKt$filterIsInstance$1 extends Lambda implements Function1 {
   final Class $klass;

   public Object invoke(Object var1) {
      return this.invoke(var1);
   }

   public final boolean invoke(@Nullable Object var1) {
      return this.$klass.isInstance(var1);
   }

   SequencesKt___SequencesJvmKt$filterIsInstance$1(Class var1) {
      super(1);
      this.$klass = var1;
   }
}
