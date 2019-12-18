package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\f\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"},
   d2 = {"<anonymous>", "R", "index", "", "invoke", "(I)Ljava/lang/Object;"}
)
final class StringsKt___StringsKt$windowedSequence$2 extends Lambda implements Function1 {
   final CharSequence $this_windowedSequence;
   final Function1 $transform;
   final int $size;

   public Object invoke(Object var1) {
      return this.invoke(((Number)var1).intValue());
   }

   public final Object invoke(int var1) {
      return this.$transform.invoke(this.$this_windowedSequence.subSequence(var1, RangesKt.coerceAtMost(var1 + this.$size, this.$this_windowedSequence.length())));
   }

   StringsKt___StringsKt$windowedSequence$2(CharSequence var1, Function1 var2, int var3) {
      super(1);
      this.$this_windowedSequence = var1;
      this.$transform = var2;
      this.$size = var3;
   }
}
