package site.hackery.paramorphism.api.config.strategies.corruption;

import kotlin.*;
import paramorphism-obfuscator.*;
import org.jetbrains.annotations.*;
import site.hackery.paramorphism.api.config.*;

@Metadata(mv = { 1, 1, 16 }, bv = { 1, 0, 3 }, k = 3)
public static final class DefaultImpls
{
    @NotNull
    public static StrategyIrregularity[] getIrregularities(final NullByteNamingStrategyConfig nullByteNamingStrategyConfig) {
        return new StrategyIrregularity[] { kd.qw };
    }
    
    @Nullable
    public static Boolean getEnabled(final NullByteNamingStrategyConfig nullByteNamingStrategyConfig) {
        return StrategyConfiguration.DefaultImpls.getEnabled(nullByteNamingStrategyConfig);
    }
    
    @NotNull
    public static ElementMask getMask(final NullByteNamingStrategyConfig nullByteNamingStrategyConfig) {
        return StrategyConfiguration.DefaultImpls.getMask(nullByteNamingStrategyConfig);
    }
    
    public static boolean getOverrideGlobalMask(final NullByteNamingStrategyConfig nullByteNamingStrategyConfig) {
        return StrategyConfiguration.DefaultImpls.getOverrideGlobalMask(nullByteNamingStrategyConfig);
    }
}
