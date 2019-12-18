package paramorphism-obfuscator;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class tp extends Lambda implements Function1 {
   public final tr bih;

   public Object invoke(Object var1) {
      return this.a((Pair)var1);
   }

   public final boolean a(@NotNull Pair var1) {
      String var2 = (String)var1.component1();
      return li.a(tr.a(this.bih), (String)tr.b(this.bih).invoke(var2));
   }

   public tp(tr var1) {
      super(1);
      this.bih = var1;
   }
}
