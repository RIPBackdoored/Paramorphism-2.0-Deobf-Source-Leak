package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.StrategyConfiguration$DefaultImpls;

public final class iq extends Lambda implements Function0 {
   public final jk nm;

   public Object invoke() {
      return this.a();
   }

   @Nullable
   public final Boolean a() {
      JsonNode var10000 = this.nm.c().e(this.nm.b() + ".enabled");
      return var10000 != null ? var10000.asBoolean() : StrategyConfiguration$DefaultImpls.getEnabled(this.nm);
   }

   public iq(jk var1) {
      super(0);
      this.nm = var1;
   }
}
