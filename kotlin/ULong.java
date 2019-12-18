package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.ULongRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 m2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001mB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b \u0010\u000bJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b!\u0010\"J\u0013\u0010#\u001a\u00020$2\b\u0010\t\u001a\u0004\u0018\u00010%HÖ\u0003J\t\u0010&\u001a\u00020\rHÖ\u0001J\u0013\u0010'\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b(\u0010\u0005J\u0013\u0010)\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u0005J\u001b\u0010+\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b,\u0010\u001dJ\u001b\u0010+\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b-\u0010\u001fJ\u001b\u0010+\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b.\u0010\u000bJ\u001b\u0010+\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b/\u0010\"J\u001b\u00100\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b1\u0010\u000bJ\u001b\u00102\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u001dJ\u001b\u00102\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u001fJ\u001b\u00102\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b5\u0010\u000bJ\u001b\u00102\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b6\u0010\"J\u001b\u00107\u001a\u0002082\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b9\u0010:J\u001b\u0010;\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b<\u0010\u001dJ\u001b\u0010;\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b=\u0010\u001fJ\u001b\u0010;\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b>\u0010\u000bJ\u001b\u0010;\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b?\u0010\"J\u001b\u0010@\u001a\u00020\u00002\u0006\u0010A\u001a\u00020\rH\u0087\fø\u0001\u0000¢\u0006\u0004\bB\u0010\u001fJ\u001b\u0010C\u001a\u00020\u00002\u0006\u0010A\u001a\u00020\rH\u0087\fø\u0001\u0000¢\u0006\u0004\bD\u0010\u001fJ\u001b\u0010E\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\bF\u0010\u001dJ\u001b\u0010E\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bG\u0010\u001fJ\u001b\u0010E\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bH\u0010\u000bJ\u001b\u0010E\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bI\u0010\"J\u0010\u0010J\u001a\u00020KH\u0087\b¢\u0006\u0004\bL\u0010MJ\u0010\u0010N\u001a\u00020OH\u0087\b¢\u0006\u0004\bP\u0010QJ\u0010\u0010R\u001a\u00020SH\u0087\b¢\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020\rH\u0087\b¢\u0006\u0004\bW\u0010XJ\u0010\u0010Y\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bZ\u0010\u0005J\u0010\u0010[\u001a\u00020\\H\u0087\b¢\u0006\u0004\b]\u0010^J\u000f\u0010_\u001a\u00020`H\u0016¢\u0006\u0004\ba\u0010bJ\u0013\u0010c\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\bd\u0010MJ\u0013\u0010e\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\bf\u0010XJ\u0013\u0010g\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\bh\u0010\u0005J\u0013\u0010i\u001a\u00020\u0016H\u0087\bø\u0001\u0000¢\u0006\u0004\bj\u0010^J\u001b\u0010k\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\bl\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006n"},
   d2 = {"Lkotlin/ULong;", "", "data", "", "constructor-impl", "(J)J", "data$annotations", "()V", "and", "other", "and-VKZWuLQ", "(JJ)J", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(JB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(JI)I", "compareTo-VKZWuLQ", "(JJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(JS)I", "dec", "dec-impl", "div", "div-7apg3OU", "(JB)J", "div-WZ4Q5Ns", "(JI)J", "div-VKZWuLQ", "div-xj2QHRw", "(JS)J", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-VKZWuLQ", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/ULongRange;", "rangeTo-VKZWuLQ", "(JJ)Lkotlin/ranges/ULongRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-impl", "shr", "shr-impl", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(J)B", "toDouble", "", "toDouble-impl", "(J)D", "toFloat", "", "toFloat-impl", "(J)F", "toInt", "toInt-impl", "(J)I", "toLong", "toLong-impl", "toShort", "", "toShort-impl", "(J)S", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-VKZWuLQ", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
public final class ULong implements Comparable {
   private final long data;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = -1L;
   public static final int SIZE_BYTES = 8;
   public static final int SIZE_BITS = 64;
   public static final ULong$Companion Companion = new ULong$Companion((DefaultConstructorMarker)null);

   @InlineOnly
   private int compareTo_VKZWuLQ/* $FF was: compareTo-VKZWuLQ*/(long var1) {
      return compareTo-VKZWuLQ(this.data, var1);
   }

   public int compareTo(Object var1) {
      return this.compareTo-VKZWuLQ(((ULong)var1).unbox-impl());
   }

   @NotNull
   public String toString() {
      return toString-impl(this.data);
   }

   /** @deprecated */
   @PublishedApi
   public static void data$annotations() {
   }

   @PublishedApi
   private ULong(long var1) {
      super();
      this.data = var1;
   }

   @InlineOnly
   private static final int compareTo_7apg3OU/* $FF was: compareTo-7apg3OU*/(long var0, byte var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 255L);
      boolean var8 = false;
      return UnsignedKt.ulongCompare(var0, var6);
   }

   @InlineOnly
   private static final int compareTo_xj2QHRw/* $FF was: compareTo-xj2QHRw*/(long var0, short var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 65535L);
      boolean var8 = false;
      return UnsignedKt.ulongCompare(var0, var6);
   }

   @InlineOnly
   private static final int compareTo_WZ4Q5Ns/* $FF was: compareTo-WZ4Q5Ns*/(long var0, int var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 4294967295L);
      boolean var8 = false;
      return UnsignedKt.ulongCompare(var0, var6);
   }

   @InlineOnly
   private static int compareTo_VKZWuLQ/* $FF was: compareTo-VKZWuLQ*/(long var0, long var2) {
      byte var4 = 0;
      return UnsignedKt.ulongCompare(var0, var2);
   }

   @InlineOnly
   private static final long plus_7apg3OU/* $FF was: plus-7apg3OU*/(long var0, byte var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 255L);
      boolean var8 = false;
      return constructor-impl(var0 + var6);
   }

   @InlineOnly
   private static final long plus_xj2QHRw/* $FF was: plus-xj2QHRw*/(long var0, short var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 65535L);
      boolean var8 = false;
      return constructor-impl(var0 + var6);
   }

   @InlineOnly
   private static final long plus_WZ4Q5Ns/* $FF was: plus-WZ4Q5Ns*/(long var0, int var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 4294967295L);
      boolean var8 = false;
      return constructor-impl(var0 + var6);
   }

   @InlineOnly
   private static final long plus_VKZWuLQ/* $FF was: plus-VKZWuLQ*/(long var0, long var2) {
      byte var4 = 0;
      return constructor-impl(var0 + var2);
   }

   @InlineOnly
   private static final long minus_7apg3OU/* $FF was: minus-7apg3OU*/(long var0, byte var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 255L);
      boolean var8 = false;
      return constructor-impl(var0 - var6);
   }

   @InlineOnly
   private static final long minus_xj2QHRw/* $FF was: minus-xj2QHRw*/(long var0, short var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 65535L);
      boolean var8 = false;
      return constructor-impl(var0 - var6);
   }

   @InlineOnly
   private static final long minus_WZ4Q5Ns/* $FF was: minus-WZ4Q5Ns*/(long var0, int var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 4294967295L);
      boolean var8 = false;
      return constructor-impl(var0 - var6);
   }

   @InlineOnly
   private static final long minus_VKZWuLQ/* $FF was: minus-VKZWuLQ*/(long var0, long var2) {
      byte var4 = 0;
      return constructor-impl(var0 - var2);
   }

   @InlineOnly
   private static final long times_7apg3OU/* $FF was: times-7apg3OU*/(long var0, byte var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 255L);
      boolean var8 = false;
      return constructor-impl(var0 * var6);
   }

   @InlineOnly
   private static final long times_xj2QHRw/* $FF was: times-xj2QHRw*/(long var0, short var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 65535L);
      boolean var8 = false;
      return constructor-impl(var0 * var6);
   }

   @InlineOnly
   private static final long times_WZ4Q5Ns/* $FF was: times-WZ4Q5Ns*/(long var0, int var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 4294967295L);
      boolean var8 = false;
      return constructor-impl(var0 * var6);
   }

   @InlineOnly
   private static final long times_VKZWuLQ/* $FF was: times-VKZWuLQ*/(long var0, long var2) {
      byte var4 = 0;
      return constructor-impl(var0 * var2);
   }

   @InlineOnly
   private static final long div_7apg3OU/* $FF was: div-7apg3OU*/(long var0, byte var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 255L);
      boolean var8 = false;
      return UnsignedKt.ulongDivide-eb3DHEI(var0, var6);
   }

   @InlineOnly
   private static final long div_xj2QHRw/* $FF was: div-xj2QHRw*/(long var0, short var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 65535L);
      boolean var8 = false;
      return UnsignedKt.ulongDivide-eb3DHEI(var0, var6);
   }

   @InlineOnly
   private static final long div_WZ4Q5Ns/* $FF was: div-WZ4Q5Ns*/(long var0, int var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 4294967295L);
      boolean var8 = false;
      return UnsignedKt.ulongDivide-eb3DHEI(var0, var6);
   }

   @InlineOnly
   private static final long div_VKZWuLQ/* $FF was: div-VKZWuLQ*/(long var0, long var2) {
      byte var4 = 0;
      return UnsignedKt.ulongDivide-eb3DHEI(var0, var2);
   }

   @InlineOnly
   private static final long rem_7apg3OU/* $FF was: rem-7apg3OU*/(long var0, byte var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 255L);
      boolean var8 = false;
      return UnsignedKt.ulongRemainder-eb3DHEI(var0, var6);
   }

   @InlineOnly
   private static final long rem_xj2QHRw/* $FF was: rem-xj2QHRw*/(long var0, short var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 65535L);
      boolean var8 = false;
      return UnsignedKt.ulongRemainder-eb3DHEI(var0, var6);
   }

   @InlineOnly
   private static final long rem_WZ4Q5Ns/* $FF was: rem-WZ4Q5Ns*/(long var0, int var2) {
      byte var3 = 0;
      boolean var7 = false;
      long var6 = constructor-impl((long)var2 & 4294967295L);
      boolean var8 = false;
      return UnsignedKt.ulongRemainder-eb3DHEI(var0, var6);
   }

   @InlineOnly
   private static final long rem_VKZWuLQ/* $FF was: rem-VKZWuLQ*/(long var0, long var2) {
      byte var4 = 0;
      return UnsignedKt.ulongRemainder-eb3DHEI(var0, var2);
   }

   @InlineOnly
   private static final long inc_impl/* $FF was: inc-impl*/(long var0) {
      byte var2 = 0;
      return constructor-impl(var0 + 1L);
   }

   @InlineOnly
   private static final long dec_impl/* $FF was: dec-impl*/(long var0) {
      byte var2 = 0;
      return constructor-impl(var0 + -1L);
   }

   @InlineOnly
   private static final ULongRange rangeTo_VKZWuLQ/* $FF was: rangeTo-VKZWuLQ*/(long var0, long var2) {
      byte var4 = 0;
      return new ULongRange(var0, var2, (DefaultConstructorMarker)null);
   }

   @InlineOnly
   private static final long shl_impl/* $FF was: shl-impl*/(long var0, int var2) {
      byte var3 = 0;
      return constructor-impl(var0 << var2);
   }

   @InlineOnly
   private static final long shr_impl/* $FF was: shr-impl*/(long var0, int var2) {
      byte var3 = 0;
      return constructor-impl(var0 >>> var2);
   }

   @InlineOnly
   private static final long and_VKZWuLQ/* $FF was: and-VKZWuLQ*/(long var0, long var2) {
      byte var4 = 0;
      return constructor-impl(var0 & var2);
   }

   @InlineOnly
   private static final long or_VKZWuLQ/* $FF was: or-VKZWuLQ*/(long var0, long var2) {
      byte var4 = 0;
      return constructor-impl(var0 | var2);
   }

   @InlineOnly
   private static final long xor_VKZWuLQ/* $FF was: xor-VKZWuLQ*/(long var0, long var2) {
      byte var4 = 0;
      return constructor-impl(var0 ^ var2);
   }

   @InlineOnly
   private static final long inv_impl/* $FF was: inv-impl*/(long var0) {
      byte var2 = 0;
      return constructor-impl(~var0);
   }

   @InlineOnly
   private static final byte toByte_impl/* $FF was: toByte-impl*/(long var0) {
      byte var2 = 0;
      return (byte)((int)var0);
   }

   @InlineOnly
   private static final short toShort_impl/* $FF was: toShort-impl*/(long var0) {
      byte var2 = 0;
      return (short)((int)var0);
   }

   @InlineOnly
   private static final int toInt_impl/* $FF was: toInt-impl*/(long var0) {
      byte var2 = 0;
      return (int)var0;
   }

   @InlineOnly
   private static final long toLong_impl/* $FF was: toLong-impl*/(long var0) {
      byte var2 = 0;
      return var0;
   }

   @InlineOnly
   private static final byte toUByte_impl/* $FF was: toUByte-impl*/(long var0) {
      byte var2 = 0;
      boolean var5 = false;
      return UByte.constructor-impl((byte)((int)var0));
   }

   @InlineOnly
   private static final short toUShort_impl/* $FF was: toUShort-impl*/(long var0) {
      byte var2 = 0;
      boolean var5 = false;
      return UShort.constructor-impl((short)((int)var0));
   }

   @InlineOnly
   private static final int toUInt_impl/* $FF was: toUInt-impl*/(long var0) {
      byte var2 = 0;
      boolean var5 = false;
      return UInt.constructor-impl((int)var0);
   }

   @InlineOnly
   private static final long toULong_impl/* $FF was: toULong-impl*/(long var0) {
      byte var2 = 0;
      return var0;
   }

   @InlineOnly
   private static final float toFloat_impl/* $FF was: toFloat-impl*/(long var0) {
      byte var2 = 0;
      boolean var5 = false;
      return (float)UnsignedKt.ulongToDouble(var0);
   }

   @InlineOnly
   private static final double toDouble_impl/* $FF was: toDouble-impl*/(long var0) {
      byte var2 = 0;
      return UnsignedKt.ulongToDouble(var0);
   }

   @NotNull
   public static String toString_impl/* $FF was: toString-impl*/(long var0) {
      return UnsignedKt.ulongToString(var0);
   }

   @PublishedApi
   public static long constructor_impl/* $FF was: constructor-impl*/(long var0) {
      return var0;
   }

   @NotNull
   public static final ULong box_impl/* $FF was: box-impl*/(long var0) {
      return new ULong(var0);
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(long var0) {
      return (int)(var0 ^ var0 >>> 32);
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(long var0, @Nullable Object var2) {
      if (var2 instanceof ULong) {
         long var3 = ((ULong)var2).unbox-impl();
         if (var0 == var3) {
            return true;
         }
      }

      return false;
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(long var0, long var2) {
      throw null;
   }

   public final long unbox_impl/* $FF was: unbox-impl*/() {
      return this.data;
   }

   public int hashCode() {
      return hashCode-impl(this.data);
   }

   public boolean equals(Object var1) {
      return equals-impl(this.data, var1);
   }
}
