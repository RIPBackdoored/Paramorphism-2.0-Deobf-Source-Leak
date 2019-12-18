package site.hackery.paramorphism.api.config;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
import site.hackery.paramorphism.api.config.util.EmptyElementMask;
import site.hackery.paramorphism.api.custom.CustomTransformer;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class ParamorphismConfig$DefaultImpls {
   @NotNull
   public static File getInput(ParamorphismConfig var0) {
      throw (Throwable)(new ConfigurationException("No input file supplied to configuration"));
   }

   @NotNull
   public static File getOutput(ParamorphismConfig var0) {
      return new File(var0.getInput().getAbsoluteFile().getParent(), FilesKt.getNameWithoutExtension(var0.getInput()) + ".obf.jar");
   }

   @NotNull
   public static File[] getLibraries(ParamorphismConfig var0) {
      return new File[0];
   }

   public static boolean getUseJavaRuntime(ParamorphismConfig var0) {
      return true;
   }

   @NotNull
   public static ElementMask getMask(ParamorphismConfig var0) {
      return (ElementMask)(new EmptyElementMask());
   }

   @Nullable
   public static ElementMask getShadedLibraries(ParamorphismConfig var0) {
      return null;
   }

   public static boolean getBranding(ParamorphismConfig var0) {
      return true;
   }

   @NotNull
   public static RemappingStrategyConfig getRemapper(ParamorphismConfig var0) {
      return (RemappingStrategyConfig)(new ParamorphismConfig$remapper$1());
   }

   @NotNull
   public static SourceFileStrippingStrategyConfig getSourceFileStripping(ParamorphismConfig var0) {
      return (SourceFileStrippingStrategyConfig)(new ParamorphismConfig$sourceFileStripping$1());
   }

   @NotNull
   public static LineNumberStrippingStrategyConfig getLineNumberStripping(ParamorphismConfig var0) {
      return (LineNumberStrippingStrategyConfig)(new ParamorphismConfig$lineNumberStripping$1());
   }

   @NotNull
   public static KotlinIntrinsicsConcealingStrategyConfig getKotlinIntrinsicsConcealing(ParamorphismConfig var0) {
      return (KotlinIntrinsicsConcealingStrategyConfig)(new ParamorphismConfig$kotlinIntrinsicsConcealing$1());
   }

   @NotNull
   public static KotlinMetadataStrippingStrategyConfig getKotlinMetadataStripping(ParamorphismConfig var0) {
      return (KotlinMetadataStrippingStrategyConfig)(new ParamorphismConfig$kotlinMetadataStripping$1());
   }

   @NotNull
   public static KotlinDelegatedPropertiesConcealingStrategyConfig getKotlinDelegatedPropertiesConcealing(ParamorphismConfig var0) {
      return (KotlinDelegatedPropertiesConcealingStrategyConfig)(new ParamorphismConfig$kotlinDelegatedPropertiesConcealing$1());
   }

   @NotNull
   public static NullByteNamingStrategyConfig getNullByteNaming(ParamorphismConfig var0) {
      return (NullByteNamingStrategyConfig)(new ParamorphismConfig$nullByteNaming$1());
   }

   @NotNull
   public static LocalVariableStrippingStrategyConfig getLocalVariableStripping(ParamorphismConfig var0) {
      return (LocalVariableStrippingStrategyConfig)(new ParamorphismConfig$localVariableStripping$1());
   }

   @NotNull
   public static StringConcealingStrategyConfig getStringConcealing(ParamorphismConfig var0) {
      return (StringConcealingStrategyConfig)(new ParamorphismConfig$stringConcealing$1());
   }

   @NotNull
   public static MethodIndirectionStrategyConfig getMethodIndirection(ParamorphismConfig var0) {
      return (MethodIndirectionStrategyConfig)(new ParamorphismConfig$methodIndirection$1());
   }

   @NotNull
   public static SpigotRemapPluginYMLStrategyConfig getSpigotRemapPluginYML(ParamorphismConfig var0) {
      return (SpigotRemapPluginYMLStrategyConfig)(new ParamorphismConfig$spigotRemapPluginYML$1());
   }

   @NotNull
   public static JumpReplacementStrategyConfig getJumpReplacement(ParamorphismConfig var0) {
      return (JumpReplacementStrategyConfig)(new ParamorphismConfig$jumpReplacement$1());
   }

   @NotNull
   public static PackingStrategyConfig getPacker(ParamorphismConfig var0) {
      return (PackingStrategyConfig)(new ParamorphismConfig$packer$1());
   }

   @NotNull
   public static ForceNoVerifyStrategyConfig getForceNoVerify(ParamorphismConfig var0) {
      return (ForceNoVerifyStrategyConfig)(new ParamorphismConfig$forceNoVerify$1());
   }

   @NotNull
   public static DeeplyNestedClassInjectionStrategyConfig getDeeplyNestedClassInjection(ParamorphismConfig var0) {
      return (DeeplyNestedClassInjectionStrategyConfig)(new ParamorphismConfig$deeplyNestedClassInjection$1());
   }

   @NotNull
   public static CustomTransformer[] getCustomTransformers(ParamorphismConfig var0) {
      return new CustomTransformer[0];
   }
}
