package paramorphism-obfuscator;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig;
import site.hackery.paramorphism.api.resources.ClassSet;

public final class ow extends mm {
   public static final KProperty[] bah = new KProperty[]{(KProperty)Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinClass(ow.class), "prefixedClasses", "<v#0>"))};
   private la bai;
   @NotNull
   private final RemappingStrategyConfig baj;

   protected void f(@NotNull kg var1) {
      ClassSet var2 = var1.b();
      lu var3 = new lu(this.a().getNameGeneration().getDictionaries(), this.a().getNameGeneration().getShuffle() ? var1.h().a() : null);
      String var10000 = this.a().getPrefix();
      if (var10000 != null) {
         String var4 = var10000;
         boolean var5 = false;
         boolean var6 = false;
         boolean var8 = false;
         Lazy var12 = LazyKt.lazy((Function0)(new oa(this, var1)));
         KProperty var9 = bah[0];
         Lazy var10 = var12;
         this.a(var1, Reflection.getOrCreateKotlinClass(mf.class), (Function1)(new pa(var10, var9, var4)));
      }

      this.a(var1, Reflection.getOrCreateKotlinClass(lx.class), (Function1)(new ob(this, var1, var3, var2)));
      Function1 var11 = (Function1)(new ov(this));
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), var11);
      this.a(var1, Reflection.getOrCreateKotlinClass(ly.class), var11);
      this.a(var1, Reflection.getOrCreateKotlinClass(mg.class), (Function1)(new ox(this, var1)));
   }

   @NotNull
   protected RemappingStrategyConfig a() {
      return this.baj;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.a();
   }

   public ow(@NotNull RemappingStrategyConfig var1) {
      super(mq.vg);
      this.baj = var1;
   }

   public static final Function1 a(ow var0, kg var1) {
      return var0.e(var1);
   }

   public static final la a(ow var0) {
      la var10000 = var0.bai;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("[Hidden]");
      }

      return var10000;
   }

   public static final void a(ow var0, la var1) {
      var0.bai = var1;
   }
}
