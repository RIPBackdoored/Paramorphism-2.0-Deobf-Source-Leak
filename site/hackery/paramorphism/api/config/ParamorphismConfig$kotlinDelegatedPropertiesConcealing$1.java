package site.hackery.paramorphism.api.config;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin.KotlinDelegatedPropertiesConcealingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin.KotlinDelegatedPropertiesConcealingStrategyConfig$DefaultImpls;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002"},
   d2 = {"site/hackery/paramorphism/api/config/ParamorphismConfig$kotlinDelegatedPropertiesConcealing$1", "Lsite/hackery/paramorphism/api/config/strategies/obfuscation/kotlin/KotlinDelegatedPropertiesConcealingStrategyConfig;", "paramorphism"}
)
public final class ParamorphismConfig$kotlinDelegatedPropertiesConcealing$1 implements KotlinDelegatedPropertiesConcealingStrategyConfig {
   ParamorphismConfig$kotlinDelegatedPropertiesConcealing$1() {
      super();
   }

   @Nullable
   public Boolean getEnabled() {
      return KotlinDelegatedPropertiesConcealingStrategyConfig$DefaultImpls.getEnabled(this);
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return KotlinDelegatedPropertiesConcealingStrategyConfig$DefaultImpls.getIrregularities(this);
   }

   @NotNull
   public ElementMask getMask() {
      return KotlinDelegatedPropertiesConcealingStrategyConfig$DefaultImpls.getMask(this);
   }

   public boolean getOverrideGlobalMask() {
      return KotlinDelegatedPropertiesConcealingStrategyConfig$DefaultImpls.getOverrideGlobalMask(this);
   }
}
