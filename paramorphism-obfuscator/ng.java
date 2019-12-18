package paramorphism-obfuscator;

import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class ng extends Lambda implements Function1 {
   public final Set wk;
   public final nf wl;

   public Object invoke(Object var1) {
      this.a((lz)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull lz var1) {
      if (this.wk.contains(var1.a().getOriginalName())) {
         this.wl.a((me)var1);
      }
   }

   public ng(Set var1, nf var2) {
      super(1);
      this.wk = var1;
      this.wl = var2;
   }
}
