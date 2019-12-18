package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\r\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"},
   d2 = {"<anonymous>", "", "it", "", "invoke"}
)
final class StringsKt___StringsKt$windowedSequence$1 extends Lambda implements Function1 {
   public static final StringsKt___StringsKt$windowedSequence$1 INSTANCE = new StringsKt___StringsKt$windowedSequence$1();

   public Object invoke(Object var1) {
      return this.invoke((CharSequence)var1);
   }

   @NotNull
   public final String invoke(@NotNull CharSequence var1) {
      return var1.toString();
   }

   StringsKt___StringsKt$windowedSequence$1() {
      super(1);
   }
}
