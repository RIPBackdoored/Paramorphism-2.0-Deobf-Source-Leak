package kotlin.ranges;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0015B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\u0011\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0096\u0002J\u0013\u0010\r\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u000bH\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\b¨\u0006\u0016"},
   d2 = {"Lkotlin/ranges/LongRange;", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/ClosedRange;", "", "start", "endInclusive", "(JJ)V", "getEndInclusive", "()Ljava/lang/Long;", "getStart", "contains", "", "value", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"}
)
public final class LongRange extends LongProgression implements ClosedRange {
   @NotNull
   private static final LongRange EMPTY = new LongRange(1L, 0L);
   public static final LongRange$Companion Companion = new LongRange$Companion((DefaultConstructorMarker)null);

   @NotNull
   public Long getStart() {
      return this.getFirst();
   }

   public Comparable getStart() {
      return (Comparable)this.getStart();
   }

   @NotNull
   public Long getEndInclusive() {
      return this.getLast();
   }

   public Comparable getEndInclusive() {
      return (Comparable)this.getEndInclusive();
   }

   public boolean contains(long var1) {
      return this.getFirst() <= var1 && var1 <= this.getLast();
   }

   public boolean contains(Comparable var1) {
      return this.contains(((Number)var1).longValue());
   }

   public boolean isEmpty() {
      return this.getFirst() > this.getLast();
   }

   public boolean equals(@Nullable Object var1) {
      return var1 instanceof LongRange && (this.isEmpty() && ((LongRange)var1).isEmpty() || this.getFirst() == ((LongRange)var1).getFirst() && this.getLast() == ((LongRange)var1).getLast());
   }

   public int hashCode() {
      return this.isEmpty() ? -1 : (int)((long)31 * (this.getFirst() ^ this.getFirst() >>> 32) + (this.getLast() ^ this.getLast() >>> 32));
   }

   @NotNull
   public String toString() {
      return this.getFirst() + ".." + this.getLast();
   }

   public LongRange(long var1, long var3) {
      super(var1, var3, 1L);
   }

   public static final LongRange access$getEMPTY$cp() {
      return EMPTY;
   }
}
