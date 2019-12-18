package site.hackery.paramorphism.api.config;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.LineNumberStrippingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.LineNumberStrippingStrategyConfig$DefaultImpls;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002"},
   d2 = {"site/hackery/paramorphism/api/config/ParamorphismConfig$lineNumberStripping$1", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/stripping/LineNumberStrippingStrategyConfig;", "paramorphism"}
)
public final class ParamorphismConfig$lineNumberStripping$1 implements LineNumberStrippingStrategyConfig {
   ParamorphismConfig$lineNumberStripping$1() {
      super();
   }

   @Nullable
   public Boolean getEnabled() {
      return LineNumberStrippingStrategyConfig$DefaultImpls.getEnabled(this);
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return LineNumberStrippingStrategyConfig$DefaultImpls.getIrregularities(this);
   }

   @NotNull
   public ElementMask getMask() {
      return LineNumberStrippingStrategyConfig$DefaultImpls.getMask(this);
   }

   public boolean getOverrideGlobalMask() {
      return LineNumberStrippingStrategyConfig$DefaultImpls.getOverrideGlobalMask(this);
   }
}
