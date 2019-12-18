package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import site.hackery.paramorphism.api.config.ParamorphismConfig$DefaultImpls;

public final class is extends Lambda implements Function0 {
   public final jg no;

   public Object invoke() {
      return this.a();
   }

   public final boolean a() {
      JsonNode var10000 = this.no.e("use_java_runtime");
      return var10000 != null ? var10000.booleanValue() : ParamorphismConfig$DefaultImpls.getUseJavaRuntime(this.no);
   }

   public is(jg var1) {
      super(0);
      this.no = var1;
   }
}
