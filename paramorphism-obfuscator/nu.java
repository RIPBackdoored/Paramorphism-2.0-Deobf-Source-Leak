package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.LineNumberStrippingStrategyConfig;

public final class nu extends mm {
   @NotNull
   private final LineNumberStrippingStrategyConfig xd;

   protected void f(@NotNull kg var1) {
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)ns.xb);
   }

   @NotNull
   protected LineNumberStrippingStrategyConfig a() {
      return this.xd;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public nu(@NotNull LineNumberStrippingStrategyConfig var1) {
      super((mq)null, 1, (DefaultConstructorMarker)null);
      this.xd = var1;
   }
}
