package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"},
   d2 = {"<anonymous>", "", "line", "invoke"}
)
final class StringsKt__IndentKt$getIndentFunction$1 extends Lambda implements Function1 {
   public static final StringsKt__IndentKt$getIndentFunction$1 INSTANCE = new StringsKt__IndentKt$getIndentFunction$1();

   public Object invoke(Object var1) {
      return this.invoke((String)var1);
   }

   @NotNull
   public final String invoke(@NotNull String var1) {
      return var1;
   }

   StringsKt__IndentKt$getIndentFunction$1() {
      super(1);
   }
}
