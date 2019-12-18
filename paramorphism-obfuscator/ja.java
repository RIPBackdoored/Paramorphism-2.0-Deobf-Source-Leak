package paramorphism-obfuscator;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.naming.NameGenerationConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig;

public final class ja extends jk implements RemappingStrategyConfig {
   public static final KProperty[] nz = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ja.class), "manifestEntries", "getManifestEntries()[Ljava/lang/String;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ja.class), "flat", "getFlat()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ja.class), "prefix", "getPrefix()Ljava/lang/String;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ja.class), "nameGeneration", "getNameGeneration()Lsite/hackery/paramorphism/config/JacksonNameGenerationConfig;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ja.class), "excludeEnums", "getExcludeEnums()Z"))};
   @NotNull
   private final Lazy oa;
   @NotNull
   private final Lazy ob;
   @Nullable
   private final Lazy oc;
   @NotNull
   private final Lazy od;
   @NotNull
   private final Lazy oe;
   public final jg of;

   @NotNull
   public String[] getManifestEntries() {
      Lazy var1 = this.oa;
      KProperty var3 = nz[0];
      boolean var4 = false;
      return (String[])var1.getValue();
   }

   public boolean getFlat() {
      Lazy var1 = this.ob;
      KProperty var3 = nz[1];
      boolean var4 = false;
      return (Boolean)var1.getValue();
   }

   @Nullable
   public String getPrefix() {
      Lazy var1 = this.oc;
      KProperty var3 = nz[2];
      boolean var4 = false;
      return (String)var1.getValue();
   }

   @NotNull
   public iv d() {
      Lazy var1 = this.od;
      KProperty var3 = nz[3];
      boolean var4 = false;
      return (iv)var1.getValue();
   }

   public NameGenerationConfig getNameGeneration() {
      return (NameGenerationConfig)this.d();
   }

   public boolean getExcludeEnums() {
      Lazy var1 = this.oe;
      KProperty var3 = nz[4];
      boolean var4 = false;
      return (Boolean)var1.getValue();
   }

   public ja(jg var1, String var2, jn var3) {
      super(var2, var3);
      this.of = var1;
      this.oa = LazyKt.lazy((Function0)(new it(this)));
      this.ob = LazyKt.lazy((Function0)(new jb(this)));
      this.oc = LazyKt.lazy((Function0)(new jj(this)));
      this.od = LazyKt.lazy((Function0)(new jl(this)));
      this.oe = LazyKt.lazy((Function0)(new iy(this)));
   }
}
