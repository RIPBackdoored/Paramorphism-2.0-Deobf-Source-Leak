package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import site.hackery.paramorphism.api.config.naming.NameGenerationConfig$DefaultImpls;

public final class iu extends Lambda implements Function0 {
   public final iv nq;

   public Object invoke() {
      return this.a();
   }

   public final boolean a() {
      JsonNode var10000 = iv.b(this.nq).e(iv.a(this.nq) + ".shuffle");
      return var10000 != null ? var10000.asBoolean() : NameGenerationConfig$DefaultImpls.getShuffle(this.nq);
   }

   public iu(iv var1) {
      super(0);
      this.nq = var1;
   }
}
