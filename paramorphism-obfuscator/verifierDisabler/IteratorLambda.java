package paramorphism-obfuscator.verifierDisabler;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class IteratorLambda extends Lambda implements Function1 {
   public final ClassWrapper rj;
   public final String rk;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.rj.getType(), this.rk, Reflection.getOrCreateKotlinClass(Set.class), Reflection.getOrCreateKotlinClass(Object[].class));
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Set.class), "iterator", Reflection.getOrCreateKotlinClass(Iterator.class));
      VarInstruction.addObjectStoreTwo((IInstructionWrapper)var1);
      var1.getLabels().a("loop_head").a();
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Iterator.class), "hasNext", var1.bm());
      JumpInstruction.addIfEqual((IInstructionWrapper)var1, var1.getLabels().a("not_has_next"));
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Iterator.class), "next", Reflection.getOrCreateKotlinClass(Object.class));
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object[].class));
      VarInstruction.addObjectStoreThree((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadThree((IInstructionWrapper)var1);
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object.class), "equals", var1.bm(), Reflection.getOrCreateKotlinClass(Object.class));
      JumpInstruction.addIfEqual((IInstructionWrapper)var1, var1.getLabels().a("not_equals"));
      VarInstruction.addObjectLoadThree((IInstructionWrapper)var1);
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
      var1.getLabels().a("not_equals").a();
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a("loop_head"));
      var1.getLabels().a("not_has_next").a();
      TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(NoSuchElementException.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(NoSuchElementException.class), "<init>", var1.be(), Reflection.getOrCreateKotlinClass(String.class));
      JumpInstruction.addAThrow((IInstructionWrapper)var1);
   }

   public IteratorLambda(ClassWrapper var1, String var2) {
      super(1);
      this.rj = var1;
      this.rk = var2;
   }
}
