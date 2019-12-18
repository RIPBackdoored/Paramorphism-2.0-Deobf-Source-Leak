package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class tz extends Lambda implements Function1 {
   public final ub biw;

   public tz(ub var1) {
      super(1);
      this.biw = var1;
   }

   public Object invoke(Object var1) {
      return this.a((ClassInfo)var1);
   }

   public final boolean a(@NotNull ClassInfo var1) {
      String var2 = var1.component1();
      return !ub.a(this.biw).containsKey(var2);
   }
}
