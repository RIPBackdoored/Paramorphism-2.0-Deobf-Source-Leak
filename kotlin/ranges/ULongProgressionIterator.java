package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.collections.ULongIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0003\u0018\u00002\u00020\u0001B \u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\t\u0010\n\u001a\u00020\u000bH\u0096\u0002J\u0010\u0010\r\u001a\u00020\u0003H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u000eR\u0013\u0010\b\u001a\u00020\u0003X\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\f\u001a\u00020\u0003X\u0082\u000eø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\tR\u0013\u0010\u0005\u001a\u00020\u0003X\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"},
   d2 = {"Lkotlin/ranges/ULongProgressionIterator;", "Lkotlin/collections/ULongIterator;", "first", "Lkotlin/ULong;", "last", "step", "", "(JJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "finalElement", "J", "hasNext", "", "next", "nextULong", "()J", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
final class ULongProgressionIterator extends ULongIterator {
   private final long finalElement;
   private boolean hasNext;
   private final long step;
   private long next;

   public boolean hasNext() {
      return this.hasNext;
   }

   public long nextULong() {
      long var1 = this.next;
      if (var1 == this.finalElement) {
         if (!this.hasNext) {
            throw (Throwable)(new NoSuchElementException());
         }

         this.hasNext = false;
      } else {
         long var3 = this.next;
         long var5 = this.step;
         boolean var7 = false;
         long var9 = ULong.constructor-impl(var3 + var5);
         this.next = var9;
      }

      return var1;
   }

   private ULongProgressionIterator(long var1, long var3, long var5) {
      super();
      this.finalElement = var3;
      ULongProgressionIterator var10000;
      boolean var10001;
      boolean var9;
      int var11;
      if (var5 > 0L) {
         var9 = false;
         var11 = UnsignedKt.ulongCompare(var1, var3);
         var10000 = this;
         var10001 = var11 <= 0;
      } else {
         var9 = false;
         var11 = UnsignedKt.ulongCompare(var1, var3);
         var10000 = this;
         var10001 = var11 >= 0;
      }

      var10000.hasNext = var10001;
      var9 = false;
      long var13 = ULong.constructor-impl(var5);
      this.step = var13;
      this.next = this.hasNext ? var1 : this.finalElement;
   }

   public ULongProgressionIterator(long var1, long var3, long var5, DefaultConstructorMarker var7) {
      this(var1, var3, var5);
   }
}
