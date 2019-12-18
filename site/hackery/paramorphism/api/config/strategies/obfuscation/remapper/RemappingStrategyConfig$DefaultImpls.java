package site.hackery.paramorphism.api.config.strategies.obfuscation.remapper;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.StrategyConfiguration$DefaultImpls;
import site.hackery.paramorphism.api.config.StrategyIrregularity;
import site.hackery.paramorphism.api.config.naming.NameGenerationConfig;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class RemappingStrategyConfig$DefaultImpls {
   @NotNull
   public static String[] getManifestEntries(RemappingStrategyConfig var0) {
      return new String[]{"Main-Class", "Agent-Class", "Premain-Class"};
   }

   public static boolean getFlat(RemappingStrategyConfig var0) {
      return false;
   }

   @Nullable
   public static String getPrefix(RemappingStrategyConfig var0) {
      return null;
   }

   @NotNull
   public static NameGenerationConfig getNameGeneration(RemappingStrategyConfig var0) {
      return (NameGenerationConfig)(new RemappingStrategyConfig$nameGeneration$1());
   }

   public static boolean getExcludeEnums(RemappingStrategyConfig var0) {
      return false;
   }

   @Nullable
   public static Boolean getEnabled(RemappingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getEnabled((StrategyConfiguration)var0);
   }

   @NotNull
   public static StrategyIrregularity[] getIrregularities(RemappingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getIrregularities((StrategyConfiguration)var0);
   }

   @NotNull
   public static ElementMask getMask(RemappingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getMask((StrategyConfiguration)var0);
   }

   public static boolean getOverrideGlobalMask(RemappingStrategyConfig var0) {
      return StrategyConfiguration$DefaultImpls.getOverrideGlobalMask((StrategyConfiguration)var0);
   }
}
