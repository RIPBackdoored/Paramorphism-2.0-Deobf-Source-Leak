package kotlin.ranges;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CharIterator;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00192\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0019B\u001f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0006H\u0016J\b\u0010\u0014\u001a\u00020\u0010H\u0016J\t\u0010\u0015\u001a\u00020\u0016H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\b\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"},
   d2 = {"Lkotlin/ranges/CharProgression;", "", "", "start", "endInclusive", "step", "", "(CCI)V", "first", "getFirst", "()C", "last", "getLast", "getStep", "()I", "equals", "", "other", "", "hashCode", "isEmpty", "iterator", "Lkotlin/collections/CharIterator;", "toString", "", "Companion", "kotlin-stdlib"}
)
public class CharProgression implements Iterable, KMappedMarker {
   private final char first;
   private final char last;
   private final int step;
   public static final CharProgression$Companion Companion = new CharProgression$Companion((DefaultConstructorMarker)null);

   public final char getFirst() {
      return this.first;
   }

   public final char getLast() {
      return this.last;
   }

   public final int getStep() {
      return this.step;
   }

   @NotNull
   public CharIterator iterator() {
      return (CharIterator)(new CharProgressionIterator(this.first, this.last, this.step));
   }

   public Iterator iterator() {
      return (Iterator)this.iterator();
   }

   public boolean isEmpty() {
      return this.step > 0 ? this.first > this.last : this.first < this.last;
   }

   public boolean equals(@Nullable Object var1) {
      return var1 instanceof CharProgression && (this.isEmpty() && ((CharProgression)var1).isEmpty() || this.first == ((CharProgression)var1).first && this.last == ((CharProgression)var1).last && this.step == ((CharProgression)var1).step);
   }

   public int hashCode() {
      return this.isEmpty() ? -1 : 31 * (31 * this.first + this.last) + this.step;
   }

   @NotNull
   public String toString() {
      return this.step > 0 ? this.first + ".." + this.last + " step " + this.step : this.first + " downTo " + this.last + " step " + -this.step;
   }

   public CharProgression(char var1, char var2, int var3) {
      super();
      if (var3 == 0) {
         throw (Throwable)(new IllegalArgumentException("Step must be non-zero."));
      } else if (var3 == Integer.MIN_VALUE) {
         throw (Throwable)(new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation."));
      } else {
         this.first = var1;
         this.last = (char)ProgressionUtilKt.getProgressionLastElement(var1, var2, var3);
         this.step = var3;
      }
   }
}
