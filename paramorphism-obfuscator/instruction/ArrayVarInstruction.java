package paramorphism-obfuscator.instruction;

import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import paramorphism-obfuscator.Primitives;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class ArrayVarInstruction {
   @NotNull
   public static final Unit addIntArrayLoad(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.IALOAD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongArrayLoad(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LALOAD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatArrayLoad(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FALOAD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleArrayLoad(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.DALOAD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addObjectArrayLoad(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.AALOAD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addByteArrayLoad(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.BALOAD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addCharArrayLoad(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.CALOAD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addShortArrayLoad(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.SALOAD)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addIntArrayStore(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.IASTORE)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addLongArrayStore(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.LASTORE)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addFloatArrayStore(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.FASTORE)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addDoubleArrayStore(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.DASTORE)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addObjectArrayStore(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.AASTORE)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addByteArrayStore(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.BASTORE)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addCharArrayStore(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.CASTORE)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addShortArrayStore(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.SASTORE)));
      return Unit.INSTANCE;
   }

   @NotNull
   public static final Unit addArrayLength(@NotNull IInstructionWrapper var0) {
      var0.getInstructions().add((AbstractInsnNode)(new InsnNode(Opcodes.ARRAYLENGTH)));
      return Unit.INSTANCE;
   }

   public static final void addIndexInt(@NotNull IInstructionWrapper var0, @NotNull Object var1) {
      InsnList var10000 = var0.getInstructions();
      byte var10002;
      switch(Primitives.getType(var1).getSort()) {
      case 1:
         var10002 = 4;
         break;
      case 2:
         var10002 = 5;
         break;
      case 3:
         var10002 = 8;
         break;
      case 4:
         var10002 = 9;
         break;
      case 5:
         var10002 = 10;
         break;
      case 6:
         var10002 = 6;
         break;
      case 7:
         var10002 = 11;
         break;
      case 8:
         var10002 = 7;
         break;
      default:
         String var2 = "Invalid type for primitive array creation";
         boolean var7 = true;
         boolean var3 = false;
         throw (Throwable)(new IllegalStateException(var2.toString()));
      }

      byte var8 = var10002;
      short var9 = 188;
      var10000.add((AbstractInsnNode)(new IntInsnNode(var9, var8)));
   }

   public static final void addNewArray(@NotNull IInstructionWrapper var0, @NotNull Object var1) {
      var0.getInstructions().add((AbstractInsnNode)(new TypeInsnNode(Opcodes.ANEWARRAY, Primitives.getType(var1).getInternalName())));
   }

   public static final void addMultiNewArray(@NotNull IInstructionWrapper var0, @NotNull Object var1, int var2) {
      var0.getInstructions().add((AbstractInsnNode)(new MultiANewArrayInsnNode(Primitives.getType(var1).getDescriptor(), var2)));
   }
}
