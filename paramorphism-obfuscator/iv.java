package paramorphism-obfuscator;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.naming.NameGenerationConfig;
import site.hackery.paramorphism.api.config.naming.NameGenerationDictionaries;

public final class iv implements NameGenerationConfig {
   public static final KProperty[] nr = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(iv.class), "dictionaries", "getDictionaries()Lsite/hackery/paramorphism/config/JacksonNameGenerationDictionaries;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(iv.class), "shuffle", "getShuffle()Z"))};
   @NotNull
   private final Lazy ns;
   @NotNull
   private final Lazy nt;
   private final String nu;
   private final jn nv;

   @NotNull
   public jy a() {
      Lazy var1 = this.ns;
      KProperty var3 = nr[0];
      boolean var4 = false;
      return (jy)var1.getValue();
   }

   public NameGenerationDictionaries getDictionaries() {
      return (NameGenerationDictionaries)this.a();
   }

   public boolean getShuffle() {
      Lazy var1 = this.nt;
      KProperty var3 = nr[1];
      boolean var4 = false;
      return (Boolean)var1.getValue();
   }

   public iv(@NotNull String var1, @NotNull jn var2) {
      super();
      this.nu = var1;
      this.nv = var2;
      this.ns = LazyKt.lazy((Function0)(new jc(this)));
      this.nt = LazyKt.lazy((Function0)(new iu(this)));
   }

   public static final String a(iv var0) {
      return var0.nu;
   }

   public static final jn b(iv var0) {
      return var0.nv;
   }
}
