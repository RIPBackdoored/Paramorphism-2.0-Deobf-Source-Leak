package kotlin;

import java.util.NoSuchElementException;
import kotlin.collections.UByteIterator;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0010\u0010\t\u001a\u00020\nH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"},
   d2 = {"Lkotlin/UByteArray$Iterator;", "Lkotlin/collections/UByteIterator;", "array", "", "([B)V", "index", "", "hasNext", "", "nextUByte", "Lkotlin/UByte;", "()B", "kotlin-stdlib"}
)
final class UByteArray$Iterator extends UByteIterator {
   private int index;
   private final byte[] array;

   public boolean hasNext() {
      return this.index < this.array.length;
   }

   public byte nextUByte() {
      if (this.index < this.array.length) {
         byte[] var10000 = this.array;
         int var1;
         this.index = (var1 = this.index) + 1;
         byte var3 = var10000[var1];
         boolean var2 = false;
         return UByte.constructor-impl(var3);
      } else {
         throw (Throwable)(new NoSuchElementException(String.valueOf(this.index)));
      }
   }

   public UByteArray$Iterator(@NotNull byte[] var1) {
      super();
      this.array = var1;
   }
}
