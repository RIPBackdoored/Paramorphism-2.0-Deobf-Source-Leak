package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class uc extends Lambda implements Function1 {
   public final tx bja;

   public uc(tx var1) {
      super(1);
      this.bja = var1;
   }

   public Object invoke(Object var1) {
      return this.a((String)var1);
   }

   public final boolean a(@NotNull String var1) {
      return !tx.a(this.bja).containsKey(var1);
   }
}
