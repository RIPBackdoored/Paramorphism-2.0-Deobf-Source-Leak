package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class tn extends Lambda implements Function1 {
   public final tt bif;

   public Object invoke(Object var1) {
      return this.a((String)var1);
   }

   public final boolean a(@NotNull String var1) {
      return li.a(tt.a(this.bif), var1);
   }

   public tn(tt var1) {
      super(1);
      this.bif = var1;
   }
}
