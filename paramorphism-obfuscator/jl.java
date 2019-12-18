package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class jl extends Lambda implements Function0 {
   public final ja ps;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final iv a() {
      return new iv(this.ps.b() + ".name_generation", (jn)this.ps.of);
   }

   public jl(ja var1) {
      super(0);
      this.ps = var1;
   }
}
