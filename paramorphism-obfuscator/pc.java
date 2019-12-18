package paramorphism-obfuscator;

import java.util.Map;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class pc extends Lambda implements Function3 {
   public final ot bav;

   public Object invoke(Object var1, Object var2, Object var3) {
      return this.a((String)var1, (String)var2, (String)var3);
   }

   @Nullable
   public final String a(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      le var4 = this.bav.e().a(var1);
      boolean var7;
      String var26;
      if ((Boolean)this.bav.d().invoke(var1)) {
         Map var10000 = (Map)this.bav.b().e().get(var1);
         Integer var5 = var10000 != null ? (Integer)var10000.get(var2 + ' ' + var3) : null;
         if (var5 != null) {
            var26 = this.bav.c().a(-1, var5, oj.zg);
            if (var26 != null) {
               String var20 = var26;
               var7 = false;
               boolean var22 = false;
               boolean var25 = false;
               return var20;
            }
         }
      }

      if (var4 != null) {
         le var27 = var4.d();
         boolean var6;
         boolean var12;
         boolean var14;
         if (var27 != null) {
            var26 = var27.a();
            if (var26 != null) {
               String var18 = var26;
               var6 = false;
               var7 = false;
               boolean var9 = false;
               var26 = this.a(var18, var2, var3);
               if (var26 != null) {
                  String var24 = var26;
                  boolean var11 = false;
                  var12 = false;
                  var14 = false;
                  return var24;
               }

               Void var28 = (Void)null;
            }
         }

         le[] var19 = var4.e();
         var6 = false;
         le[] var21 = var19;
         int var8 = var19.length;

         for(int var23 = 0; var23 < var8; ++var23) {
            le var10 = var21[var23];
            var12 = false;
            var26 = this.a(var10.a(), var2, var3);
            if (var26 != null) {
               String var13 = var26;
               var14 = false;
               boolean var15 = false;
               boolean var17 = false;
               return var13;
            }
         }
      }

      return null;
   }

   public pc(ot var1) {
      super(3);
      this.bav = var1;
   }
}
