package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import site.hackery.paramorphism.api.config.ParamorphismConfig$DefaultImpls;

public final class jq extends Lambda implements Function0 {
   public final jg pw;

   public Object invoke() {
      return this.a();
   }

   public final boolean a() {
      JsonNode var10000 = this.pw.e("branding");
      return var10000 != null ? var10000.booleanValue() : ParamorphismConfig$DefaultImpls.getBranding(this.pw);
   }

   public jq(jg var1) {
      super(0);
      this.pw = var1;
   }
}
