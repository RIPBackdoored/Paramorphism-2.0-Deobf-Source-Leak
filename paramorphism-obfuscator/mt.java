package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class mt extends Lambda implements Function1 {
   public final mm vq;
   public final kg vr;

   public Object invoke(Object var1) {
      return this.a((String)var1);
   }

   public final boolean a(@NotNull String var1) {
      return this.vq.a(this.vr, var1);
   }

   public mt(mm var1, kg var2) {
      super(1);
      this.vq = var1;
      this.vr = var2;
   }
}
