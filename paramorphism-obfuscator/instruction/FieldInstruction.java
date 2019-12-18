package paramorphism-obfuscator.instruction;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import paramorphism-obfuscator.Primitives;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class FieldInstruction {
   public static final void addGetStatic(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3) {
      var0.getInstructions().add((AbstractInsnNode)(new FieldInsnNode(Opcodes.GETSTATIC, Primitives.getType(var1).getInternalName(), var2, Primitives.getType(var3).getDescriptor())));
   }

   public static final void addGetField(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3) {
      var0.getInstructions().add((AbstractInsnNode)(new FieldInsnNode(Opcodes.GETFIELD, Primitives.getType(var1).getInternalName(), var2, Primitives.getType(var3).getDescriptor())));
   }

   public static final void addPutStatic(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3) {
      var0.getInstructions().add((AbstractInsnNode)(new FieldInsnNode(Opcodes.PUTSTATIC, Primitives.getType(var1).getInternalName(), var2, Primitives.getType(var3).getDescriptor())));
   }

   public static final void addPutField(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3) {
      var0.getInstructions().add((AbstractInsnNode)(new FieldInsnNode(Opcodes.PUTFIELD, Primitives.getType(var1).getInternalName(), var2, Primitives.getType(var3).getDescriptor())));
   }
}
