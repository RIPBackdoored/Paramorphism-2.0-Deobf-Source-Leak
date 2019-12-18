package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;

public final class lo implements lr {
   private final int ub;

   public boolean a(@NotNull AbstractInsnNode var1) {
      return var1.getOpcode() == this.ub;
   }

   public lo(int var1) {
      super();
      this.ub = var1;
   }
}
