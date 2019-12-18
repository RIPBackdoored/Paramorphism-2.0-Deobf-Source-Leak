package kotlin.ranges;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UInt;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00172\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0017B\u0018\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0004\u001a\u00020\u00038VX\u0096\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\t\u0010\bø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"},
   d2 = {"Lkotlin/ranges/UIntRange;", "Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/UInt;", "start", "endInclusive", "(IILkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndInclusive", "()Lkotlin/UInt;", "getStart", "contains", "", "value", "contains-WZ4Q5Ns", "(I)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
public final class UIntRange extends UIntProgression implements ClosedRange {
   @NotNull
   private static final UIntRange EMPTY = new UIntRange(-1, 0, (DefaultConstructorMarker)null);
   public static final UIntRange$Companion Companion = new UIntRange$Companion((DefaultConstructorMarker)null);

   @NotNull
   public UInt getStart() {
      return UInt.box-impl(this.getFirst());
   }

   public Comparable getStart() {
      return (Comparable)this.getStart();
   }

   @NotNull
   public UInt getEndInclusive() {
      return UInt.box-impl(this.getLast());
   }

   public Comparable getEndInclusive() {
      return (Comparable)this.getEndInclusive();
   }

   public boolean contains_WZ4Q5Ns/* $FF was: contains-WZ4Q5Ns*/(int var1) {
      int var2 = this.getFirst();
      boolean var3 = false;
      boolean var10000;
      if (UnsignedKt.uintCompare(var2, var1) <= 0) {
         int var5 = this.getLast();
         boolean var4 = false;
         if (UnsignedKt.uintCompare(var1, var5) <= 0) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   public boolean contains(Comparable var1) {
      return this.contains-WZ4Q5Ns(((UInt)var1).unbox-impl());
   }

   public boolean isEmpty() {
      int var1 = this.getFirst();
      int var2 = this.getLast();
      boolean var3 = false;
      return UnsignedKt.uintCompare(var1, var2) > 0;
   }

   public boolean equals(@Nullable Object var1) {
      return var1 instanceof UIntRange && (this.isEmpty() && ((UIntRange)var1).isEmpty() || this.getFirst() == ((UIntRange)var1).getFirst() && this.getLast() == ((UIntRange)var1).getLast());
   }

   public int hashCode() {
      int var10000;
      if (this.isEmpty()) {
         var10000 = -1;
      } else {
         int var1 = this.getFirst();
         byte var3 = 31;
         boolean var2 = false;
         var10000 = var3 * var1;
         var1 = this.getLast();
         int var5 = var10000;
         var2 = false;
         var10000 = var5 + var1;
      }

      return var10000;
   }

   @NotNull
   public String toString() {
      return UInt.toString-impl(this.getFirst()) + ".." + UInt.toString-impl(this.getLast());
   }

   private UIntRange(int var1, int var2) {
      super(var1, var2, 1, (DefaultConstructorMarker)null);
   }

   public UIntRange(int var1, int var2, DefaultConstructorMarker var3) {
      this(var1, var2);
   }

   public static final UIntRange access$getEMPTY$cp() {
      return EMPTY;
   }
}
