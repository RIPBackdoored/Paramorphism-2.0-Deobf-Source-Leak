package site.hackery.paramorphism.api.config.strategies.obfuscation.stripping;

import kotlin.*;
import org.jetbrains.annotations.*;
import site.hackery.paramorphism.api.config.*;

@Metadata(mv = { 1, 1, 16 }, bv = { 1, 0, 3 }, k = 3)
public static final class DefaultImpls
{
    @Nullable
    public static String sourceFile(final SourceFileStrippingStrategyConfig sourceFileStrippingStrategyConfig, @NotNull final String s) {
        return null;
    }
    
    @Nullable
    public static Boolean getEnabled(final SourceFileStrippingStrategyConfig sourceFileStrippingStrategyConfig) {
        return StrategyConfiguration.DefaultImpls.getEnabled(sourceFileStrippingStrategyConfig);
    }
    
    @NotNull
    public static StrategyIrregularity[] getIrregularities(final SourceFileStrippingStrategyConfig sourceFileStrippingStrategyConfig) {
        return StrategyConfiguration.DefaultImpls.getIrregularities(sourceFileStrippingStrategyConfig);
    }
    
    @NotNull
    public static ElementMask getMask(final SourceFileStrippingStrategyConfig sourceFileStrippingStrategyConfig) {
        return StrategyConfiguration.DefaultImpls.getMask(sourceFileStrippingStrategyConfig);
    }
    
    public static boolean getOverrideGlobalMask(final SourceFileStrippingStrategyConfig sourceFileStrippingStrategyConfig) {
        return StrategyConfiguration.DefaultImpls.getOverrideGlobalMask(sourceFileStrippingStrategyConfig);
    }
}
