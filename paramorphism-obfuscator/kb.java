package paramorphism-obfuscator;

import java.io.File;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ParamorphismConfig$DefaultImpls;

public final class kb extends Lambda implements Function0 {
   public final jg qu;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final File a() {
      File var10000 = this.qu.a("input");
      if (var10000 == null) {
         var10000 = ParamorphismConfig$DefaultImpls.getInput(this.qu);
      }

      return var10000;
   }

   public kb(jg var1) {
      super(0);
      this.qu = var1;
   }
}
