package paramorphism-obfuscator;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class rg extends Lambda implements Function1 {
   public final ClassWrapper bev;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 3);
      NumberInstruction.addLdc((IInstructionWrapper)var1, -4294967296L);
      MathInstruction.addLAnd((IInstructionWrapper)var1);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 32);
      MathInstruction.addLShiftRight((IInstructionWrapper)var1);
      MathInstruction.addLongToInt((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 6);
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 3);
      NumberInstruction.addLdc((IInstructionWrapper)var1, 4294967295L);
      MathInstruction.addLAnd((IInstructionWrapper)var1);
      MathInstruction.addLongToInt((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 7);
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 6);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 7);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.bev.getType(), "a", Reflection.getOrCreateKotlinClass(CallSite.class), Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(MethodType.class), var1.bi(), var1.bi());
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
   }

   public rg(ClassWrapper var1) {
      super(1);
      this.bev = var1;
   }
}
