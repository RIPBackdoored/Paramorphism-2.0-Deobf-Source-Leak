package paramorphism-obfuscator;

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class ry extends Lambda implements Function1 {
   public final sb bgj;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      var1.setMaxLocals(4);
      var1.setMaxStack(7);
      TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ConstantCallSite.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      NumberInstruction.addLdc((IInstructionWrapper)var1, this.bgj.bgq);
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Lookup.class), "findStatic", Reflection.getOrCreateKotlinClass(MethodHandle.class), Reflection.getOrCreateKotlinClass(Class.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class));
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ConstantCallSite.class), "<init>", var1.be(), Reflection.getOrCreateKotlinClass(MethodHandle.class));
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
   }

   public ry(sb var1) {
      super(1);
      this.bgj = var1;
   }
}
