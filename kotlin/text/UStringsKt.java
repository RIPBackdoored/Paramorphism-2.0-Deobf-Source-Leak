package kotlin.text;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.jvm.JvmName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\f\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001a\u0014\u0010\u0010\u001a\u00020\u0002*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0010\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0014\u001a\u00020\u0007*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u001c\u0010\u0014\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a\u0011\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0018\u001a\u00020\n*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\u001c\u0010\u0018\u001a\u00020\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001a\u001a\u0011\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u001c\u001a\u00020\r*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a\u001c\u0010\u001c\u001a\u00020\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a\u0011\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "},
   d2 = {"toString", "", "Lkotlin/UByte;", "radix", "", "toString-LxnNnR4", "(BI)Ljava/lang/String;", "Lkotlin/UInt;", "toString-V7xB4Y4", "(II)Ljava/lang/String;", "Lkotlin/ULong;", "toString-JSWoG40", "(JI)Ljava/lang/String;", "Lkotlin/UShort;", "toString-olVBNx4", "(SI)Ljava/lang/String;", "toUByte", "(Ljava/lang/String;)B", "(Ljava/lang/String;I)B", "toUByteOrNull", "toUInt", "(Ljava/lang/String;)I", "(Ljava/lang/String;I)I", "toUIntOrNull", "toULong", "(Ljava/lang/String;)J", "(Ljava/lang/String;I)J", "toULongOrNull", "toUShort", "(Ljava/lang/String;)S", "(Ljava/lang/String;I)S", "toUShortOrNull", "kotlin-stdlib"}
)
@JvmName(
   name = "UStringsKt"
)
public final class UStringsKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String toString_LxnNnR4/* $FF was: toString-LxnNnR4*/(byte var0, int var1) {
      boolean var3 = false;
      int var2 = var0 & 255;
      var3 = false;
      return Integer.toString(var2, CharsKt.checkRadix(var1));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String toString_olVBNx4/* $FF was: toString-olVBNx4*/(short var0, int var1) {
      boolean var3 = false;
      int var2 = var0 & '\uffff';
      var3 = false;
      return Integer.toString(var2, CharsKt.checkRadix(var1));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String toString_V7xB4Y4/* $FF was: toString-V7xB4Y4*/(int var0, int var1) {
      boolean var3 = false;
      long var2 = (long)var0 & 4294967295L;
      boolean var4 = false;
      return Long.toString(var2, CharsKt.checkRadix(var1));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String toString_JSWoG40/* $FF was: toString-JSWoG40*/(long var0, int var2) {
      boolean var5 = false;
      return UnsignedKt.ulongToString(var0, CharsKt.checkRadix(var2));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final byte toUByte(@NotNull String var0) {
      UByte var10000 = toUByteOrNull(var0);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final byte toUByte(@NotNull String var0, int var1) {
      UByte var10000 = toUByteOrNull(var0, var1);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final short toUShort(@NotNull String var0) {
      UShort var10000 = toUShortOrNull(var0);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final short toUShort(@NotNull String var0, int var1) {
      UShort var10000 = toUShortOrNull(var0, var1);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int toUInt(@NotNull String var0) {
      UInt var10000 = toUIntOrNull(var0);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int toUInt(@NotNull String var0, int var1) {
      UInt var10000 = toUIntOrNull(var0, var1);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long toULong(@NotNull String var0) {
      ULong var10000 = toULongOrNull(var0);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long toULong(@NotNull String var0, int var1) {
      ULong var10000 = toULongOrNull(var0, var1);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UByte toUByteOrNull(@NotNull String var0) {
      return toUByteOrNull(var0, 10);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UByte toUByteOrNull(@NotNull String var0, int var1) {
      UInt var10000 = toUIntOrNull(var0, var1);
      if (var10000 != null) {
         int var2 = var10000.unbox-impl();
         byte var4 = -1;
         boolean var5 = false;
         boolean var8 = false;
         int var7 = UInt.constructor-impl(var4 & 255);
         var8 = false;
         if (UnsignedKt.uintCompare(var2, var7) > 0) {
            return null;
         } else {
            boolean var9 = false;
            boolean var6 = false;
            return UByte.box-impl(UByte.constructor-impl((byte)var2));
         }
      } else {
         return null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UShort toUShortOrNull(@NotNull String var0) {
      return toUShortOrNull(var0, 10);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UShort toUShortOrNull(@NotNull String var0, int var1) {
      UInt var10000 = toUIntOrNull(var0, var1);
      if (var10000 != null) {
         int var2 = var10000.unbox-impl();
         byte var4 = -1;
         boolean var5 = false;
         boolean var8 = false;
         int var7 = UInt.constructor-impl(var4 & '\uffff');
         var8 = false;
         if (UnsignedKt.uintCompare(var2, var7) > 0) {
            return null;
         } else {
            boolean var9 = false;
            boolean var6 = false;
            return UShort.box-impl(UShort.constructor-impl((short)var2));
         }
      } else {
         return null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UInt toUIntOrNull(@NotNull String var0) {
      return toUIntOrNull(var0, 10);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UInt toUIntOrNull(@NotNull String var0, int var1) {
      CharsKt.checkRadix(var1);
      int var2 = var0.length();
      if (var2 == 0) {
         return null;
      } else {
         byte var3 = -1;
         boolean var4 = false;
         char var5 = var0.charAt(0);
         byte var16;
         if (var5 < '0') {
            if (var2 == 1 || var5 != '+') {
               return null;
            }

            var16 = 1;
         } else {
            var16 = 0;
         }

         boolean var8 = false;
         int var6 = UInt.constructor-impl(var1);
         boolean var9 = false;
         int var7 = UnsignedKt.uintDivide-J1ME1BU(var3, var6);
         int var17 = 0;
         int var18 = var16;

         for(int var10 = var2; var18 < var10; ++var18) {
            int var11 = CharsKt.digitOf(var0.charAt(var18), var1);
            if (var11 < 0) {
               return null;
            }

            boolean var13 = false;
            if (UnsignedKt.uintCompare(var17, var7) > 0) {
               return null;
            }

            var13 = false;
            var17 = UInt.constructor-impl(var17 * var6);
            int var12 = var17;
            boolean var15 = false;
            int var14 = UInt.constructor-impl(var11);
            var15 = false;
            var17 = UInt.constructor-impl(var17 + var14);
            boolean var19 = false;
            if (UnsignedKt.uintCompare(var17, var12) < 0) {
               return null;
            }
         }

         return UInt.box-impl(var17);
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final ULong toULongOrNull(@NotNull String var0) {
      return toULongOrNull(var0, 10);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final ULong toULongOrNull(@NotNull String var0, int var1) {
      CharsKt.checkRadix(var1);
      int var2 = var0.length();
      if (var2 == 0) {
         return null;
      } else {
         long var3 = -1L;
         boolean var5 = false;
         char var6 = var0.charAt(0);
         byte var28;
         if (var6 < '0') {
            if (var2 == 1 || var6 != '+') {
               return null;
            }

            var28 = 1;
         } else {
            var28 = 0;
         }

         boolean var9 = false;
         int var7 = UInt.constructor-impl(var1);
         boolean var12 = false;
         boolean var16 = false;
         long var17 = ULong.constructor-impl((long)var7 & 4294967295L);
         boolean var19 = false;
         long var8 = UnsignedKt.ulongDivide-eb3DHEI(var3, var17);
         long var10 = 0L;
         int var29 = var28;

         for(int var13 = var2; var29 < var13; ++var29) {
            int var14 = CharsKt.digitOf(var0.charAt(var29), var1);
            if (var14 < 0) {
               return null;
            }

            boolean var30 = false;
            if (UnsignedKt.ulongCompare(var10, var8) > 0) {
               return null;
            }

            var30 = false;
            boolean var21 = false;
            long var22 = ULong.constructor-impl((long)var7 & 4294967295L);
            boolean var24 = false;
            var10 = ULong.constructor-impl(var10 * var22);
            long var15 = var10;
            boolean var20 = false;
            int var31 = UInt.constructor-impl(var14);
            var20 = false;
            var24 = false;
            long var25 = ULong.constructor-impl((long)var31 & 4294967295L);
            boolean var27 = false;
            var10 = ULong.constructor-impl(var10 + var25);
            var19 = false;
            if (UnsignedKt.ulongCompare(var10, var15) < 0) {
               return null;
            }
         }

         return ULong.box-impl(var10);
      }
   }
}
