package site.hackery.paramorphism.api.config.strategies.obfuscation.kotlin;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.StrategyConfiguration$DefaultImpls;
import site.hackery.paramorphism.api.config.StrategyIrregularity;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3
)
public final class KotlinMetadataStrippingStrategyConfig$DefaultImpls {
   @Nullable
   public static Boolean getEnabled(KotlinMetadataStrippingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getEnabled((StrategyConfiguration)var0);
   }

   @NotNull
   public static StrategyIrregularity[] getIrregularities(KotlinMetadataStrippingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getIrregularities((StrategyConfiguration)var0);
   }

   @NotNull
   public static ElementMask getMask(KotlinMetadataStrippingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getMask((StrategyConfiguration)var0);
   }

   public static boolean getOverrideGlobalMask(KotlinMetadataStrippingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getOverrideGlobalMask((StrategyConfiguration)var0);
   }
}
