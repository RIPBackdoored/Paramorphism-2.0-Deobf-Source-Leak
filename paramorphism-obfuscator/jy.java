package paramorphism-obfuscator;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.naming.NameGenerationDictionaries;
import site.hackery.paramorphism.api.naming.Dictionaries;
import site.hackery.paramorphism.api.naming.MappingDictionary;

public final class jy implements NameGenerationDictionaries {
   public static final KProperty[] qk = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jy.class), "default", "getDefault()Lsite/hackery/paramorphism/api/naming/MappingDictionary;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jy.class), "packages", "getPackages()Lsite/hackery/paramorphism/api/naming/MappingDictionary;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jy.class), "classes", "getClasses()Lsite/hackery/paramorphism/api/naming/MappingDictionary;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jy.class), "methods", "getMethods()Lsite/hackery/paramorphism/api/naming/MappingDictionary;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jy.class), "fields", "getFields()Lsite/hackery/paramorphism/api/naming/MappingDictionary;"))};
   private final Lazy ql;
   @NotNull
   private final Lazy qm;
   @NotNull
   private final Lazy qn;
   @NotNull
   private final Lazy qo;
   @NotNull
   private final Lazy qp;
   private final String qq;
   private final jn qr;

   private final Lazy a(String var1, MappingDictionary var2) {
      return LazyKt.lazy((Function0)(new jx(this, var1, var2)));
   }

   private final MappingDictionary a() {
      Lazy var1 = this.ql;
      KProperty var3 = qk[0];
      boolean var4 = false;
      return (MappingDictionary)var1.getValue();
   }

   @NotNull
   public MappingDictionary getPackages() {
      Lazy var1 = this.qm;
      KProperty var3 = qk[1];
      boolean var4 = false;
      return (MappingDictionary)var1.getValue();
   }

   @NotNull
   public MappingDictionary getClasses() {
      Lazy var1 = this.qn;
      KProperty var3 = qk[2];
      boolean var4 = false;
      return (MappingDictionary)var1.getValue();
   }

   @NotNull
   public MappingDictionary getMethods() {
      Lazy var1 = this.qo;
      KProperty var3 = qk[3];
      boolean var4 = false;
      return (MappingDictionary)var1.getValue();
   }

   @NotNull
   public MappingDictionary getFields() {
      Lazy var1 = this.qp;
      KProperty var3 = qk[4];
      boolean var4 = false;
      return (MappingDictionary)var1.getValue();
   }

   public jy(@NotNull String var1, @NotNull jn var2) {
      super();
      this.qq = var1;
      this.qr = var2;
      this.ql = this.a("default", Dictionaries.INSTANCE.getALPHABET());
      this.qm = this.a("packages", this.a());
      this.qn = this.a("classes", this.a());
      this.qo = this.a("methods", this.a());
      this.qp = this.a("fields", this.a());
   }

   public static final jn a(jy var0) {
      return var0.qr;
   }

   public static final String b(jy var0) {
      return var0.qq;
   }
}
