package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class nq extends Lambda implements Function1 {
   public final nr wz;

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      var1.d().sourceFile = this.wz.a().sourceFile(var1.d().name);
      var1.d().sourceDebug = (String)null;
      var1.a(true);
   }

   public nq(nr var1) {
      super(1);
      this.wz = var1;
   }
}
