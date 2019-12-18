package paramorphism-obfuscator;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ElementMask;

public final class ju implements ElementMask {
   public static final KProperty[] qa = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ju.class), "include", "getInclude()[Ljava/lang/String;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ju.class), "exclude", "getExclude()[Ljava/lang/String;"))};
   @NotNull
   private final Lazy qb;
   @NotNull
   private final Lazy qc;
   @NotNull
   private final String qd;
   private final jn qe;

   @NotNull
   public String[] getInclude() {
      Lazy var1 = this.qb;
      KProperty var3 = qa[0];
      boolean var4 = false;
      return (String[])var1.getValue();
   }

   @NotNull
   public String[] getExclude() {
      Lazy var1 = this.qc;
      KProperty var3 = qa[1];
      boolean var4 = false;
      return (String[])var1.getValue();
   }

   @NotNull
   public final String a() {
      return this.qd;
   }

   public ju(@NotNull String var1, @NotNull jn var2) {
      super();
      this.qd = var1;
      this.qe = var2;
      this.qb = LazyKt.lazy((Function0)(new ip(this)));
      this.qc = LazyKt.lazy((Function0)(new js(this)));
   }

   public static final jn a(ju var0) {
      return var0.qe;
   }
}
