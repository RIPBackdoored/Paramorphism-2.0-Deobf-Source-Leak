package paramorphism-obfuscator;

import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class oy extends Lambda implements Function3 {
   public final ot bam;

   public Object invoke(Object var1, Object var2, Object var3) {
      return this.a((String)var1, (String)var2, (String)var3);
   }

   @Nullable
   public final String a(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      le var4 = this.bam.e().a(var1);
      if ((Boolean)this.bam.d().invoke(var1)) {
         Map var10000 = (Map)this.bam.b().d().get(var1);
         oi var5 = var10000 != null ? (oi)var10000.get(var2 + ' ' + var3) : null;
         if (var5 != null) {
            if (!(var5 instanceof ok)) {
               if (var5 instanceof oo) {
                  return var2;
               }

               throw new NoWhenBranchMatchedException();
            }

            String var21 = ((ok)var5).a();
            List var22 = (List)ot.a(this.bam).get(var1);
            if (var22 != null) {
               while(var22.contains(var21 + var3)) {
                  var21 = '_' + var21;
               }
            }

            return var21;
         }
      }

      if (var4 != null) {
         le var25 = var4.d();
         boolean var6;
         boolean var12;
         boolean var14;
         String var26;
         if (var25 != null) {
            var26 = var25.a();
            if (var26 != null) {
               String var18 = var26;
               var6 = false;
               boolean var7 = false;
               boolean var9 = false;
               var26 = this.a(var18, var2, var3);
               if (var26 != null) {
                  String var24 = var26;
                  boolean var11 = false;
                  var12 = false;
                  var14 = false;
                  return var24;
               }

               Void var27 = (Void)null;
            }
         }

         le[] var19 = var4.e();
         var6 = false;
         le[] var20 = var19;
         int var8 = var19.length;

         for(int var23 = 0; var23 < var8; ++var23) {
            le var10 = var20[var23];
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

   public oy(ot var1) {
      super(3);
      this.bam = var1;
   }
}
