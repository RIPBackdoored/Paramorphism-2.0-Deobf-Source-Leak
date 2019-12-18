package paramorphism-obfuscator.instruction;

import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class MonitorInstruction {
   @NotNull
   public static final Unit addMonitorEnter(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.MONITORENTER)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addMonitorExit(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.MONITOREXIT)));
      return Unit.INSTANCE;
   }
}
