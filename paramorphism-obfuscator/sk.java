package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.strategies.InvalidBytecode;
import paramorphism-obfuscator.verifierDisabler.PriorityHookLambda;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.corruption.ForceNoVerifyStrategyConfig;

public final class sk extends mm {
   @NotNull
   private final ForceNoVerifyStrategyConfig bhb;

   protected void f(@NotNull kg var1) {
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)(new InvalidBytecode(var1)));
      this.a(var1, Reflection.getOrCreateKotlinClass(ClassInfoList.class), (Function1)(new PriorityHookLambda(var1)));
      this.a(var1, Reflection.getOrCreateKotlinClass(mg.class), (Function1)(new ReplaceMainClassLambda(var1)));
   }

   @NotNull
   protected ForceNoVerifyStrategyConfig a() {
      return this.bhb;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public sk(@NotNull ForceNoVerifyStrategyConfig var1) {
      super(mq.vk);
      this.bhb = var1;
   }
}
