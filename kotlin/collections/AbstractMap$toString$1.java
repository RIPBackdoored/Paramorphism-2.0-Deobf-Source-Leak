package kotlin.collections;

import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010&\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0006\b\u0001\u0010\u0003 \u00012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005H\n¢\u0006\u0002\b\u0006"},
   d2 = {"<anonymous>", "", "K", "V", "it", "", "invoke"}
)
final class AbstractMap$toString$1 extends Lambda implements Function1 {
   final AbstractMap this$0;

   public Object invoke(Object var1) {
      return this.invoke((Entry)var1);
   }

   @NotNull
   public final String invoke(@NotNull Entry var1) {
      return AbstractMap.access$toString(this.this$0, var1);
   }

   AbstractMap$toString$1(AbstractMap var1) {
      super(1);
      this.this$0 = var1;
   }
}
