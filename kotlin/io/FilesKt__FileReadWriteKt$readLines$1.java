package kotlin.io;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"},
   d2 = {"<anonymous>", "", "it", "", "invoke"}
)
final class FilesKt__FileReadWriteKt$readLines$1 extends Lambda implements Function1 {
   final ArrayList $result;

   public Object invoke(Object var1) {
      this.invoke((String)var1);
      return Unit.INSTANCE;
   }

   public final void invoke(@NotNull String var1) {
      this.$result.add(var1);
   }

   FilesKt__FileReadWriteKt$readLines$1(ArrayList var1) {
      super(1);
      this.$result = var1;
   }
}
