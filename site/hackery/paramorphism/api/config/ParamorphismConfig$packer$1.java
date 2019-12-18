package site.hackery.paramorphism.api.config;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.strategies.packing.PackingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.packing.PackingStrategyConfig$DefaultImpls;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002"},
   d2 = {"site/hackery/paramorphism/api/config/ParamorphismConfig$packer$1", "Lsite/hackery/paramorphism/api/config/strategies/packing/PackingStrategyConfig;", "paramorphism"}
)
public final class ParamorphismConfig$packer$1 implements PackingStrategyConfig {
   ParamorphismConfig$packer$1() {
      super();
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return PackingStrategyConfig$DefaultImpls.getIrregularities(this);
   }

   @NotNull
   public String getPackedPrefix() {
      return PackingStrategyConfig$DefaultImpls.getPackedPrefix(this);
   }

   @NotNull
   public String getPackedExtension() {
      return PackingStrategyConfig$DefaultImpls.getPackedExtension(this);
   }

   @Nullable
   public Boolean getEnabled() {
      return PackingStrategyConfig$DefaultImpls.getEnabled(this);
   }

   @NotNull
   public ElementMask getMask() {
      return PackingStrategyConfig$DefaultImpls.getMask(this);
   }

   public boolean getOverrideGlobalMask() {
      return PackingStrategyConfig$DefaultImpls.getOverrideGlobalMask(this);
   }
}
