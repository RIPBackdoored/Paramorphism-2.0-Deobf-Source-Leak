package kotlin.random;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b'\u0018\u0000 \u00182\u00020\u0001:\u0002\u0017\u0018B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J$\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0004H\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\u0018\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016¨\u0006\u0019"},
   d2 = {"Lkotlin/random/Random;", "", "()V", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "Companion", "Default", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
public abstract class Random {
   private static final Random defaultRandom;
   /** @deprecated */
   @JvmField
   @NotNull
   public static final Random$Companion Companion;
   public static final Random$Default Default = new Random$Default((DefaultConstructorMarker)null);

   public abstract int nextBits(int var1);

   public int nextInt() {
      return this.nextBits(32);
   }

   public int nextInt(int var1) {
      return this.nextInt(0, var1);
   }

   public int nextInt(int var1, int var2) {
      RandomKt.checkRangeBounds(var1, var2);
      int var3 = var2 - var1;
      int var4;
      if (var3 <= 0 && var3 != Integer.MIN_VALUE) {
         while(true) {
            do {
               var4 = this.nextInt();
            } while(var1 > var4 || var2 <= var4);

            return var4;
         }
      } else {
         int var10000;
         int var5;
         if ((var3 & -var3) == var3) {
            var5 = PlatformRandomKt.fastLog2(var3);
            var10000 = this.nextBits(var5);
         } else {
            boolean var7 = false;

            int var6;
            do {
               var6 = this.nextInt() >>> 1;
               var5 = var6 % var3;
            } while(var6 - var5 + (var3 - 1) < 0);

            var10000 = var5;
         }

         var4 = var10000;
         return var1 + var4;
      }
   }

   public long nextLong() {
      return ((long)this.nextInt() << 32) + (long)this.nextInt();
   }

   public long nextLong(long var1) {
      return this.nextLong(0L, var1);
   }

   public long nextLong(long var1, long var3) {
      RandomKt.checkRangeBounds(var1, var3);
      long var5 = var3 - var1;
      long var7;
      if (var5 > 0L) {
         var7 = 0L;
         if ((var5 & -var5) == var5) {
            int var9 = (int)var5;
            int var10 = (int)(var5 >>> 32);
            long var10000;
            int var11;
            if (var9 != 0) {
               var11 = PlatformRandomKt.fastLog2(var9);
               var10000 = (long)this.nextBits(var11) & 4294967295L;
            } else if (var10 == 1) {
               var10000 = (long)this.nextInt() & 4294967295L;
            } else {
               var11 = PlatformRandomKt.fastLog2(var10);
               var10000 = ((long)this.nextBits(var11) << 32) + (long)this.nextInt();
            }

            var7 = var10000;
         } else {
            long var13 = 0L;

            long var14;
            do {
               var14 = this.nextLong() >>> 1;
               var13 = var14 % var5;
            } while(var14 - var13 + (var5 - 1L) < 0L);

            var7 = var13;
         }

         return var1 + var7;
      } else {
         while(true) {
            do {
               var7 = this.nextLong();
            } while(var1 > var7 || var3 <= var7);

            return var7;
         }
      }
   }

   public boolean nextBoolean() {
      return this.nextBits(1) != 0;
   }

   public double nextDouble() {
      return PlatformRandomKt.doubleFromParts(this.nextBits(26), this.nextBits(27));
   }

   public double nextDouble(double var1) {
      return this.nextDouble(0.0D, var1);
   }

   public double nextDouble(double var1, double var3) {
      boolean var11;
      double var15;
      label37: {
         RandomKt.checkRangeBounds(var1, var3);
         double var5 = var3 - var1;
         var11 = false;
         if (Double.isInfinite(var5)) {
            boolean var10000;
            boolean var14;
            label34: {
               var11 = false;
               var14 = false;
               if (!Double.isInfinite(var1)) {
                  var14 = false;
                  if (!Double.isNaN(var1)) {
                     var10000 = true;
                     break label34;
                  }
               }

               var10000 = false;
            }

            if (var10000) {
               label28: {
                  var11 = false;
                  var14 = false;
                  if (!Double.isInfinite(var3)) {
                     var14 = false;
                     if (!Double.isNaN(var3)) {
                        var10000 = true;
                        break label28;
                     }
                  }

                  var10000 = false;
               }

               if (var10000) {
                  double var9 = this.nextDouble() * (var3 / (double)2 - var1 / (double)2);
                  var15 = var1 + var9 + var9;
                  break label37;
               }
            }
         }

         var15 = var1 + this.nextDouble() * var5;
      }

      double var7 = var15;
      if (var7 >= var3) {
         var11 = false;
         var15 = Math.nextAfter(var3, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
      } else {
         var15 = var7;
      }

      return var15;
   }

   public float nextFloat() {
      return (float)this.nextBits(24) / (float)16777216;
   }

   @NotNull
   public byte[] nextBytes(@NotNull byte[] var1, int var2, int var3) {
      boolean var17;
      label49: {
         int var10000 = var1.length;
         if (0 <= var2) {
            if (var10000 >= var2) {
               var10000 = var1.length;
               if (0 <= var3) {
                  if (var10000 >= var3) {
                     var17 = true;
                     break label49;
                  }
               }
            }
         }

         var17 = false;
      }

      boolean var4 = var17;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7;
      String var15;
      if (!var4) {
         var7 = false;
         var15 = "fromIndex (" + var2 + ") or toIndex (" + var3 + ") are out of range: 0.." + var1.length + '.';
         throw (Throwable)(new IllegalArgumentException(var15.toString()));
      } else {
         var4 = var2 <= var3;
         var5 = false;
         var6 = false;
         if (!var4) {
            var7 = false;
            var15 = "fromIndex (" + var2 + ") must be not greater than toIndex (" + var3 + ").";
            throw (Throwable)(new IllegalArgumentException(var15.toString()));
         } else {
            int var12 = (var3 - var2) / 4;
            int var13 = var2;
            var6 = false;
            var7 = false;
            int var16 = 0;

            int var8;
            for(var8 = var12; var16 < var8; ++var16) {
               boolean var10 = false;
               int var11 = this.nextInt();
               var1[var13] = (byte)var11;
               var1[var13 + 1] = (byte)(var11 >>> 8);
               var1[var13 + 2] = (byte)(var11 >>> 16);
               var1[var13 + 3] = (byte)(var11 >>> 24);
               var13 += 4;
            }

            int var14 = var3 - var13;
            var16 = this.nextBits(var14 * 8);
            var8 = 0;

            for(int var9 = var14; var8 < var9; ++var8) {
               var1[var13 + var8] = (byte)(var16 >>> var8 * 8);
            }

            return var1;
         }
      }
   }

   public static byte[] nextBytes$default(Random var0, byte[] var1, int var2, int var3, int var4, Object var5) {
      if (var5 != null) {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: nextBytes");
      } else {
         if ((var4 & 2) != 0) {
            var2 = 0;
         }

         if ((var4 & 4) != 0) {
            var3 = var1.length;
         }

         return var0.nextBytes(var1, var2, var3);
      }
   }

   @NotNull
   public byte[] nextBytes(@NotNull byte[] var1) {
      return this.nextBytes(var1, 0, var1.length);
   }

   @NotNull
   public byte[] nextBytes(int var1) {
      return this.nextBytes(new byte[var1]);
   }

   public Random() {
      super();
   }

   static {
      boolean var0 = false;
      defaultRandom = PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
      Companion = Random$Companion.INSTANCE;
   }

   public static final Random access$getDefaultRandom$cp() {
      return defaultRandom;
   }
}
