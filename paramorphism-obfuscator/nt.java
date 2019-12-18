package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.LocalVariableStrippingStrategyConfig;

public final class nt extends mm {
   @NotNull
   private final LocalVariableStrippingStrategyConfig xc;

   protected void f(@NotNull kg var1) {
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)np.wy);
   }

   @NotNull
   protected LocalVariableStrippingStrategyConfig a() {
      return this.xc;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public nt(@NotNull LocalVariableStrippingStrategyConfig var1) {
      super((mq)null, 1, (DefaultConstructorMarker)null);
      this.xc = var1;
   }
}
