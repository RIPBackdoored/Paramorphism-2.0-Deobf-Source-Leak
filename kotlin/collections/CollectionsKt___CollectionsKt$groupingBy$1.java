package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0001J\u0015\u0010\u0002\u001a\u00028\u00012\u0006\u0010\u0003\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H\u0016¨\u0006\u0007"},
   d2 = {"kotlin/collections/CollectionsKt___CollectionsKt$groupingBy$1", "Lkotlin/collections/Grouping;", "keyOf", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "sourceIterator", "", "kotlin-stdlib"}
)
public final class CollectionsKt___CollectionsKt$groupingBy$1 implements Grouping {
   final Iterable $this_groupingBy;
   final Function1 $keySelector;

   @NotNull
   public Iterator sourceIterator() {
      return this.$this_groupingBy.iterator();
   }

   public Object keyOf(Object var1) {
      return this.$keySelector.invoke(var1);
   }

   public CollectionsKt___CollectionsKt$groupingBy$1(Iterable var1, Function1 var2) {
      super();
      this.$this_groupingBy = var1;
      this.$keySelector = var2;
   }
}
