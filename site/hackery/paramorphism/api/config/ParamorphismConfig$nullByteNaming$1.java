package site.hackery.paramorphism.api.config;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.strategies.corruption.NullByteNamingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.corruption.NullByteNamingStrategyConfig$DefaultImpls;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002"},
   d2 = {"site/hackery/paramorphism/api/config/ParamorphismConfig$nullByteNaming$1", "Lsite/hackery/paramorphism/api/config/strategies/corruption/NullByteNamingStrategyConfig;", "paramorphism"}
)
public final class ParamorphismConfig$nullByteNaming$1 implements NullByteNamingStrategyConfig {
   ParamorphismConfig$nullByteNaming$1() {
      super();
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return NullByteNamingStrategyConfig$DefaultImpls.getIrregularities(this);
   }

   @Nullable
   public Boolean getEnabled() {
      return NullByteNamingStrategyConfig$DefaultImpls.getEnabled(this);
   }

   @NotNull
   public ElementMask getMask() {
      return NullByteNamingStrategyConfig$DefaultImpls.getMask(this);
   }

   public boolean getOverrideGlobalMask() {
      return NullByteNamingStrategyConfig$DefaultImpls.getOverrideGlobalMask(this);
   }
}
