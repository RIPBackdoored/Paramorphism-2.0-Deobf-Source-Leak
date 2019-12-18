package kotlin;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0002\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000bH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000eH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000fH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000f2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u0010H\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\u0011\u001a\u00020\u0001*\u00020\u0001H\u0087\n¨\u0006\u0012"},
   d2 = {"dec", "Ljava/math/BigDecimal;", "div", "other", "inc", "minus", "mod", "plus", "rem", "times", "toBigDecimal", "", "mathContext", "Ljava/math/MathContext;", "", "", "", "unaryMinus", "kotlin-stdlib"},
   xs = "kotlin/MathKt"
)
class MathKt__BigDecimalsKt {
   @InlineOnly
   private static final BigDecimal plus(@NotNull BigDecimal var0, BigDecimal var1) {
      byte var2 = 0;
      return var0.add(var1);
   }

   @InlineOnly
   private static final BigDecimal minus(@NotNull BigDecimal var0, BigDecimal var1) {
      byte var2 = 0;
      return var0.subtract(var1);
   }

   @InlineOnly
   private static final BigDecimal times(@NotNull BigDecimal var0, BigDecimal var1) {
      byte var2 = 0;
      return var0.multiply(var1);
   }

   @InlineOnly
   private static final BigDecimal div(@NotNull BigDecimal var0, BigDecimal var1) {
      byte var2 = 0;
      return var0.divide(var1, RoundingMode.HALF_EVEN);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use rem(other) instead",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "rem(other)"
),
      level = DeprecationLevel.ERROR
   )
   @InlineOnly
   private static final BigDecimal mod(@NotNull BigDecimal var0, BigDecimal var1) {
      byte var2 = 0;
      return var0.remainder(var1);
   }

   @InlineOnly
   private static final BigDecimal rem(@NotNull BigDecimal var0, BigDecimal var1) {
      byte var2 = 0;
      return var0.remainder(var1);
   }

   @InlineOnly
   private static final BigDecimal unaryMinus(@NotNull BigDecimal var0) {
      byte var1 = 0;
      return var0.negate();
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal inc(@NotNull BigDecimal var0) {
      byte var1 = 0;
      return var0.add(BigDecimal.ONE);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal dec(@NotNull BigDecimal var0) {
      byte var1 = 0;
      return var0.subtract(BigDecimal.ONE);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(int var0) {
      byte var1 = 0;
      return BigDecimal.valueOf((long)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(int var0, MathContext var1) {
      byte var2 = 0;
      return new BigDecimal(var0, var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(long var0) {
      byte var2 = 0;
      return BigDecimal.valueOf(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(long var0, MathContext var2) {
      byte var3 = 0;
      return new BigDecimal(var0, var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(float var0) {
      byte var1 = 0;
      return new BigDecimal(String.valueOf(var0));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(float var0, MathContext var1) {
      byte var2 = 0;
      return new BigDecimal(String.valueOf(var0), var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(double var0) {
      byte var2 = 0;
      return new BigDecimal(String.valueOf(var0));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(double var0, MathContext var2) {
      byte var3 = 0;
      return new BigDecimal(String.valueOf(var0), var2);
   }

   public MathKt__BigDecimalsKt() {
      super();
   }
}
