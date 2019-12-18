package paramorphism-obfuscator;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class uh extends Lambda implements Function1 {
   public static final uh bjg = new uh();

   public Object invoke(Object var1) {
      return this.a((Pair)var1);
   }

   @NotNull
   public final String a(@NotNull Pair var1) {
      String var2 = (String)var1.component1();
      return var2;
   }

   public uh() {
      super(1);
   }
}
