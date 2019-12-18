package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyIrregularity;
import site.hackery.paramorphism.api.config.strategies.corruption.NullByteNamingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.corruption.NullByteNamingStrategyConfig$DefaultImpls;

public final class jw extends jk implements NullByteNamingStrategyConfig {
   public final jg qg;

   public jw(jg var1, String var2, jn var3) {
      super(var2, var3);
      this.qg = var1;
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return NullByteNamingStrategyConfig$DefaultImpls.getIrregularities(this);
   }
}
