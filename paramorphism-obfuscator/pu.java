package paramorphism-obfuscator;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
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
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class pu extends Lambda implements Function1 {
   public final px bbu;
   public final ClassWrapper bbv;
   public final FieldNode bbw;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 3);
      NumberInstruction.addLdc((IInstructionWrapper)var1, -4294967296L);
      MathInstruction.addLAnd((IInstructionWrapper)var1);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 32);
      MathInstruction.addLLogicalShiftRight((IInstructionWrapper)var1);
      MathInstruction.addLongToInt((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 6);
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 3);
      NumberInstruction.addLdc((IInstructionWrapper)var1, 4294967295L);
      MathInstruction.addLAnd((IInstructionWrapper)var1);
      MathInstruction.addLongToInt((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 7);
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.bbv.getType(), this.bbw.name, Reflection.getOrCreateKotlinClass(Object[].class));
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 6);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, this.bbu.bca.name);
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 7);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 5);
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, this.bbu.bca.name, this.bbu.bcb, Reflection.getOrCreateKotlinClass(CallSite.class), Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(MethodType.class), var1.bi(), var1.bi());
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
   }

   public pu(px var1, ClassWrapper var2, FieldNode var3) {
      super(1);
      this.bbu = var1;
      this.bbv = var2;
      this.bbw = var3;
   }
}
