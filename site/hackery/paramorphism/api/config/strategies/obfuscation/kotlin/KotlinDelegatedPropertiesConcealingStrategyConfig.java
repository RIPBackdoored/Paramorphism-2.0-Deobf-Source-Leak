package site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin;

import kotlin.*;
import org.jetbrains.annotations.*;
import site.hackery.paramorphism.api.config.*;

@Metadata(mv = { 1, 1, 16 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001¨\u0006\u0002" }, d2 = { "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/kotlin/KotlinDelegatedPropertiesConcealingStrategyConfig;", "Lsite/hackery/paramorphism/api/config/StrategyConfiguration;", "paramorphism" })
public interface KotlinDelegatedPropertiesConcealingStrategyConfig extends StrategyConfiguration
{
    @Metadata(mv = { 1, 1, 16 }, bv = { 1, 0, 3 }, k = 3)
    public static final class DefaultImpls
    {
        @Nullable
        public static Boolean getEnabled(final KotlinDelegatedPropertiesConcealingStrategyConfig kotlinDelegatedPropertiesConcealingStrategyConfig) {
            return StrategyConfiguration.DefaultImpls.getEnabled(kotlinDelegatedPropertiesConcealingStrategyConfig);
        }
        
        @NotNull
        public static StrategyIrregularity[] getIrregularities(final KotlinDelegatedPropertiesConcealingStrategyConfig kotlinDelegatedPropertiesConcealingStrategyConfig) {
            return StrategyConfiguration.DefaultImpls.getIrregularities(kotlinDelegatedPropertiesConcealingStrategyConfig);
        }
        
        @NotNull
        public static ElementMask getMask(final KotlinDelegatedPropertiesConcealingStrategyConfig kotlinDelegatedPropertiesConcealingStrategyConfig) {
            return StrategyConfiguration.DefaultImpls.getMask(kotlinDelegatedPropertiesConcealingStrategyConfig);
        }
        
        public static boolean getOverrideGlobalMask(final KotlinDelegatedPropertiesConcealingStrategyConfig kotlinDelegatedPropertiesConcealingStrategyConfig) {
            return StrategyConfiguration.DefaultImpls.getOverrideGlobalMask(kotlinDelegatedPropertiesConcealingStrategyConfig);
        }
    }
}
