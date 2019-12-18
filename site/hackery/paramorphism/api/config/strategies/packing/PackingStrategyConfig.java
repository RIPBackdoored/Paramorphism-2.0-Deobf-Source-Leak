package site.hackery.paramorphism.api.config.strategies.packing;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.StrategyIrregularity;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006\r"},
   d2 = {"Lsite/hackery/paramorphism/api/config/strategies/packing/PackingStrategyConfig;", "Lsite/hackery/paramorphism/api/config/StrategyConfiguration;", "irregularities", "", "Lsite/hackery/paramorphism/api/config/StrategyIrregularity;", "getIrregularities", "()[Lsite/hackery/paramorphism/api/config/StrategyIrregularity;", "packedExtension", "", "getPackedExtension", "()Ljava/lang/String;", "packedPrefix", "getPackedPrefix", "paramorphism"}
)
public interface PackingStrategyConfig extends StrategyConfiguration {
   @NotNull
   StrategyIrregularity[] getIrregularities();

   @NotNull
   String getPackedPrefix();

   @NotNull
   String getPackedExtension();
}
