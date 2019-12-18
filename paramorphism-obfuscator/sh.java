package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class sh extends Lambda implements Function1 {
   public static final sh bgx = new sh();

   public Object invoke(Object var1) {
      return this.a((ClassInfo)var1);
   }

   @NotNull
   public final String a(@NotNull ClassInfo var1) {
      return var1.getOriginalName();
   }

   public sh() {
      super(1);
   }
}
