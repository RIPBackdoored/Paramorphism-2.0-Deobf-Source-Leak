package site.hackery.paramorphism.api.config;

import site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002" }, d2 = { "site/hackery/paramorphism/api/config/ParamorphismConfig$kotlinIntrinsicsConcealing$1", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/kotlin/KotlinIntrinsicsConcealingStrategyConfig;", "paramorphism" })
public static final class ParamorphismConfig$kotlinIntrinsicsConcealing$1 implements KotlinIntrinsicsConcealingStrategyConfig {
    ParamorphismConfig$kotlinIntrinsicsConcealing$1() {
        super();
    }
    
    @Nullable
    @Override
    public Boolean getEnabled() {
        return KotlinIntrinsicsConcealingStrategyConfig.DefaultImpls.getEnabled(this);
    }
    
    @NotNull
    @Override
    public StrategyIrregularity[] getIrregularities() {
        return KotlinIntrinsicsConcealingStrategyConfig.DefaultImpls.getIrregularities(this);
    }
    
    @NotNull
    @Override
    public ElementMask getMask() {
        return KotlinIntrinsicsConcealingStrategyConfig.DefaultImpls.getMask(this);
    }
    
    @Override
    public boolean getOverrideGlobalMask() {
        return KotlinIntrinsicsConcealingStrategyConfig.DefaultImpls.getOverrideGlobalMask(this);
    }
}