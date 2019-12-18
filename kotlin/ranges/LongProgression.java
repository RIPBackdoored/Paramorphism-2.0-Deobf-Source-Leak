package kotlin.ranges;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.LongIterator;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B\u001f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u000eH\u0016J\t\u0010\u0014\u001a\u00020\u0015H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0019"},
   d2 = {"Lkotlin/ranges/LongProgression;", "", "", "start", "endInclusive", "step", "(JJJ)V", "first", "getFirst", "()J", "last", "getLast", "getStep", "equals", "", "other", "", "hashCode", "", "isEmpty", "iterator", "Lkotlin/collections/LongIterator;", "toString", "", "Companion", "kotlin-stdlib"}
)
public class LongProgression implements Iterable, KMappedMarker {
   private final long first;
   private final long last;
   private final long step;
   public static final LongProgression$Companion Companion = new LongProgression$Companion((DefaultConstructorMarker)null);

   public final long getFirst() {
      return this.first;
   }

   public final long getLast() {
      return this.last;
   }

   public final long getStep() {
      return this.step;
   }

   @NotNull
   public LongIterator iterator() {
      return (LongIterator)(new LongProgressionIterator(this.first, this.last, this.step));
   }

   public Iterator iterator() {
      return (Iterator)this.iterator();
   }

   public boolean isEmpty() {
      return this.step > 0L ? this.first > this.last : this.first < this.last;
   }

   public boolean equals(@Nullable Object var1) {
      return var1 instanceof LongProgression && (this.isEmpty() && ((LongProgression)var1).isEmpty() || this.first == ((LongProgression)var1).first && this.last == ((LongProgression)var1).last && this.step == ((LongProgression)var1).step);
   }

   public int hashCode() {
      return this.isEmpty() ? -1 : (int)((long)31 * ((long)31 * (this.first ^ this.first >>> 32) + (this.last ^ this.last >>> 32)) + (this.step ^ this.step >>> 32));
   }

   @NotNull
   public String toString() {
      return this.step > 0L ? this.first + ".." + this.last + " step " + this.step : this.first + " downTo " + this.last + " step " + -this.step;
   }

   public LongProgression(long var1, long var3, long var5) {
      super();
      if (var5 == 0L) {
         throw (Throwable)(new IllegalArgumentException("Step must be non-zero."));
      } else if (var5 == Long.MIN_VALUE) {
         throw (Throwable)(new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation."));
      } else {
         this.first = var1;
         this.last = ProgressionUtilKt.getProgressionLastElement(var1, var3, var5);
         this.step = var5;
      }
   }
}
