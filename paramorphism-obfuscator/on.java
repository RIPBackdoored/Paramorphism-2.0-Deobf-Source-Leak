package paramorphism-obfuscator;

import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig;

public final class on extends Lambda implements Function3 {
   public final kg zm;
   public final oh zn;
   public final lc zo;
   public final RemappingStrategyConfig zp;
   public final lu zq;

   public on(kg var1, oh var2, lc var3, RemappingStrategyConfig var4, lu var5) {
      super(3);
      this.zm = var1;
      this.zn = var2;
      this.zo = var3;
      this.zp = var4;
      this.zq = var5;
   }

   public Object invoke(Object var1, Object var2, Object var3) {
      return this.a((String)var1, (String)var2, (String)var3);
   }

   @Nullable
   public final String a(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      Map var10000 = (Map)this.zn.d().get(var1);
      oi var4 = var10000 != null ? (oi)var10000.get(var2 + ' ' + var3) : null;
      if (var4 != null) {
         String var6;
         if (var4 instanceof ok) {
            var6 = ((ok)var4).a();
         } else {
            if (!Intrinsics.areEqual((Object)var4, (Object)oo.zr)) {
               throw new NoWhenBranchMatchedException();
            }

            var6 = null;
         }

         return var6;
      } else {
         return null;
      }
   }
}
