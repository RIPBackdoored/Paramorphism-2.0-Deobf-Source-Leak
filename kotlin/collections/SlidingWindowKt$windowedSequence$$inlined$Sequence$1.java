package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096\u0002¨\u0006\u0004¸\u0006\u0000"},
   d2 = {"kotlin/sequences/SequencesKt__SequencesKt$Sequence$1", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib"}
)
public final class SlidingWindowKt$windowedSequence$$inlined$Sequence$1 implements Sequence {
   final Sequence $this_windowedSequence$inlined;
   final int $size$inlined;
   final int $step$inlined;
   final boolean $partialWindows$inlined;
   final boolean $reuseBuffer$inlined;

   public SlidingWindowKt$windowedSequence$$inlined$Sequence$1(Sequence var1, int var2, int var3, boolean var4, boolean var5) {
      super();
      this.$this_windowedSequence$inlined = var1;
      this.$size$inlined = var2;
      this.$step$inlined = var3;
      this.$partialWindows$inlined = var4;
      this.$reuseBuffer$inlined = var5;
   }

   @NotNull
   public Iterator iterator() {
      boolean var1 = false;
      return SlidingWindowKt.windowedIterator(this.$this_windowedSequence$inlined.iterator(), this.$size$inlined, this.$step$inlined, this.$partialWindows$inlined, this.$reuseBuffer$inlined);
   }
}
