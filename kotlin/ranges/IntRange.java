package kotlin.ranges;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0014B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\u0011\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0096\u0002J\u0013\u0010\r\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0003H\u0016J\b\u0010\u0011\u001a\u00020\u000bH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0014\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\b¨\u0006\u0015"},
   d2 = {"Lkotlin/ranges/IntRange;", "Lkotlin/ranges/IntProgression;", "Lkotlin/ranges/ClosedRange;", "", "start", "endInclusive", "(II)V", "getEndInclusive", "()Ljava/lang/Integer;", "getStart", "contains", "", "value", "equals", "other", "", "hashCode", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"}
)
public final class IntRange extends IntProgression implements ClosedRange {
   @NotNull
   private static final IntRange EMPTY = new IntRange(1, 0);
   public static final IntRange$Companion Companion = new IntRange$Companion((DefaultConstructorMarker)null);

   @NotNull
   public Integer getStart() {
      return this.getFirst();
   }

   public Comparable getStart() {
      return (Comparable)this.getStart();
   }

   @NotNull
   public Integer getEndInclusive() {
      return this.getLast();
   }

   public Comparable getEndInclusive() {
      return (Comparable)this.getEndInclusive();
   }

   public boolean contains(int var1) {
      return this.getFirst() <= var1 && var1 <= this.getLast();
   }

   public boolean contains(Comparable var1) {
      return this.contains(((Number)var1).intValue());
   }

   public boolean isEmpty() {
      return this.getFirst() > this.getLast();
   }

   public boolean equals(@Nullable Object var1) {
      return var1 instanceof IntRange && (this.isEmpty() && ((IntRange)var1).isEmpty() || this.getFirst() == ((IntRange)var1).getFirst() && this.getLast() == ((IntRange)var1).getLast());
   }

   public int hashCode() {
      return this.isEmpty() ? -1 : 31 * this.getFirst() + this.getLast();
   }

   @NotNull
   public String toString() {
      return this.getFirst() + ".." + this.getLast();
   }

   public IntRange(int var1, int var2) {
      super(var1, var2, 1);
   }

   public static final IntRange access$getEMPTY$cp() {
      return EMPTY;
   }
}
