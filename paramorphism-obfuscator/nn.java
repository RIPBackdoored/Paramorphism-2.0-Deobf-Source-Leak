package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.scenarios.spigot.SpigotRemapPluginYMLStrategyConfig;

public final class nn extends mm {
   @NotNull
   private final SpigotRemapPluginYMLStrategyConfig ww;

   protected void f(@NotNull kg var1) {
      this.a(var1, Reflection.getOrCreateKotlinClass(mg.class), (Function1)(new no(var1)));
   }

   @NotNull
   protected SpigotRemapPluginYMLStrategyConfig a() {
      return this.ww;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public nn(@NotNull SpigotRemapPluginYMLStrategyConfig var1) {
      super((mq)null, 1, (DefaultConstructorMarker)null);
      this.ww = var1;
   }
}
