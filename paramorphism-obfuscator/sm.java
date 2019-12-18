package paramorphism-obfuscator;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.corruption.NullByteNamingStrategyConfig;

public final class sm extends mm {
   public static final KProperty[] bhd = new KProperty[]{(KProperty)Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinClass(sm.class), "prefixedClasses", "<v#0>"))};
   @NotNull
   private final NullByteNamingStrategyConfig bhe;

   protected void f(@NotNull kg var1) {
      Lazy var10000 = LazyKt.lazy((Function0)(new sa(this, var1)));
      KProperty var3 = bhd[0];
      Lazy var2 = var10000;
      this.a(var1, Reflection.getOrCreateKotlinClass(mf.class), (Function1)(new sj(var2, var3)));
   }

   @NotNull
   protected NullByteNamingStrategyConfig a() {
      return this.bhe;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public sm(@NotNull NullByteNamingStrategyConfig var1) {
      super((mq)null, 1, (DefaultConstructorMarker)null);
      this.bhe = var1;
   }

   public static final Function1 a(sm var0, kg var1) {
      return var0.e(var1);
   }
}
