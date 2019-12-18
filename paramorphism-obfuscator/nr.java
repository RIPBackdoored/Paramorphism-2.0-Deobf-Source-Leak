package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.SourceFileStrippingStrategyConfig;

public final class nr extends mm {
   @NotNull
   private final SourceFileStrippingStrategyConfig xa;

   protected void f(@NotNull kg var1) {
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)(new nq(this)));
   }

   @NotNull
   protected SourceFileStrippingStrategyConfig a() {
      return this.xa;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public nr(@NotNull SourceFileStrippingStrategyConfig var1) {
      super((mq)null, 1, (DefaultConstructorMarker)null);
      this.xa = var1;
   }
}
