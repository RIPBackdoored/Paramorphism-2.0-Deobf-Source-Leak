package kotlin.random;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0004H\u0007\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\fH\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003H\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0000\u001a\u0014\u0010\r\u001a\u00020\u0003*\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000fH\u0007\u001a\u0014\u0010\u0010\u001a\u00020\u0004*\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0011H\u0007\u001a\u0014\u0010\u0012\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0003H\u0000¨\u0006\u0014"},
   d2 = {"Random", "Lkotlin/random/Random;", "seed", "", "", "boundsErrorMessage", "", "from", "", "until", "checkRangeBounds", "", "", "nextInt", "range", "Lkotlin/ranges/IntRange;", "nextLong", "Lkotlin/ranges/LongRange;", "takeUpperBits", "bitCount", "kotlin-stdlib"}
)
public final class RandomKt {
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Random Random(int var0) {
      return (Random)(new XorWowRandom(var0, var0 >> 31));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Random Random(long var0) {
      return (Random)(new XorWowRandom((int)var0, (int)(var0 >> 32)));
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final int nextInt(@NotNull Random var0, @NotNull IntRange var1) {
      if (var1.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot get random in empty range: " + var1));
      } else {
         return var1.getLast() < 0 ? var0.nextInt(var1.getFirst(), var1.getLast() + 1) : (var1.getFirst() > Integer.MIN_VALUE ? var0.nextInt(var1.getFirst() - 1, var1.getLast()) + 1 : var0.nextInt());
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final long nextLong(@NotNull Random var0, @NotNull LongRange var1) {
      if (var1.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot get random in empty range: " + var1));
      } else {
         return var1.getLast() < 4294967295L ? var0.nextLong(var1.getStart(), var1.getEndInclusive() + 1L) : (var1.getStart() > Long.MIN_VALUE ? var0.nextLong(var1.getStart() - 1L, var1.getEndInclusive()) + 1L : var0.nextLong());
      }
   }

   public static final int takeUpperBits(int var0, int var1) {
      return var0 >>> 32 - var1 & -var1 >> 31;
   }

   public static final void checkRangeBounds(int var0, int var1) {
      boolean var2 = var1 > var0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var5 = false;
         String var6 = boundsErrorMessage(var0, var1);
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   public static final void checkRangeBounds(long var0, long var2) {
      boolean var4 = var2 > var0;
      boolean var5 = false;
      boolean var6 = false;
      if (!var4) {
         boolean var7 = false;
         String var8 = boundsErrorMessage(var0, var2);
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      }
   }

   public static final void checkRangeBounds(double var0, double var2) {
      boolean var4 = var2 > var0;
      boolean var5 = false;
      boolean var6 = false;
      if (!var4) {
         boolean var7 = false;
         String var8 = boundsErrorMessage(var0, var2);
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      }
   }

   @NotNull
   public static final String boundsErrorMessage(@NotNull Object var0, @NotNull Object var1) {
      return "Random range is empty: [" + var0 + ", " + var1 + ").";
   }
}
