package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class ua extends Lambda implements Function1 {
   public final ub bix;

   public ua(ub var1) {
      super(1);
      this.bix = var1;
   }

   public Object invoke(Object var1) {
      return this.a((String)var1);
   }

   public final boolean a(@NotNull String var1) {
      return !ub.a(this.bix).containsKey(var1);
   }
}
