package kotlin.ranges;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0002H\u0096\u0002J\u0013\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\fH\u0016J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0002H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\t¨\u0006\u0019"},
   d2 = {"Lkotlin/ranges/ClosedDoubleRange;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "start", "endInclusive", "(DD)V", "_endInclusive", "_start", "getEndInclusive", "()Ljava/lang/Double;", "getStart", "contains", "", "value", "equals", "other", "", "hashCode", "", "isEmpty", "lessThanOrEquals", "a", "b", "toString", "", "kotlin-stdlib"}
)
final class ClosedDoubleRange implements ClosedFloatingPointRange {
   private final double _start;
   private final double _endInclusive;

   @NotNull
   public Double getStart() {
      return this._start;
   }

   public Comparable getStart() {
      return (Comparable)this.getStart();
   }

   @NotNull
   public Double getEndInclusive() {
      return this._endInclusive;
   }

   public Comparable getEndInclusive() {
      return (Comparable)this.getEndInclusive();
   }

   public boolean lessThanOrEquals(double var1, double var3) {
      return var1 <= var3;
   }

   public boolean lessThanOrEquals(Comparable var1, Comparable var2) {
      return this.lessThanOrEquals(((Number)var1).doubleValue(), ((Number)var2).doubleValue());
   }

   public boolean contains(double var1) {
      return var1 >= this._start && var1 <= this._endInclusive;
   }

   public boolean contains(Comparable var1) {
      return this.contains(((Number)var1).doubleValue());
   }

   public boolean isEmpty() {
      return this._start > this._endInclusive;
   }

   public boolean equals(@Nullable Object var1) {
      return var1 instanceof ClosedDoubleRange && (this.isEmpty() && ((ClosedDoubleRange)var1).isEmpty() || this._start == ((ClosedDoubleRange)var1)._start && this._endInclusive == ((ClosedDoubleRange)var1)._endInclusive);
   }

   public int hashCode() {
      return this.isEmpty() ? -1 : 31 * Double.valueOf(this._start).hashCode() + Double.valueOf(this._endInclusive).hashCode();
   }

   @NotNull
   public String toString() {
      return this._start + ".." + this._endInclusive;
   }

   public ClosedDoubleRange(double var1, double var3) {
      super();
      this._start = var1;
      this._endInclusive = var3;
   }
}
