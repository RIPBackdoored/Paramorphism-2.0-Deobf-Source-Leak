package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

public final class mp implements ig {
   public final mm ve;
   public final Function1 vf;

   public void b(@NotNull Object var1) {
      if (!(var1 instanceof mb) || ((mb)var1).a() == mm.a(this.ve)) {
         this.vf.invoke(var1);
      }
   }

   public mp(mm var1, Function1 var2) {
      super();
      this.ve = var1;
      this.vf = var2;
   }
}
