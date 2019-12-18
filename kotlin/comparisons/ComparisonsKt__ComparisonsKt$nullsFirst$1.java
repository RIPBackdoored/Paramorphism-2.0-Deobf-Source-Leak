package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u0001H\u00022\b\u0010\u0005\u001a\u0004\u0018\u0001H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007"},
   d2 = {"<anonymous>", "", "T", "", "a", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"}
)
final class ComparisonsKt__ComparisonsKt$nullsFirst$1 implements Comparator {
   final Comparator $comparator;

   public final int compare(@Nullable Object var1, @Nullable Object var2) {
      return var1 == var2 ? 0 : (var1 == null ? -1 : (var2 == null ? 1 : this.$comparator.compare(var1, var2)));
   }

   ComparisonsKt__ComparisonsKt$nullsFirst$1(Comparator var1) {
      super();
      this.$comparator = var1;
   }
}
