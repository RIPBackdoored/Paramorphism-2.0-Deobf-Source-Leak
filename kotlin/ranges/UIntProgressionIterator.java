package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UInt;
import kotlin.UnsignedKt;
import kotlin.collections.UIntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0003\u0018\u00002\u00020\u0001B \u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\t\u0010\n\u001a\u00020\u000bH\u0096\u0002J\u0010\u0010\r\u001a\u00020\u0003H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u000eR\u0013\u0010\b\u001a\u00020\u0003X\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\f\u001a\u00020\u0003X\u0082\u000eø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\tR\u0013\u0010\u0005\u001a\u00020\u0003X\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"},
   d2 = {"Lkotlin/ranges/UIntProgressionIterator;", "Lkotlin/collections/UIntIterator;", "first", "Lkotlin/UInt;", "last", "step", "", "(IIILkotlin/jvm/internal/DefaultConstructorMarker;)V", "finalElement", "I", "hasNext", "", "next", "nextUInt", "()I", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
final class UIntProgressionIterator extends UIntIterator {
   private final int finalElement;
   private boolean hasNext;
   private final int step;
   private int next;

   public boolean hasNext() {
      return this.hasNext;
   }

   public int nextUInt() {
      int var1 = this.next;
      if (var1 == this.finalElement) {
         if (!this.hasNext) {
            throw (Throwable)(new NoSuchElementException());
         }

         this.hasNext = false;
      } else {
         int var2 = this.next;
         int var3 = this.step;
         boolean var4 = false;
         int var6 = UInt.constructor-impl(var2 + var3);
         this.next = var6;
      }

      return var1;
   }

   private UIntProgressionIterator(int var1, int var2, int var3) {
      super();
      this.finalElement = var2;
      UIntProgressionIterator var10000;
      boolean var10001;
      boolean var5;
      int var7;
      if (var3 > 0) {
         var5 = false;
         var7 = UnsignedKt.uintCompare(var1, var2);
         var10000 = this;
         var10001 = var7 <= 0;
      } else {
         var5 = false;
         var7 = UnsignedKt.uintCompare(var1, var2);
         var10000 = this;
         var10001 = var7 >= 0;
      }

      var10000.hasNext = var10001;
      var5 = false;
      var7 = UInt.constructor-impl(var3);
      this.step = var7;
      this.next = this.hasNext ? var1 : this.finalElement;
   }

   public UIntProgressionIterator(int var1, int var2, int var3, DefaultConstructorMarker var4) {
      this(var1, var2, var3);
   }
}
