package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig$DefaultImpls;

public final class iy extends Lambda implements Function0 {
   public final ja nx;

   public Object invoke() {
      return this.a();
   }

   public final boolean a() {
      JsonNode var10000 = this.nx.c().e(this.nx.b() + ".exclude_enums");
      return var10000 != null ? var10000.asBoolean() : RemappingStrategyConfig$DefaultImpls.getExcludeEnums(this.nx);
   }

   public iy(ja var1) {
      super(0);
      this.nx = var1;
   }
}
