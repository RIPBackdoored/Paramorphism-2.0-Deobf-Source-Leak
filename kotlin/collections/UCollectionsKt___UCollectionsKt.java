package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.JvmName;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\u0005\u001a\u001a\u0010\f\u001a\u00020\r*\b\u0012\u0004\u0012\u00020\u00030\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001a\u0010\u0010\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u001a\u0010\u0013\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u001a\u0010\u0016\u001a\u00020\u0017*\b\u0012\u0004\u0012\u00020\n0\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"},
   d2 = {"sum", "Lkotlin/UInt;", "", "Lkotlin/UByte;", "sumOfUByte", "(Ljava/lang/Iterable;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Ljava/lang/Iterable;)J", "Lkotlin/UShort;", "sumOfUShort", "toUByteArray", "Lkotlin/UByteArray;", "", "(Ljava/util/Collection;)[B", "toUIntArray", "Lkotlin/UIntArray;", "(Ljava/util/Collection;)[I", "toULongArray", "Lkotlin/ULongArray;", "(Ljava/util/Collection;)[J", "toUShortArray", "Lkotlin/UShortArray;", "(Ljava/util/Collection;)[S", "kotlin-stdlib"},
   xs = "kotlin/collections/UCollectionsKt"
)
class UCollectionsKt___UCollectionsKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] toUByteArray(@NotNull Collection var0) {
      byte[] var1 = UByteArray.constructor-impl(var0.size());
      int var2 = 0;
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         byte var3 = ((UByte)var4.next()).unbox-impl();
         UByteArray.set-VurrAj0(var1, var2++, var3);
      }

      return var1;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final int[] toUIntArray(@NotNull Collection var0) {
      int[] var1 = UIntArray.constructor-impl(var0.size());
      int var2 = 0;
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         int var3 = ((UInt)var4.next()).unbox-impl();
         UIntArray.set-VXSXFK8(var1, var2++, var3);
      }

      return var1;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final long[] toULongArray(@NotNull Collection var0) {
      long[] var1 = ULongArray.constructor-impl(var0.size());
      int var2 = 0;
      Iterator var5 = var0.iterator();

      while(var5.hasNext()) {
         long var3 = ((ULong)var5.next()).unbox-impl();
         ULongArray.set-k8EXiF4(var1, var2++, var3);
      }

      return var1;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final short[] toUShortArray(@NotNull Collection var0) {
      short[] var1 = UShortArray.constructor-impl(var0.size());
      int var2 = 0;
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         short var3 = ((UShort)var4.next()).unbox-impl();
         UShortArray.set-01HTLdE(var1, var2++, var3);
      }

      return var1;
   }

   @JvmName(
      name = "sumOfUInt"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int sumOfUInt(@NotNull Iterable var0) {
      int var1 = 0;

      int var2;
      for(Iterator var3 = var0.iterator(); var3.hasNext(); var1 = UInt.constructor-impl(var1 + var2)) {
         var2 = ((UInt)var3.next()).unbox-impl();
         boolean var5 = false;
      }

      return var1;
   }

   @JvmName(
      name = "sumOfULong"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long sumOfULong(@NotNull Iterable var0) {
      long var1 = 0L;

      long var3;
      for(Iterator var5 = var0.iterator(); var5.hasNext(); var1 = ULong.constructor-impl(var1 + var3)) {
         var3 = ((ULong)var5.next()).unbox-impl();
         boolean var8 = false;
      }

      return var1;
   }

   @JvmName(
      name = "sumOfUByte"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int sumOfUByte(@NotNull Iterable var0) {
      int var1 = 0;

      int var7;
      for(Iterator var3 = var0.iterator(); var3.hasNext(); var1 = UInt.constructor-impl(var1 + var7)) {
         byte var2 = ((UByte)var3.next()).unbox-impl();
         boolean var5 = false;
         boolean var8 = false;
         var7 = UInt.constructor-impl(var2 & 255);
         var8 = false;
      }

      return var1;
   }

   @JvmName(
      name = "sumOfUShort"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int sumOfUShort(@NotNull Iterable var0) {
      int var1 = 0;

      int var7;
      for(Iterator var3 = var0.iterator(); var3.hasNext(); var1 = UInt.constructor-impl(var1 + var7)) {
         short var2 = ((UShort)var3.next()).unbox-impl();
         boolean var5 = false;
         boolean var8 = false;
         var7 = UInt.constructor-impl(var2 & '\uffff');
         var8 = false;
      }

      return var1;
   }

   public UCollectionsKt___UCollectionsKt() {
      super();
   }
}
