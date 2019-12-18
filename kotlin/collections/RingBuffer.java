package kotlin.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\f\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00028\u0000¢\u0006\u0002\u0010\u0015J\u0016\u0010\u0016\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u0018J\u0006\u0010\u0019\u001a\u00020\u001aJ\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\u001cH\u0096\u0002J\u000e\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u0006J\u0015\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tH\u0014¢\u0006\u0002\u0010 J'\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\u0004\b\u0001\u0010\u00012\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00010\tH\u0014¢\u0006\u0002\u0010\"J9\u0010#\u001a\u00020\u0013\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\t2\u0006\u0010\u0014\u001a\u0002H\u00012\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010&J\u0015\u0010'\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u0006H\u0082\bR\u0018\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006("},
   d2 = {"Lkotlin/collections/RingBuffer;", "T", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "capacity", "", "(I)V", "buffer", "", "", "[Ljava/lang/Object;", "getCapacity", "()I", "<set-?>", "size", "getSize", "startIndex", "add", "", "element", "(Ljava/lang/Object;)V", "get", "index", "(I)Ljava/lang/Object;", "isFull", "", "iterator", "", "removeFirst", "n", "toArray", "()[Ljava/lang/Object;", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "fill", "fromIndex", "toIndex", "([Ljava/lang/Object;Ljava/lang/Object;II)V", "forward", "kotlin-stdlib"}
)
final class RingBuffer extends AbstractList implements RandomAccess {
   private final Object[] buffer;
   private int startIndex;
   private int size;
   private final int capacity;

   public int getSize() {
      return this.size;
   }

   public Object get(int var1) {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.size());
      int var3 = this.startIndex;
      Object[] var5 = this.buffer;
      boolean var4 = false;
      int var6 = (var3 + var1) % this.getCapacity();
      return var5[var6];
   }

   public final boolean isFull() {
      return this.size() == this.capacity;
   }

   @NotNull
   public Iterator iterator() {
      return (Iterator)(new RingBuffer$iterator$1(this));
   }

   @NotNull
   public Object[] toArray(@NotNull Object[] var1) {
      Object[] var10000;
      int var4;
      if (var1.length < this.size()) {
         var4 = this.size();
         boolean var5 = false;
         var10000 = Arrays.copyOf(var1, var4);
      } else {
         var10000 = var1;
      }

      Object[] var2 = var10000;
      int var3 = this.size();
      var4 = 0;

      int var6;
      for(var6 = this.startIndex; var4 < var3 && var6 < this.capacity; ++var6) {
         var2[var4] = this.buffer[var6];
         ++var4;
      }

      for(var6 = 0; var4 < var3; ++var6) {
         var2[var4] = this.buffer[var6];
         ++var4;
      }

      if (var2.length > this.size()) {
         var2[this.size()] = null;
      }

      return var2;
   }

   @NotNull
   public Object[] toArray() {
      return this.toArray(new Object[this.size()]);
   }

   public final void add(Object var1) {
      if (this.isFull()) {
         throw (Throwable)(new IllegalStateException("ring buffer is full"));
      } else {
         Object[] var10000 = this.buffer;
         int var3 = this.startIndex;
         int var4 = this.size();
         Object[] var6 = var10000;
         boolean var5 = false;
         int var7 = (var3 + var4) % this.getCapacity();
         var6[var7] = var1;
         this.size = this.size() + 1;
      }
   }

   public final void removeFirst(int var1) {
      boolean var2 = var1 >= 0;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5;
      String var9;
      if (!var2) {
         var5 = false;
         var9 = "n shouldn't be negative but it is " + var1;
         throw (Throwable)(new IllegalArgumentException(var9.toString()));
      } else {
         var2 = var1 <= this.size();
         var3 = false;
         var4 = false;
         if (!var2) {
            var5 = false;
            var9 = "n shouldn't be greater than the buffer size: n = " + var1 + ", size = " + this.size();
            throw (Throwable)(new IllegalArgumentException(var9.toString()));
         } else {
            if (var1 > 0) {
               int var7 = this.startIndex;
               boolean var6 = false;
               int var8 = (var7 + var1) % this.getCapacity();
               if (var7 > var8) {
                  this.fill(this.buffer, (Object)null, var7, this.capacity);
                  this.fill(this.buffer, (Object)null, 0, var8);
               } else {
                  this.fill(this.buffer, (Object)null, var7, var8);
               }

               this.startIndex = var8;
               this.size = this.size() - var1;
            }

         }
      }
   }

   private final int forward(int var1, int var2) {
      byte var3 = 0;
      return (var1 + var2) % this.getCapacity();
   }

   private final void fill(@NotNull Object[] var1, Object var2, int var3, int var4) {
      int var5 = var3;

      for(int var6 = var4; var5 < var6; ++var5) {
         var1[var5] = var2;
      }

   }

   static void fill$default(RingBuffer var0, Object[] var1, Object var2, int var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var3 = 0;
      }

      if ((var5 & 4) != 0) {
         var4 = var1.length;
      }

      var0.fill(var1, var2, var3, var4);
   }

   public final int getCapacity() {
      return this.capacity;
   }

   public RingBuffer(int var1) {
      super();
      this.capacity = var1;
      boolean var2 = this.capacity >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var5 = false;
         String var6 = "ring buffer capacity should not be negative but it is " + this.capacity;
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         this.buffer = new Object[this.capacity];
      }
   }

   public static final Object[] access$getBuffer$p(RingBuffer var0) {
      return var0.buffer;
   }

   public static final int access$forward(RingBuffer var0, int var1, int var2) {
      return var0.forward(var1, var2);
   }

   public static final int access$getSize$p(RingBuffer var0) {
      return var0.size();
   }

   public static final void access$setSize$p(RingBuffer var0, int var1) {
      var0.size = var1;
   }

   public static final int access$getStartIndex$p(RingBuffer var0) {
      return var0.startIndex;
   }

   public static final void access$setStartIndex$p(RingBuffer var0, int var1) {
      var0.startIndex = var1;
   }
}
