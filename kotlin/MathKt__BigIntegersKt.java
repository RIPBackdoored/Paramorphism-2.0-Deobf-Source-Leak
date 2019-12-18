package kotlin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0005\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\r\u0010\u0006\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0087\f\u001a\u0015\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0087\f\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0010\u001a\u00020\u0011*\u00020\u0001H\u0087\b\u001a!\u0010\u0010\u001a\u00020\u0011*\u00020\u00012\b\b\u0002\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\rH\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\u0016H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0018\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f¨\u0006\u0019"},
   d2 = {"and", "Ljava/math/BigInteger;", "other", "dec", "div", "inc", "inv", "minus", "or", "plus", "rem", "shl", "n", "", "shr", "times", "toBigDecimal", "Ljava/math/BigDecimal;", "scale", "mathContext", "Ljava/math/MathContext;", "toBigInteger", "", "unaryMinus", "xor", "kotlin-stdlib"},
   xs = "kotlin/MathKt"
)
class MathKt__BigIntegersKt extends MathKt__BigDecimalsKt {
   @InlineOnly
   private static final BigInteger plus(@NotNull BigInteger var0, BigInteger var1) {
      byte var2 = 0;
      return var0.add(var1);
   }

   @InlineOnly
   private static final BigInteger minus(@NotNull BigInteger var0, BigInteger var1) {
      byte var2 = 0;
      return var0.subtract(var1);
   }

   @InlineOnly
   private static final BigInteger times(@NotNull BigInteger var0, BigInteger var1) {
      byte var2 = 0;
      return var0.multiply(var1);
   }

   @InlineOnly
   private static final BigInteger div(@NotNull BigInteger var0, BigInteger var1) {
      byte var2 = 0;
      return var0.divide(var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final BigInteger rem(@NotNull BigInteger var0, BigInteger var1) {
      byte var2 = 0;
      return var0.remainder(var1);
   }

   @InlineOnly
   private static final BigInteger unaryMinus(@NotNull BigInteger var0) {
      byte var1 = 0;
      return var0.negate();
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger inc(@NotNull BigInteger var0) {
      byte var1 = 0;
      return var0.add(BigInteger.ONE);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger dec(@NotNull BigInteger var0) {
      byte var1 = 0;
      return var0.subtract(BigInteger.ONE);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger inv(@NotNull BigInteger var0) {
      byte var1 = 0;
      return var0.not();
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger and(@NotNull BigInteger var0, BigInteger var1) {
      byte var2 = 0;
      return var0.and(var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger or(@NotNull BigInteger var0, BigInteger var1) {
      byte var2 = 0;
      return var0.or(var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger xor(@NotNull BigInteger var0, BigInteger var1) {
      byte var2 = 0;
      return var0.xor(var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger shl(@NotNull BigInteger var0, int var1) {
      byte var2 = 0;
      return var0.shiftLeft(var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger shr(@NotNull BigInteger var0, int var1) {
      byte var2 = 0;
      return var0.shiftRight(var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger toBigInteger(int var0) {
      byte var1 = 0;
      return BigInteger.valueOf((long)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger toBigInteger(long var0) {
      byte var2 = 0;
      return BigInteger.valueOf(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(@NotNull BigInteger var0) {
      byte var1 = 0;
      return new BigDecimal(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(@NotNull BigInteger var0, int var1, MathContext var2) {
      byte var3 = 0;
      return new BigDecimal(var0, var1, var2);
   }

   static BigDecimal toBigDecimal$default(BigInteger var0, int var1, MathContext var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = MathContext.UNLIMITED;
      }

      boolean var5 = false;
      return new BigDecimal(var0, var1, var2);
   }

   public MathKt__BigIntegersKt() {
      super();
   }
}
