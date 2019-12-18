package site.hackery.paramorphism.api.config.strategies.corruption;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import paramorphism-obfuscator.kc;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.StrategyConfiguration$DefaultImpls;
import site.hackery.paramorphism.api.config.StrategyIrregularity;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3
)
public final class ForceNoVerifyStrategyConfig$DefaultImpls {
   @NotNull
   public static StrategyIrregularity[] getIrregularities(ForceNoVerifyStrategyConfig var0) {
      return new StrategyIrregularity[]{(StrategyIrregularity)kc.qv};
   }

   @Nullable
   public static Boolean getEnabled(ForceNoVerifyStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getEnabled((StrategyConfiguration)var0);
   }

   @NotNull
   public static ElementMask getMask(ForceNoVerifyStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getMask((StrategyConfiguration)var0);
   }

   public static boolean getOverrideGlobalMask(ForceNoVerifyStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getOverrideGlobalMask((StrategyConfiguration)var0);
   }
}
