package paramorphism-obfuscator;

import java.util.jar.Manifest;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ParamorphismConfig;
import site.hackery.paramorphism.api.resources.ClassSet;
import site.hackery.paramorphism.api.resources.ClassSetGroup;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class kg {
   public static final KProperty[] qz = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(kg.class), "classes", "getClasses()Lsite/hackery/paramorphism/api/resources/ClassSet;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(kg.class), "injectedClasses", "getInjectedClasses()Lsite/hackery/paramorphism/api/resources/ClassSet;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(kg.class), "resources", "getResources()Lsite/hackery/paramorphism/api/resources/ResourceSet;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(kg.class), "shadedClassResources", "getShadedClassResources()Lsite/hackery/paramorphism/api/resources/ResourceSet;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(kg.class), "libraries", "getLibraries()Lsite/hackery/paramorphism/api/resources/ClassSetGroup;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(kg.class), "manifest", "getManifest()Ljava/util/jar/Manifest;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(kg.class), "randoms", "getRandoms()Lsite/hackery/paramorphism/util/RandomPlatter;"))};
   @NotNull
   private final ih ra;
   @NotNull
   private final Lazy rb;
   @NotNull
   private final Lazy rc;
   @NotNull
   private final Lazy rd;
   @NotNull
   private final Lazy re;
   @NotNull
   private final Lazy rf;
   @NotNull
   private final Lazy rg;
   @NotNull
   private final Lazy rh;
   @NotNull
   private final ParamorphismConfig ri;

   @NotNull
   public final ih a() {
      return this.ra;
   }

   @NotNull
   public final ClassSet b() {
      Lazy var1 = this.rb;
      KProperty var3 = qz[0];
      boolean var4 = false;
      return (ClassSet)var1.getValue();
   }

   @NotNull
   public final ClassSet c() {
      Lazy var1 = this.rc;
      KProperty var3 = qz[1];
      boolean var4 = false;
      return (ClassSet)var1.getValue();
   }

   @NotNull
   public final ResourceSet d() {
      Lazy var1 = this.rd;
      KProperty var3 = qz[2];
      boolean var4 = false;
      return (ResourceSet)var1.getValue();
   }

   @NotNull
   public final ResourceSet e() {
      Lazy var1 = this.re;
      KProperty var3 = qz[3];
      boolean var4 = false;
      return (ResourceSet)var1.getValue();
   }

   @NotNull
   public final ClassSetGroup f() {
      Lazy var1 = this.rf;
      KProperty var3 = qz[4];
      boolean var4 = false;
      return (ClassSetGroup)var1.getValue();
   }

   @NotNull
   public final Manifest g() {
      Lazy var1 = this.rg;
      KProperty var3 = qz[5];
      boolean var4 = false;
      return (Manifest)var1.getValue();
   }

   @NotNull
   public final lg h() {
      Lazy var1 = this.rh;
      KProperty var3 = qz[6];
      boolean var4 = false;
      return (lg)var1.getValue();
   }

   @NotNull
   public final ParamorphismConfig i() {
      return this.ri;
   }

   public kg(@NotNull ParamorphismConfig var1) {
      super();
      this.ri = var1;
      this.ra = new ih();
      this.rb = LazyKt.lazy((Function0)(new ue(this)));
      this.rc = LazyKt.lazy((Function0)mi.uy);
      this.rd = LazyKt.lazy((Function0)(new mj(this)));
      this.re = LazyKt.lazy((Function0)(new mh(this)));
      this.rf = LazyKt.lazy((Function0)(new ss(this)));
      this.rg = LazyKt.lazy((Function0)(new ManifestLambda(this)));
      this.rh = LazyKt.lazy((Function0)(new ml(this)));
   }
}
