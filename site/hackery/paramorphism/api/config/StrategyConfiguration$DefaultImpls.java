package site.hackery.paramorphism.api.config;

import kotlin.*;
import org.jetbrains.annotations.*;
import site.hackery.paramorphism.api.config.util.*;

@Metadata(mv = { 1, 1, 16 }, bv = { 1, 0, 3 }, k = 3)
public static final class DefaultImpls
{
    @Nullable
    public static Boolean getEnabled(final StrategyConfiguration strategyConfiguration) {
        return null;
    }
    
    @NotNull
    public static StrategyIrregularity[] getIrregularities(final StrategyConfiguration strategyConfiguration) {
        return new StrategyIrregularity[0];
    }
    
    @NotNull
    public static ElementMask getMask(final StrategyConfiguration strategyConfiguration) {
        return new EmptyElementMask();
    }
    
    public static boolean getOverrideGlobalMask(final StrategyConfiguration strategyConfiguration) {
        return false;
    }
}
