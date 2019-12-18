package kotlin.jvm.internal;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.DoubleIterator;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0013\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lkotlin/jvm/internal/ArrayDoubleIterator;", "Lkotlin/collections/DoubleIterator;", "array", "", "([D)V", "index", "", "hasNext", "", "nextDouble", "", "kotlin-stdlib"}
)
final class ArrayDoubleIterator extends DoubleIterator {
   private int index;
   private final double[] array;

   public boolean hasNext() {
      return this.index < this.array.length;
   }

   public double nextDouble() {
      double var5;
      try {
         double[] var10000 = this.array;
         int var1;
         this.index = (var1 = this.index) + 1;
         var5 = var10000[var1];
      } catch (ArrayIndexOutOfBoundsException var4) {
         --this.index;
         throw (Throwable)(new NoSuchElementException(var4.getMessage()));
      }

      return var5;
   }

   public ArrayDoubleIterator(@NotNull double[] var1) {
      super();
      this.array = var1;
   }
}
