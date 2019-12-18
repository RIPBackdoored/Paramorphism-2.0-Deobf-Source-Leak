package paramorphism-obfuscator;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.packing.PackingStrategyConfig;

public final class mz extends mm {
   @NotNull
   private final PackingStrategyConfig wa;

   protected void f(@NotNull kg var1) {
      nd var2 = (nd)(new nk(var1.h().a()));
      Function1 var3 = this.e(var1);
      boolean var5 = false;
      Set var4 = (Set)(new LinkedHashSet());
      this.a(var1, Reflection.getOrCreateKotlinClass(lx.class), (Function1)(new na(var4, var1)));
      nf var6 = new nf(this, var3, var2);
      this.a(var1, Reflection.getOrCreateKotlinClass(me.class), (Function1)(new mu(var6)));
      this.a(var1, Reflection.getOrCreateKotlinClass(lz.class), (Function1)(new ng(var4, var6)));
      this.a(var1, Reflection.getOrCreateKotlinClass(mg.class), (Function1)(new mx(this, var1, var2)));
   }

   @NotNull
   protected PackingStrategyConfig b() {
      return this.wa;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.b();
   }

   public mz(@NotNull PackingStrategyConfig var1) {
      super(mq.vj);
      this.wa = var1;
   }
}
