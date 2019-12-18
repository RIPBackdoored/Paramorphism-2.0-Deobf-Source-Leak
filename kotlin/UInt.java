package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.UIntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 j2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001jB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000bJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0005J\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u000fJ\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u000bJ\u001b\u0010\u0019\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0016J\u0013\u0010\u001f\u001a\u00020 2\b\u0010\t\u001a\u0004\u0018\u00010!HÖ\u0003J\t\u0010\"\u001a\u00020\u0003HÖ\u0001J\u0013\u0010#\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b$\u0010\u0005J\u0013\u0010%\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b&\u0010\u0005J\u001b\u0010'\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\b(\u0010\u000fJ\u001b\u0010'\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b)\u0010\u000bJ\u001b\u0010'\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b*\u0010\u001dJ\u001b\u0010'\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b+\u0010\u0016J\u001b\u0010,\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b-\u0010\u000bJ\u001b\u0010.\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\b/\u0010\u000fJ\u001b\u0010.\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b0\u0010\u000bJ\u001b\u0010.\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b1\u0010\u001dJ\u001b\u0010.\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b2\u0010\u0016J\u001b\u00103\u001a\u0002042\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b5\u00106J\u001b\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\b8\u0010\u000fJ\u001b\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b9\u0010\u000bJ\u001b\u00107\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b:\u0010\u001dJ\u001b\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b;\u0010\u0016J\u001b\u0010<\u001a\u00020\u00002\u0006\u0010=\u001a\u00020\u0003H\u0087\fø\u0001\u0000¢\u0006\u0004\b>\u0010\u000bJ\u001b\u0010?\u001a\u00020\u00002\u0006\u0010=\u001a\u00020\u0003H\u0087\fø\u0001\u0000¢\u0006\u0004\b@\u0010\u000bJ\u001b\u0010A\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u000fJ\u001b\u0010A\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bC\u0010\u000bJ\u001b\u0010A\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bD\u0010\u001dJ\u001b\u0010A\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bE\u0010\u0016J\u0010\u0010F\u001a\u00020GH\u0087\b¢\u0006\u0004\bH\u0010IJ\u0010\u0010J\u001a\u00020KH\u0087\b¢\u0006\u0004\bL\u0010MJ\u0010\u0010N\u001a\u00020OH\u0087\b¢\u0006\u0004\bP\u0010QJ\u0010\u0010R\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bS\u0010\u0005J\u0010\u0010T\u001a\u00020UH\u0087\b¢\u0006\u0004\bV\u0010WJ\u0010\u0010X\u001a\u00020YH\u0087\b¢\u0006\u0004\bZ\u0010[J\u000f\u0010\\\u001a\u00020]H\u0016¢\u0006\u0004\b^\u0010_J\u0013\u0010`\u001a\u00020\rH\u0087\bø\u0001\u0000¢\u0006\u0004\ba\u0010IJ\u0013\u0010b\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\bc\u0010\u0005J\u0013\u0010d\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\be\u0010WJ\u0013\u0010f\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\bg\u0010[J\u001b\u0010h\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\bi\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006k"},
   d2 = {"Lkotlin/UInt;", "", "data", "", "constructor-impl", "(I)I", "data$annotations", "()V", "and", "other", "and-WZ4Q5Ns", "(II)I", "compareTo", "Lkotlin/UByte;", "compareTo-7apg3OU", "(IB)I", "compareTo-WZ4Q5Ns", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(IJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(IS)I", "dec", "dec-impl", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(IJ)J", "div-xj2QHRw", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-WZ4Q5Ns", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-WZ4Q5Ns", "(II)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-impl", "shr", "shr-impl", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(I)B", "toDouble", "", "toDouble-impl", "(I)D", "toFloat", "", "toFloat-impl", "(I)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(I)J", "toShort", "", "toShort-impl", "(I)S", "toString", "", "toString-impl", "(I)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-WZ4Q5Ns", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
public final class UInt implements Comparable {
   private final int data;
   public static final int MIN_VALUE = 0;
   public static final int MAX_VALUE = -1;
   public static final int SIZE_BYTES = 4;
   public static final int SIZE_BITS = 32;
   public static final UInt$Companion Companion = new UInt$Companion((DefaultConstructorMarker)null);

   @InlineOnly
   private int compareTo_WZ4Q5Ns/* $FF was: compareTo-WZ4Q5Ns*/(int var1) {
      return compareTo-WZ4Q5Ns(this.data, var1);
   }

   public int compareTo(Object var1) {
      return this.compareTo-WZ4Q5Ns(((UInt)var1).unbox-impl());
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
   private UInt(int var1) {
      super();
      this.data = var1;
   }

   @InlineOnly
   private static final int compareTo_7apg3OU/* $FF was: compareTo-7apg3OU*/(int var0, byte var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & 255);
      var5 = false;
      return UnsignedKt.uintCompare(var0, var4);
   }

   @InlineOnly
   private static final int compareTo_xj2QHRw/* $FF was: compareTo-xj2QHRw*/(int var0, short var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & '\uffff');
      var5 = false;
      return UnsignedKt.uintCompare(var0, var4);
   }

   @InlineOnly
   private static int compareTo_WZ4Q5Ns/* $FF was: compareTo-WZ4Q5Ns*/(int var0, int var1) {
      byte var2 = 0;
      return UnsignedKt.uintCompare(var0, var1);
   }

   @InlineOnly
   private static final int compareTo_VKZWuLQ/* $FF was: compareTo-VKZWuLQ*/(int var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 4294967295L);
      boolean var6 = false;
      return UnsignedKt.ulongCompare(var4, var1);
   }

   @InlineOnly
   private static final int plus_7apg3OU/* $FF was: plus-7apg3OU*/(int var0, byte var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & 255);
      var5 = false;
      return constructor-impl(var0 + var4);
   }

   @InlineOnly
   private static final int plus_xj2QHRw/* $FF was: plus-xj2QHRw*/(int var0, short var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & '\uffff');
      var5 = false;
      return constructor-impl(var0 + var4);
   }

   @InlineOnly
   private static final int plus_WZ4Q5Ns/* $FF was: plus-WZ4Q5Ns*/(int var0, int var1) {
      byte var2 = 0;
      return constructor-impl(var0 + var1);
   }

   @InlineOnly
   private static final long plus_VKZWuLQ/* $FF was: plus-VKZWuLQ*/(int var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 4294967295L);
      boolean var6 = false;
      return ULong.constructor-impl(var4 + var1);
   }

   @InlineOnly
   private static final int minus_7apg3OU/* $FF was: minus-7apg3OU*/(int var0, byte var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & 255);
      var5 = false;
      return constructor-impl(var0 - var4);
   }

   @InlineOnly
   private static final int minus_xj2QHRw/* $FF was: minus-xj2QHRw*/(int var0, short var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & '\uffff');
      var5 = false;
      return constructor-impl(var0 - var4);
   }

   @InlineOnly
   private static final int minus_WZ4Q5Ns/* $FF was: minus-WZ4Q5Ns*/(int var0, int var1) {
      byte var2 = 0;
      return constructor-impl(var0 - var1);
   }

   @InlineOnly
   private static final long minus_VKZWuLQ/* $FF was: minus-VKZWuLQ*/(int var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 4294967295L);
      boolean var6 = false;
      return ULong.constructor-impl(var4 - var1);
   }

   @InlineOnly
   private static final int times_7apg3OU/* $FF was: times-7apg3OU*/(int var0, byte var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & 255);
      var5 = false;
      return constructor-impl(var0 * var4);
   }

   @InlineOnly
   private static final int times_xj2QHRw/* $FF was: times-xj2QHRw*/(int var0, short var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & '\uffff');
      var5 = false;
      return constructor-impl(var0 * var4);
   }

   @InlineOnly
   private static final int times_WZ4Q5Ns/* $FF was: times-WZ4Q5Ns*/(int var0, int var1) {
      byte var2 = 0;
      return constructor-impl(var0 * var1);
   }

   @InlineOnly
   private static final long times_VKZWuLQ/* $FF was: times-VKZWuLQ*/(int var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 4294967295L);
      boolean var6 = false;
      return ULong.constructor-impl(var4 * var1);
   }

   @InlineOnly
   private static final int div_7apg3OU/* $FF was: div-7apg3OU*/(int var0, byte var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & 255);
      var5 = false;
      return UnsignedKt.uintDivide-J1ME1BU(var0, var4);
   }

   @InlineOnly
   private static final int div_xj2QHRw/* $FF was: div-xj2QHRw*/(int var0, short var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & '\uffff');
      var5 = false;
      return UnsignedKt.uintDivide-J1ME1BU(var0, var4);
   }

   @InlineOnly
   private static final int div_WZ4Q5Ns/* $FF was: div-WZ4Q5Ns*/(int var0, int var1) {
      byte var2 = 0;
      return UnsignedKt.uintDivide-J1ME1BU(var0, var1);
   }

   @InlineOnly
   private static final long div_VKZWuLQ/* $FF was: div-VKZWuLQ*/(int var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 4294967295L);
      boolean var6 = false;
      return UnsignedKt.ulongDivide-eb3DHEI(var4, var1);
   }

   @InlineOnly
   private static final int rem_7apg3OU/* $FF was: rem-7apg3OU*/(int var0, byte var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & 255);
      var5 = false;
      return UnsignedKt.uintRemainder-J1ME1BU(var0, var4);
   }

   @InlineOnly
   private static final int rem_xj2QHRw/* $FF was: rem-xj2QHRw*/(int var0, short var1) {
      byte var2 = 0;
      boolean var5 = false;
      int var4 = constructor-impl(var1 & '\uffff');
      var5 = false;
      return UnsignedKt.uintRemainder-J1ME1BU(var0, var4);
   }

   @InlineOnly
   private static final int rem_WZ4Q5Ns/* $FF was: rem-WZ4Q5Ns*/(int var0, int var1) {
      byte var2 = 0;
      return UnsignedKt.uintRemainder-J1ME1BU(var0, var1);
   }

   @InlineOnly
   private static final long rem_VKZWuLQ/* $FF was: rem-VKZWuLQ*/(int var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 4294967295L);
      boolean var6 = false;
      return UnsignedKt.ulongRemainder-eb3DHEI(var4, var1);
   }

   @InlineOnly
   private static final int inc_impl/* $FF was: inc-impl*/(int var0) {
      byte var1 = 0;
      return constructor-impl(var0 + 1);
   }

   @InlineOnly
   private static final int dec_impl/* $FF was: dec-impl*/(int var0) {
      byte var1 = 0;
      return constructor-impl(var0 + -1);
   }

   @InlineOnly
   private static final UIntRange rangeTo_WZ4Q5Ns/* $FF was: rangeTo-WZ4Q5Ns*/(int var0, int var1) {
      byte var2 = 0;
      return new UIntRange(var0, var1, (DefaultConstructorMarker)null);
   }

   @InlineOnly
   private static final int shl_impl/* $FF was: shl-impl*/(int var0, int var1) {
      byte var2 = 0;
      return constructor-impl(var0 << var1);
   }

   @InlineOnly
   private static final int shr_impl/* $FF was: shr-impl*/(int var0, int var1) {
      byte var2 = 0;
      return constructor-impl(var0 >>> var1);
   }

   @InlineOnly
   private static final int and_WZ4Q5Ns/* $FF was: and-WZ4Q5Ns*/(int var0, int var1) {
      byte var2 = 0;
      return constructor-impl(var0 & var1);
   }

   @InlineOnly
   private static final int or_WZ4Q5Ns/* $FF was: or-WZ4Q5Ns*/(int var0, int var1) {
      byte var2 = 0;
      return constructor-impl(var0 | var1);
   }

   @InlineOnly
   private static final int xor_WZ4Q5Ns/* $FF was: xor-WZ4Q5Ns*/(int var0, int var1) {
      byte var2 = 0;
      return constructor-impl(var0 ^ var1);
   }

   @InlineOnly
   private static final int inv_impl/* $FF was: inv-impl*/(int var0) {
      byte var1 = 0;
      return constructor-impl(~var0);
   }

   @InlineOnly
   private static final byte toByte_impl/* $FF was: toByte-impl*/(int var0) {
      byte var1 = 0;
      return (byte)var0;
   }

   @InlineOnly
   private static final short toShort_impl/* $FF was: toShort-impl*/(int var0) {
      byte var1 = 0;
      return (short)var0;
   }

   @InlineOnly
   private static final int toInt_impl/* $FF was: toInt-impl*/(int var0) {
      byte var1 = 0;
      return var0;
   }

   @InlineOnly
   private static final long toLong_impl/* $FF was: toLong-impl*/(int var0) {
      byte var1 = 0;
      return (long)var0 & 4294967295L;
   }

   @InlineOnly
   private static final byte toUByte_impl/* $FF was: toUByte-impl*/(int var0) {
      byte var1 = 0;
      boolean var3 = false;
      return UByte.constructor-impl((byte)var0);
   }

   @InlineOnly
   private static final short toUShort_impl/* $FF was: toUShort-impl*/(int var0) {
      byte var1 = 0;
      boolean var3 = false;
      return UShort.constructor-impl((short)var0);
   }

   @InlineOnly
   private static final int toUInt_impl/* $FF was: toUInt-impl*/(int var0) {
      byte var1 = 0;
      return var0;
   }

   @InlineOnly
   private static final long toULong_impl/* $FF was: toULong-impl*/(int var0) {
      byte var1 = 0;
      return ULong.constructor-impl((long)var0 & 4294967295L);
   }

   @InlineOnly
   private static final float toFloat_impl/* $FF was: toFloat-impl*/(int var0) {
      byte var1 = 0;
      boolean var3 = false;
      return (float)UnsignedKt.uintToDouble(var0);
   }

   @InlineOnly
   private static final double toDouble_impl/* $FF was: toDouble-impl*/(int var0) {
      byte var1 = 0;
      return UnsignedKt.uintToDouble(var0);
   }

   @NotNull
   public static String toString_impl/* $FF was: toString-impl*/(int var0) {
      boolean var2 = false;
      return String.valueOf((long)var0 & 4294967295L);
   }

   @PublishedApi
   public static int constructor_impl/* $FF was: constructor-impl*/(int var0) {
      return var0;
   }

   @NotNull
   public static final UInt box_impl/* $FF was: box-impl*/(int var0) {
      return new UInt(var0);
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(int var0) {
      return var0;
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(int var0, @Nullable Object var1) {
      if (var1 instanceof UInt) {
         int var2 = ((UInt)var1).unbox-impl();
         if (var0 == var2) {
            return true;
         }
      }

      return false;
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(int var0, int var1) {
      throw null;
   }

   public final int unbox_impl/* $FF was: unbox-impl*/() {
      return this.data;
   }

   public int hashCode() {
      return hashCode-impl(this.data);
   }

   public boolean equals(Object var1) {
      return equals-impl(this.data, var1);
   }
}
