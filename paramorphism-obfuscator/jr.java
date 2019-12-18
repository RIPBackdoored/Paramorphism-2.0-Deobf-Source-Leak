package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyIrregularity;
import site.hackery.paramorphism.api.config.strategies.corruption.DeeplyNestedClassInjectionStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.corruption.DeeplyNestedClassInjectionStrategyConfig$DefaultImpls;

public final class jr extends jk implements DeeplyNestedClassInjectionStrategyConfig {
   public final jg px;

   public jr(jg var1, String var2, jn var3) {
      super(var2, var3);
      this.px = var1;
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return DeeplyNestedClassInjectionStrategyConfig$DefaultImpls.getIrregularities(this);
   }
}
