package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007"},
   d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"}
)
public final class ComparisonsKt__ComparisonsKt$thenComparator$1 implements Comparator {
   final Comparator $this_thenComparator;
   final Function2 $comparison;

   public final int compare(Object var1, Object var2) {
      int var3 = this.$this_thenComparator.compare(var1, var2);
      return var3 != 0 ? var3 : ((Number)this.$comparison.invoke(var1, var2)).intValue();
   }

   public ComparisonsKt__ComparisonsKt$thenComparator$1(Comparator var1, Function2 var2) {
      super();
      this.$this_thenComparator = var1;
      this.$comparison = var2;
   }
}
