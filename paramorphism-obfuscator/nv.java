package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.obfuscation.flow.JumpReplacementStrategyConfig;

public final class nv extends mm {
   public static final KProperty[] xe = new KProperty[]{(KProperty)Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinClass(nv.class), "errorSlot", "<v#0>"))};
   @NotNull
   private final JumpReplacementStrategyConfig xf;

   protected void f(@NotNull kg var1) {
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)nx.xh);
   }

   @NotNull
   protected JumpReplacementStrategyConfig a() {
      return this.xf;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public nv(@NotNull JumpReplacementStrategyConfig var1) {
      super(mq.vi);
      this.xf = var1;
   }
}
