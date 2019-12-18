package kotlin.collections;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0012\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\bH\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001f\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000bH\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010!\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000eH\u0001ø\u0001\u0000¢\u0006\u0004\b\"\u0010#\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006$"},
   d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "sortArray-GBYM_sE", "([B)V", "sortArray--ajY-9A", "([I)V", "sortArray-QwZRm1k", "([J)V", "sortArray-rL5Bavg", "([S)V", "kotlin-stdlib"}
)
public final class UArraySortingKt {
   @ExperimentalUnsignedTypes
   private static final int partition_4UcCI2c/* $FF was: partition-4UcCI2c*/(byte[] var0, int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      byte var5 = UByteArray.get-impl(var0, (var1 + var2) / 2);

      while(var3 <= var4) {
         while(true) {
            byte var6 = UByteArray.get-impl(var0, var3);
            boolean var7 = false;
            boolean var9 = false;
            int var10 = var6 & 255;
            var9 = false;
            int var11 = var5 & 255;
            if (Intrinsics.compare(var10, var11) >= 0) {
               while(true) {
                  var6 = UByteArray.get-impl(var0, var4);
                  var7 = false;
                  var9 = false;
                  var10 = var6 & 255;
                  var9 = false;
                  var11 = var5 & 255;
                  if (Intrinsics.compare(var10, var11) <= 0) {
                     if (var3 <= var4) {
                        var6 = UByteArray.get-impl(var0, var3);
                        UByteArray.set-VurrAj0(var0, var3, UByteArray.get-impl(var0, var4));
                        UByteArray.set-VurrAj0(var0, var4, var6);
                        ++var3;
                        --var4;
                     }
                     break;
                  }

                  --var4;
               }
            } else {
               ++var3;
            }
         }
      }

      return var3;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort_4UcCI2c/* $FF was: quickSort-4UcCI2c*/(byte[] var0, int var1, int var2) {
      int var3 = partition-4UcCI2c(var0, var1, var2);
      if (var1 < var3 - 1) {
         quickSort-4UcCI2c(var0, var1, var3 - 1);
      }

      if (var3 < var2) {
         quickSort-4UcCI2c(var0, var3, var2);
      }

   }

   @ExperimentalUnsignedTypes
   private static final int partition_Aa5vz7o/* $FF was: partition-Aa5vz7o*/(short[] var0, int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      short var5 = UShortArray.get-impl(var0, (var1 + var2) / 2);

      while(var3 <= var4) {
         while(true) {
            short var6 = UShortArray.get-impl(var0, var3);
            boolean var7 = false;
            boolean var9 = false;
            int var10 = var6 & '\uffff';
            var9 = false;
            int var11 = var5 & '\uffff';
            if (Intrinsics.compare(var10, var11) >= 0) {
               while(true) {
                  var6 = UShortArray.get-impl(var0, var4);
                  var7 = false;
                  var9 = false;
                  var10 = var6 & '\uffff';
                  var9 = false;
                  var11 = var5 & '\uffff';
                  if (Intrinsics.compare(var10, var11) <= 0) {
                     if (var3 <= var4) {
                        var6 = UShortArray.get-impl(var0, var3);
                        UShortArray.set-01HTLdE(var0, var3, UShortArray.get-impl(var0, var4));
                        UShortArray.set-01HTLdE(var0, var4, var6);
                        ++var3;
                        --var4;
                     }
                     break;
                  }

                  --var4;
               }
            } else {
               ++var3;
            }
         }
      }

      return var3;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort_Aa5vz7o/* $FF was: quickSort-Aa5vz7o*/(short[] var0, int var1, int var2) {
      int var3 = partition-Aa5vz7o(var0, var1, var2);
      if (var1 < var3 - 1) {
         quickSort-Aa5vz7o(var0, var1, var3 - 1);
      }

      if (var3 < var2) {
         quickSort-Aa5vz7o(var0, var3, var2);
      }

   }

   @ExperimentalUnsignedTypes
   private static final int partition_oBK06Vg/* $FF was: partition-oBK06Vg*/(int[] var0, int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      int var5 = UIntArray.get-impl(var0, (var1 + var2) / 2);

      while(var3 <= var4) {
         while(true) {
            int var6 = UIntArray.get-impl(var0, var3);
            boolean var7 = false;
            if (UnsignedKt.uintCompare(var6, var5) >= 0) {
               while(true) {
                  var6 = UIntArray.get-impl(var0, var4);
                  var7 = false;
                  if (UnsignedKt.uintCompare(var6, var5) <= 0) {
                     if (var3 <= var4) {
                        var6 = UIntArray.get-impl(var0, var3);
                        UIntArray.set-VXSXFK8(var0, var3, UIntArray.get-impl(var0, var4));
                        UIntArray.set-VXSXFK8(var0, var4, var6);
                        ++var3;
                        --var4;
                     }
                     break;
                  }

                  --var4;
               }
            } else {
               ++var3;
            }
         }
      }

      return var3;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort_oBK06Vg/* $FF was: quickSort-oBK06Vg*/(int[] var0, int var1, int var2) {
      int var3 = partition-oBK06Vg(var0, var1, var2);
      if (var1 < var3 - 1) {
         quickSort-oBK06Vg(var0, var1, var3 - 1);
      }

      if (var3 < var2) {
         quickSort-oBK06Vg(var0, var3, var2);
      }

   }

   @ExperimentalUnsignedTypes
   private static final int partition__nroSd4/* $FF was: partition--nroSd4*/(long[] var0, int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      long var5 = ULongArray.get-impl(var0, (var1 + var2) / 2);

      while(var3 <= var4) {
         while(true) {
            long var7 = ULongArray.get-impl(var0, var3);
            boolean var9 = false;
            if (UnsignedKt.ulongCompare(var7, var5) >= 0) {
               while(true) {
                  var7 = ULongArray.get-impl(var0, var4);
                  var9 = false;
                  if (UnsignedKt.ulongCompare(var7, var5) <= 0) {
                     if (var3 <= var4) {
                        var7 = ULongArray.get-impl(var0, var3);
                        ULongArray.set-k8EXiF4(var0, var3, ULongArray.get-impl(var0, var4));
                        ULongArray.set-k8EXiF4(var0, var4, var7);
                        ++var3;
                        --var4;
                     }
                     break;
                  }

                  --var4;
               }
            } else {
               ++var3;
            }
         }
      }

      return var3;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort__nroSd4/* $FF was: quickSort--nroSd4*/(long[] var0, int var1, int var2) {
      int var3 = partition--nroSd4(var0, var1, var2);
      if (var1 < var3 - 1) {
         quickSort--nroSd4(var0, var1, var3 - 1);
      }

      if (var3 < var2) {
         quickSort--nroSd4(var0, var3, var2);
      }

   }

   @ExperimentalUnsignedTypes
   public static final void sortArray_GBYM_sE/* $FF was: sortArray-GBYM_sE*/(@NotNull byte[] var0) {
      quickSort-4UcCI2c(var0, 0, UByteArray.getSize-impl(var0) - 1);
   }

   @ExperimentalUnsignedTypes
   public static final void sortArray_rL5Bavg/* $FF was: sortArray-rL5Bavg*/(@NotNull short[] var0) {
      quickSort-Aa5vz7o(var0, 0, UShortArray.getSize-impl(var0) - 1);
   }

   @ExperimentalUnsignedTypes
   public static final void sortArray__ajY_9A/* $FF was: sortArray--ajY-9A*/(@NotNull int[] var0) {
      quickSort-oBK06Vg(var0, 0, UIntArray.getSize-impl(var0) - 1);
   }

   @ExperimentalUnsignedTypes
   public static final void sortArray_QwZRm1k/* $FF was: sortArray-QwZRm1k*/(@NotNull long[] var0) {
      quickSort--nroSd4(var0, 0, ULongArray.getSize-impl(var0) - 1);
   }
}
