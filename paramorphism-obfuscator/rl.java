package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class rl extends Lambda implements Function1 {
   public final Function1 bfg;

   public Object invoke(Object var1) {
      return this.a((ClassInfo)var1);
   }

   public final boolean a(@NotNull ClassInfo var1) {
      String var2 = var1.component1();
      return (Boolean)this.bfg.invoke(var2);
   }

   public rl(Function1 var1) {
      super(1);
      this.bfg = var1;
   }
}
