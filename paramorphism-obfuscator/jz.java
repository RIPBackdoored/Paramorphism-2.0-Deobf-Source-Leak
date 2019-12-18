package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import site.hackery.paramorphism.api.config.StrategyConfiguration$DefaultImpls;

public final class jz extends Lambda implements Function0 {
   public final jk qs;

   public Object invoke() {
      return this.a();
   }

   public final boolean a() {
      JsonNode var10000 = this.qs.c().e(this.qs.b() + ".override_global_mask");
      return var10000 != null ? var10000.asBoolean() : StrategyConfiguration$DefaultImpls.getOverrideGlobalMask(this.qs);
   }

   public jz(jk var1) {
      super(0);
      this.qs = var1;
   }
}
