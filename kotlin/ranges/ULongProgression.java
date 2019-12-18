package kotlin.ranges;

import java.util.Iterator;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.collections.ULongIterator;
import kotlin.internal.UProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0017\u0018\u0000 \u001a2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001aB\"\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0010H\u0016J\t\u0010\u0016\u001a\u00020\u0017H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0016\u0010\b\u001a\u00020\u0002ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0016\u0010\f\u001a\u00020\u0002ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"},
   d2 = {"Lkotlin/ranges/ULongProgression;", "", "Lkotlin/ULong;", "start", "endInclusive", "step", "", "(JJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "first", "getFirst", "()J", "J", "last", "getLast", "getStep", "equals", "", "other", "", "hashCode", "", "isEmpty", "iterator", "Lkotlin/collections/ULongIterator;", "toString", "", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
public class ULongProgression implements Iterable, KMappedMarker {
   private final long first;
   private final long last;
   private final long step;
   public static final ULongProgression$Companion Companion = new ULongProgression$Companion((DefaultConstructorMarker)null);

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
   public ULongIterator iterator() {
      return (ULongIterator)(new ULongProgressionIterator(this.first, this.last, this.step, (DefaultConstructorMarker)null));
   }

   public Iterator iterator() {
      return (Iterator)this.iterator();
   }

   public boolean isEmpty() {
      boolean var10000;
      long var1;
      long var3;
      boolean var5;
      if (this.step > 0L) {
         var1 = this.first;
         var3 = this.last;
         var5 = false;
         var10000 = UnsignedKt.ulongCompare(var1, var3) > 0;
      } else {
         var1 = this.first;
         var3 = this.last;
         var5 = false;
         var10000 = UnsignedKt.ulongCompare(var1, var3) < 0;
      }

      return var10000;
   }

   public boolean equals(@Nullable Object var1) {
      return var1 instanceof ULongProgression && (this.isEmpty() && ((ULongProgression)var1).isEmpty() || this.first == ((ULongProgression)var1).first && this.last == ((ULongProgression)var1).last && this.step == ((ULongProgression)var1).step);
   }

   public int hashCode() {
      int var10000;
      if (this.isEmpty()) {
         var10000 = -1;
      } else {
         long var1 = this.first;
         long var3 = this.first;
         byte var5 = 32;
         byte var8 = 31;
         byte var7 = 31;
         boolean var6 = false;
         long var9 = ULong.constructor-impl(var3 >>> var5);
         boolean var12 = false;
         var9 = ULong.constructor-impl(var1 ^ var9);
         boolean var11 = false;
         int var14 = (int)var9;
         int var10001 = var8 * var14;
         var1 = this.last;
         var3 = this.last;
         var5 = 32;
         int var13 = var10001;
         var6 = false;
         var9 = ULong.constructor-impl(var3 >>> var5);
         var12 = false;
         var9 = ULong.constructor-impl(var1 ^ var9);
         var11 = false;
         var14 = (int)var9;
         var10000 = var7 * (var13 + var14) + (int)(this.step ^ this.step >>> 32);
      }

      return var10000;
   }

   @NotNull
   public String toString() {
      return this.step > 0L ? ULong.toString-impl(this.first) + ".." + ULong.toString-impl(this.last) + " step " + this.step : ULong.toString-impl(this.first) + " downTo " + ULong.toString-impl(this.last) + " step " + -this.step;
   }

   private ULongProgression(long var1, long var3, long var5) {
      super();
      if (var5 == 0L) {
         throw (Throwable)(new IllegalArgumentException("Step must be non-zero."));
      } else if (var5 == Long.MIN_VALUE) {
         throw (Throwable)(new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation."));
      } else {
         this.first = var1;
         this.last = UProgressionUtilKt.getProgressionLastElement-7ftBX0g(var1, var3, var5);
         this.step = var5;
      }
   }

   public ULongProgression(long var1, long var3, long var5, DefaultConstructorMarker var7) {
      this(var1, var3, var5);
   }
}
