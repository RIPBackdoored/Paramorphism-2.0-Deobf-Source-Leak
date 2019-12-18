package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin.KotlinMetadataStrippingStrategyConfig;

public final class pm extends mm {
   @NotNull
   private final KotlinMetadataStrippingStrategyConfig bbe;

   protected void f(@NotNull kg var1) {
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)pj.bbb);
   }

   @NotNull
   protected KotlinMetadataStrippingStrategyConfig a() {
      return this.bbe;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public pm(@NotNull KotlinMetadataStrippingStrategyConfig var1) {
      super((mq)null, 1, (DefaultConstructorMarker)null);
      this.bbe = var1;
   }
}
