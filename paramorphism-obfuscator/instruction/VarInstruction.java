package paramorphism-obfuscator.instruction;

import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class VarInstruction {
   public static final void addIntLoad(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ILOAD, var1)));
   }

   public static final void addLongLoad(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.LLOAD, var1)));
   }

   public static final void addFloatLoad(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.FLOAD, var1)));
   }

   public static final void addDoubleLoad(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.DLOAD, var1)));
   }

   public static final void addObjectLoad(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ALOAD, var1)));
   }

   public static final void addIntStore(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ISTORE, var1)));
   }

   public static final void addLongStore(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.LSTORE, var1)));
   }

   public static final void addFloatStore(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.FSTORE, var1)));
   }

   public static final void addDoubleStore(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.DSTORE, var1)));
   }

   public static final void addObjectStore(@NotNull IInstructionWrapper var0, int var1) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ASTORE, var1)));
   }

   @NotNull
   public static final Unit addIntLoadZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ILOAD, 0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongLoadZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.LLOAD, 0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatLoadZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.FLOAD, 0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleLoadZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.DLOAD, 0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addObjectLoadZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ALOAD, 0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntStoreZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ISTORE, 0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongStoreZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.LSTORE, 0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatStoreZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.FSTORE, 0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleStoreZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.DSTORE, 0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addObjectStoreZero(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ASTORE, 0)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntLoadOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ILOAD, 1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongLoadOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.LLOAD, 1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatLoadOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.FLOAD, 1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleLoadOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.DLOAD, 1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addObjectLoadOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ALOAD, 1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntStoreOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ISTORE, 1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongStoreOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.LSTORE, 1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatStoreOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.FSTORE, 1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleStoreOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.DSTORE, 1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addObjectStoreOne(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ASTORE, 1)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntLoadTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ILOAD, 2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongLoadTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.LLOAD, 2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatLoadTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.FLOAD, 2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleLoadTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.DLOAD, 2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addObjectLoadTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ALOAD, 2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntStoreTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ISTORE, 2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongStoreTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.LSTORE, 2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatStoreTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.FSTORE, 2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleStoreTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.DSTORE, 2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addObjectStoreTwo(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ASTORE, 2)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntLoadThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ILOAD, 3)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongLoadThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.LLOAD, 3)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatLoadThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.FLOAD, 3)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleLoadThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.DLOAD, 3)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addObjectLoadThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ALOAD, 3)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntStoreThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ISTORE, 3)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongStoreThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.LSTORE, 3)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatStoreThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.FSTORE, 3)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleStoreThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.DSTORE, 3)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addObjectStoreThree(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new VarInsnNode(Opcodes.ASTORE, 3)));
      return Unit.INSTANCE;
   }
}
