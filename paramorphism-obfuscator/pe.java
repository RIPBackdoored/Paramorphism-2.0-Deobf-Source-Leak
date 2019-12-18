package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class pe extends Lambda implements Function1 {
   public static final pe bax = new pe();

   public Object invoke(Object var1) {
      return this.a((ClassInfo)var1);
   }

   @NotNull
   public final ClassNode a(@NotNull ClassInfo var1) {
      ClassNode var2 = var1.component2();
      return var2;
   }

   public pe() {
      super(1);
   }
}
