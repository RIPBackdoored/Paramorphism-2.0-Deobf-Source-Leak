package paramorphism-obfuscator;

public class g {
   public g() {
      super();
   }

   public static double[] a(double[] var0) {
      int var1 = var0.length / 2;
      h var2 = new h(var1, var1);
      h var3 = new h(var1, 1);

      int var4;
      for(var4 = 0; var4 < var1; ++var4) {
         var3.w[var4] = var0[2 * var4 + 1];
      }

      for(var4 = 0; var4 < var1; ++var4) {
         double var5 = 1.0D;

         for(int var7 = 0; var7 < var1; ++var7) {
            var2.w[var4 * var1 + var7] = var5;
            var5 *= var0[2 * var4];
         }
      }

      h var8 = new h(var1, var1);
      h.a(var2, var8, var1);
      h var9 = new h(var1, 1);
      h.a(var8, var3, var9);
      return var9.w;
   }
}
