package paramorphism-obfuscator;

public class h {
   public int u;
   public int v;
   public double[] w;

   public h(int var1, int var2) {
      super();
      this.u = var1;
      this.v = var2;
      this.w = new double[var1 * var2];
   }

   public static void a(h var0, h var1, h var2) {
      int var5 = 0;
      int var6 = var1.u * var1.v;

      for(int var7 = 0; var7 < var0.u; ++var7) {
         int var8 = var7 * var0.v;
         int var9 = 0;
         int var10 = var5;
         int var11 = var9 + var1.v;

         double var3;
         for(var3 = var0.w[var8++]; var9 < var11; var2.w[var10++] = var3 * var1.w[var9++]) {
         }

         while(var9 != var6) {
            var10 = var5;
            var11 = var9 + var1.v;

            double[] var10000;
            int var10001;
            for(var3 = var0.w[var8++]; var9 < var11; var10000[var10001] += var3 * var1.w[var9++]) {
               var10000 = var2.w;
               var10001 = var10++;
            }
         }

         var5 += var2.v;
      }

   }

   public static void a(h var0, h var1, int var2) {
      double var3 = Math.abs(var0.w[0]);
      int var5 = var0.w.length;

      for(int var6 = 1; var6 < var5; ++var6) {
         double var7 = Math.abs(var0.w[var6]);
         if (var7 > var3) {
            var3 = var7;
         }
      }

      switch(var2) {
      case 0:
      default:
         break;
      case 1:
         a(var0, var1);
         break;
      case 2:
         a(var0, var1, var3);
         break;
      case 3:
         b(var0, var1, var3);
         break;
      case 4:
         c(var0, var1, var3);
         break;
      case 5:
         d(var0, var1, var3);
      }

   }

   public static void a(h var0, h var1) {
      var1.w[0] = 1.0D / var0.w[0];
   }

   public static void a(h var0, h var1, double var2) {
      double[] var4 = var0.w;
      double var5 = var4[0] * var2;
      double var7 = var4[1] * var2;
      double var9 = var4[2] * var2;
      double var11 = var4[3] * var2;
      double var15 = -var9;
      double var17 = -var7;
      double var21 = (var5 * var11 + var7 * var15) / var2;
      var4 = var1.w;
      var4[0] = var11 / var21;
      var4[1] = var17 / var21;
      var4[2] = var15 / var21;
      var4[3] = var5 / var21;
   }

   public static void b(h var0, h var1, double var2) {
      double[] var4 = var0.w;
      double var5 = var4[0] * var2;
      double var7 = var4[1] * var2;
      double var9 = var4[2] * var2;
      double var11 = var4[3] * var2;
      double var13 = var4[4] * var2;
      double var15 = var4[5] * var2;
      double var17 = var4[6] * var2;
      double var19 = var4[7] * var2;
      double var21 = var4[8] * var2;
      double var23 = var13 * var21 - var15 * var19;
      double var25 = -(var11 * var21 - var15 * var17);
      double var27 = var11 * var19 - var13 * var17;
      double var29 = -(var7 * var21 - var9 * var19);
      double var31 = var5 * var21 - var9 * var17;
      double var33 = -(var5 * var19 - var7 * var17);
      double var35 = var7 * var15 - var9 * var13;
      double var37 = -(var5 * var15 - var9 * var11);
      double var39 = var5 * var13 - var7 * var11;
      double var41 = (var5 * var23 + var7 * var25 + var9 * var27) / var2;
      var4 = var1.w;
      var4[0] = var23 / var41;
      var4[1] = var29 / var41;
      var4[2] = var35 / var41;
      var4[3] = var25 / var41;
      var4[4] = var31 / var41;
      var4[5] = var37 / var41;
      var4[6] = var27 / var41;
      var4[7] = var33 / var41;
      var4[8] = var39 / var41;
   }

   public static void c(h var0, h var1, double var2) {
      double[] var4 = var0.w;
      double var5 = var4[0] * var2;
      double var7 = var4[1] * var2;
      double var9 = var4[2] * var2;
      double var11 = var4[3] * var2;
      double var13 = var4[4] * var2;
      double var15 = var4[5] * var2;
      double var17 = var4[6] * var2;
      double var19 = var4[7] * var2;
      double var21 = var4[8] * var2;
      double var23 = var4[9] * var2;
      double var25 = var4[10] * var2;
      double var27 = var4[11] * var2;
      double var29 = var4[12] * var2;
      double var31 = var4[13] * var2;
      double var33 = var4[14] * var2;
      double var35 = var4[15] * var2;
      double var37 = var15 * (var25 * var35 - var27 * var33) - var17 * (var23 * var35 - var27 * var31) + var19 * (var23 * var33 - var25 * var31);
      double var39 = -(var13 * (var25 * var35 - var27 * var33) - var17 * (var21 * var35 - var27 * var29) + var19 * (var21 * var33 - var25 * var29));
      double var41 = var13 * (var23 * var35 - var27 * var31) - var15 * (var21 * var35 - var27 * var29) + var19 * (var21 * var31 - var23 * var29);
      double var43 = -(var13 * (var23 * var33 - var25 * var31) - var15 * (var21 * var33 - var25 * var29) + var17 * (var21 * var31 - var23 * var29));
      double var45 = -(var7 * (var25 * var35 - var27 * var33) - var9 * (var23 * var35 - var27 * var31) + var11 * (var23 * var33 - var25 * var31));
      double var47 = var5 * (var25 * var35 - var27 * var33) - var9 * (var21 * var35 - var27 * var29) + var11 * (var21 * var33 - var25 * var29);
      double var49 = -(var5 * (var23 * var35 - var27 * var31) - var7 * (var21 * var35 - var27 * var29) + var11 * (var21 * var31 - var23 * var29));
      double var51 = var5 * (var23 * var33 - var25 * var31) - var7 * (var21 * var33 - var25 * var29) + var9 * (var21 * var31 - var23 * var29);
      double var53 = var7 * (var17 * var35 - var19 * var33) - var9 * (var15 * var35 - var19 * var31) + var11 * (var15 * var33 - var17 * var31);
      double var55 = -(var5 * (var17 * var35 - var19 * var33) - var9 * (var13 * var35 - var19 * var29) + var11 * (var13 * var33 - var17 * var29));
      double var57 = var5 * (var15 * var35 - var19 * var31) - var7 * (var13 * var35 - var19 * var29) + var11 * (var13 * var31 - var15 * var29);
      double var59 = -(var5 * (var15 * var33 - var17 * var31) - var7 * (var13 * var33 - var17 * var29) + var9 * (var13 * var31 - var15 * var29));
      double var61 = -(var7 * (var17 * var27 - var19 * var25) - var9 * (var15 * var27 - var19 * var23) + var11 * (var15 * var25 - var17 * var23));
      double var63 = var5 * (var17 * var27 - var19 * var25) - var9 * (var13 * var27 - var19 * var21) + var11 * (var13 * var25 - var17 * var21);
      double var65 = -(var5 * (var15 * var27 - var19 * var23) - var7 * (var13 * var27 - var19 * var21) + var11 * (var13 * var23 - var15 * var21));
      double var67 = var5 * (var15 * var25 - var17 * var23) - var7 * (var13 * var25 - var17 * var21) + var9 * (var13 * var23 - var15 * var21);
      double var69 = (var5 * var37 + var7 * var39 + var9 * var41 + var11 * var43) / var2;
      var4 = var1.w;
      var4[0] = var37 / var69;
      var4[1] = var45 / var69;
      var4[2] = var53 / var69;
      var4[3] = var61 / var69;
      var4[4] = var39 / var69;
      var4[5] = var47 / var69;
      var4[6] = var55 / var69;
      var4[7] = var63 / var69;
      var4[8] = var41 / var69;
      var4[9] = var49 / var69;
      var4[10] = var57 / var69;
      var4[11] = var65 / var69;
      var4[12] = var43 / var69;
      var4[13] = var51 / var69;
      var4[14] = var59 / var69;
      var4[15] = var67 / var69;
   }

   public static void d(h var0, h var1, double var2) {
      double[] var4 = var0.w;
      double var5 = var4[0] * var2;
      double var7 = var4[1] * var2;
      double var9 = var4[2] * var2;
      double var11 = var4[3] * var2;
      double var13 = var4[4] * var2;
      double var15 = var4[5] * var2;
      double var17 = var4[6] * var2;
      double var19 = var4[7] * var2;
      double var21 = var4[8] * var2;
      double var23 = var4[9] * var2;
      double var25 = var4[10] * var2;
      double var27 = var4[11] * var2;
      double var29 = var4[12] * var2;
      double var31 = var4[13] * var2;
      double var33 = var4[14] * var2;
      double var35 = var4[15] * var2;
      double var37 = var4[16] * var2;
      double var39 = var4[17] * var2;
      double var41 = var4[18] * var2;
      double var43 = var4[19] * var2;
      double var45 = var4[20] * var2;
      double var47 = var4[21] * var2;
      double var49 = var4[22] * var2;
      double var51 = var4[23] * var2;
      double var53 = var4[24] * var2;
      double var55 = var17 * (var29 * (var41 * var53 - var43 * var51) - var31 * (var39 * var53 - var43 * var49) + var33 * (var39 * var51 - var41 * var49)) - var19 * (var27 * (var41 * var53 - var43 * var51) - var31 * (var37 * var53 - var43 * var47) + var33 * (var37 * var51 - var41 * var47)) + var21 * (var27 * (var39 * var53 - var43 * var49) - var29 * (var37 * var53 - var43 * var47) + var33 * (var37 * var49 - var39 * var47)) - var23 * (var27 * (var39 * var51 - var41 * var49) - var29 * (var37 * var51 - var41 * var47) + var31 * (var37 * var49 - var39 * var47));
      double var57 = -(var15 * (var29 * (var41 * var53 - var43 * var51) - var31 * (var39 * var53 - var43 * var49) + var33 * (var39 * var51 - var41 * var49)) - var19 * (var25 * (var41 * var53 - var43 * var51) - var31 * (var35 * var53 - var43 * var45) + var33 * (var35 * var51 - var41 * var45)) + var21 * (var25 * (var39 * var53 - var43 * var49) - var29 * (var35 * var53 - var43 * var45) + var33 * (var35 * var49 - var39 * var45)) - var23 * (var25 * (var39 * var51 - var41 * var49) - var29 * (var35 * var51 - var41 * var45) + var31 * (var35 * var49 - var39 * var45)));
      double var59 = var15 * (var27 * (var41 * var53 - var43 * var51) - var31 * (var37 * var53 - var43 * var47) + var33 * (var37 * var51 - var41 * var47)) - var17 * (var25 * (var41 * var53 - var43 * var51) - var31 * (var35 * var53 - var43 * var45) + var33 * (var35 * var51 - var41 * var45)) + var21 * (var25 * (var37 * var53 - var43 * var47) - var27 * (var35 * var53 - var43 * var45) + var33 * (var35 * var47 - var37 * var45)) - var23 * (var25 * (var37 * var51 - var41 * var47) - var27 * (var35 * var51 - var41 * var45) + var31 * (var35 * var47 - var37 * var45));
      double var61 = -(var15 * (var27 * (var39 * var53 - var43 * var49) - var29 * (var37 * var53 - var43 * var47) + var33 * (var37 * var49 - var39 * var47)) - var17 * (var25 * (var39 * var53 - var43 * var49) - var29 * (var35 * var53 - var43 * var45) + var33 * (var35 * var49 - var39 * var45)) + var19 * (var25 * (var37 * var53 - var43 * var47) - var27 * (var35 * var53 - var43 * var45) + var33 * (var35 * var47 - var37 * var45)) - var23 * (var25 * (var37 * var49 - var39 * var47) - var27 * (var35 * var49 - var39 * var45) + var29 * (var35 * var47 - var37 * var45)));
      double var63 = var15 * (var27 * (var39 * var51 - var41 * var49) - var29 * (var37 * var51 - var41 * var47) + var31 * (var37 * var49 - var39 * var47)) - var17 * (var25 * (var39 * var51 - var41 * var49) - var29 * (var35 * var51 - var41 * var45) + var31 * (var35 * var49 - var39 * var45)) + var19 * (var25 * (var37 * var51 - var41 * var47) - var27 * (var35 * var51 - var41 * var45) + var31 * (var35 * var47 - var37 * var45)) - var21 * (var25 * (var37 * var49 - var39 * var47) - var27 * (var35 * var49 - var39 * var45) + var29 * (var35 * var47 - var37 * var45));
      double var65 = -(var7 * (var29 * (var41 * var53 - var43 * var51) - var31 * (var39 * var53 - var43 * var49) + var33 * (var39 * var51 - var41 * var49)) - var9 * (var27 * (var41 * var53 - var43 * var51) - var31 * (var37 * var53 - var43 * var47) + var33 * (var37 * var51 - var41 * var47)) + var11 * (var27 * (var39 * var53 - var43 * var49) - var29 * (var37 * var53 - var43 * var47) + var33 * (var37 * var49 - var39 * var47)) - var13 * (var27 * (var39 * var51 - var41 * var49) - var29 * (var37 * var51 - var41 * var47) + var31 * (var37 * var49 - var39 * var47)));
      double var67 = var5 * (var29 * (var41 * var53 - var43 * var51) - var31 * (var39 * var53 - var43 * var49) + var33 * (var39 * var51 - var41 * var49)) - var9 * (var25 * (var41 * var53 - var43 * var51) - var31 * (var35 * var53 - var43 * var45) + var33 * (var35 * var51 - var41 * var45)) + var11 * (var25 * (var39 * var53 - var43 * var49) - var29 * (var35 * var53 - var43 * var45) + var33 * (var35 * var49 - var39 * var45)) - var13 * (var25 * (var39 * var51 - var41 * var49) - var29 * (var35 * var51 - var41 * var45) + var31 * (var35 * var49 - var39 * var45));
      double var69 = -(var5 * (var27 * (var41 * var53 - var43 * var51) - var31 * (var37 * var53 - var43 * var47) + var33 * (var37 * var51 - var41 * var47)) - var7 * (var25 * (var41 * var53 - var43 * var51) - var31 * (var35 * var53 - var43 * var45) + var33 * (var35 * var51 - var41 * var45)) + var11 * (var25 * (var37 * var53 - var43 * var47) - var27 * (var35 * var53 - var43 * var45) + var33 * (var35 * var47 - var37 * var45)) - var13 * (var25 * (var37 * var51 - var41 * var47) - var27 * (var35 * var51 - var41 * var45) + var31 * (var35 * var47 - var37 * var45)));
      double var71 = var5 * (var27 * (var39 * var53 - var43 * var49) - var29 * (var37 * var53 - var43 * var47) + var33 * (var37 * var49 - var39 * var47)) - var7 * (var25 * (var39 * var53 - var43 * var49) - var29 * (var35 * var53 - var43 * var45) + var33 * (var35 * var49 - var39 * var45)) + var9 * (var25 * (var37 * var53 - var43 * var47) - var27 * (var35 * var53 - var43 * var45) + var33 * (var35 * var47 - var37 * var45)) - var13 * (var25 * (var37 * var49 - var39 * var47) - var27 * (var35 * var49 - var39 * var45) + var29 * (var35 * var47 - var37 * var45));
      double var73 = -(var5 * (var27 * (var39 * var51 - var41 * var49) - var29 * (var37 * var51 - var41 * var47) + var31 * (var37 * var49 - var39 * var47)) - var7 * (var25 * (var39 * var51 - var41 * var49) - var29 * (var35 * var51 - var41 * var45) + var31 * (var35 * var49 - var39 * var45)) + var9 * (var25 * (var37 * var51 - var41 * var47) - var27 * (var35 * var51 - var41 * var45) + var31 * (var35 * var47 - var37 * var45)) - var11 * (var25 * (var37 * var49 - var39 * var47) - var27 * (var35 * var49 - var39 * var45) + var29 * (var35 * var47 - var37 * var45)));
      double var75 = var7 * (var19 * (var41 * var53 - var43 * var51) - var21 * (var39 * var53 - var43 * var49) + var23 * (var39 * var51 - var41 * var49)) - var9 * (var17 * (var41 * var53 - var43 * var51) - var21 * (var37 * var53 - var43 * var47) + var23 * (var37 * var51 - var41 * var47)) + var11 * (var17 * (var39 * var53 - var43 * var49) - var19 * (var37 * var53 - var43 * var47) + var23 * (var37 * var49 - var39 * var47)) - var13 * (var17 * (var39 * var51 - var41 * var49) - var19 * (var37 * var51 - var41 * var47) + var21 * (var37 * var49 - var39 * var47));
      double var77 = -(var5 * (var19 * (var41 * var53 - var43 * var51) - var21 * (var39 * var53 - var43 * var49) + var23 * (var39 * var51 - var41 * var49)) - var9 * (var15 * (var41 * var53 - var43 * var51) - var21 * (var35 * var53 - var43 * var45) + var23 * (var35 * var51 - var41 * var45)) + var11 * (var15 * (var39 * var53 - var43 * var49) - var19 * (var35 * var53 - var43 * var45) + var23 * (var35 * var49 - var39 * var45)) - var13 * (var15 * (var39 * var51 - var41 * var49) - var19 * (var35 * var51 - var41 * var45) + var21 * (var35 * var49 - var39 * var45)));
      double var79 = var5 * (var17 * (var41 * var53 - var43 * var51) - var21 * (var37 * var53 - var43 * var47) + var23 * (var37 * var51 - var41 * var47)) - var7 * (var15 * (var41 * var53 - var43 * var51) - var21 * (var35 * var53 - var43 * var45) + var23 * (var35 * var51 - var41 * var45)) + var11 * (var15 * (var37 * var53 - var43 * var47) - var17 * (var35 * var53 - var43 * var45) + var23 * (var35 * var47 - var37 * var45)) - var13 * (var15 * (var37 * var51 - var41 * var47) - var17 * (var35 * var51 - var41 * var45) + var21 * (var35 * var47 - var37 * var45));
      double var81 = -(var5 * (var17 * (var39 * var53 - var43 * var49) - var19 * (var37 * var53 - var43 * var47) + var23 * (var37 * var49 - var39 * var47)) - var7 * (var15 * (var39 * var53 - var43 * var49) - var19 * (var35 * var53 - var43 * var45) + var23 * (var35 * var49 - var39 * var45)) + var9 * (var15 * (var37 * var53 - var43 * var47) - var17 * (var35 * var53 - var43 * var45) + var23 * (var35 * var47 - var37 * var45)) - var13 * (var15 * (var37 * var49 - var39 * var47) - var17 * (var35 * var49 - var39 * var45) + var19 * (var35 * var47 - var37 * var45)));
      double var83 = var5 * (var17 * (var39 * var51 - var41 * var49) - var19 * (var37 * var51 - var41 * var47) + var21 * (var37 * var49 - var39 * var47)) - var7 * (var15 * (var39 * var51 - var41 * var49) - var19 * (var35 * var51 - var41 * var45) + var21 * (var35 * var49 - var39 * var45)) + var9 * (var15 * (var37 * var51 - var41 * var47) - var17 * (var35 * var51 - var41 * var45) + var21 * (var35 * var47 - var37 * var45)) - var11 * (var15 * (var37 * var49 - var39 * var47) - var17 * (var35 * var49 - var39 * var45) + var19 * (var35 * var47 - var37 * var45));
      double var85 = -(var7 * (var19 * (var31 * var53 - var33 * var51) - var21 * (var29 * var53 - var33 * var49) + var23 * (var29 * var51 - var31 * var49)) - var9 * (var17 * (var31 * var53 - var33 * var51) - var21 * (var27 * var53 - var33 * var47) + var23 * (var27 * var51 - var31 * var47)) + var11 * (var17 * (var29 * var53 - var33 * var49) - var19 * (var27 * var53 - var33 * var47) + var23 * (var27 * var49 - var29 * var47)) - var13 * (var17 * (var29 * var51 - var31 * var49) - var19 * (var27 * var51 - var31 * var47) + var21 * (var27 * var49 - var29 * var47)));
      double var87 = var5 * (var19 * (var31 * var53 - var33 * var51) - var21 * (var29 * var53 - var33 * var49) + var23 * (var29 * var51 - var31 * var49)) - var9 * (var15 * (var31 * var53 - var33 * var51) - var21 * (var25 * var53 - var33 * var45) + var23 * (var25 * var51 - var31 * var45)) + var11 * (var15 * (var29 * var53 - var33 * var49) - var19 * (var25 * var53 - var33 * var45) + var23 * (var25 * var49 - var29 * var45)) - var13 * (var15 * (var29 * var51 - var31 * var49) - var19 * (var25 * var51 - var31 * var45) + var21 * (var25 * var49 - var29 * var45));
      double var89 = -(var5 * (var17 * (var31 * var53 - var33 * var51) - var21 * (var27 * var53 - var33 * var47) + var23 * (var27 * var51 - var31 * var47)) - var7 * (var15 * (var31 * var53 - var33 * var51) - var21 * (var25 * var53 - var33 * var45) + var23 * (var25 * var51 - var31 * var45)) + var11 * (var15 * (var27 * var53 - var33 * var47) - var17 * (var25 * var53 - var33 * var45) + var23 * (var25 * var47 - var27 * var45)) - var13 * (var15 * (var27 * var51 - var31 * var47) - var17 * (var25 * var51 - var31 * var45) + var21 * (var25 * var47 - var27 * var45)));
      double var91 = var5 * (var17 * (var29 * var53 - var33 * var49) - var19 * (var27 * var53 - var33 * var47) + var23 * (var27 * var49 - var29 * var47)) - var7 * (var15 * (var29 * var53 - var33 * var49) - var19 * (var25 * var53 - var33 * var45) + var23 * (var25 * var49 - var29 * var45)) + var9 * (var15 * (var27 * var53 - var33 * var47) - var17 * (var25 * var53 - var33 * var45) + var23 * (var25 * var47 - var27 * var45)) - var13 * (var15 * (var27 * var49 - var29 * var47) - var17 * (var25 * var49 - var29 * var45) + var19 * (var25 * var47 - var27 * var45));
      double var93 = -(var5 * (var17 * (var29 * var51 - var31 * var49) - var19 * (var27 * var51 - var31 * var47) + var21 * (var27 * var49 - var29 * var47)) - var7 * (var15 * (var29 * var51 - var31 * var49) - var19 * (var25 * var51 - var31 * var45) + var21 * (var25 * var49 - var29 * var45)) + var9 * (var15 * (var27 * var51 - var31 * var47) - var17 * (var25 * var51 - var31 * var45) + var21 * (var25 * var47 - var27 * var45)) - var11 * (var15 * (var27 * var49 - var29 * var47) - var17 * (var25 * var49 - var29 * var45) + var19 * (var25 * var47 - var27 * var45)));
      double var95 = var7 * (var19 * (var31 * var43 - var33 * var41) - var21 * (var29 * var43 - var33 * var39) + var23 * (var29 * var41 - var31 * var39)) - var9 * (var17 * (var31 * var43 - var33 * var41) - var21 * (var27 * var43 - var33 * var37) + var23 * (var27 * var41 - var31 * var37)) + var11 * (var17 * (var29 * var43 - var33 * var39) - var19 * (var27 * var43 - var33 * var37) + var23 * (var27 * var39 - var29 * var37)) - var13 * (var17 * (var29 * var41 - var31 * var39) - var19 * (var27 * var41 - var31 * var37) + var21 * (var27 * var39 - var29 * var37));
      double var97 = -(var5 * (var19 * (var31 * var43 - var33 * var41) - var21 * (var29 * var43 - var33 * var39) + var23 * (var29 * var41 - var31 * var39)) - var9 * (var15 * (var31 * var43 - var33 * var41) - var21 * (var25 * var43 - var33 * var35) + var23 * (var25 * var41 - var31 * var35)) + var11 * (var15 * (var29 * var43 - var33 * var39) - var19 * (var25 * var43 - var33 * var35) + var23 * (var25 * var39 - var29 * var35)) - var13 * (var15 * (var29 * var41 - var31 * var39) - var19 * (var25 * var41 - var31 * var35) + var21 * (var25 * var39 - var29 * var35)));
      double var99 = var5 * (var17 * (var31 * var43 - var33 * var41) - var21 * (var27 * var43 - var33 * var37) + var23 * (var27 * var41 - var31 * var37)) - var7 * (var15 * (var31 * var43 - var33 * var41) - var21 * (var25 * var43 - var33 * var35) + var23 * (var25 * var41 - var31 * var35)) + var11 * (var15 * (var27 * var43 - var33 * var37) - var17 * (var25 * var43 - var33 * var35) + var23 * (var25 * var37 - var27 * var35)) - var13 * (var15 * (var27 * var41 - var31 * var37) - var17 * (var25 * var41 - var31 * var35) + var21 * (var25 * var37 - var27 * var35));
      double var101 = -(var5 * (var17 * (var29 * var43 - var33 * var39) - var19 * (var27 * var43 - var33 * var37) + var23 * (var27 * var39 - var29 * var37)) - var7 * (var15 * (var29 * var43 - var33 * var39) - var19 * (var25 * var43 - var33 * var35) + var23 * (var25 * var39 - var29 * var35)) + var9 * (var15 * (var27 * var43 - var33 * var37) - var17 * (var25 * var43 - var33 * var35) + var23 * (var25 * var37 - var27 * var35)) - var13 * (var15 * (var27 * var39 - var29 * var37) - var17 * (var25 * var39 - var29 * var35) + var19 * (var25 * var37 - var27 * var35)));
      double var103 = var5 * (var17 * (var29 * var41 - var31 * var39) - var19 * (var27 * var41 - var31 * var37) + var21 * (var27 * var39 - var29 * var37)) - var7 * (var15 * (var29 * var41 - var31 * var39) - var19 * (var25 * var41 - var31 * var35) + var21 * (var25 * var39 - var29 * var35)) + var9 * (var15 * (var27 * var41 - var31 * var37) - var17 * (var25 * var41 - var31 * var35) + var21 * (var25 * var37 - var27 * var35)) - var11 * (var15 * (var27 * var39 - var29 * var37) - var17 * (var25 * var39 - var29 * var35) + var19 * (var25 * var37 - var27 * var35));
      double var105 = (var5 * var55 + var7 * var57 + var9 * var59 + var11 * var61 + var13 * var63) / var2;
      var4 = var1.w;
      var4[0] = var55 / var105;
      var4[1] = var65 / var105;
      var4[2] = var75 / var105;
      var4[3] = var85 / var105;
      var4[4] = var95 / var105;
      var4[5] = var57 / var105;
      var4[6] = var67 / var105;
      var4[7] = var77 / var105;
      var4[8] = var87 / var105;
      var4[9] = var97 / var105;
      var4[10] = var59 / var105;
      var4[11] = var69 / var105;
      var4[12] = var79 / var105;
      var4[13] = var89 / var105;
      var4[14] = var99 / var105;
      var4[15] = var61 / var105;
      var4[16] = var71 / var105;
      var4[17] = var81 / var105;
      var4[18] = var91 / var105;
      var4[19] = var101 / var105;
      var4[20] = var63 / var105;
      var4[21] = var73 / var105;
      var4[22] = var83 / var105;
      var4[23] = var93 / var105;
      var4[24] = var103 / var105;
   }
}
