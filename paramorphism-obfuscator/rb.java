package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class rb extends Lambda implements Function0 {
   public final rn bei;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final String a() {
      return rn.a(this.bei) + "_s/";
   }

   public rb(rn var1) {
      super(0);
      this.bei = var1;
   }
}
