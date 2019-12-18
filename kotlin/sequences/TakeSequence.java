package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH\u0096\u0002J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lkotlin/sequences/TakeSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "count", "", "(Lkotlin/sequences/Sequence;I)V", "drop", "n", "iterator", "", "take", "kotlin-stdlib"}
)
public final class TakeSequence implements Sequence, DropTakeSequence {
   private final Sequence sequence;
   private final int count;

   @NotNull
   public Sequence drop(int var1) {
      return var1 >= this.count ? SequencesKt.emptySequence() : (Sequence)(new SubSequence(this.sequence, var1, this.count));
   }

   @NotNull
   public Sequence take(int var1) {
      return var1 >= this.count ? (Sequence)this : (Sequence)(new TakeSequence(this.sequence, var1));
   }

   @NotNull
   public Iterator iterator() {
      return (Iterator)(new TakeSequence$iterator$1(this));
   }

   public TakeSequence(@NotNull Sequence var1, int var2) {
      super();
      this.sequence = var1;
      this.count = var2;
      boolean var3 = this.count >= 0;
      boolean var4 = false;
      boolean var5 = false;
      if (!var3) {
         boolean var6 = false;
         String var7 = "count must be non-negative, but was " + this.count + '.';
         throw (Throwable)(new IllegalArgumentException(var7.toString()));
      }
   }

   public static final int access$getCount$p(TakeSequence var0) {
      return var0.count;
   }

   public static final Sequence access$getSequence$p(TakeSequence var0) {
      return var0.sequence;
   }
}
