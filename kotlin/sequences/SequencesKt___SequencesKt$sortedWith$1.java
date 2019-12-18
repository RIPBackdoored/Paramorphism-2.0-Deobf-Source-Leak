package kotlin.sequences;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096\u0002¨\u0006\u0004"},
   d2 = {"kotlin/sequences/SequencesKt___SequencesKt$sortedWith$1", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib"}
)
public final class SequencesKt___SequencesKt$sortedWith$1 implements Sequence {
   final Sequence $this_sortedWith;
   final Comparator $comparator;

   @NotNull
   public Iterator iterator() {
      List var1 = SequencesKt.toMutableList(this.$this_sortedWith);
      CollectionsKt.sortWith(var1, this.$comparator);
      return var1.iterator();
   }

   SequencesKt___SequencesKt$sortedWith$1(Sequence var1, Comparator var2) {
      super();
      this.$this_sortedWith = var1;
      this.$comparator = var2;
   }
}
