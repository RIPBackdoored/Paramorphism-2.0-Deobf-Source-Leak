package site.hackery.paramorphism.api.config;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.SourceFileStrippingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.SourceFileStrippingStrategyConfig$DefaultImpls;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002"},
   d2 = {"site/hackery/paramorphism/api/config/ParamorphismConfig$sourceFileStripping$1", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/stripping/SourceFileStrippingStrategyConfig;", "paramorphism"}
)
public final class ParamorphismConfig$sourceFileStripping$1 implements SourceFileStrippingStrategyConfig {
   ParamorphismConfig$sourceFileStripping$1() {
      super();
   }

   @Nullable
   public String sourceFile(@NotNull String var1) {
      return SourceFileStrippingStrategyConfig$DefaultImpls.sourceFile(this, var1);
   }

   @Nullable
   public Boolean getEnabled() {
      return SourceFileStrippingStrategyConfig$DefaultImpls.getEnabled(this);
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return SourceFileStrippingStrategyConfig$DefaultImpls.getIrregularities(this);
   }

   @NotNull
   public ElementMask getMask() {
      return SourceFileStrippingStrategyConfig$DefaultImpls.getMask(this);
   }

   public boolean getOverrideGlobalMask() {
      return SourceFileStrippingStrategyConfig$DefaultImpls.getOverrideGlobalMask(this);
   }
}
