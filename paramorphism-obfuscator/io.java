package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyIrregularity;
import site.hackery.paramorphism.api.config.strategies.obfuscation.flow.JumpReplacementStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.flow.JumpReplacementStrategyConfig$DefaultImpls;

public final class io extends jk implements JumpReplacementStrategyConfig {
   public final jg nk;

   public io(jg var1, String var2, jn var3) {
      super(var2, var3);
      this.nk = var1;
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return JumpReplacementStrategyConfig$DefaultImpls.getIrregularities(this);
   }
}
