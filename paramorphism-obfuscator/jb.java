package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig$DefaultImpls;

public final class jb extends Lambda implements Function0 {
   public final ja og;

   public Object invoke() {
      return this.a();
   }

   public final boolean a() {
      JsonNode var10000 = this.og.c().e(this.og.b() + ".flat");
      return var10000 != null ? var10000.asBoolean() : RemappingStrategyConfig$DefaultImpls.getFlat(this.og);
   }

   public jb(ja var1) {
      super(0);
      this.og = var1;
   }
}
