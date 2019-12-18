package site.hackery.paramorphism.api.config.strategies.obfuscation.stripping;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.StrategyConfiguration;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0016¨\u0006\u0005"},
   d2 = {"Lsite/hackery/paramorphism/api/config/strategies/obfuscation/stripping/SourceFileStrippingStrategyConfig;", "Lsite/hackery/paramorphism/api/config/StrategyConfiguration;", "sourceFile", "", "internalName", "paramorphism"}
)
public interface SourceFileStrippingStrategyConfig extends StrategyConfiguration {
   @Nullable
   String sourceFile(@NotNull String var1);
}
