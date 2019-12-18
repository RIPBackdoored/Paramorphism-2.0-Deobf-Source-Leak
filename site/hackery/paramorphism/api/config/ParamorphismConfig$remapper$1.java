package site.hackery.paramorphism.api.config;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.naming.NameGenerationConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig$DefaultImpls;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002"},
   d2 = {"site/hackery/paramorphism/api/config/ParamorphismConfig$remapper$1", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/remapper/RemappingStrategyConfig;", "paramorphism"}
)
public final class ParamorphismConfig$remapper$1 implements RemappingStrategyConfig {
   ParamorphismConfig$remapper$1() {
      super();
   }

   @NotNull
   public String[] getManifestEntries() {
      return RemappingStrategyConfig$DefaultImpls.getManifestEntries(this);
   }

   public boolean getFlat() {
      return RemappingStrategyConfig$DefaultImpls.getFlat(this);
   }

   @NotNull
   public NameGenerationConfig getNameGeneration() {
      return RemappingStrategyConfig$DefaultImpls.getNameGeneration(this);
   }

   public boolean getExcludeEnums() {
      return RemappingStrategyConfig$DefaultImpls.getExcludeEnums(this);
   }

   @Nullable
   public Boolean getEnabled() {
      return RemappingStrategyConfig$DefaultImpls.getEnabled(this);
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return RemappingStrategyConfig$DefaultImpls.getIrregularities(this);
   }

   @NotNull
   public ElementMask getMask() {
      return RemappingStrategyConfig$DefaultImpls.getMask(this);
   }

   public boolean getOverrideGlobalMask() {
      return RemappingStrategyConfig$DefaultImpls.getOverrideGlobalMask(this);
   }
}
