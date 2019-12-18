package site.hackery.paramorphism.api.config;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"},
   d2 = {"Lsite/hackery/paramorphism/api/config/StrategyConfiguration;", "", "enabled", "", "getEnabled", "()Ljava/lang/Boolean;", "irregularities", "", "Lsite/hackery/paramorphism/api/config/StrategyIrregularity;", "getIrregularities", "()[Lsite/hackery/paramorphism/api/config/StrategyIrregularity;", "mask", "Lsite/hackery/paramorphism/api/config/ElementMask;", "getMask", "()Lsite/hackery/paramorphism/api/config/ElementMask;", "overrideGlobalMask", "getOverrideGlobalMask", "()Z", "paramorphism"}
)
public interface StrategyConfiguration {
   @Nullable
   Boolean getEnabled();

   @NotNull
   StrategyIrregularity[] getIrregularities();

   @NotNull
   ElementMask getMask();

   boolean getOverrideGlobalMask();
}
