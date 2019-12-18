package paramorphism-obfuscator.instruction;

import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class NumberInstruction {
   @NotNull
   public static final Unit addNull(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ACONST_NULL)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addNot(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ICONST_M1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ICONST_0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ICONST_1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addITwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ICONST_2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ICONST_3)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIFour(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ICONST_4)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIFive(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ICONST_5)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LCONST_0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LCONST_1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FCONST_0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FCONST_1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FCONST_2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.DCONST_0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.DCONST_1)));
      return Unit.INSTANCE;
   }

   public static final void addSmallInt(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new IntInsnNode(Opcodes.BIPUSH, var1)));
   }

   public static final void addBigInt(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new IntInsnNode(Opcodes.SIPUSH, var1)));
   }

   public static final void addLdc(@NotNull IInstructionWrapper var0, @NotNull Object var1) {
      var0.getInstructions().add((AbstractInsnNode)(new LdcInsnNode(var1)));
   }
}
