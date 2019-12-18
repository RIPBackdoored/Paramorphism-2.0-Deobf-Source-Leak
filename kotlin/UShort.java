package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001fB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u0010J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0013J\u001b\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b \u0010\u0018J\u0013\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003J\t\u0010$\u001a\u00020\rHÖ\u0001J\u0013\u0010%\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b&\u0010\u0005J\u0013\u0010'\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b(\u0010\u0005J\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b*\u0010\u0010J\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b+\u0010\u0013J\u001b\u0010)\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b,\u0010\u001fJ\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b-\u0010\u0018J\u001b\u0010.\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b/\u0010\u000bJ\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b1\u0010\u0010J\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b2\u0010\u0013J\u001b\u00100\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u001fJ\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u0018J\u001b\u00105\u001a\u0002062\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b7\u00108J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b:\u0010\u0010J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b;\u0010\u0013J\u001b\u00109\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b<\u0010\u001fJ\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b=\u0010\u0018J\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b?\u0010\u0010J\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u0013J\u001b\u0010>\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u001fJ\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u0018J\u0010\u0010C\u001a\u00020DH\u0087\b¢\u0006\u0004\bE\u0010FJ\u0010\u0010G\u001a\u00020HH\u0087\b¢\u0006\u0004\bI\u0010JJ\u0010\u0010K\u001a\u00020LH\u0087\b¢\u0006\u0004\bM\u0010NJ\u0010\u0010O\u001a\u00020\rH\u0087\b¢\u0006\u0004\bP\u0010QJ\u0010\u0010R\u001a\u00020SH\u0087\b¢\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bW\u0010\u0005J\u000f\u0010X\u001a\u00020YH\u0016¢\u0006\u0004\bZ\u0010[J\u0013\u0010\\\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b]\u0010FJ\u0013\u0010^\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\b_\u0010QJ\u0013\u0010`\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\ba\u0010UJ\u0013\u0010b\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\bc\u0010\u0005J\u001b\u0010d\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\be\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006g"},
   d2 = {"Lkotlin/UShort;", "", "data", "", "constructor-impl", "(S)S", "data$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-impl", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(S)B", "toDouble", "", "toDouble-impl", "(S)D", "toFloat", "", "toFloat-impl", "(S)F", "toInt", "toInt-impl", "(S)I", "toLong", "", "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
public final class UShort implements Comparable {
   private final short data;
   public static final short MIN_VALUE = 0;
   public static final short MAX_VALUE = -1;
   public static final int SIZE_BYTES = 2;
   public static final int SIZE_BITS = 16;
   public static final UShort$Companion Companion = new UShort$Companion((DefaultConstructorMarker)null);

   @InlineOnly
   private int compareTo_xj2QHRw/* $FF was: compareTo-xj2QHRw*/(short var1) {
      return compareTo-xj2QHRw(this.data, var1);
   }

   public int compareTo(Object var1) {
      return this.compareTo-xj2QHRw(((UShort)var1).unbox-impl());
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
   private UShort(short var1) {
      super();
      this.data = var1;
   }

   @InlineOnly
   private static final int compareTo_7apg3OU/* $FF was: compareTo-7apg3OU*/(short var0, byte var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var5 = var0 & '\uffff';
      var4 = false;
      int var6 = var1 & 255;
      return Intrinsics.compare(var5, var6);
   }

   @InlineOnly
   private static int compareTo_xj2QHRw/* $FF was: compareTo-xj2QHRw*/(short var0, short var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var5 = var0 & '\uffff';
      var4 = false;
      int var6 = var1 & '\uffff';
      return Intrinsics.compare(var5, var6);
   }

   @InlineOnly
   private static final int compareTo_WZ4Q5Ns/* $FF was: compareTo-WZ4Q5Ns*/(short var0, int var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      var4 = false;
      return UnsignedKt.uintCompare(var3, var1);
   }

   @InlineOnly
   private static final int compareTo_VKZWuLQ/* $FF was: compareTo-VKZWuLQ*/(short var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 65535L);
      boolean var6 = false;
      return UnsignedKt.ulongCompare(var4, var1);
   }

   @InlineOnly
   private static final int plus_7apg3OU/* $FF was: plus-7apg3OU*/(short var0, byte var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      boolean var5 = false;
      int var6 = UInt.constructor-impl(var1 & 255);
      var5 = false;
      return UInt.constructor-impl(var3 + var6);
   }

   @InlineOnly
   private static final int plus_xj2QHRw/* $FF was: plus-xj2QHRw*/(short var0, short var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      boolean var5 = false;
      int var6 = UInt.constructor-impl(var1 & '\uffff');
      var5 = false;
      return UInt.constructor-impl(var3 + var6);
   }

   @InlineOnly
   private static final int plus_WZ4Q5Ns/* $FF was: plus-WZ4Q5Ns*/(short var0, int var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      var4 = false;
      return UInt.constructor-impl(var3 + var1);
   }

   @InlineOnly
   private static final long plus_VKZWuLQ/* $FF was: plus-VKZWuLQ*/(short var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 65535L);
      boolean var6 = false;
      return ULong.constructor-impl(var4 + var1);
   }

   @InlineOnly
   private static final int minus_7apg3OU/* $FF was: minus-7apg3OU*/(short var0, byte var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      boolean var5 = false;
      int var6 = UInt.constructor-impl(var1 & 255);
      var5 = false;
      return UInt.constructor-impl(var3 - var6);
   }

   @InlineOnly
   private static final int minus_xj2QHRw/* $FF was: minus-xj2QHRw*/(short var0, short var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      boolean var5 = false;
      int var6 = UInt.constructor-impl(var1 & '\uffff');
      var5 = false;
      return UInt.constructor-impl(var3 - var6);
   }

   @InlineOnly
   private static final int minus_WZ4Q5Ns/* $FF was: minus-WZ4Q5Ns*/(short var0, int var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      var4 = false;
      return UInt.constructor-impl(var3 - var1);
   }

   @InlineOnly
   private static final long minus_VKZWuLQ/* $FF was: minus-VKZWuLQ*/(short var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 65535L);
      boolean var6 = false;
      return ULong.constructor-impl(var4 - var1);
   }

   @InlineOnly
   private static final int times_7apg3OU/* $FF was: times-7apg3OU*/(short var0, byte var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      boolean var5 = false;
      int var6 = UInt.constructor-impl(var1 & 255);
      var5 = false;
      return UInt.constructor-impl(var3 * var6);
   }

   @InlineOnly
   private static final int times_xj2QHRw/* $FF was: times-xj2QHRw*/(short var0, short var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      boolean var5 = false;
      int var6 = UInt.constructor-impl(var1 & '\uffff');
      var5 = false;
      return UInt.constructor-impl(var3 * var6);
   }

   @InlineOnly
   private static final int times_WZ4Q5Ns/* $FF was: times-WZ4Q5Ns*/(short var0, int var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      var4 = false;
      return UInt.constructor-impl(var3 * var1);
   }

   @InlineOnly
   private static final long times_VKZWuLQ/* $FF was: times-VKZWuLQ*/(short var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 65535L);
      boolean var6 = false;
      return ULong.constructor-impl(var4 * var1);
   }

   @InlineOnly
   private static final int div_7apg3OU/* $FF was: div-7apg3OU*/(short var0, byte var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      boolean var5 = false;
      int var6 = UInt.constructor-impl(var1 & 255);
      var5 = false;
      return UnsignedKt.uintDivide-J1ME1BU(var3, var6);
   }

   @InlineOnly
   private static final int div_xj2QHRw/* $FF was: div-xj2QHRw*/(short var0, short var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      boolean var5 = false;
      int var6 = UInt.constructor-impl(var1 & '\uffff');
      var5 = false;
      return UnsignedKt.uintDivide-J1ME1BU(var3, var6);
   }

   @InlineOnly
   private static final int div_WZ4Q5Ns/* $FF was: div-WZ4Q5Ns*/(short var0, int var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      var4 = false;
      return UnsignedKt.uintDivide-J1ME1BU(var3, var1);
   }

   @InlineOnly
   private static final long div_VKZWuLQ/* $FF was: div-VKZWuLQ*/(short var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 65535L);
      boolean var6 = false;
      return UnsignedKt.ulongDivide-eb3DHEI(var4, var1);
   }

   @InlineOnly
   private static final int rem_7apg3OU/* $FF was: rem-7apg3OU*/(short var0, byte var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      boolean var5 = false;
      int var6 = UInt.constructor-impl(var1 & 255);
      var5 = false;
      return UnsignedKt.uintRemainder-J1ME1BU(var3, var6);
   }

   @InlineOnly
   private static final int rem_xj2QHRw/* $FF was: rem-xj2QHRw*/(short var0, short var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      boolean var5 = false;
      int var6 = UInt.constructor-impl(var1 & '\uffff');
      var5 = false;
      return UnsignedKt.uintRemainder-J1ME1BU(var3, var6);
   }

   @InlineOnly
   private static final int rem_WZ4Q5Ns/* $FF was: rem-WZ4Q5Ns*/(short var0, int var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var3 = UInt.constructor-impl(var0 & '\uffff');
      var4 = false;
      return UnsignedKt.uintRemainder-J1ME1BU(var3, var1);
   }

   @InlineOnly
   private static final long rem_VKZWuLQ/* $FF was: rem-VKZWuLQ*/(short var0, long var1) {
      byte var3 = 0;
      boolean var5 = false;
      long var4 = ULong.constructor-impl((long)var0 & 65535L);
      boolean var6 = false;
      return UnsignedKt.ulongRemainder-eb3DHEI(var4, var1);
   }

   @InlineOnly
   private static final short inc_impl/* $FF was: inc-impl*/(short var0) {
      byte var1 = 0;
      return constructor-impl((short)(var0 + 1));
   }

   @InlineOnly
   private static final short dec_impl/* $FF was: dec-impl*/(short var0) {
      byte var1 = 0;
      return constructor-impl((short)(var0 + -1));
   }

   @InlineOnly
   private static final UIntRange rangeTo_xj2QHRw/* $FF was: rangeTo-xj2QHRw*/(short var0, short var1) {
      byte var2 = 0;
      boolean var4 = false;
      int var7 = UInt.constructor-impl(var0 & '\uffff');
      var4 = false;
      int var8 = UInt.constructor-impl(var1 & '\uffff');
      Object var9 = null;
      return new UIntRange(var7, var8, (DefaultConstructorMarker)var9);
   }

   @InlineOnly
   private static final short and_xj2QHRw/* $FF was: and-xj2QHRw*/(short var0, short var1) {
      byte var2 = 0;
      boolean var5 = false;
      return constructor-impl((short)(var0 & var1));
   }

   @InlineOnly
   private static final short or_xj2QHRw/* $FF was: or-xj2QHRw*/(short var0, short var1) {
      byte var2 = 0;
      boolean var5 = false;
      return constructor-impl((short)(var0 | var1));
   }

   @InlineOnly
   private static final short xor_xj2QHRw/* $FF was: xor-xj2QHRw*/(short var0, short var1) {
      byte var2 = 0;
      boolean var5 = false;
      return constructor-impl((short)(var0 ^ var1));
   }

   @InlineOnly
   private static final short inv_impl/* $FF was: inv-impl*/(short var0) {
      byte var1 = 0;
      boolean var3 = false;
      return constructor-impl((short)(~var0));
   }

   @InlineOnly
   private static final byte toByte_impl/* $FF was: toByte-impl*/(short var0) {
      byte var1 = 0;
      return (byte)var0;
   }

   @InlineOnly
   private static final short toShort_impl/* $FF was: toShort-impl*/(short var0) {
      byte var1 = 0;
      return var0;
   }

   @InlineOnly
   private static final int toInt_impl/* $FF was: toInt-impl*/(short var0) {
      byte var1 = 0;
      return var0 & '\uffff';
   }

   @InlineOnly
   private static final long toLong_impl/* $FF was: toLong-impl*/(short var0) {
      byte var1 = 0;
      return (long)var0 & 65535L;
   }

   @InlineOnly
   private static final byte toUByte_impl/* $FF was: toUByte-impl*/(short var0) {
      byte var1 = 0;
      boolean var3 = false;
      return UByte.constructor-impl((byte)var0);
   }

   @InlineOnly
   private static final short toUShort_impl/* $FF was: toUShort-impl*/(short var0) {
      byte var1 = 0;
      return var0;
   }

   @InlineOnly
   private static final int toUInt_impl/* $FF was: toUInt-impl*/(short var0) {
      byte var1 = 0;
      return UInt.constructor-impl(var0 & '\uffff');
   }

   @InlineOnly
   private static final long toULong_impl/* $FF was: toULong-impl*/(short var0) {
      byte var1 = 0;
      return ULong.constructor-impl((long)var0 & 65535L);
   }

   @InlineOnly
   private static final float toFloat_impl/* $FF was: toFloat-impl*/(short var0) {
      byte var1 = 0;
      boolean var3 = false;
      return (float)(var0 & '\uffff');
   }

   @InlineOnly
   private static final double toDouble_impl/* $FF was: toDouble-impl*/(short var0) {
      byte var1 = 0;
      boolean var3 = false;
      return (double)(var0 & '\uffff');
   }

   @NotNull
   public static String toString_impl/* $FF was: toString-impl*/(short var0) {
      boolean var2 = false;
      return String.valueOf(var0 & '\uffff');
   }

   @PublishedApi
   public static short constructor_impl/* $FF was: constructor-impl*/(short var0) {
      return var0;
   }

   @NotNull
   public static final UShort box_impl/* $FF was: box-impl*/(short var0) {
      return new UShort(var0);
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(short var0) {
      return var0;
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(short var0, @Nullable Object var1) {
      if (var1 instanceof UShort) {
         short var2 = ((UShort)var1).unbox-impl();
         if (var0 == var2) {
            return true;
         }
      }

      return false;
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(short var0, short var1) {
      throw null;
   }

   public final short unbox_impl/* $FF was: unbox-impl*/() {
      return this.data;
   }

   public int hashCode() {
      return hashCode-impl(this.data);
   }

   public boolean equals(Object var1) {
      return equals-impl(this.data, var1);
   }
}
