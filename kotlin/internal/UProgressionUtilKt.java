package kotlin.internal;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a*\u0010\u0000\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a*\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0006\u001a*\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"},
   d2 = {"differenceModulo", "Lkotlin/UInt;", "a", "b", "c", "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", "end", "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"}
)
public final class UProgressionUtilKt {
   private static final int differenceModulo_WZ9TVnA/* $FF was: differenceModulo-WZ9TVnA*/(int var0, int var1, int var2) {
      boolean var5 = false;
      int var3 = UnsignedKt.uintRemainder-J1ME1BU(var0, var2);
      boolean var6 = false;
      int var4 = UnsignedKt.uintRemainder-J1ME1BU(var1, var2);
      var6 = false;
      int var10000;
      if (UnsignedKt.uintCompare(var3, var4) >= 0) {
         var6 = false;
         var10000 = UInt.constructor-impl(var3 - var4);
      } else {
         var6 = false;
         int var7 = UInt.constructor-impl(var3 - var4);
         var6 = false;
         var10000 = UInt.constructor-impl(var7 + var2);
      }

      return var10000;
   }

   private static final long differenceModulo_sambcqE/* $FF was: differenceModulo-sambcqE*/(long var0, long var2, long var4) {
      boolean var10 = false;
      long var6 = UnsignedKt.ulongRemainder-eb3DHEI(var0, var4);
      boolean var12 = false;
      long var8 = UnsignedKt.ulongRemainder-eb3DHEI(var2, var4);
      var12 = false;
      long var10000;
      if (UnsignedKt.ulongCompare(var6, var8) >= 0) {
         var12 = false;
         var10000 = ULong.constructor-impl(var6 - var8);
      } else {
         var12 = false;
         long var13 = ULong.constructor-impl(var6 - var8);
         var12 = false;
         var10000 = ULong.constructor-impl(var13 + var4);
      }

      return var10000;
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final int getProgressionLastElement_Nkh28Cs/* $FF was: getProgressionLastElement-Nkh28Cs*/(int var0, int var1, int var2) {
      int var10000;
      boolean var4;
      boolean var5;
      int var8;
      int var9;
      if (var2 > 0) {
         var4 = false;
         if (UnsignedKt.uintCompare(var0, var1) >= 0) {
            var10000 = var1;
         } else {
            var5 = false;
            var8 = UInt.constructor-impl(var2);
            var9 = differenceModulo-WZ9TVnA(var1, var0, var8);
            var5 = false;
            var10000 = UInt.constructor-impl(var1 - var9);
         }
      } else {
         if (var2 >= 0) {
            throw (Throwable)(new IllegalArgumentException("Step is zero."));
         }

         var4 = false;
         if (UnsignedKt.uintCompare(var0, var1) <= 0) {
            var10000 = var1;
         } else {
            var9 = -var2;
            var5 = false;
            var8 = UInt.constructor-impl(var9);
            var9 = differenceModulo-WZ9TVnA(var0, var1, var8);
            var5 = false;
            var10000 = UInt.constructor-impl(var1 + var9);
         }
      }

      return var10000;
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final long getProgressionLastElement_7ftBX0g/* $FF was: getProgressionLastElement-7ftBX0g*/(long var0, long var2, long var4) {
      long var10000;
      long var17;
      boolean var8;
      boolean var10;
      long var15;
      if (var4 > 0L) {
         var8 = false;
         if (UnsignedKt.ulongCompare(var0, var2) >= 0) {
            var10000 = var2;
         } else {
            var10 = false;
            var15 = ULong.constructor-impl(var4);
            var17 = differenceModulo-sambcqE(var2, var0, var15);
            var10 = false;
            var10000 = ULong.constructor-impl(var2 - var17);
         }
      } else {
         if (var4 >= 0L) {
            throw (Throwable)(new IllegalArgumentException("Step is zero."));
         }

         var8 = false;
         if (UnsignedKt.ulongCompare(var0, var2) <= 0) {
            var10000 = var2;
         } else {
            var17 = -var4;
            var10 = false;
            var15 = ULong.constructor-impl(var17);
            var17 = differenceModulo-sambcqE(var0, var2, var15);
            var10 = false;
            var10000 = ULong.constructor-impl(var2 + var17);
         }
      }

      return var10000;
   }
}
