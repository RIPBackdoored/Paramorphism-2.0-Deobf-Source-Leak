package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class mj extends Lambda implements Function0 {
   public final kg uz;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final tx a() {
      return new tx((ResourceSet)(new sz((ResourceSet)(new tc(this.uz.i().getInput())))));
   }

   public mj(kg var1) {
      super(0);
      this.uz = var1;
   }
}
