package site.hackery.paramorphism.api.config.strategies.packing;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import paramorphism-obfuscator.kd;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.StrategyConfiguration$DefaultImpls;
import site.hackery.paramorphism.api.config.StrategyIrregularity;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class PackingStrategyConfig$DefaultImpls {
   @NotNull
   public static StrategyIrregularity[] getIrregularities(PackingStrategyConfig var0) {
      return new StrategyIrregularity[]{(StrategyIrregularity)kd.qw};
   }

   @NotNull
   public static String getPackedPrefix(PackingStrategyConfig var0) {
      return "META-INF/classes/";
   }

   @NotNull
   public static String getPackedExtension(PackingStrategyConfig var0) {
      return ".class";
   }

   @Nullable
   public static Boolean getEnabled(PackingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getEnabled((StrategyConfiguration)var0);
   }

   @NotNull
   public static ElementMask getMask(PackingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getMask((StrategyConfiguration)var0);
   }

   public static boolean getOverrideGlobalMask(PackingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getOverrideGlobalMask((StrategyConfiguration)var0);
   }
}
