package kotlin.random;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.ranges.UIntRange;
import kotlin.ranges.ULongRange;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\"\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0001ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u001e\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013\u001a2\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0014\u001a\u00020\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0003H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001b\u001a&\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001c\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007ø\u0001\u0000¢\u0006\u0002\u0010 \u001a\u0014\u0010!\u001a\u00020\b*\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\"\u001a\u001e\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0004\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b#\u0010$\u001a&\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b%\u0010&\u001a\u001c\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u001e\u001a\u00020'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006)"},
   d2 = {"checkUIntRangeBounds", "", "from", "Lkotlin/UInt;", "until", "checkUIntRangeBounds-J1ME1BU", "(II)V", "checkULongRangeBounds", "Lkotlin/ULong;", "checkULongRangeBounds-eb3DHEI", "(JJ)V", "nextUBytes", "Lkotlin/UByteArray;", "Lkotlin/random/Random;", "size", "", "(Lkotlin/random/Random;I)[B", "array", "nextUBytes-EVgfTAA", "(Lkotlin/random/Random;[B)[B", "fromIndex", "toIndex", "nextUBytes-Wvrt4B4", "(Lkotlin/random/Random;[BII)[B", "nextUInt", "(Lkotlin/random/Random;)I", "nextUInt-qCasIEU", "(Lkotlin/random/Random;I)I", "nextUInt-a8DCA5k", "(Lkotlin/random/Random;II)I", "range", "Lkotlin/ranges/UIntRange;", "(Lkotlin/random/Random;Lkotlin/ranges/UIntRange;)I", "nextULong", "(Lkotlin/random/Random;)J", "nextULong-V1Xi4fY", "(Lkotlin/random/Random;J)J", "nextULong-jmpaW-c", "(Lkotlin/random/Random;JJ)J", "Lkotlin/ranges/ULongRange;", "(Lkotlin/random/Random;Lkotlin/ranges/ULongRange;)J", "kotlin-stdlib"}
)
public final class URandomKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int nextUInt(@NotNull Random var0) {
      int var1 = var0.nextInt();
      boolean var2 = false;
      return UInt.constructor-impl(var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int nextUInt_qCasIEU/* $FF was: nextUInt-qCasIEU*/(@NotNull Random var0, int var1) {
      return nextUInt-a8DCA5k(var0, 0, var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int nextUInt_a8DCA5k/* $FF was: nextUInt-a8DCA5k*/(@NotNull Random var0, int var1, int var2) {
      checkUIntRangeBounds-J1ME1BU(var1, var2);
      boolean var5 = false;
      int var3 = var1 ^ Integer.MIN_VALUE;
      boolean var6 = false;
      int var4 = var2 ^ Integer.MIN_VALUE;
      int var8 = var0.nextInt(var3, var4) ^ Integer.MIN_VALUE;
      boolean var7 = false;
      return UInt.constructor-impl(var8);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int nextUInt(@NotNull Random var0, @NotNull UIntRange var1) {
      if (var1.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot get random in empty range: " + var1));
      } else {
         int var2 = var1.getLast();
         byte var3 = -1;
         boolean var4 = false;
         int var10000;
         int var6;
         byte var8;
         if (UnsignedKt.uintCompare(var2, var3) < 0) {
            int var10001 = var1.getFirst();
            var2 = var1.getLast();
            var8 = 1;
            var6 = var10001;
            var4 = false;
            int var7 = UInt.constructor-impl(var2 + var8);
            var10000 = nextUInt-a8DCA5k(var0, var6, var7);
         } else {
            var2 = var1.getFirst();
            var8 = 0;
            var4 = false;
            if (UnsignedKt.uintCompare(var2, var8) > 0) {
               var2 = var1.getFirst();
               var8 = 1;
               var4 = false;
               var6 = UInt.constructor-impl(var2 - var8);
               var2 = nextUInt-a8DCA5k(var0, var6, var1.getLast());
               var8 = 1;
               var4 = false;
               var10000 = UInt.constructor-impl(var2 + var8);
            } else {
               var10000 = nextUInt(var0);
            }
         }

         return var10000;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long nextULong(@NotNull Random var0) {
      long var1 = var0.nextLong();
      boolean var3 = false;
      return ULong.constructor-impl(var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long nextULong_V1Xi4fY/* $FF was: nextULong-V1Xi4fY*/(@NotNull Random var0, long var1) {
      return nextULong-jmpaW-c(var0, 0L, var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long nextULong_jmpaW_c/* $FF was: nextULong-jmpaW-c*/(@NotNull Random var0, long var1, long var3) {
      checkULongRangeBounds-eb3DHEI(var1, var3);
      boolean var9 = false;
      long var5 = var1 ^ Long.MIN_VALUE;
      boolean var11 = false;
      long var7 = var3 ^ Long.MIN_VALUE;
      long var14 = var0.nextLong(var5, var7) ^ Long.MIN_VALUE;
      boolean var13 = false;
      return ULong.constructor-impl(var14);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long nextULong(@NotNull Random var0, @NotNull ULongRange var1) {
      if (var1.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot get random in empty range: " + var1));
      } else {
         long var2 = var1.getLast();
         long var4 = -1L;
         boolean var6 = false;
         boolean var5;
         boolean var9;
         long var10;
         boolean var12;
         long var14;
         long var10000;
         byte var18;
         if (UnsignedKt.ulongCompare(var2, var4) < 0) {
            long var10001 = var1.getFirst();
            var2 = var1.getLast();
            var18 = 1;
            var14 = var10001;
            var5 = false;
            var9 = false;
            var10 = ULong.constructor-impl((long)var18 & 4294967295L);
            var12 = false;
            long var16 = ULong.constructor-impl(var2 + var10);
            var10000 = nextULong-jmpaW-c(var0, var14, var16);
         } else {
            var2 = var1.getFirst();
            var4 = 0L;
            var6 = false;
            if (UnsignedKt.ulongCompare(var2, var4) > 0) {
               var2 = var1.getFirst();
               var18 = 1;
               var5 = false;
               var9 = false;
               var10 = ULong.constructor-impl((long)var18 & 4294967295L);
               var12 = false;
               var14 = ULong.constructor-impl(var2 - var10);
               var2 = nextULong-jmpaW-c(var0, var14, var1.getLast());
               var18 = 1;
               var5 = false;
               var9 = false;
               var10 = ULong.constructor-impl((long)var18 & 4294967295L);
               var12 = false;
               var10000 = ULong.constructor-impl(var2 + var10);
            } else {
               var10000 = nextULong(var0);
            }
         }

         return var10000;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] nextUBytes_EVgfTAA/* $FF was: nextUBytes-EVgfTAA*/(@NotNull Random var0, @NotNull byte[] var1) {
      boolean var3 = false;
      var0.nextBytes(var1);
      return var1;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] nextUBytes(@NotNull Random var0, int var1) {
      byte[] var2 = var0.nextBytes(var1);
      boolean var3 = false;
      return UByteArray.constructor-impl(var2);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] nextUBytes_Wvrt4B4/* $FF was: nextUBytes-Wvrt4B4*/(@NotNull Random var0, @NotNull byte[] var1, int var2, int var3) {
      boolean var5 = false;
      var0.nextBytes(var1, var2, var3);
      return var1;
   }

   public static byte[] nextUBytes_Wvrt4B4$default/* $FF was: nextUBytes-Wvrt4B4$default*/(Random var0, byte[] var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UByteArray.getSize-impl(var1);
      }

      return nextUBytes-Wvrt4B4(var0, var1, var2, var3);
   }

   @ExperimentalUnsignedTypes
   public static final void checkUIntRangeBounds_J1ME1BU/* $FF was: checkUIntRangeBounds-J1ME1BU*/(int var0, int var1) {
      boolean var3 = false;
      boolean var2 = UnsignedKt.uintCompare(var1, var0) > 0;
      var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var5 = false;
         String var6 = RandomKt.boundsErrorMessage(UInt.box-impl(var0), UInt.box-impl(var1));
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   @ExperimentalUnsignedTypes
   public static final void checkULongRangeBounds_eb3DHEI/* $FF was: checkULongRangeBounds-eb3DHEI*/(long var0, long var2) {
      boolean var6 = false;
      boolean var4 = UnsignedKt.ulongCompare(var2, var0) > 0;
      boolean var5 = false;
      var6 = false;
      if (!var4) {
         boolean var7 = false;
         String var8 = RandomKt.boundsErrorMessage(ULong.box-impl(var0), ULong.box-impl(var2));
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      }
   }
}
