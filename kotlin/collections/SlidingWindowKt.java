package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000\u001aH\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u0006\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u001aD\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u000e\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000¨\u0006\u000f"},
   d2 = {"checkWindowSizeStep", "", "size", "", "step", "windowedIterator", "", "", "T", "iterator", "partialWindows", "", "reuseBuffer", "windowedSequence", "Lkotlin/sequences/Sequence;", "kotlin-stdlib"}
)
public final class SlidingWindowKt {
   public static final void checkWindowSizeStep(int var0, int var1) {
      boolean var2 = var0 > 0 && var1 > 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var5 = false;
         String var6 = var0 != var1 ? "Both size " + var0 + " and step " + var1 + " must be greater than zero." : "size " + var0 + " must be greater than zero.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   @NotNull
   public static final Sequence windowedSequence(@NotNull Sequence var0, int var1, int var2, boolean var3, boolean var4) {
      checkWindowSizeStep(var1, var2);
      boolean var5 = false;
      return (Sequence)(new SlidingWindowKt$windowedSequence$$inlined$Sequence$1(var0, var1, var2, var3, var4));
   }

   @NotNull
   public static final Iterator windowedIterator(@NotNull Iterator var0, int var1, int var2, boolean var3, boolean var4) {
      return !var0.hasNext() ? (Iterator)EmptyIterator.INSTANCE : SequencesKt.iterator((Function2)(new SlidingWindowKt$windowedIterator$1(var2, var1, var0, var4, var3, (Continuation)null)));
   }
}
