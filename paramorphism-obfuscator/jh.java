package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.SourceFileStrippingStrategyConfig;
import site.hackery.paramorphism.api.config.strategies.obfuscation.stripping.SourceFileStrippingStrategyConfig$DefaultImpls;

public final class jh extends jk implements SourceFileStrippingStrategyConfig {
   public final jg pj;

   @Nullable
   public String sourceFile(@NotNull String var1) {
      // $FF: Couldn't be decompiled
   }

   public jh(jg var1, String var2, jn var3) {
      super(var2, var3);
      this.pj = var1;
   }
}
