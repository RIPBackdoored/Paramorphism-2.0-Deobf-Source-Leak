package kotlin.random;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\b\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005B7\b\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003¢\u0006\u0002\u0010\fJ\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003H\u0016J\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"},
   d2 = {"Lkotlin/random/XorWowRandom;", "Lkotlin/random/Random;", "seed1", "", "seed2", "(II)V", "x", "y", "z", "w", "v", "addend", "(IIIIII)V", "nextBits", "bitCount", "nextInt", "kotlin-stdlib"}
)
public final class XorWowRandom extends Random {
   private int x;
   private int y;
   private int z;
   private int w;
   private int v;
   private int addend;

   public int nextInt() {
      int var1 = this.x;
      var1 ^= var1 >>> 2;
      this.x = this.y;
      this.y = this.z;
      this.z = this.w;
      int var2 = this.v;
      this.w = var2;
      var1 = var1 ^ var1 << 1 ^ var2 ^ var2 << 4;
      this.v = var1;
      this.addend += 362437;
      return var1 + this.addend;
   }

   public int nextBits(int var1) {
      return RandomKt.takeUpperBits(this.nextInt(), var1);
   }

   public XorWowRandom(int var1, int var2, int var3, int var4, int var5, int var6) {
      super();
      this.x = var1;
      this.y = var2;
      this.z = var3;
      this.w = var4;
      this.v = var5;
      this.addend = var6;
      boolean var7 = (this.x | this.y | this.z | this.w | this.v) != 0;
      boolean var8 = false;
      boolean var9 = false;
      if (!var7) {
         boolean var15 = false;
         String var16 = "Initial state must have at least one non-zero element.";
         throw (Throwable)(new IllegalArgumentException(var16.toString()));
      } else {
         byte var13 = 64;
         var8 = false;
         var9 = false;
         int var14 = 0;

         for(byte var10 = var13; var14 < var10; ++var14) {
            boolean var12 = false;
            this.nextInt();
         }

      }
   }

   public XorWowRandom(int var1, int var2) {
      this(var1, var2, 0, 0, ~var1, var1 << 10 ^ var2 >>> 4);
   }
}
