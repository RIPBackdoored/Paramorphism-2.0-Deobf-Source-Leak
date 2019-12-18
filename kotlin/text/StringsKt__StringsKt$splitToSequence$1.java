package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"},
   d2 = {"<anonymous>", "", "it", "Lkotlin/ranges/IntRange;", "invoke"}
)
final class StringsKt__StringsKt$splitToSequence$1 extends Lambda implements Function1 {
   final CharSequence $this_splitToSequence;

   public Object invoke(Object var1) {
      return this.invoke((IntRange)var1);
   }

   @NotNull
   public final String invoke(@NotNull IntRange var1) {
      return StringsKt.substring(this.$this_splitToSequence, var1);
   }

   StringsKt__StringsKt$splitToSequence$1(CharSequence var1) {
      super(1);
      this.$this_splitToSequence = var1;
   }
}
