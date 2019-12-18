package kotlin.collections.unsigned;

import java.util.List;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000F\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010\u0002\n\u0002\b\t\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u0007H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\n0\u0001*\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0001*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00022\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00062\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a\u001f\u0010\u001f\u001a\u00020\u0002*\u00020\u00032\u0006\u0010 \u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b!\u0010\"\u001a\u001f\u0010\u001f\u001a\u00020\u0006*\u00020\u00072\u0006\u0010 \u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b#\u0010$\u001a\u001f\u0010\u001f\u001a\u00020\n*\u00020\u000b2\u0006\u0010 \u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b%\u0010&\u001a\u001f\u0010\u001f\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010 \u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b'\u0010(\u001a2\u0010)\u001a\u00020**\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00022\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b+\u0010,\u001a2\u0010)\u001a\u00020**\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00062\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b-\u0010.\u001a2\u0010)\u001a\u00020**\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b/\u00100\u001a2\u0010)\u001a\u00020**\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b1\u00102\u0082\u0002\u0004\n\u0002\b\u0019¨\u00063"},
   d2 = {"asList", "", "Lkotlin/UByte;", "Lkotlin/UByteArray;", "asList-GBYM_sE", "([B)Ljava/util/List;", "Lkotlin/UInt;", "Lkotlin/UIntArray;", "asList--ajY-9A", "([I)Ljava/util/List;", "Lkotlin/ULong;", "Lkotlin/ULongArray;", "asList-QwZRm1k", "([J)Ljava/util/List;", "Lkotlin/UShort;", "Lkotlin/UShortArray;", "asList-rL5Bavg", "([S)Ljava/util/List;", "binarySearch", "", "element", "fromIndex", "toIndex", "binarySearch-WpHrYlw", "([BBII)I", "binarySearch-2fe2U9s", "([IIII)I", "binarySearch-K6DWlUc", "([JJII)I", "binarySearch-EtDCXyQ", "([SSII)I", "elementAt", "index", "elementAt-PpDY95g", "([BI)B", "elementAt-qFRl0hI", "([II)I", "elementAt-r7IrZao", "([JI)J", "elementAt-nggk6HY", "([SI)S", "fill", "", "fill-WpHrYlw", "([BBII)V", "fill-2fe2U9s", "([IIII)V", "fill-K6DWlUc", "([JJII)V", "fill-EtDCXyQ", "([SSII)V", "kotlin-stdlib"},
   xs = "kotlin/collections/unsigned/UArraysKt",
   pn = "kotlin.collections"
)
class UArraysKt___UArraysJvmKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final int elementAt_qFRl0hI/* $FF was: elementAt-qFRl0hI*/(@NotNull int[] var0, int var1) {
      byte var2 = 0;
      return UIntArray.get-impl(var0, var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final long elementAt_r7IrZao/* $FF was: elementAt-r7IrZao*/(@NotNull long[] var0, int var1) {
      byte var2 = 0;
      return ULongArray.get-impl(var0, var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final byte elementAt_PpDY95g/* $FF was: elementAt-PpDY95g*/(@NotNull byte[] var0, int var1) {
      byte var2 = 0;
      return UByteArray.get-impl(var0, var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final short elementAt_nggk6HY/* $FF was: elementAt-nggk6HY*/(@NotNull short[] var0, int var1) {
      byte var2 = 0;
      return UShortArray.get-impl(var0, var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final List asList__ajY_9A/* $FF was: asList--ajY-9A*/(@NotNull int[] var0) {
      return (List)(new UArraysKt___UArraysJvmKt$asList$1(var0));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final List asList_QwZRm1k/* $FF was: asList-QwZRm1k*/(@NotNull long[] var0) {
      return (List)(new UArraysKt___UArraysJvmKt$asList$2(var0));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final List asList_GBYM_sE/* $FF was: asList-GBYM_sE*/(@NotNull byte[] var0) {
      return (List)(new UArraysKt___UArraysJvmKt$asList$3(var0));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final List asList_rL5Bavg/* $FF was: asList-rL5Bavg*/(@NotNull short[] var0) {
      return (List)(new UArraysKt___UArraysJvmKt$asList$4(var0));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int binarySearch_2fe2U9s/* $FF was: binarySearch-2fe2U9s*/(@NotNull int[] var0, int var1, int var2, int var3) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var2, var3, UIntArray.getSize-impl(var0));
      boolean var6 = false;
      int var4 = var1;
      int var5 = var2;
      int var10 = var3 - 1;

      while(var5 <= var10) {
         int var7 = var5 + var10 >>> 1;
         int var8 = var0[var7];
         int var9 = UnsignedKt.uintCompare(var8, var4);
         if (var9 < 0) {
            var5 = var7 + 1;
         } else {
            if (var9 <= 0) {
               return var7;
            }

            var10 = var7 - 1;
         }
      }

      return -(var5 + 1);
   }

   public static int binarySearch_2fe2U9s$default/* $FF was: binarySearch-2fe2U9s$default*/(int[] var0, int var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UIntArray.getSize-impl(var0);
      }

      return UArraysKt.binarySearch-2fe2U9s(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int binarySearch_K6DWlUc/* $FF was: binarySearch-K6DWlUc*/(@NotNull long[] var0, long var1, int var3, int var4) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var3, var4, ULongArray.getSize-impl(var0));
      boolean var9 = false;
      long var5 = var1;
      int var7 = var3;
      int var8 = var4 - 1;

      while(var7 <= var8) {
         int var13 = var7 + var8 >>> 1;
         long var10 = var0[var13];
         int var12 = UnsignedKt.ulongCompare(var10, var5);
         if (var12 < 0) {
            var7 = var13 + 1;
         } else {
            if (var12 <= 0) {
               return var13;
            }

            var8 = var13 - 1;
         }
      }

      return -(var7 + 1);
   }

   public static int binarySearch_K6DWlUc$default/* $FF was: binarySearch-K6DWlUc$default*/(long[] var0, long var1, int var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var3 = 0;
      }

      if ((var5 & 4) != 0) {
         var4 = ULongArray.getSize-impl(var0);
      }

      return UArraysKt.binarySearch-K6DWlUc(var0, var1, var3, var4);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int binarySearch_WpHrYlw/* $FF was: binarySearch-WpHrYlw*/(@NotNull byte[] var0, byte var1, int var2, int var3) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var2, var3, UByteArray.getSize-impl(var0));
      boolean var6 = false;
      int var4 = var1 & 255;
      int var5 = var2;
      int var10 = var3 - 1;

      while(var5 <= var10) {
         int var7 = var5 + var10 >>> 1;
         byte var8 = var0[var7];
         int var9 = UnsignedKt.uintCompare(var8, var4);
         if (var9 < 0) {
            var5 = var7 + 1;
         } else {
            if (var9 <= 0) {
               return var7;
            }

            var10 = var7 - 1;
         }
      }

      return -(var5 + 1);
   }

   public static int binarySearch_WpHrYlw$default/* $FF was: binarySearch-WpHrYlw$default*/(byte[] var0, byte var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UByteArray.getSize-impl(var0);
      }

      return UArraysKt.binarySearch-WpHrYlw(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int binarySearch_EtDCXyQ/* $FF was: binarySearch-EtDCXyQ*/(@NotNull short[] var0, short var1, int var2, int var3) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var2, var3, UShortArray.getSize-impl(var0));
      boolean var6 = false;
      int var4 = var1 & '\uffff';
      int var5 = var2;
      int var10 = var3 - 1;

      while(var5 <= var10) {
         int var7 = var5 + var10 >>> 1;
         short var8 = var0[var7];
         int var9 = UnsignedKt.uintCompare(var8, var4);
         if (var9 < 0) {
            var5 = var7 + 1;
         } else {
            if (var9 <= 0) {
               return var7;
            }

            var10 = var7 - 1;
         }
      }

      return -(var5 + 1);
   }

   public static int binarySearch_EtDCXyQ$default/* $FF was: binarySearch-EtDCXyQ$default*/(short[] var0, short var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UShortArray.getSize-impl(var0);
      }

      return UArraysKt.binarySearch-EtDCXyQ(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final void fill_2fe2U9s/* $FF was: fill-2fe2U9s*/(@NotNull int[] var0, int var1, int var2, int var3) {
      boolean var5 = false;
      ArraysKt.fill(var0, var1, var2, var3);
   }

   public static void fill_2fe2U9s$default/* $FF was: fill-2fe2U9s$default*/(int[] var0, int var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UIntArray.getSize-impl(var0);
      }

      UArraysKt.fill-2fe2U9s(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final void fill_K6DWlUc/* $FF was: fill-K6DWlUc*/(@NotNull long[] var0, long var1, int var3, int var4) {
      boolean var7 = false;
      ArraysKt.fill(var0, var1, var3, var4);
   }

   public static void fill_K6DWlUc$default/* $FF was: fill-K6DWlUc$default*/(long[] var0, long var1, int var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var3 = 0;
      }

      if ((var5 & 4) != 0) {
         var4 = ULongArray.getSize-impl(var0);
      }

      UArraysKt.fill-K6DWlUc(var0, var1, var3, var4);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final void fill_WpHrYlw/* $FF was: fill-WpHrYlw*/(@NotNull byte[] var0, byte var1, int var2, int var3) {
      boolean var5 = false;
      ArraysKt.fill(var0, var1, var2, var3);
   }

   public static void fill_WpHrYlw$default/* $FF was: fill-WpHrYlw$default*/(byte[] var0, byte var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UByteArray.getSize-impl(var0);
      }

      UArraysKt.fill-WpHrYlw(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final void fill_EtDCXyQ/* $FF was: fill-EtDCXyQ*/(@NotNull short[] var0, short var1, int var2, int var3) {
      boolean var5 = false;
      ArraysKt.fill(var0, var1, var2, var3);
   }

   public static void fill_EtDCXyQ$default/* $FF was: fill-EtDCXyQ$default*/(short[] var0, short var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UShortArray.getSize-impl(var0);
      }

      UArraysKt.fill-EtDCXyQ(var0, var1, var2, var3);
   }

   public UArraysKt___UArraysJvmKt() {
      super();
   }
}
