package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class mr extends Lambda implements Function1 {
   public final mm vl;
   public final kg vm;

   public Object invoke(Object var1) {
      return this.a((md)var1);
   }

   public final boolean a(@NotNull md var1) {
      return this.vl.a(this.vm, var1.c());
   }

   public mr(mm var1, kg var2) {
      super(1);
      this.vl = var1;
      this.vm = var2;
   }
}
