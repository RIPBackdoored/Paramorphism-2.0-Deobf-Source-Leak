package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyIrregularity;
import site.hackery.paramorphism.api.config.strategies.corruption.ForceNoVerifyStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.corruption.ForceNoVerifyStrategyConfig$DefaultImpls;

public final class ke extends jk implements ForceNoVerifyStrategyConfig {
   public final jg qx;

   public ke(jg var1, String var2, jn var3) {
      super(var2, var3);
      this.qx = var1;
   }

   @NotNull
   public StrategyIrregularity[] getIrregularities() {
      return ForceNoVerifyStrategyConfig$DefaultImpls.getIrregularities(this);
   }
}
