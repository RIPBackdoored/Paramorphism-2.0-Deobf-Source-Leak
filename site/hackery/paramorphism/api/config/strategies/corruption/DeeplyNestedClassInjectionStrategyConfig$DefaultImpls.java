package site.hackery.paramorphism.api.config.strategies.corruption;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import paramorphism-obfuscator.kd;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.StrategyConfiguration$DefaultImpls;
import site.hackery.paramorphism.api.config.StrategyIrregularity;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3
)
public final class DeeplyNestedClassInjectionStrategyConfig$DefaultImpls {
   @NotNull
   public static StrategyIrregularity[] getIrregularities(DeeplyNestedClassInjectionStrategyConfig var0) {
      return new StrategyIrregularity[]{(StrategyIrregularity)kd.qw};
   }

   @Nullable
   public static Boolean getEnabled(DeeplyNestedClassInjectionStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getEnabled((StrategyConfiguration)var0);
   }

   @NotNull
   public static ElementMask getMask(DeeplyNestedClassInjectionStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getMask((StrategyConfiguration)var0);
   }

   public static boolean getOverrideGlobalMask(DeeplyNestedClassInjectionStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getOverrideGlobalMask((StrategyConfiguration)var0);
   }
}
