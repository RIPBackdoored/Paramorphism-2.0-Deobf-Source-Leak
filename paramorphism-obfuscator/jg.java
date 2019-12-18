package paramorphism-obfuscator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.TypeCastException;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.config.ParamorphismConfig;
import site.hackery.paramorphism.api.config.ParamorphismConfig$DefaultImpls;
import site.hackery.paramorphism.api.config.strategies.concealment.MethodIndirectionStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.concealment.StringConcealingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.corruption.DeeplyNestedClassInjectionStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.corruption.ForceNoVerifyStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.corruption.NullByteNamingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.flow.JumpReplacementStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin.KotlinDelegatedPropertiesConcealingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin.KotlinIntrinsicsConcealingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin.KotlinMetadataStrippingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.LineNumberStrippingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.LocalVariableStrippingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.SourceFileStrippingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.packing.PackingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.scenarios.spigot.SpigotRemapPluginYMLStrategyConfig;
import site.hackery.paramorphism.api.custom.CustomTransformer;

public final class jg implements ParamorphismConfig, jn {
   public static final KProperty[] ol = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jg.class), "input", "getInput()Ljava/io/File;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jg.class), "output", "getOutput()Ljava/io/File;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jg.class), "libraries", "getLibraries()[Ljava/io/File;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jg.class), "useJavaRuntime", "getUseJavaRuntime()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jg.class), "shadedLibraries", "getShadedLibraries()Lsite/hackery/paramorphism/config/JacksonElementMask;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(jg.class), "branding", "getBranding()Z"))};
   @NotNull
   private final Lazy om;
   @NotNull
   private final Lazy on;
   @NotNull
   private final Lazy oo;
   @NotNull
   private final Lazy op;
   @NotNull
   private final ju oq;
   @Nullable
   private final Lazy or;
   @NotNull
   private final Lazy os;
   @NotNull
   private final RemappingStrategyConfig ot;
   @NotNull
   private final SourceFileStrippingStrategyConfig ou;
   @NotNull
   private final LineNumberStrippingStrategyConfig ov;
   @NotNull
   private final KotlinIntrinsicsConcealingStrategyConfig ow;
   @NotNull
   private final KotlinMetadataStrippingStrategyConfig ox;
   @NotNull
   private final KotlinDelegatedPropertiesConcealingStrategyConfig oy;
   @NotNull
   private final NullByteNamingStrategyConfig oz;
   @NotNull
   private final LocalVariableStrippingStrategyConfig pa;
   @NotNull
   private final StringConcealingStrategyConfig pb;
   @NotNull
   private final MethodIndirectionStrategyConfig pc;
   @NotNull
   private final SpigotRemapPluginYMLStrategyConfig pd;
   @NotNull
   private final JumpReplacementStrategyConfig pe;
   @NotNull
   private final ForceNoVerifyStrategyConfig pf;
   @NotNull
   private final DeeplyNestedClassInjectionStrategyConfig pg;
   @NotNull
   private final File ph;
   private final JsonNode pi;

   @Nullable
   public JsonNode e(@NotNull String var1) {
      JsonNode var2 = this.pi;

      String var3;
      for(Iterator var4 = StringsKt.split$default((CharSequence)var1, new char[]{'.'}, false, 0, 6, (Object)null).iterator(); var4.hasNext(); var2 = var2 != null ? var2.get(var3) : null) {
         var3 = (String)var4.next();
      }

      return var2;
   }

   @NotNull
   public File getWorkingDirectory() {
      return this.d();
   }

   @NotNull
   public File getInput() {
      Lazy var1 = this.om;
      KProperty var3 = ol[0];
      boolean var4 = false;
      return (File)var1.getValue();
   }

   @NotNull
   public File getOutput() {
      Lazy var1 = this.on;
      KProperty var3 = ol[1];
      boolean var4 = false;
      return (File)var1.getValue();
   }

   @NotNull
   public File[] getLibraries() {
      Lazy var1 = this.oo;
      KProperty var3 = ol[2];
      boolean var4 = false;
      return (File[])var1.getValue();
   }

   public boolean getUseJavaRuntime() {
      Lazy var1 = this.op;
      KProperty var3 = ol[3];
      boolean var4 = false;
      return (Boolean)var1.getValue();
   }

   @NotNull
   public ju b() {
      return this.oq;
   }

   public ElementMask getMask() {
      return (ElementMask)this.b();
   }

   @Nullable
   public ju c() {
      Lazy var1 = this.or;
      KProperty var3 = ol[4];
      boolean var4 = false;
      return (ju)var1.getValue();
   }

   public ElementMask getShadedLibraries() {
      return (ElementMask)this.c();
   }

   public boolean getBranding() {
      Lazy var1 = this.os;
      KProperty var3 = ol[5];
      boolean var4 = false;
      return (Boolean)var1.getValue();
   }

   @NotNull
   public RemappingStrategyConfig getRemapper() {
      return this.ot;
   }

   @NotNull
   public SourceFileStrippingStrategyConfig getSourceFileStripping() {
      return this.ou;
   }

   @NotNull
   public LineNumberStrippingStrategyConfig getLineNumberStripping() {
      return this.ov;
   }

   @NotNull
   public KotlinIntrinsicsConcealingStrategyConfig getKotlinIntrinsicsConcealing() {
      return this.ow;
   }

   @NotNull
   public KotlinMetadataStrippingStrategyConfig getKotlinMetadataStripping() {
      return this.ox;
   }

   @NotNull
   public KotlinDelegatedPropertiesConcealingStrategyConfig getKotlinDelegatedPropertiesConcealing() {
      return this.oy;
   }

   @NotNull
   public NullByteNamingStrategyConfig getNullByteNaming() {
      return this.oz;
   }

   @NotNull
   public LocalVariableStrippingStrategyConfig getLocalVariableStripping() {
      return this.pa;
   }

   @NotNull
   public StringConcealingStrategyConfig getStringConcealing() {
      return this.pb;
   }

   @NotNull
   public MethodIndirectionStrategyConfig getMethodIndirection() {
      return this.pc;
   }

   @NotNull
   public SpigotRemapPluginYMLStrategyConfig getSpigotRemapPluginYML() {
      return this.pd;
   }

   @NotNull
   public JumpReplacementStrategyConfig getJumpReplacement() {
      return this.pe;
   }

   @NotNull
   public ForceNoVerifyStrategyConfig getForceNoVerify() {
      return this.pf;
   }

   @NotNull
   public DeeplyNestedClassInjectionStrategyConfig getDeeplyNestedClassInjection() {
      return this.pg;
   }

   @NotNull
   public File d() {
      return this.ph;
   }

   public jg(@NotNull File var1, @NotNull JsonNode var2) {
      super();
      this.ph = var1;
      this.pi = var2;
      this.om = LazyKt.lazy((Function0)(new kb(this)));
      this.on = LazyKt.lazy((Function0)(new jv(this)));
      this.oo = LazyKt.lazy((Function0)(new ir(this)));
      this.op = LazyKt.lazy((Function0)(new is(this)));
      this.oq = new ju("mask", (jn)this);
      this.or = LazyKt.lazy((Function0)(new jm(this)));
      this.os = LazyKt.lazy((Function0)(new jq(this)));
      this.ot = (RemappingStrategyConfig)(new ja(this, "remapper", (jn)this));
      this.ou = (SourceFileStrippingStrategyConfig)(new jh(this, "source_file_stripping", (jn)this));
      this.ov = (LineNumberStrippingStrategyConfig)(new ji(this, "line_number_stripping", (jn)this));
      this.ow = (KotlinIntrinsicsConcealingStrategyConfig)(new jp(this, "kotlin_intrinsics_concealing", (jn)this));
      this.ox = (KotlinMetadataStrippingStrategyConfig)(new jo(this, "kotlin_metadata_stripping", (jn)this));
      this.oy = (KotlinDelegatedPropertiesConcealingStrategyConfig)(new kf(this, "kotlin_delegated_properties_concealing", (jn)this));
      this.oz = (NullByteNamingStrategyConfig)(new jw(this, "null_byte_naming", (jn)this));
      this.pa = (LocalVariableStrippingStrategyConfig)(new iw(this, "local_variable_stripping", (jn)this));
      this.pb = (StringConcealingStrategyConfig)(new jt(this, "string_concealing", (jn)this));
      this.pc = (MethodIndirectionStrategyConfig)(new in(this, "method_indirection", (jn)this));
      this.pd = (SpigotRemapPluginYMLStrategyConfig)(new jf(this, "spigot_remap_plugin_yml", (jn)this));
      this.pe = (JumpReplacementStrategyConfig)(new io(this, "jump_replacement", (jn)this));
      this.pf = (ForceNoVerifyStrategyConfig)(new ke(this, "force_no_verify", (jn)this));
      this.pg = (DeeplyNestedClassInjectionStrategyConfig)(new jr(this, "deeply_nested_class_injection", (jn)this));
   }

   public jg(@NotNull File var1) {
      File var13 = var1.getAbsoluteFile().getParentFile();
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      String var5 = FilesKt.getExtension(var1);
      boolean var6 = false;
      if (var5 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         label58: {
            JsonFactory var10000;
            label57: {
               String var7 = var5.toLowerCase();
               switch(var7.hashCode()) {
               case 119768:
                  if (!var7.equals("yml")) {
                     break label58;
                  }
                  break;
               case 3271912:
                  if (!var7.equals("json")) {
                     break label58;
                  }

                  var10000 = new JsonFactory();
                  break label57;
               case 3701415:
                  if (!var7.equals("yaml")) {
                     break label58;
                  }
                  break;
               default:
                  break label58;
               }

               var10000 = (JsonFactory)(new YAMLFactory());
            }

            JsonFactory var15 = var10000;
            ObjectMapper var16 = new ObjectMapper(var15);
            JsonNode var14 = var16.readTree(var1);
            this(var13, var14);
            return;
         }

         String var8 = "Unsupported configuration";
         boolean var11 = false;
         throw (Throwable)(new IllegalStateException(var8.toString()));
      }
   }

   @NotNull
   public CustomTransformer[] getCustomTransformers() {
      return ParamorphismConfig$DefaultImpls.getCustomTransformers(this);
   }

   @NotNull
   public PackingStrategyConfig getPacker() {
      return ParamorphismConfig$DefaultImpls.getPacker(this);
   }

   @Nullable
   public File a(@NotNull String var1) {
      return ix.c(this, var1);
   }

   @NotNull
   public File d(@NotNull String var1) {
      return ix.a(this, var1);
   }

   @Nullable
   public String b(@NotNull String var1) {
      return ix.b(this, var1);
   }
}
