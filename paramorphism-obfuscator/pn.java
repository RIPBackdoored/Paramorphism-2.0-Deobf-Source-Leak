package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin.KotlinIntrinsicsConcealingStrategyConfig;

public final class pn extends mm {
   @NotNull
   private final KotlinIntrinsicsConcealingStrategyConfig bbf;

   protected void f(@NotNull kg var1) {
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)pp.bbh);
   }

   @NotNull
   protected KotlinIntrinsicsConcealingStrategyConfig a() {
      return this.bbf;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public pn(@NotNull KotlinIntrinsicsConcealingStrategyConfig var1) {
      super((mq)null, 1, (DefaultConstructorMarker)null);
      this.bbf = var1;
   }
}
