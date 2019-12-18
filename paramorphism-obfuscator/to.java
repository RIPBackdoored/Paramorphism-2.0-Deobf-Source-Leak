package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class to extends Lambda implements Function1 {
   public final tr big;

   public Object invoke(Object var1) {
      return this.a((String)var1);
   }

   public final boolean a(@NotNull String var1) {
      return li.a(tr.a(this.big), (String)tr.b(this.big).invoke(var1));
   }

   public to(tr var1) {
      super(1);
      this.big = var1;
   }
}
