package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\n¢\u0006\u0002\b\u0005"},
   d2 = {"<anonymous>", "", "T", "it", "", "invoke"}
)
final class SequencesKt__SequencesKt$flatten$2 extends Lambda implements Function1 {
   public static final SequencesKt__SequencesKt$flatten$2 INSTANCE = new SequencesKt__SequencesKt$flatten$2();

   public Object invoke(Object var1) {
      return this.invoke((Iterable)var1);
   }

   @NotNull
   public final Iterator invoke(@NotNull Iterable var1) {
      return var1.iterator();
   }

   SequencesKt__SequencesKt$flatten$2() {
      super(1);
   }
}