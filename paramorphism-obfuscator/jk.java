package paramorphism-obfuscator;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.StrategyConfiguration$DefaultImpls;
import site.hackery.paramorphism.api.config.StrategyIrregularity;

public abstract class jk implements StrategyConfiguration {
   public static final KProperty[] pm = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jk.class), "enabled", "getEnabled()Ljava/lang/Boolean;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jk.class), "overrideGlobalMask", "getOverrideGlobalMask()Z"))};
   @Nullable
   private final Lazy pn;
   @NotNull
   private final ju po;
   @NotNull
   private final Lazy pp;
   @NotNull
   private final String pq;
   @NotNull
   private final jn pr;

   @Nullable
   public Boolean getEnabled() {
      Lazy var1 = this.pn;
      KProperty var3 = pm[0];
      boolean var4 = false;
      return (Boolean)var1.getValue();
   }

   @NotNull
   public ju a() {
      return this.po;
   }

   public ElementMask getMask() {
      return (ElementMask)this.a();
   }

   public boolean getOverrideGlobalMask() {
      Lazy var1 = this.pp;
      KProperty var3 = pm[1];
      boolean var4 = false;
      return (Boolean)var1.getValue();
   }

   @NotNull
   protected final String b() {
      return this.pq;
   }

   @NotNull
   protected final jn c() {
      return this.pr;
   }

   public jk(@NotNull String var1, @NotNull jn var2) {
      super();
      this.pq = var1;
      this.pr = var2;
      this.pn = LazyKt.lazy((Function0)(new iq(this)));
      this.po = new ju(this.pq + ".mask", this.pr);
      this.pp = LazyKt.lazy((Function0)(new jz(this)));
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return StrategyConfiguration$DefaultImpls.getIrregularities(this);
   }
}
