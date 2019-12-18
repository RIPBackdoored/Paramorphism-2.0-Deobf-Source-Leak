package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class tu extends Lambda implements Function1 {
   public final tt biq;

   public Object invoke(Object var1) {
      return this.a((ClassInfo)var1);
   }

   public final boolean a(@NotNull ClassInfo var1) {
      String var2 = var1.component1();
      return li.a(tt.a(this.biq), var2);
   }

   public tu(tt var1) {
      super(1);
      this.biq = var1;
   }
}
