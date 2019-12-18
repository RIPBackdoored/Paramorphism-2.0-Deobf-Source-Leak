package kotlin.coroutines.experimental;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096\u0002¨\u0006\u0004¸\u0006\u0000"},
   d2 = {"kotlin/sequences/SequencesKt__SequencesKt$Sequence$1", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib"}
)
public final class SequenceBuilderKt__SequenceBuilderKt$buildSequence$$inlined$Sequence$1 implements Sequence {
   final Function2 $builderAction$inlined;

   public SequenceBuilderKt__SequenceBuilderKt$buildSequence$$inlined$Sequence$1(Function2 var1) {
      super();
      this.$builderAction$inlined = var1;
   }

   @NotNull
   public Iterator iterator() {
      boolean var1 = false;
      return SequenceBuilderKt.buildIterator(this.$builderAction$inlined);
   }
}
