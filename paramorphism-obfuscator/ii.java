package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

public final class ii implements ig {
   public final Function1 mp;

   public void b(@NotNull Object var1) {
      this.mp.invoke(var1);
   }

   public ii(Function1 var1) {
      super();
      this.mp = var1;
   }
}
