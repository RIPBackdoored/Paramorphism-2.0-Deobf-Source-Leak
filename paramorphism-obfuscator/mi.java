package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassSet;

public final class mi extends Lambda implements Function0 {
   public static final mi uy = new mi();

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final ub a() {
      return new ub((ClassSet)null);
   }

   public mi() {
      super(0);
   }
}
