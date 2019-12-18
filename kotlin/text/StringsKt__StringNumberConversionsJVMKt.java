package kotlin.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000X\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0005H\u0082\b¢\u0006\u0004\b\u0006\u0010\u0007\u001a\r\u0010\b\u001a\u00020\t*\u00020\u0003H\u0087\b\u001a\u0015\u0010\b\u001a\u00020\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u000e\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u0003H\u0007\u001a\u0016\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\r\u0010\r\u001a\u00020\u000e*\u00020\u0003H\u0087\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007\u001a\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\r\u0010\u0012\u001a\u00020\u0013*\u00020\u0003H\u0087\b\u001a\r\u0010\u0014\u001a\u00020\u0015*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010\u0016\u001a\u00020\u0017*\u00020\u0003H\u0087\b\u001a\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0017*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0019\u001a\r\u0010\u001a\u001a\u00020\u001b*\u00020\u0003H\u0087\b\u001a\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u001b*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u001d\u001a\r\u0010\u001e\u001a\u00020\u0010*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u001e\u001a\u00020\u0010*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010\u001f\u001a\u00020 *\u00020\u0003H\u0087\b\u001a\u0015\u0010\u001f\u001a\u00020 *\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010!\u001a\u00020\"*\u00020\u0003H\u0087\b\u001a\u0015\u0010!\u001a\u00020\"*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020 2\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\"2\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b¨\u0006$"},
   d2 = {"screenFloatValue", "T", "str", "", "parse", "Lkotlin/Function1;", "screenFloatValue$StringsKt__StringNumberConversionsJVMKt", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "toBigDecimal", "Ljava/math/BigDecimal;", "mathContext", "Ljava/math/MathContext;", "toBigDecimalOrNull", "toBigInteger", "Ljava/math/BigInteger;", "radix", "", "toBigIntegerOrNull", "toBoolean", "", "toByte", "", "toDouble", "", "toDoubleOrNull", "(Ljava/lang/String;)Ljava/lang/Double;", "toFloat", "", "toFloatOrNull", "(Ljava/lang/String;)Ljava/lang/Float;", "toInt", "toLong", "", "toShort", "", "toString", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringNumberConversionsJVMKt extends StringsKt__StringBuilderKt {
   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final String toString(byte var0, int var1) {
      byte var2 = 0;
      int var4 = CharsKt.checkRadix(var1);
      boolean var5 = false;
      return Integer.toString(var0, CharsKt.checkRadix(var4));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final String toString(short var0, int var1) {
      byte var2 = 0;
      int var4 = CharsKt.checkRadix(var1);
      boolean var5 = false;
      return Integer.toString(var0, CharsKt.checkRadix(var4));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final String toString(int var0, int var1) {
      byte var2 = 0;
      return Integer.toString(var0, CharsKt.checkRadix(var1));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final String toString(long var0, int var2) {
      byte var3 = 0;
      return Long.toString(var0, CharsKt.checkRadix(var2));
   }

   @InlineOnly
   private static final boolean toBoolean(@NotNull String var0) {
      byte var1 = 0;
      return Boolean.parseBoolean(var0);
   }

   @InlineOnly
   private static final byte toByte(@NotNull String var0) {
      byte var1 = 0;
      return Byte.parseByte(var0);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte toByte(@NotNull String var0, int var1) {
      byte var2 = 0;
      return Byte.parseByte(var0, CharsKt.checkRadix(var1));
   }

   @InlineOnly
   private static final short toShort(@NotNull String var0) {
      byte var1 = 0;
      return Short.parseShort(var0);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short toShort(@NotNull String var0, int var1) {
      byte var2 = 0;
      return Short.parseShort(var0, CharsKt.checkRadix(var1));
   }

   @InlineOnly
   private static final int toInt(@NotNull String var0) {
      byte var1 = 0;
      return Integer.parseInt(var0);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final int toInt(@NotNull String var0, int var1) {
      byte var2 = 0;
      return Integer.parseInt(var0, CharsKt.checkRadix(var1));
   }

   @InlineOnly
   private static final long toLong(@NotNull String var0) {
      byte var1 = 0;
      return Long.parseLong(var0);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final long toLong(@NotNull String var0, int var1) {
      byte var2 = 0;
      return Long.parseLong(var0, CharsKt.checkRadix(var1));
   }

   @InlineOnly
   private static final float toFloat(@NotNull String var0) {
      byte var1 = 0;
      return Float.parseFloat(var0);
   }

   @InlineOnly
   private static final double toDouble(@NotNull String var0) {
      byte var1 = 0;
      return Double.parseDouble(var0);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Float toFloatOrNull(@NotNull String var0) {
      boolean var1 = false;

      Float var2;
      try {
         Float var10000;
         if (ScreenFloatValueRegEx.value.matches((CharSequence)var0)) {
            boolean var3 = false;
            var10000 = Float.parseFloat(var0);
         } else {
            var10000 = null;
         }

         var2 = var10000;
      } catch (NumberFormatException var4) {
         var2 = null;
      }

      return var2;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Double toDoubleOrNull(@NotNull String var0) {
      boolean var1 = false;

      Double var2;
      try {
         Double var10000;
         if (ScreenFloatValueRegEx.value.matches((CharSequence)var0)) {
            boolean var3 = false;
            var10000 = Double.parseDouble(var0);
         } else {
            var10000 = null;
         }

         var2 = var10000;
      } catch (NumberFormatException var4) {
         var2 = null;
      }

      return var2;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger toBigInteger(@NotNull String var0) {
      byte var1 = 0;
      return new BigInteger(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger toBigInteger(@NotNull String var0, int var1) {
      byte var2 = 0;
      return new BigInteger(var0, CharsKt.checkRadix(var1));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @Nullable
   public static final BigInteger toBigIntegerOrNull(@NotNull String var0) {
      return StringsKt.toBigIntegerOrNull(var0, 10);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @Nullable
   public static final BigInteger toBigIntegerOrNull(@NotNull String var0, int var1) {
      CharsKt.checkRadix(var1);
      int var2 = var0.length();
      switch(var2) {
      case 0:
         return null;
      case 1:
         if (CharsKt.digitOf(var0.charAt(0), var1) < 0) {
            return null;
         }
         break;
      default:
         int var3 = var0.charAt(0) == '-' ? 1 : 0;
         int var4 = var3;

         for(int var5 = var2; var4 < var5; ++var4) {
            if (CharsKt.digitOf(var0.charAt(var4), var1) < 0) {
               return null;
            }
         }
      }

      boolean var6 = false;
      return new BigInteger(var0, CharsKt.checkRadix(var1));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(@NotNull String var0) {
      byte var1 = 0;
      return new BigDecimal(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(@NotNull String var0, MathContext var1) {
      byte var2 = 0;
      return new BigDecimal(var0, var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @Nullable
   public static final BigDecimal toBigDecimalOrNull(@NotNull String var0) {
      boolean var1 = false;

      BigDecimal var2;
      try {
         BigDecimal var10000;
         if (ScreenFloatValueRegEx.value.matches((CharSequence)var0)) {
            boolean var3 = false;
            boolean var5 = false;
            var10000 = new BigDecimal(var0);
         } else {
            var10000 = null;
         }

         var2 = var10000;
      } catch (NumberFormatException var6) {
         var2 = null;
      }

      return var2;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @Nullable
   public static final BigDecimal toBigDecimalOrNull(@NotNull String var0, @NotNull MathContext var1) {
      boolean var2 = false;

      BigDecimal var8;
      try {
         BigDecimal var10000;
         if (ScreenFloatValueRegEx.value.matches((CharSequence)var0)) {
            boolean var4 = false;
            boolean var7 = false;
            var10000 = new BigDecimal(var0, var1);
         } else {
            var10000 = null;
         }

         var8 = var10000;
      } catch (NumberFormatException var9) {
         var8 = null;
      }

      return var8;
   }

   private static final Object screenFloatValue$StringsKt__StringNumberConversionsJVMKt(String var0, Function1 var1) {
      byte var2 = 0;

      Object var3;
      try {
         var3 = ScreenFloatValueRegEx.value.matches((CharSequence)var0) ? var1.invoke(var0) : null;
      } catch (NumberFormatException var5) {
         var3 = null;
      }

      return var3;
   }

   public StringsKt__StringNumberConversionsJVMKt() {
      super();
   }
}
