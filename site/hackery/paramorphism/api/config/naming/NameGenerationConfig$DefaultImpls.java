package site.hackery.paramorphism.api.config.naming;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.naming.Dictionaries;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3
)
public final class NameGenerationConfig$DefaultImpls {
   @NotNull
   public static NameGenerationDictionaries getDictionaries(NameGenerationConfig var0) {
      return (NameGenerationDictionaries)(new UniformNameGenerationDictionaries(Dictionaries.INSTANCE.getALPHABET()));
   }

   public static boolean getShuffle(NameGenerationConfig var0) {
      return false;
   }
}
