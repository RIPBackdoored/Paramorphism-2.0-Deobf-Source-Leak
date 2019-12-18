package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class yg {
   private final IInstructionWrapper bku;
   @NotNull
   private final LabelNode bkv;

   public final void a() {
      this.bku.getInstructions().add((AbstractInsnNode)this.bkv);
   }

   @NotNull
   public final LabelNode b() {
      return this.bkv;
   }

   public yg(@NotNull IInstructionWrapper var1, @NotNull LabelNode var2) {
      super();
      this.bku = var1;
      this.bkv = var2;
   }
}
