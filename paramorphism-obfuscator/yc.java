package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class yc {
   @NotNull
   public static final xt a(@NotNull IInstructionWrapper var0, @NotNull Function1 var1) {
      LabelNode var2 = new LabelNode();
      LabelNode var3 = new LabelNode();
      LabelNode var4 = new LabelNode();
      var0.getInstructions().add((AbstractInsnNode)var2);
      var1.invoke(var0);
      var0.getInstructions().add((AbstractInsnNode)(new JumpInsnNode(Opcodes.GOTO, var4)));
      var0.getInstructions().add((AbstractInsnNode)var3);
      var0.getInstructions().add((AbstractInsnNode)var4);
      return new xt(var0, var2, var3, var4);
   }
}
