package site.hackery.paramorphism.api.config;

import site.hackery.paramorphism.api.config.strategies.concealment.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002" }, d2 = { "site/hackery/paramorphism/api/config/ParamorphismConfig$methodIndirection$1", "Lsite/hackery/paramorphism/api/config/strategies/concealment/MethodIndirectionStrategyConfig;", "paramorphism" })
public static final class ParamorphismConfig$methodIndirection$1 implements MethodIndirectionStrategyConfig {
    ParamorphismConfig$methodIndirection$1() {
        super();
    }
    
    @Nullable
    @Override
    public Boolean getEnabled() {
        return MethodIndirectionStrategyConfig.DefaultImpls.getEnabled(this);
    }
    
    @NotNull
    @Override
    public StrategyIrregularity[] getIrregularities() {
        return MethodIndirectionStrategyConfig.DefaultImpls.getIrregularities(this);
    }
    
    @NotNull
    @Override
    public ElementMask getMask() {
        return MethodIndirectionStrategyConfig.DefaultImpls.getMask(this);
    }
    
    @Override
    public boolean getOverrideGlobalMask() {
        return MethodIndirectionStrategyConfig.DefaultImpls.getOverrideGlobalMask(this);
    }
}