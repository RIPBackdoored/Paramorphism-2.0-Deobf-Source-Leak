package paramorphism-obfuscator.instruction;

import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnNode;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class MathInstruction {
   @NotNull
   public static final Unit addIAdd(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.IADD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLAdd(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LADD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFAdd(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FADD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDAdd(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.DADD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addISubtract(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ISUB)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLSubtract(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LSUB)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFSubtract(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FSUB)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDSubtract(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.DSUB)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIMultiply(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.IMUL)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLMultiply(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LMUL)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFMultiply(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FMUL)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDMultiply(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.DMUL)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIDivide(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.IDIV)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLDivide(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LDIV)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFDivide(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FDIV)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDDivide(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.DDIV)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIRemainder(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.IREM)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLRemainder(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LREM)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFRemainder(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FREM)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDRemainder(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.DREM)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addINegative(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.INEG)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLNegative(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LNEG)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFNegative(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FNEG)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDNegative(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.DNEG)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIShiftLeft(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ISHL)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLShiftLeft(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LSHL)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIShiftRight(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ISHR)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLShiftRight(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LSHR)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addILogicalShiftRight(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.IUSHR)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLLogicalShiftRight(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LUSHR)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIAnd(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.IAND)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLAnd(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LAND)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIOr(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.IOR)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLOr(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LOR)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIExclusiveOr(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.IXOR)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLExclusiveOr(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LXOR)));
      return Unit.INSTANCE;
   }

   public static final void addIIncrementOne(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new IincInsnNode(var1, 1)));
   }

   public static final void addIIncrement(@NotNull IInstructionWrapper var0, int var1, int var2) {
      var0.getInstructions().add((AbstractInsnNode)(new IincInsnNode(var1, var2)));
   }

   @NotNull
   public static final Unit addIntToLong(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.I2L)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntToFloat(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.I2F)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntToDouble(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.I2D)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongToInt(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.L2I)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongToFloat(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.L2F)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongToDouble(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.L2D)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatToInt(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.F2I)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatToLong(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.F2L)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatToDouble(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.F2D)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleToInt(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.D2I)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleToLong(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.D2L)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleToFloat(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.D2F)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntToByte(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.I2B)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntToChar(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.I2C)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntToShort(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.I2S)));
      return Unit.INSTANCE;
   }
}
