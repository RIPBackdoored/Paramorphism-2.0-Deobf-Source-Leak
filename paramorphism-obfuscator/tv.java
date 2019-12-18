package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class tv extends Lambda implements Function1 {
   public final ts bir;

   public Object invoke(Object var1) {
      return this.a((String)var1);
   }

   public final boolean a(@NotNull String var1) {
      return !li.a(ts.a(this.bir), var1);
   }

   public tv(ts var1) {
      super(1);
      this.bir = var1;
   }
}
