package paramorphism-obfuscator;

import java.util.Map;
import java.util.Set;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig;

public final class od extends Lambda implements Function0 {
   public final Map yc;
   public final String yd;
   public final kg ye;
   public final oh yf;
   public final lc yg;
   public final RemappingStrategyConfig yh;
   public final lu yi;

   public od(Map var1, String var2, kg var3, oh var4, lc var5, RemappingStrategyConfig var6, lu var7) {
      super(0);
      this.yc = var1;
      this.yd = var2;
      this.ye = var3;
      this.yf = var4;
      this.yg = var5;
      this.yh = var6;
      this.yi = var7;
   }

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final String a() {
      int var1 = 0;

      while(true) {
         String var10000 = this.yi.a(-2, var1, oj.zh);
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         String var2 = var10000;
         Set var3 = (Set)this.yc.get(this.yd);
         if (!(var3 != null ? var3.contains(var2) : false)) {
            return var2;
         }

         ++var1;
      }
   }
}
