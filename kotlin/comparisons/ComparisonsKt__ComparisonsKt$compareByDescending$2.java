package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u000e\u0010\u0004\u001a\n \u0005*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0006\u001a\n \u0005*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0007\u0010\b"},
   d2 = {"<anonymous>", "", "T", "K", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"}
)
public final class ComparisonsKt__ComparisonsKt$compareByDescending$2 implements Comparator {
   final Comparator $comparator;
   final Function1 $selector;

   public final int compare(Object var1, Object var2) {
      Comparator var3 = this.$comparator;
      boolean var4 = false;
      return var3.compare(this.$selector.invoke(var2), this.$selector.invoke(var1));
   }

   public ComparisonsKt__ComparisonsKt$compareByDescending$2(Comparator var1, Function1 var2) {
      super();
      this.$comparator = var1;
      this.$selector = var2;
   }
}
