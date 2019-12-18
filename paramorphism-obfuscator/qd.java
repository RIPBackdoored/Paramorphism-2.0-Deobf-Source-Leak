package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class qd extends Lambda implements Function0 {
   public final qc bcp;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final String a() {
      return qc.a(this.bcp) + "_m/";
   }

   public qd(qc var1) {
      super(0);
      this.bcp = var1;
   }
}
