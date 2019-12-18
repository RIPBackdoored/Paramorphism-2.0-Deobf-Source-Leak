package site.hackery.paramorphism.api.config.strategies.obfuscation.remapper;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.naming.NameGenerationConfig;
import site.hackery.paramorphism.api.config.naming.NameGenerationConfig$DefaultImpls;
import site.hackery.paramorphism.api.config.naming.NameGenerationDictionaries;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002"},
   d2 = {"site/hackery/paramorphism/api/config/strategies/obfuscation/remapper/RemappingStrategyConfig$nameGeneration$1", "Lsite/hackery/paramorphism/api/config/naming/NameGenerationConfig;", "paramorphism"}
)
public final class RemappingStrategyConfig$nameGeneration$1 implements NameGenerationConfig {
   RemappingStrategyConfig$nameGeneration$1() {
      super();
   }

   @NotNull
   public NameGenerationDictionaries getDictionaries() {
      return NameGenerationConfig$DefaultImpls.getDictionaries(this);
   }

   public boolean getShuffle() {
      return NameGenerationConfig$DefaultImpls.getShuffle(this);
   }
}
