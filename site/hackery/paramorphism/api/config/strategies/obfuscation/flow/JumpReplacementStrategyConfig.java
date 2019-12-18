package site.hackery.paramorphism.api.config.strategies.obfuscation.flow;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.StrategyIrregularity;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lsite/hackery/paramorphism/api/config/strategies/obfuscation/flow/JumpReplacementStrategyConfig;", "Lsite/hackery/paramorphism/api/config/StrategyConfiguration;", "irregularities", "", "Lsite/hackery/paramorphism/api/config/StrategyIrregularity;", "getIrregularities", "()[Lsite/hackery/paramorphism/api/config/StrategyIrregularity;", "paramorphism"}
)
public interface JumpReplacementStrategyConfig extends StrategyConfiguration {
   @NotNull
   StrategyIrregularity[] getIrregularities();
}
