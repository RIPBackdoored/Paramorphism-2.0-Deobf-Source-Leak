package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.MethodNode;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class xk {
   @NotNull
   public static final MethodNode a(@NotNull MethodNode var0, @NotNull Function1 var1) {
      MethodWrapper var2 = new MethodWrapper(var0);
      var1.invoke(var2);
      return var2.getMethod();
   }
}
