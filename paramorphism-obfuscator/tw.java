package paramorphism-obfuscator;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class tw extends Lambda implements Function1 {
   public final tx bis;

   public tw(tx var1) {
      super(1);
      this.bis = var1;
   }

   public Object invoke(Object var1) {
      return this.a((Pair)var1);
   }

   public final boolean a(@NotNull Pair var1) {
      String var2 = (String)var1.component1();
      return !tx.a(this.bis).containsKey(var2);
   }
}
