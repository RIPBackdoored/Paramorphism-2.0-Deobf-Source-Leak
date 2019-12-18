package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class tm extends Lambda implements Function1 {
   public final ts bie;

   public Object invoke(Object var1) {
      return this.a((ClassInfo)var1);
   }

   public final boolean a(@NotNull ClassInfo var1) {
      String var2 = var1.component1();
      return !li.a(ts.a(this.bie), var2);
   }

   public tm(ts var1) {
      super(1);
      this.bie = var1;
   }
}
