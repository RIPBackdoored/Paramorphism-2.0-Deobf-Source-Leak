package paramorphism-obfuscator.verifierDisabler;

import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class GetByteLambda extends Lambda implements Function1 {
   public final ClassWrapper rl;
   public final FieldNode rm;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addLongLoadZero((IInstructionWrapper)var1);
      NumberInstruction.addLZero((IInstructionWrapper)var1);
      JumpInstruction.addLongCompare((IInstructionWrapper)var1);
      JumpInstruction.addIfNotEqual((IInstructionWrapper)var1, var1.getLabels().a("not_zero"));
      NumberInstruction.addNull((IInstructionWrapper)var1);
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
      var1.getLabels().a("not_zero").a();
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 40);
      ArrayVarInstruction.addIndexInt((IInstructionWrapper)var1, var1.bf());
      VarInstruction.addObjectStoreTwo((IInstructionWrapper)var1);
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      VarInstruction.addIntStoreThree((IInstructionWrapper)var1);
      var1.getLabels().a("loop_head").a();
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.rl.getType(), this.rm.name, "sun/misc/Unsafe");
      VarInstruction.addLongLoadZero((IInstructionWrapper)var1);
      VarInstruction.addIntLoadThree((IInstructionWrapper)var1);
      MathInstruction.addIntToLong((IInstructionWrapper)var1);
      MathInstruction.addLAdd((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, "sun/misc/Unsafe", "getByte", var1.bg(), var1.bk());
      StackInstruction.addDup((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 4);
      JumpInstruction.addIfEqual((IInstructionWrapper)var1, var1.getLabels().a("exit_loop"));
      VarInstruction.addIntLoadThree((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      ArrayVarInstruction.addArrayLength((IInstructionWrapper)var1);
      JumpInstruction.addIfIntCompareLess((IInstructionWrapper)var1, var1.getLabels().a("less_than_length"));
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      VarInstruction.addIntLoadThree((IInstructionWrapper)var1);
      NumberInstruction.addITwo((IInstructionWrapper)var1);
      MathInstruction.addIMultiply((IInstructionWrapper)var1);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Arrays.class), "copyOf", Reflection.getOrCreateKotlinClass(char[].class), Reflection.getOrCreateKotlinClass(char[].class), var1.bi());
      VarInstruction.addObjectStoreTwo((IInstructionWrapper)var1);
      var1.getLabels().a("less_than_length").a();
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      VarInstruction.addIntLoadThree((IInstructionWrapper)var1);
      MathInstruction.addIIncrementOne((IInstructionWrapper)var1, 3);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 4);
      MathInstruction.addIntToChar((IInstructionWrapper)var1);
      ArrayVarInstruction.addCharArrayStore((IInstructionWrapper)var1);
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a("loop_head"));
      var1.getLabels().a("exit_loop").a();
      TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(String.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      VarInstruction.addIntLoadThree((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(String.class), "<init>", var1.be(), Reflection.getOrCreateKotlinClass(char[].class), var1.bi(), var1.bi());
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
   }

   public GetByteLambda(ClassWrapper var1, FieldNode var2) {
      super(1);
      this.rl = var1;
      this.rm = var2;
   }
}
