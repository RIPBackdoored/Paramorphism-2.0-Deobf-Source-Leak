package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\b\u001a\r\u0010\b\u001a\u00020\t*\u00020\u0001H\u0087\b\u001a\r\u0010\b\u001a\u00020\t*\u00020\u0005H\u0087\b\u001a\r\u0010\n\u001a\u00020\t*\u00020\u0001H\u0087\b\u001a\r\u0010\n\u001a\u00020\t*\u00020\u0005H\u0087\b\u001a\r\u0010\u000b\u001a\u00020\t*\u00020\u0001H\u0087\b\u001a\r\u0010\u000b\u001a\u00020\t*\u00020\u0005H\u0087\b\u001a\r\u0010\f\u001a\u00020\u0004*\u00020\u0001H\u0087\b\u001a\r\u0010\f\u001a\u00020\u0007*\u00020\u0005H\u0087\b\u001a\r\u0010\r\u001a\u00020\u0004*\u00020\u0001H\u0087\b\u001a\r\u0010\r\u001a\u00020\u0007*\u00020\u0005H\u0087\b¨\u0006\u000e"},
   d2 = {"fromBits", "", "Lkotlin/Double$Companion;", "bits", "", "", "Lkotlin/Float$Companion;", "", "isFinite", "", "isInfinite", "isNaN", "toBits", "toRawBits", "kotlin-stdlib"},
   xs = "kotlin/MathKt"
)
class MathKt__NumbersKt extends MathKt__BigIntegersKt {
   @InlineOnly
   private static final boolean isNaN(double var0) {
      byte var2 = 0;
      return Double.isNaN(var0);
   }

   @InlineOnly
   private static final boolean isNaN(float var0) {
      byte var1 = 0;
      return Float.isNaN(var0);
   }

   @InlineOnly
   private static final boolean isInfinite(double var0) {
      byte var2 = 0;
      return Double.isInfinite(var0);
   }

   @InlineOnly
   private static final boolean isInfinite(float var0) {
      byte var1 = 0;
      return Float.isInfinite(var0);
   }

   @InlineOnly
   private static final boolean isFinite(double var0) {
      byte var2 = 0;
      boolean var5 = false;
      boolean var10000;
      if (!Double.isInfinite(var0)) {
         var5 = false;
         if (!Double.isNaN(var0)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   @InlineOnly
   private static final boolean isFinite(float var0) {
      byte var1 = 0;
      boolean var3 = false;
      boolean var10000;
      if (!Float.isInfinite(var0)) {
         var3 = false;
         if (!Float.isNaN(var0)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final long toBits(double var0) {
      byte var2 = 0;
      return Double.doubleToLongBits(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final long toRawBits(double var0) {
      byte var2 = 0;
      return Double.doubleToRawLongBits(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final double fromBits(@NotNull DoubleCompanionObject var0, long var1) {
      byte var3 = 0;
      return Double.longBitsToDouble(var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final int toBits(float var0) {
      byte var1 = 0;
      return Float.floatToIntBits(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final int toRawBits(float var0) {
      byte var1 = 0;
      return Float.floatToRawIntBits(var0);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final float fromBits(@NotNull FloatCompanionObject var0, int var1) {
      byte var2 = 0;
      return Float.intBitsToFloat(var1);
   }

   public MathKt__NumbersKt() {
      super();
   }
}
