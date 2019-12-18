package paramorphism-obfuscator;

import java.io.File;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ParamorphismConfig$DefaultImpls;

public final class jv extends Lambda implements Function0 {
   public final jg qf;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final File a() {
      File var10000 = this.qf.a("output");
      if (var10000 == null) {
         var10000 = ParamorphismConfig$DefaultImpls.getOutput(this.qf);
      }

      return var10000;
   }

   public jv(jg var1) {
      super(0);
      this.qf = var1;
   }
}
