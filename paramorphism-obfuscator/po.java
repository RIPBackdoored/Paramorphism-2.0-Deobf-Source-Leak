package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin.KotlinDelegatedPropertiesConcealingStrategyConfig;

public final class po extends mm {
   @NotNull
   private final KotlinDelegatedPropertiesConcealingStrategyConfig bbg;

   protected void f(@NotNull kg var1) {
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)pk.bbc);
   }

   @NotNull
   protected KotlinDelegatedPropertiesConcealingStrategyConfig a() {
      return this.bbg;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public po(@NotNull KotlinDelegatedPropertiesConcealingStrategyConfig var1) {
      super((mq)null, 1, (DefaultConstructorMarker)null);
      this.bbg = var1;
   }
}
