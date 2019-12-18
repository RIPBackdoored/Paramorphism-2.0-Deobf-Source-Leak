package kotlin.math;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b7\u001a\u0011\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\fH\u0087\b\u001a\u0011\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0019\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0019\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010 \u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010 \u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010!\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010!\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\"\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\"\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010#\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010#\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010$\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010$\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010%\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010%\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010&\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010&\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0019\u0010'\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u0001H\u0087\b\u001a\u0019\u0010'\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010(\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010(\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010)\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010)\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0018\u0010*\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010+\u001a\u00020\u0001H\u0007\u001a\u0018\u0010*\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010+\u001a\u00020\u0006H\u0007\u001a\u0011\u0010,\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010,\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010-\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0010\u0010-\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0007\u001a\u0019\u0010.\u001a\u00020\u00012\u0006\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u0001H\u0087\b\u001a\u0019\u0010.\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u0006H\u0087\b\u001a\u0019\u0010.\u001a\u00020\t2\u0006\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\tH\u0087\b\u001a\u0019\u0010.\u001a\u00020\f2\u0006\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\fH\u0087\b\u001a\u0019\u00101\u001a\u00020\u00012\u0006\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u0001H\u0087\b\u001a\u0019\u00101\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u0006H\u0087\b\u001a\u0019\u00101\u001a\u00020\t2\u0006\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\tH\u0087\b\u001a\u0019\u00101\u001a\u00020\f2\u0006\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\fH\u0087\b\u001a\u0011\u00102\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00102\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00103\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00103\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00104\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00104\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00105\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00105\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00106\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00106\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00107\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00107\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u00108\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0010\u00108\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0007\u001a\u0015\u00109\u001a\u00020\u0001*\u00020\u00012\u0006\u0010:\u001a\u00020\u0001H\u0087\b\u001a\u0015\u00109\u001a\u00020\u0006*\u00020\u00062\u0006\u0010:\u001a\u00020\u0006H\u0087\b\u001a\r\u0010;\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010;\u001a\u00020\u0006*\u00020\u0006H\u0087\b\u001a\u0015\u0010<\u001a\u00020\u0001*\u00020\u00012\u0006\u0010=\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010<\u001a\u00020\u0006*\u00020\u00062\u0006\u0010=\u001a\u00020\u0006H\u0087\b\u001a\r\u0010>\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010>\u001a\u00020\u0006*\u00020\u0006H\u0087\b\u001a\u0015\u0010?\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010?\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0018\u001a\u00020\tH\u0087\b\u001a\u0015\u0010?\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010?\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0018\u001a\u00020\tH\u0087\b\u001a\f\u0010@\u001a\u00020\t*\u00020\u0001H\u0007\u001a\f\u0010@\u001a\u00020\t*\u00020\u0006H\u0007\u001a\f\u0010A\u001a\u00020\f*\u00020\u0001H\u0007\u001a\f\u0010A\u001a\u00020\f*\u00020\u0006H\u0007\u001a\u0015\u0010B\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010B\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\tH\u0087\b\u001a\u0015\u0010B\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010B\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u000f\u001a\u00020\tH\u0087\b\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00018Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u001f\u0010\u0000\u001a\u00020\u0006*\u00020\u00068Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0007\u001a\u0004\b\u0004\u0010\b\"\u001f\u0010\u0000\u001a\u00020\t*\u00020\t8Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\n\u001a\u0004\b\u0004\u0010\u000b\"\u001f\u0010\u0000\u001a\u00020\f*\u00020\f8Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\r\u001a\u0004\b\u0004\u0010\u000e\"\u001f\u0010\u000f\u001a\u00020\u0001*\u00020\u00018Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u0003\u001a\u0004\b\u0011\u0010\u0005\"\u001f\u0010\u000f\u001a\u00020\u0006*\u00020\u00068Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u0007\u001a\u0004\b\u0011\u0010\b\"\u001e\u0010\u000f\u001a\u00020\t*\u00020\t8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\n\u001a\u0004\b\u0011\u0010\u000b\"\u001e\u0010\u000f\u001a\u00020\t*\u00020\f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\r\u001a\u0004\b\u0011\u0010\u0012\"\u001f\u0010\u0013\u001a\u00020\u0001*\u00020\u00018Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u0003\u001a\u0004\b\u0015\u0010\u0005\"\u001f\u0010\u0013\u001a\u00020\u0006*\u00020\u00068Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u0007\u001a\u0004\b\u0015\u0010\b¨\u0006C"},
   d2 = {"absoluteValue", "", "absoluteValue$annotations", "(D)V", "getAbsoluteValue", "(D)D", "", "(F)V", "(F)F", "", "(I)V", "(I)I", "", "(J)V", "(J)J", "sign", "sign$annotations", "getSign", "(J)I", "ulp", "ulp$annotations", "getUlp", "abs", "x", "n", "acos", "acosh", "asin", "asinh", "atan", "atan2", "y", "atanh", "ceil", "cos", "cosh", "exp", "expm1", "floor", "hypot", "ln", "ln1p", "log", "base", "log10", "log2", "max", "a", "b", "min", "round", "sin", "sinh", "sqrt", "tan", "tanh", "truncate", "IEEErem", "divisor", "nextDown", "nextTowards", "to", "nextUp", "pow", "roundToInt", "roundToLong", "withSign", "kotlin-stdlib"},
   xs = "kotlin/math/MathKt"
)
class MathKt__MathJVMKt extends MathKt__MathHKt {
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double sin(double var0) {
      byte var2 = 0;
      return Math.sin(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double cos(double var0) {
      byte var2 = 0;
      return Math.cos(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double tan(double var0) {
      byte var2 = 0;
      return Math.tan(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double asin(double var0) {
      byte var2 = 0;
      return Math.asin(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double acos(double var0) {
      byte var2 = 0;
      return Math.acos(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double atan(double var0) {
      byte var2 = 0;
      return Math.atan(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double atan2(double var0, double var2) {
      byte var4 = 0;
      return Math.atan2(var0, var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double sinh(double var0) {
      byte var2 = 0;
      return Math.sinh(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double cosh(double var0) {
      byte var2 = 0;
      return Math.cosh(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double tanh(double var0) {
      byte var2 = 0;
      return Math.tanh(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final double asinh(double var0) {
      double var10000;
      if (var0 >= Constants.taylor_n_bound) {
         var10000 = var0 > Constants.upper_taylor_n_bound ? (var0 > Constants.upper_taylor_2_bound ? Math.log(var0) + Constants.LN2 : Math.log(var0 * (double)2 + (double)1 / (var0 * (double)2))) : Math.log(var0 + Math.sqrt(var0 * var0 + (double)1));
      } else if (var0 <= -Constants.taylor_n_bound) {
         var10000 = -MathKt.asinh(-var0);
      } else {
         double var2 = var0;
         if (Math.abs(var0) >= Constants.taylor_2_bound) {
            var2 = var0 - var0 * var0 * var0 / (double)6;
         }

         var10000 = var2;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final double acosh(double var0) {
      double var10000;
      if (var0 < (double)1) {
         var10000 = DoubleCompanionObject.INSTANCE.getNaN();
      } else if (var0 > Constants.upper_taylor_2_bound) {
         var10000 = Math.log(var0) + Constants.LN2;
      } else if (var0 - (double)1 >= Constants.taylor_n_bound) {
         var10000 = Math.log(var0 + Math.sqrt(var0 * var0 - (double)1));
      } else {
         double var2 = Math.sqrt(var0 - (double)1);
         double var4 = var2;
         if (var2 >= Constants.taylor_2_bound) {
            var4 = var2 - var2 * var2 * var2 / (double)12;
         }

         var10000 = Math.sqrt(2.0D) * var4;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final double atanh(double var0) {
      if (Math.abs(var0) < Constants.taylor_n_bound) {
         double var2 = var0;
         if (Math.abs(var0) > Constants.taylor_2_bound) {
            var2 = var0 + var0 * var0 * var0 / (double)3;
         }

         return var2;
      } else {
         return Math.log(((double)1 + var0) / ((double)1 - var0)) / (double)2;
      }
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double hypot(double var0, double var2) {
      byte var4 = 0;
      return Math.hypot(var0, var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double sqrt(double var0) {
      byte var2 = 0;
      return Math.sqrt(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double exp(double var0) {
      byte var2 = 0;
      return Math.exp(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double expm1(double var0) {
      byte var2 = 0;
      return Math.expm1(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final double log(double var0, double var2) {
      return var2 > 0.0D && var2 != 1.0D ? Math.log(var0) / Math.log(var2) : DoubleCompanionObject.INSTANCE.getNaN();
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double ln(double var0) {
      byte var2 = 0;
      return Math.log(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double log10(double var0) {
      byte var2 = 0;
      return Math.log10(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final double log2(double var0) {
      return Math.log(var0) / Constants.LN2;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double ln1p(double var0) {
      byte var2 = 0;
      return Math.log1p(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double ceil(double var0) {
      byte var2 = 0;
      return Math.ceil(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double floor(double var0) {
      byte var2 = 0;
      return Math.floor(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final double truncate(double var0) {
      boolean var4 = false;
      double var10000;
      if (!Double.isNaN(var0)) {
         var4 = false;
         if (!Double.isInfinite(var0)) {
            boolean var2;
            if (var0 > (double)0) {
               var2 = false;
               var10000 = Math.floor(var0);
            } else {
               var2 = false;
               var10000 = Math.ceil(var0);
            }

            return var10000;
         }
      }

      var10000 = var0;
      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double round(double var0) {
      byte var2 = 0;
      return Math.rint(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double abs(double var0) {
      byte var2 = 0;
      return Math.abs(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double sign(double var0) {
      byte var2 = 0;
      return Math.signum(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double min(double var0, double var2) {
      byte var4 = 0;
      return Math.min(var0, var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double max(double var0, double var2) {
      byte var4 = 0;
      return Math.max(var0, var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double pow(double var0, double var2) {
      byte var4 = 0;
      return Math.pow(var0, var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double pow(double var0, int var2) {
      byte var3 = 0;
      return Math.pow(var0, (double)var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double IEEErem(double var0, double var2) {
      byte var4 = 0;
      return Math.IEEEremainder(var0, var2);
   }

   /** @deprecated */
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   public static void absoluteValue$annotations(double var0) {
   }

   private static final double getAbsoluteValue(double var0) {
      byte var2 = 0;
      return Math.abs(var0);
   }

   /** @deprecated */
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   public static void sign$annotations(double var0) {
   }

   private static final double getSign(double var0) {
      byte var2 = 0;
      return Math.signum(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double withSign(double var0, double var2) {
      byte var4 = 0;
      return Math.copySign(var0, var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double withSign(double var0, int var2) {
      byte var3 = 0;
      return Math.copySign(var0, (double)var2);
   }

   /** @deprecated */
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   public static void ulp$annotations(double var0) {
   }

   private static final double getUlp(double var0) {
      byte var2 = 0;
      return Math.ulp(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double nextUp(double var0) {
      byte var2 = 0;
      return Math.nextUp(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double nextDown(double var0) {
      byte var2 = 0;
      return Math.nextAfter(var0, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double nextTowards(double var0, double var2) {
      byte var4 = 0;
      return Math.nextAfter(var0, var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final int roundToInt(double var0) {
      boolean var4 = false;
      if (Double.isNaN(var0)) {
         throw (Throwable)(new IllegalArgumentException("Cannot round NaN value."));
      } else {
         return var0 > (double)0 ? 0 : (var0 < (double)Integer.MIN_VALUE ? Integer.MIN_VALUE : (int)Math.round(var0));
      }
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final long roundToLong(double var0) {
      boolean var4 = false;
      if (Double.isNaN(var0)) {
         throw (Throwable)(new IllegalArgumentException("Cannot round NaN value."));
      } else {
         return Math.round(var0);
      }
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float sin(float var0) {
      byte var1 = 0;
      return (float)Math.sin((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float cos(float var0) {
      byte var1 = 0;
      return (float)Math.cos((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float tan(float var0) {
      byte var1 = 0;
      return (float)Math.tan((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float asin(float var0) {
      byte var1 = 0;
      return (float)Math.asin((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float acos(float var0) {
      byte var1 = 0;
      return (float)Math.acos((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float atan(float var0) {
      byte var1 = 0;
      return (float)Math.atan((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float atan2(float var0, float var1) {
      byte var2 = 0;
      return (float)Math.atan2((double)var0, (double)var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float sinh(float var0) {
      byte var1 = 0;
      return (float)Math.sinh((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float cosh(float var0) {
      byte var1 = 0;
      return (float)Math.cosh((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float tanh(float var0) {
      byte var1 = 0;
      return (float)Math.tanh((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float asinh(float var0) {
      byte var1 = 0;
      return (float)MathKt.asinh((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float acosh(float var0) {
      byte var1 = 0;
      return (float)MathKt.acosh((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float atanh(float var0) {
      byte var1 = 0;
      return (float)MathKt.atanh((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float hypot(float var0, float var1) {
      byte var2 = 0;
      return (float)Math.hypot((double)var0, (double)var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float sqrt(float var0) {
      byte var1 = 0;
      return (float)Math.sqrt((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float exp(float var0) {
      byte var1 = 0;
      return (float)Math.exp((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float expm1(float var0) {
      byte var1 = 0;
      return (float)Math.expm1((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final float log(float var0, float var1) {
      return var1 > 0.0F && var1 != 1.0F ? (float)(Math.log((double)var0) / Math.log((double)var1)) : FloatCompanionObject.INSTANCE.getNaN();
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float ln(float var0) {
      byte var1 = 0;
      return (float)Math.log((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float log10(float var0) {
      byte var1 = 0;
      return (float)Math.log10((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final float log2(float var0) {
      return (float)(Math.log((double)var0) / Constants.LN2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float ln1p(float var0) {
      byte var1 = 0;
      return (float)Math.log1p((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float ceil(float var0) {
      byte var1 = 0;
      return (float)Math.ceil((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float floor(float var0) {
      byte var1 = 0;
      return (float)Math.floor((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final float truncate(float var0) {
      boolean var2 = false;
      float var10000;
      if (!Float.isNaN(var0)) {
         var2 = false;
         if (!Float.isInfinite(var0)) {
            boolean var1;
            if (var0 > (float)0) {
               var1 = false;
               var10000 = (float)Math.floor((double)var0);
            } else {
               var1 = false;
               var10000 = (float)Math.ceil((double)var0);
            }

            return var10000;
         }
      }

      var10000 = var0;
      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float round(float var0) {
      byte var1 = 0;
      return (float)Math.rint((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float abs(float var0) {
      byte var1 = 0;
      return Math.abs(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float sign(float var0) {
      byte var1 = 0;
      return Math.signum(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float min(float var0, float var1) {
      byte var2 = 0;
      return Math.min(var0, var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float max(float var0, float var1) {
      byte var2 = 0;
      return Math.max(var0, var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float pow(float var0, float var1) {
      byte var2 = 0;
      return (float)Math.pow((double)var0, (double)var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float pow(float var0, int var1) {
      byte var2 = 0;
      return (float)Math.pow((double)var0, (double)var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float IEEErem(float var0, float var1) {
      byte var2 = 0;
      return (float)Math.IEEEremainder((double)var0, (double)var1);
   }

   /** @deprecated */
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   public static void absoluteValue$annotations(float var0) {
   }

   private static final float getAbsoluteValue(float var0) {
      byte var1 = 0;
      return Math.abs(var0);
   }

   /** @deprecated */
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   public static void sign$annotations(float var0) {
   }

   private static final float getSign(float var0) {
      byte var1 = 0;
      return Math.signum(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float withSign(float var0, float var1) {
      byte var2 = 0;
      return Math.copySign(var0, var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float withSign(float var0, int var1) {
      byte var2 = 0;
      return Math.copySign(var0, (float)var1);
   }

   /** @deprecated */
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   public static void ulp$annotations(float var0) {
   }

   private static final float getUlp(float var0) {
      byte var1 = 0;
      return Math.ulp(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float nextUp(float var0) {
      byte var1 = 0;
      return Math.nextUp(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float nextDown(float var0) {
      byte var1 = 0;
      return Math.nextAfter(var0, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float nextTowards(float var0, float var1) {
      byte var2 = 0;
      return Math.nextAfter(var0, (double)var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final int roundToInt(float var0) {
      boolean var2 = false;
      if (Float.isNaN(var0)) {
         throw (Throwable)(new IllegalArgumentException("Cannot round NaN value."));
      } else {
         return Math.round(var0);
      }
   }

   @SinceKotlin(
      version = "1.2"
   )
   public static final long roundToLong(float var0) {
      return MathKt.roundToLong((double)var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final int abs(int var0) {
      byte var1 = 0;
      return Math.abs(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final int min(int var0, int var1) {
      byte var2 = 0;
      return Math.min(var0, var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final int max(int var0, int var1) {
      byte var2 = 0;
      return Math.max(var0, var1);
   }

   /** @deprecated */
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   public static void absoluteValue$annotations(int var0) {
   }

   private static final int getAbsoluteValue(int var0) {
      byte var1 = 0;
      return Math.abs(var0);
   }

   /** @deprecated */
   @SinceKotlin(
      version = "1.2"
   )
   public static void sign$annotations(int var0) {
   }

   public static final int getSign(int var0) {
      return var0 < 0 ? -1 : (var0 > 0 ? 1 : 0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final long abs(long var0) {
      byte var2 = 0;
      return Math.abs(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final long min(long var0, long var2) {
      byte var4 = 0;
      return Math.min(var0, var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final long max(long var0, long var2) {
      byte var4 = 0;
      return Math.max(var0, var2);
   }

   /** @deprecated */
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   public static void absoluteValue$annotations(long var0) {
   }

   private static final long getAbsoluteValue(long var0) {
      byte var2 = 0;
      return Math.abs(var0);
   }

   /** @deprecated */
   @SinceKotlin(
      version = "1.2"
   )
   public static void sign$annotations(long var0) {
   }

   public static final int getSign(long var0) {
      return var0 < 0L ? -1 : (var0 > 0L ? 1 : 0);
   }

   public MathKt__MathJVMKt() {
      super();
   }
}
