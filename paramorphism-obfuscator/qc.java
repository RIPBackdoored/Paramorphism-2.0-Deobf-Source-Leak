package paramorphism-obfuscator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.concealment.MethodIndirectionStrategyConfig;

public final class qc extends mm {
   public static final KProperty[] bck = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(qc.class), "methodContainerPackage", "getMethodContainerPackage()Ljava/lang/String;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(qc.class), "dispatcherClassType", "getDispatcherClassType()Lorg/objectweb/asm/Type;"))};
   private String bcl;
   private final Lazy bcm;
   private final Lazy bcn;
   @NotNull
   private final MethodIndirectionStrategyConfig bco;

   private final String a() {
      Lazy var1 = this.bcm;
      KProperty var3 = bck[0];
      boolean var4 = false;
      return (String)var1.getValue();
   }

   private final Type b() {
      Lazy var1 = this.bcn;
      KProperty var3 = bck[1];
      boolean var4 = false;
      return (Type)var1.getValue();
   }

   protected void f(@NotNull kg var1) {
      boolean var3 = false;
      Map var2 = (Map)(new LinkedHashMap());
      boolean var4 = false;
      List var7 = (List)(new ArrayList());
      Ref$IntRef var8 = new Ref$IntRef();
      var8.element = 0;
      lu var5 = new lu(var1.i().getRemapper().getNameGeneration().getDictionaries(), var1.h().a());
      Random var6 = var1.h().a();
      this.a(var1, Reflection.getOrCreateKotlinClass(lx.class), (Function1)(new qe(this, var1, var6)));
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)(new ps(this, var8, var7, var5, var2)));
      this.a(var1, Reflection.getOrCreateKotlinClass(ClassInfoList.class), (Function1)(new pr(this, var7)));
   }

   @NotNull
   protected MethodIndirectionStrategyConfig d() {
      return this.bco;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.d();
   }

   public qc(@NotNull MethodIndirectionStrategyConfig var1) {
      super((mq)null, 1, (DefaultConstructorMarker)null);
      this.bco = var1;
      this.bcm = LazyKt.lazy((Function0)(new qd(this)));
      this.bcn = LazyKt.lazy((Function0)(new qa(this)));
   }

   public static final Function1 a(qc var0, kg var1) {
      return var0.e(var1);
   }

   public static final String a(qc var0) {
      String var10000 = var0.bcl;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("[Hidden]");
      }

      return var10000;
   }

   public static final void a(qc var0, String var1) {
      var0.bcl = var1;
   }

   public static final String b(qc var0) {
      return var0.a();
   }

   public static final Type c(qc var0) {
      return var0.b();
   }
}
