package site.hackery.paramorphism.api.config;

import kotlin.*;
import java.io.*;
import org.jetbrains.annotations.*;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.*;
import site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin.*;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.*;
import site.hackery.paramorphism.api.config.strategies.concealment.*;
import site.hackery.paramorphism.api.config.strategies.scenarios.spigot.*;
import site.hackery.paramorphism.api.config.strategies.obfuscation.flow.*;
import site.hackery.paramorphism.api.config.strategies.packing.*;
import site.hackery.paramorphism.api.config.strategies.corruption.*;
import site.hackery.paramorphism.api.custom.*;
import kotlin.io.*;
import site.hackery.paramorphism.api.config.util.*;
import paramorphism-obfuscator.*;
import com.fasterxml.jackson.databind.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\bf\u0018\u0000 Z2\u00020\u0001:\u0001ZR\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u001a\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00140\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020+8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0014\u00102\u001a\u0002038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0014\u00106\u001a\u0002078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0014\u0010:\u001a\u00020;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0014\u0010>\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b?\u0010\u0016R\u0014\u0010@\u001a\u00020A8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010CR\u0014\u0010D\u001a\u00020E8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010GR\u0016\u0010H\u001a\u0004\u0018\u0001038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bI\u00105R\u0014\u0010J\u001a\u00020K8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bL\u0010MR\u0014\u0010N\u001a\u00020O8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bP\u0010QR\u0014\u0010R\u001a\u00020S8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bT\u0010UR\u0014\u0010V\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bW\u0010\u0005R\u0012\u0010X\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\bY\u0010\u0016¨\u0006[" }, d2 = { "Lsite/hackery/paramorphism/api/config/ParamorphismConfig;", "", "branding", "", "getBranding", "()Z", "customTransformers", "", "Lsite/hackery/paramorphism/api/custom/CustomTransformer;", "getCustomTransformers", "()[Lsite/hackery/paramorphism/api/custom/CustomTransformer;", "deeplyNestedClassInjection", "Lsite/hackery/paramorphism/api/config/strategies/corruption/DeeplyNestedClassInjectionStrategyConfig;", "getDeeplyNestedClassInjection", "()Lsite/hackery/paramorphism/api/config/strategies/corruption/DeeplyNestedClassInjectionStrategyConfig;", "forceNoVerify", "Lsite/hackery/paramorphism/api/config/strategies/corruption/ForceNoVerifyStrategyConfig;", "getForceNoVerify", "()Lsite/hackery/paramorphism/api/config/strategies/corruption/ForceNoVerifyStrategyConfig;", "input", "Ljava/io/File;", "getInput", "()Ljava/io/File;", "jumpReplacement", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/flow/JumpReplacementStrategyConfig;", "getJumpReplacement", "()Lsite/hackery/paramorphism/api/config/strategies/obfuscation/flow/JumpReplacementStrategyConfig;", "kotlinDelegatedPropertiesConcealing", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/kotlin/KotlinDelegatedPropertiesConcealingStrategyConfig;", "getKotlinDelegatedPropertiesConcealing", "()Lsite/hackery/paramorphism/api/config/strategies/obfuscation/kotlin/KotlinDelegatedPropertiesConcealingStrategyConfig;", "kotlinIntrinsicsConcealing", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/kotlin/KotlinIntrinsicsConcealingStrategyConfig;", "getKotlinIntrinsicsConcealing", "()Lsite/hackery/paramorphism/api/config/strategies/obfuscation/kotlin/KotlinIntrinsicsConcealingStrategyConfig;", "kotlinMetadataStripping", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/kotlin/KotlinMetadataStrippingStrategyConfig;", "getKotlinMetadataStripping", "()Lsite/hackery/paramorphism/api/config/strategies/obfuscation/kotlin/KotlinMetadataStrippingStrategyConfig;", "libraries", "getLibraries", "()[Ljava/io/File;", "lineNumberStripping", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/stripping/LineNumberStrippingStrategyConfig;", "getLineNumberStripping", "()Lsite/hackery/paramorphism/api/config/strategies/obfuscation/stripping/LineNumberStrippingStrategyConfig;", "localVariableStripping", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/stripping/LocalVariableStrippingStrategyConfig;", "getLocalVariableStripping", "()Lsite/hackery/paramorphism/api/config/strategies/obfuscation/stripping/LocalVariableStrippingStrategyConfig;", "mask", "Lsite/hackery/paramorphism/api/config/ElementMask;", "getMask", "()Lsite/hackery/paramorphism/api/config/ElementMask;", "methodIndirection", "Lsite/hackery/paramorphism/api/config/strategies/concealment/MethodIndirectionStrategyConfig;", "getMethodIndirection", "()Lsite/hackery/paramorphism/api/config/strategies/concealment/MethodIndirectionStrategyConfig;", "nullByteNaming", "Lsite/hackery/paramorphism/api/config/strategies/corruption/NullByteNamingStrategyConfig;", "getNullByteNaming", "()Lsite/hackery/paramorphism/api/config/strategies/corruption/NullByteNamingStrategyConfig;", "output", "getOutput", "packer", "Lsite/hackery/paramorphism/api/config/strategies/packing/PackingStrategyConfig;", "getPacker", "()Lsite/hackery/paramorphism/api/config/strategies/packing/PackingStrategyConfig;", "remapper", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/remapper/RemappingStrategyConfig;", "getRemapper", "()Lsite/hackery/paramorphism/api/config/strategies/obfuscation/remapper/RemappingStrategyConfig;", "shadedLibraries", "getShadedLibraries", "sourceFileStripping", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/stripping/SourceFileStrippingStrategyConfig;", "getSourceFileStripping", "()Lsite/hackery/paramorphism/api/config/strategies/obfuscation/stripping/SourceFileStrippingStrategyConfig;", "spigotRemapPluginYML", "Lsite/hackery/paramorphism/api/config/strategies/scenarios/spigot/SpigotRemapPluginYMLStrategyConfig;", "getSpigotRemapPluginYML", "()Lsite/hackery/paramorphism/api/config/strategies/scenarios/spigot/SpigotRemapPluginYMLStrategyConfig;", "stringConcealing", "Lsite/hackery/paramorphism/api/config/strategies/concealment/StringConcealingStrategyConfig;", "getStringConcealing", "()Lsite/hackery/paramorphism/api/config/strategies/concealment/StringConcealingStrategyConfig;", "useJavaRuntime", "getUseJavaRuntime", "workingDirectory", "getWorkingDirectory", "Companion", "paramorphism" })
public interface ParamorphismConfig
{
    public static final Companion Companion = ParamorphismConfig.Companion.$$INSTANCE;
    
    @NotNull
    File getWorkingDirectory();
    
    @NotNull
    File getInput();
    
    @NotNull
    File getOutput();
    
    @NotNull
    File[] getLibraries();
    
    boolean getUseJavaRuntime();
    
    @NotNull
    ElementMask getMask();
    
    @Nullable
    ElementMask getShadedLibraries();
    
    boolean getBranding();
    
    @NotNull
    RemappingStrategyConfig getRemapper();
    
    @NotNull
    SourceFileStrippingStrategyConfig getSourceFileStripping();
    
    @NotNull
    LineNumberStrippingStrategyConfig getLineNumberStripping();
    
    @NotNull
    KotlinIntrinsicsConcealingStrategyConfig getKotlinIntrinsicsConcealing();
    
    @NotNull
    KotlinMetadataStrippingStrategyConfig getKotlinMetadataStripping();
    
    @NotNull
    KotlinDelegatedPropertiesConcealingStrategyConfig getKotlinDelegatedPropertiesConcealing();
    
    @NotNull
    NullByteNamingStrategyConfig getNullByteNaming();
    
    @NotNull
    LocalVariableStrippingStrategyConfig getLocalVariableStripping();
    
    @NotNull
    StringConcealingStrategyConfig getStringConcealing();
    
    @NotNull
    MethodIndirectionStrategyConfig getMethodIndirection();
    
    @NotNull
    SpigotRemapPluginYMLStrategyConfig getSpigotRemapPluginYML();
    
    @NotNull
    JumpReplacementStrategyConfig getJumpReplacement();
    
    @NotNull
    PackingStrategyConfig getPacker();
    
    @NotNull
    ForceNoVerifyStrategyConfig getForceNoVerify();
    
    @NotNull
    DeeplyNestedClassInjectionStrategyConfig getDeeplyNestedClassInjection();
    
    @NotNull
    CustomTransformer[] getCustomTransformers();
    
    @Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3)
    public static final class DefaultImpls
    {
        @NotNull
        public static File getInput(final ParamorphismConfig paramorphismConfig) {
            throw new ConfigurationException("No input file supplied to configuration");
        }
        
        @NotNull
        public static File getOutput(final ParamorphismConfig paramorphismConfig) {
            return new File(paramorphismConfig.getInput().getAbsoluteFile().getParent(), FilesKt__UtilsKt.getNameWithoutExtension(paramorphismConfig.getInput()) + ".obf.jar");
        }
        
        @NotNull
        public static File[] getLibraries(final ParamorphismConfig paramorphismConfig) {
            return new File[0];
        }
        
        public static boolean getUseJavaRuntime(final ParamorphismConfig paramorphismConfig) {
            return true;
        }
        
        @NotNull
        public static ElementMask getMask(final ParamorphismConfig paramorphismConfig) {
            return new EmptyElementMask();
        }
        
        @Nullable
        public static ElementMask getShadedLibraries(final ParamorphismConfig paramorphismConfig) {
            return null;
        }
        
        public static boolean getBranding(final ParamorphismConfig paramorphismConfig) {
            return true;
        }
        
        @NotNull
        public static RemappingStrategyConfig getRemapper(final ParamorphismConfig paramorphismConfig) {
            return (RemappingStrategyConfig)new ParamorphismConfig$remapper.ParamorphismConfig$remapper$1();
        }
        
        @NotNull
        public static SourceFileStrippingStrategyConfig getSourceFileStripping(final ParamorphismConfig paramorphismConfig) {
            return (SourceFileStrippingStrategyConfig)new ParamorphismConfig$sourceFileStripping.ParamorphismConfig$sourceFileStripping$1();
        }
        
        @NotNull
        public static LineNumberStrippingStrategyConfig getLineNumberStripping(final ParamorphismConfig paramorphismConfig) {
            return (LineNumberStrippingStrategyConfig)new ParamorphismConfig$lineNumberStripping.ParamorphismConfig$lineNumberStripping$1();
        }
        
        @NotNull
        public static KotlinIntrinsicsConcealingStrategyConfig getKotlinIntrinsicsConcealing(final ParamorphismConfig paramorphismConfig) {
            return (KotlinIntrinsicsConcealingStrategyConfig)new ParamorphismConfig$kotlinIntrinsicsConcealing.ParamorphismConfig$kotlinIntrinsicsConcealing$1();
        }
        
        @NotNull
        public static KotlinMetadataStrippingStrategyConfig getKotlinMetadataStripping(final ParamorphismConfig paramorphismConfig) {
            return (KotlinMetadataStrippingStrategyConfig)new ParamorphismConfig$kotlinMetadataStripping.ParamorphismConfig$kotlinMetadataStripping$1();
        }
        
        @NotNull
        public static KotlinDelegatedPropertiesConcealingStrategyConfig getKotlinDelegatedPropertiesConcealing(final ParamorphismConfig paramorphismConfig) {
            return (KotlinDelegatedPropertiesConcealingStrategyConfig)new ParamorphismConfig$kotlinDelegatedPropertiesConcealing.ParamorphismConfig$kotlinDelegatedPropertiesConcealing$1();
        }
        
        @NotNull
        public static NullByteNamingStrategyConfig getNullByteNaming(final ParamorphismConfig paramorphismConfig) {
            return (NullByteNamingStrategyConfig)new ParamorphismConfig$nullByteNaming.ParamorphismConfig$nullByteNaming$1();
        }
        
        @NotNull
        public static LocalVariableStrippingStrategyConfig getLocalVariableStripping(final ParamorphismConfig paramorphismConfig) {
            return (LocalVariableStrippingStrategyConfig)new ParamorphismConfig$localVariableStripping.ParamorphismConfig$localVariableStripping$1();
        }
        
        @NotNull
        public static StringConcealingStrategyConfig getStringConcealing(final ParamorphismConfig paramorphismConfig) {
            return (StringConcealingStrategyConfig)new ParamorphismConfig$stringConcealing.ParamorphismConfig$stringConcealing$1();
        }
        
        @NotNull
        public static MethodIndirectionStrategyConfig getMethodIndirection(final ParamorphismConfig paramorphismConfig) {
            return (MethodIndirectionStrategyConfig)new ParamorphismConfig$methodIndirection.ParamorphismConfig$methodIndirection$1();
        }
        
        @NotNull
        public static SpigotRemapPluginYMLStrategyConfig getSpigotRemapPluginYML(final ParamorphismConfig paramorphismConfig) {
            return (SpigotRemapPluginYMLStrategyConfig)new ParamorphismConfig$spigotRemapPluginYML.ParamorphismConfig$spigotRemapPluginYML$1();
        }
        
        @NotNull
        public static JumpReplacementStrategyConfig getJumpReplacement(final ParamorphismConfig paramorphismConfig) {
            return (JumpReplacementStrategyConfig)new ParamorphismConfig$jumpReplacement.ParamorphismConfig$jumpReplacement$1();
        }
        
        @NotNull
        public static PackingStrategyConfig getPacker(final ParamorphismConfig paramorphismConfig) {
            return (PackingStrategyConfig)new ParamorphismConfig$packer.ParamorphismConfig$packer$1();
        }
        
        @NotNull
        public static ForceNoVerifyStrategyConfig getForceNoVerify(final ParamorphismConfig paramorphismConfig) {
            return (ForceNoVerifyStrategyConfig)new ParamorphismConfig$forceNoVerify.ParamorphismConfig$forceNoVerify$1();
        }
        
        @NotNull
        public static DeeplyNestedClassInjectionStrategyConfig getDeeplyNestedClassInjection(final ParamorphismConfig paramorphismConfig) {
            return (DeeplyNestedClassInjectionStrategyConfig)new ParamorphismConfig$deeplyNestedClassInjection.ParamorphismConfig$deeplyNestedClassInjection$1();
        }
        
        @NotNull
        public static CustomTransformer[] getCustomTransformers(final ParamorphismConfig paramorphismConfig) {
            return new CustomTransformer[0];
        }
    }
    
    @Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0000¢\u0006\u0002\b\n¨\u0006\u000b" }, d2 = { "Lsite/hackery/paramorphism/api/config/ParamorphismConfig$Companion;", "", "()V", "of", "Lsite/hackery/paramorphism/api/config/ParamorphismConfig;", "config", "Ljava/io/File;", "workingDirectory", "tree", "Lcom/fasterxml/jackson/databind/JsonNode;", "of$paramorphism", "paramorphism" })
    public static final class Companion
    {
        public static final Companion $$INSTANCE;
        
        @NotNull
        public final ParamorphismConfig of(@NotNull final File file) {
            return new jg(file);
        }
        
        @NotNull
        public final ParamorphismConfig of$paramorphism(@NotNull final File file, @NotNull final JsonNode jsonNode) {
            return new jg(file, jsonNode);
        }
        
        private Companion() {
            super();
        }
        
        static {
            $$INSTANCE = new Companion();
        }
    }
}
