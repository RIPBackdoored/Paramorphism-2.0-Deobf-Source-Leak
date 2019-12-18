package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.URandomKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\n\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\u001e\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u001e\u0010\u0000\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\u001e\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0004\u001a\u001e\u0010\u000e\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0007\u001a\u001e\u0010\u000e\u001a\u00020\b*\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\n\u001a\u001e\u0010\u000e\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\r\u001a&\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a&\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a$\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001aH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a&\u0010\u0014\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a$\u0010\u0014\u001a\u00020\b*\u00020\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001aH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010 \u001a&\u0010\u0014\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b!\u0010\"\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b'\u0010(\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\b\u0010)\u001a\u0004\u0018\u00010\u0005H\u0087\nø\u0001\u0000¢\u0006\u0002\b*\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010,\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b-\u0010.\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b0\u00101\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0005H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b2\u00103\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\b\u0010)\u001a\u0004\u0018\u00010\bH\u0087\nø\u0001\u0000¢\u0006\u0002\b4\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b5\u00106\u001a\u001f\u00107\u001a\u000208*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b:\u0010;\u001a\u001f\u00107\u001a\u000208*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b<\u0010=\u001a\u001f\u00107\u001a\u00020>*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b?\u0010@\u001a\u001f\u00107\u001a\u000208*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bA\u0010B\u001a\u0015\u0010C\u001a\u00020\u0005*\u00020%H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010D\u001a\u001c\u0010C\u001a\u00020\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000¢\u0006\u0002\u0010F\u001a\u0015\u0010C\u001a\u00020\b*\u00020/H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010G\u001a\u001c\u0010C\u001a\u00020\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000¢\u0006\u0002\u0010H\u001a\f\u0010I\u001a\u000208*\u000208H\u0007\u001a\f\u0010I\u001a\u00020>*\u00020>H\u0007\u001a\u0015\u0010J\u001a\u000208*\u0002082\u0006\u0010J\u001a\u00020KH\u0087\u0004\u001a\u0015\u0010J\u001a\u00020>*\u00020>2\u0006\u0010J\u001a\u00020LH\u0087\u0004\u001a\u001f\u0010M\u001a\u00020%*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bN\u0010O\u001a\u001f\u0010M\u001a\u00020%*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bP\u0010Q\u001a\u001f\u0010M\u001a\u00020/*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bR\u0010S\u001a\u001f\u0010M\u001a\u00020%*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bT\u0010U\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006V"},
   d2 = {"coerceAtLeast", "Lkotlin/UByte;", "minimumValue", "coerceAtLeast-Kr8caGY", "(BB)B", "Lkotlin/UInt;", "coerceAtLeast-J1ME1BU", "(II)I", "Lkotlin/ULong;", "coerceAtLeast-eb3DHEI", "(JJ)J", "Lkotlin/UShort;", "coerceAtLeast-5PvTz6A", "(SS)S", "coerceAtMost", "maximumValue", "coerceAtMost-Kr8caGY", "coerceAtMost-J1ME1BU", "coerceAtMost-eb3DHEI", "coerceAtMost-5PvTz6A", "coerceIn", "coerceIn-b33U2AM", "(BBB)B", "coerceIn-WZ9TVnA", "(III)I", "range", "Lkotlin/ranges/ClosedRange;", "coerceIn-wuiCnnA", "(ILkotlin/ranges/ClosedRange;)I", "coerceIn-sambcqE", "(JJJ)J", "coerceIn-JPwROB0", "(JLkotlin/ranges/ClosedRange;)J", "coerceIn-VKSA0NQ", "(SSS)S", "contains", "", "Lkotlin/ranges/UIntRange;", "value", "contains-68kG9v0", "(Lkotlin/ranges/UIntRange;B)Z", "element", "contains-biwQdVI", "contains-fz5IDCE", "(Lkotlin/ranges/UIntRange;J)Z", "contains-ZsK3CEQ", "(Lkotlin/ranges/UIntRange;S)Z", "Lkotlin/ranges/ULongRange;", "contains-ULb-yJY", "(Lkotlin/ranges/ULongRange;B)Z", "contains-Gab390E", "(Lkotlin/ranges/ULongRange;I)Z", "contains-GYNo2lE", "contains-uhHAxoY", "(Lkotlin/ranges/ULongRange;S)Z", "downTo", "Lkotlin/ranges/UIntProgression;", "to", "downTo-Kr8caGY", "(BB)Lkotlin/ranges/UIntProgression;", "downTo-J1ME1BU", "(II)Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ULongProgression;", "downTo-eb3DHEI", "(JJ)Lkotlin/ranges/ULongProgression;", "downTo-5PvTz6A", "(SS)Lkotlin/ranges/UIntProgression;", "random", "(Lkotlin/ranges/UIntRange;)I", "Lkotlin/random/Random;", "(Lkotlin/ranges/UIntRange;Lkotlin/random/Random;)I", "(Lkotlin/ranges/ULongRange;)J", "(Lkotlin/ranges/ULongRange;Lkotlin/random/Random;)J", "reversed", "step", "", "", "until", "until-Kr8caGY", "(BB)Lkotlin/ranges/UIntRange;", "until-J1ME1BU", "(II)Lkotlin/ranges/UIntRange;", "until-eb3DHEI", "(JJ)Lkotlin/ranges/ULongRange;", "until-5PvTz6A", "(SS)Lkotlin/ranges/UIntRange;", "kotlin-stdlib"},
   xs = "kotlin/ranges/URangesKt"
)
class URangesKt___URangesKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final int random(@NotNull UIntRange var0) {
      byte var1 = 0;
      return URangesKt.random(var0, (Random)Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final long random(@NotNull ULongRange var0) {
      byte var1 = 0;
      return URangesKt.random(var0, (Random)Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int random(@NotNull UIntRange var0, @NotNull Random var1) {
      try {
         return URandomKt.nextUInt(var1, var0);
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long random(@NotNull ULongRange var0, @NotNull Random var1) {
      try {
         return URandomKt.nextULong(var1, var0);
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final boolean contains_biwQdVI/* $FF was: contains-biwQdVI*/(@NotNull UIntRange var0, UInt var1) {
      byte var2 = 0;
      return var1 != null && var0.contains-WZ4Q5Ns(var1.unbox-impl());
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final boolean contains_GYNo2lE/* $FF was: contains-GYNo2lE*/(@NotNull ULongRange var0, ULong var1) {
      byte var2 = 0;
      return var1 != null && var0.contains-VKZWuLQ(var1.unbox-impl());
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final boolean contains_68kG9v0/* $FF was: contains-68kG9v0*/(@NotNull UIntRange var0, byte var1) {
      boolean var3 = false;
      int var5 = UInt.constructor-impl(var1 & 255);
      return var0.contains-WZ4Q5Ns(var5);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final boolean contains_ULb_yJY/* $FF was: contains-ULb-yJY*/(@NotNull ULongRange var0, byte var1) {
      boolean var3 = false;
      long var5 = ULong.constructor-impl((long)var1 & 255L);
      return var0.contains-VKZWuLQ(var5);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final boolean contains_Gab390E/* $FF was: contains-Gab390E*/(@NotNull ULongRange var0, int var1) {
      boolean var3 = false;
      long var5 = ULong.constructor-impl((long)var1 & 4294967295L);
      return var0.contains-VKZWuLQ(var5);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final boolean contains_fz5IDCE/* $FF was: contains-fz5IDCE*/(@NotNull UIntRange var0, long var1) {
      byte var5 = 32;
      boolean var6 = false;
      boolean var10000;
      if (ULong.constructor-impl(var1 >>> var5) == 0L) {
         boolean var11 = false;
         boolean var8 = false;
         int var10 = UInt.constructor-impl((int)var1);
         if (var0.contains-WZ4Q5Ns(var10)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final boolean contains_ZsK3CEQ/* $FF was: contains-ZsK3CEQ*/(@NotNull UIntRange var0, short var1) {
      boolean var3 = false;
      int var5 = UInt.constructor-impl(var1 & '\uffff');
      return var0.contains-WZ4Q5Ns(var5);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final boolean contains_uhHAxoY/* $FF was: contains-uhHAxoY*/(@NotNull ULongRange var0, short var1) {
      boolean var3 = false;
      long var5 = ULong.constructor-impl((long)var1 & 65535L);
      return var0.contains-VKZWuLQ(var5);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UIntProgression downTo_Kr8caGY/* $FF was: downTo-Kr8caGY*/(byte var0, byte var1) {
      UIntProgression$Companion var4 = UIntProgression.Companion;
      boolean var3 = false;
      int var5 = UInt.constructor-impl(var0 & 255);
      var3 = false;
      int var6 = UInt.constructor-impl(var1 & 255);
      return var4.fromClosedRange-Nkh28Cs(var5, var6, -1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UIntProgression downTo_J1ME1BU/* $FF was: downTo-J1ME1BU*/(int var0, int var1) {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(var0, var1, -1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final ULongProgression downTo_eb3DHEI/* $FF was: downTo-eb3DHEI*/(long var0, long var2) {
      return ULongProgression.Companion.fromClosedRange-7ftBX0g(var0, var2, -1L);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UIntProgression downTo_5PvTz6A/* $FF was: downTo-5PvTz6A*/(short var0, short var1) {
      UIntProgression$Companion var4 = UIntProgression.Companion;
      boolean var3 = false;
      int var5 = UInt.constructor-impl(var0 & '\uffff');
      var3 = false;
      int var6 = UInt.constructor-impl(var1 & '\uffff');
      return var4.fromClosedRange-Nkh28Cs(var5, var6, -1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UIntProgression reversed(@NotNull UIntProgression var0) {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final ULongProgression reversed(@NotNull ULongProgression var0) {
      return ULongProgression.Companion.fromClosedRange-7ftBX0g(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UIntProgression step(@NotNull UIntProgression var0, int var1) {
      RangesKt.checkStepIsPositive(var1 > 0, (Number)var1);
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(var0.getFirst(), var0.getLast(), var0.getStep() > 0 ? var1 : -var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final ULongProgression step(@NotNull ULongProgression var0, long var1) {
      RangesKt.checkStepIsPositive(var1 > 0L, (Number)var1);
      return ULongProgression.Companion.fromClosedRange-7ftBX0g(var0.getFirst(), var0.getLast(), var0.getStep() > 0L ? var1 : -var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UIntRange until_Kr8caGY/* $FF was: until-Kr8caGY*/(byte var0, byte var1) {
      byte var3 = 0;
      boolean var4 = false;
      boolean var6 = false;
      int var7 = var1 & 255;
      var6 = false;
      int var8 = var3 & 255;
      if (Intrinsics.compare(var7, var8) <= 0) {
         return UIntRange.Companion.getEMPTY();
      } else {
         boolean var9 = false;
         int var2 = UInt.constructor-impl(var0 & 255);
         byte var11 = 1;
         boolean var5 = false;
         boolean var13 = false;
         int var12 = UInt.constructor-impl(var1 & 255);
         var13 = false;
         int var10 = UInt.constructor-impl(var12 - var11);
         var4 = false;
         var4 = false;
         return new UIntRange(var2, var10, (DefaultConstructorMarker)null);
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UIntRange until_J1ME1BU/* $FF was: until-J1ME1BU*/(int var0, int var1) {
      byte var3 = 0;
      boolean var4 = false;
      if (UnsignedKt.uintCompare(var1, var3) <= 0) {
         return UIntRange.Companion.getEMPTY();
      } else {
         byte var7 = 1;
         boolean var5 = false;
         int var6 = UInt.constructor-impl(var1 - var7);
         var4 = false;
         var4 = false;
         return new UIntRange(var0, var6, (DefaultConstructorMarker)null);
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final ULongRange until_eb3DHEI/* $FF was: until-eb3DHEI*/(long var0, long var2) {
      long var6 = 0L;
      boolean var8 = false;
      if (UnsignedKt.ulongCompare(var2, var6) <= 0) {
         return ULongRange.Companion.getEMPTY();
      } else {
         byte var17 = 1;
         boolean var9 = false;
         boolean var13 = false;
         long var14 = ULong.constructor-impl((long)var17 & 4294967295L);
         boolean var16 = false;
         var6 = ULong.constructor-impl(var2 - var14);
         var8 = false;
         var8 = false;
         return new ULongRange(var0, var6, (DefaultConstructorMarker)null);
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UIntRange until_5PvTz6A/* $FF was: until-5PvTz6A*/(short var0, short var1) {
      byte var3 = 0;
      boolean var4 = false;
      boolean var6 = false;
      int var7 = var1 & '\uffff';
      var6 = false;
      int var8 = var3 & '\uffff';
      if (Intrinsics.compare(var7, var8) <= 0) {
         return UIntRange.Companion.getEMPTY();
      } else {
         boolean var9 = false;
         int var2 = UInt.constructor-impl(var0 & '\uffff');
         byte var11 = 1;
         boolean var5 = false;
         boolean var13 = false;
         int var12 = UInt.constructor-impl(var1 & '\uffff');
         var13 = false;
         int var10 = UInt.constructor-impl(var12 - var11);
         var4 = false;
         var4 = false;
         return new UIntRange(var2, var10, (DefaultConstructorMarker)null);
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int coerceAtLeast_J1ME1BU/* $FF was: coerceAtLeast-J1ME1BU*/(int var0, int var1) {
      boolean var3 = false;
      return UnsignedKt.uintCompare(var0, var1) < 0 ? var1 : var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long coerceAtLeast_eb3DHEI/* $FF was: coerceAtLeast-eb3DHEI*/(long var0, long var2) {
      boolean var6 = false;
      return UnsignedKt.ulongCompare(var0, var2) < 0 ? var2 : var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final byte coerceAtLeast_Kr8caGY/* $FF was: coerceAtLeast-Kr8caGY*/(byte var0, byte var1) {
      boolean var3 = false;
      boolean var5 = false;
      int var6 = var0 & 255;
      var5 = false;
      int var7 = var1 & 255;
      return Intrinsics.compare(var6, var7) < 0 ? var1 : var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final short coerceAtLeast_5PvTz6A/* $FF was: coerceAtLeast-5PvTz6A*/(short var0, short var1) {
      boolean var3 = false;
      boolean var5 = false;
      int var6 = var0 & '\uffff';
      var5 = false;
      int var7 = var1 & '\uffff';
      return Intrinsics.compare(var6, var7) < 0 ? var1 : var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int coerceAtMost_J1ME1BU/* $FF was: coerceAtMost-J1ME1BU*/(int var0, int var1) {
      boolean var3 = false;
      return UnsignedKt.uintCompare(var0, var1) > 0 ? var1 : var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long coerceAtMost_eb3DHEI/* $FF was: coerceAtMost-eb3DHEI*/(long var0, long var2) {
      boolean var6 = false;
      return UnsignedKt.ulongCompare(var0, var2) > 0 ? var2 : var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final byte coerceAtMost_Kr8caGY/* $FF was: coerceAtMost-Kr8caGY*/(byte var0, byte var1) {
      boolean var3 = false;
      boolean var5 = false;
      int var6 = var0 & 255;
      var5 = false;
      int var7 = var1 & 255;
      return Intrinsics.compare(var6, var7) > 0 ? var1 : var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final short coerceAtMost_5PvTz6A/* $FF was: coerceAtMost-5PvTz6A*/(short var0, short var1) {
      boolean var3 = false;
      boolean var5 = false;
      int var6 = var0 & '\uffff';
      var5 = false;
      int var7 = var1 & '\uffff';
      return Intrinsics.compare(var6, var7) > 0 ? var1 : var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int coerceIn_WZ9TVnA/* $FF was: coerceIn-WZ9TVnA*/(int var0, int var1, int var2) {
      boolean var4 = false;
      if (UnsignedKt.uintCompare(var1, var2) > 0) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UInt.toString-impl(var2) + " is less than minimum " + UInt.toString-impl(var1) + '.'));
      } else {
         var4 = false;
         if (UnsignedKt.uintCompare(var0, var1) < 0) {
            return var1;
         } else {
            var4 = false;
            return UnsignedKt.uintCompare(var0, var2) > 0 ? var2 : var0;
         }
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long coerceIn_sambcqE/* $FF was: coerceIn-sambcqE*/(long var0, long var2, long var4) {
      boolean var8 = false;
      if (UnsignedKt.ulongCompare(var2, var4) > 0) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ULong.toString-impl(var4) + " is less than minimum " + ULong.toString-impl(var2) + '.'));
      } else {
         var8 = false;
         if (UnsignedKt.ulongCompare(var0, var2) < 0) {
            return var2;
         } else {
            var8 = false;
            return UnsignedKt.ulongCompare(var0, var4) > 0 ? var4 : var0;
         }
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final byte coerceIn_b33U2AM/* $FF was: coerceIn-b33U2AM*/(byte var0, byte var1, byte var2) {
      boolean var4 = false;
      boolean var6 = false;
      int var7 = var1 & 255;
      var6 = false;
      int var8 = var2 & 255;
      if (Intrinsics.compare(var7, var8) > 0) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UByte.toString-impl(var2) + " is less than minimum " + UByte.toString-impl(var1) + '.'));
      } else {
         var4 = false;
         var6 = false;
         var7 = var0 & 255;
         var6 = false;
         var8 = var1 & 255;
         if (Intrinsics.compare(var7, var8) < 0) {
            return var1;
         } else {
            var4 = false;
            var6 = false;
            var7 = var0 & 255;
            var6 = false;
            var8 = var2 & 255;
            return Intrinsics.compare(var7, var8) > 0 ? var2 : var0;
         }
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final short coerceIn_VKSA0NQ/* $FF was: coerceIn-VKSA0NQ*/(short var0, short var1, short var2) {
      boolean var4 = false;
      boolean var6 = false;
      int var7 = var1 & '\uffff';
      var6 = false;
      int var8 = var2 & '\uffff';
      if (Intrinsics.compare(var7, var8) > 0) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UShort.toString-impl(var2) + " is less than minimum " + UShort.toString-impl(var1) + '.'));
      } else {
         var4 = false;
         var6 = false;
         var7 = var0 & '\uffff';
         var6 = false;
         var8 = var1 & '\uffff';
         if (Intrinsics.compare(var7, var8) < 0) {
            return var1;
         } else {
            var4 = false;
            var6 = false;
            var7 = var0 & '\uffff';
            var6 = false;
            var8 = var2 & '\uffff';
            return Intrinsics.compare(var7, var8) > 0 ? var2 : var0;
         }
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int coerceIn_wuiCnnA/* $FF was: coerceIn-wuiCnnA*/(int var0, @NotNull ClosedRange var1) {
      if (var1 instanceof ClosedFloatingPointRange) {
         return ((UInt)RangesKt.coerceIn(UInt.box-impl(var0), (ClosedFloatingPointRange)var1)).unbox-impl();
      } else if (var1.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: " + var1 + '.'));
      } else {
         int var3 = ((UInt)var1.getStart()).unbox-impl();
         boolean var4 = false;
         int var10000;
         if (UnsignedKt.uintCompare(var0, var3) < 0) {
            var10000 = ((UInt)var1.getStart()).unbox-impl();
         } else {
            var3 = ((UInt)var1.getEndInclusive()).unbox-impl();
            var4 = false;
            var10000 = UnsignedKt.uintCompare(var0, var3) > 0 ? ((UInt)var1.getEndInclusive()).unbox-impl() : var0;
         }

         return var10000;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long coerceIn_JPwROB0/* $FF was: coerceIn-JPwROB0*/(long var0, @NotNull ClosedRange var2) {
      if (var2 instanceof ClosedFloatingPointRange) {
         return ((ULong)RangesKt.coerceIn(ULong.box-impl(var0), (ClosedFloatingPointRange)var2)).unbox-impl();
      } else if (var2.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: " + var2 + '.'));
      } else {
         long var5 = ((ULong)var2.getStart()).unbox-impl();
         boolean var7 = false;
         long var10000;
         if (UnsignedKt.ulongCompare(var0, var5) < 0) {
            var10000 = ((ULong)var2.getStart()).unbox-impl();
         } else {
            var5 = ((ULong)var2.getEndInclusive()).unbox-impl();
            var7 = false;
            var10000 = UnsignedKt.ulongCompare(var0, var5) > 0 ? ((ULong)var2.getEndInclusive()).unbox-impl() : var0;
         }

         return var10000;
      }
   }

   public URangesKt___URangesKt() {
      super();
   }
}
