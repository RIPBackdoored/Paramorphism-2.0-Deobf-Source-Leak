package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.corruption.DeeplyNestedClassInjectionStrategyConfig;

public final class sn extends mm {
   @NotNull
   private final DeeplyNestedClassInjectionStrategyConfig bhf;

   protected void f(@NotNull kg var1) {
      this.a(var1, Reflection.getOrCreateKotlinClass(ClassInfoList.class), (Function1)sg.bgw);
   }

   @NotNull
   protected DeeplyNestedClassInjectionStrategyConfig a() {
      return this.bhf;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public sn(@NotNull DeeplyNestedClassInjectionStrategyConfig var1) {
      super(mq.vk);
      this.bhf = var1;
   }
}
