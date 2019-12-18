package paramorphism-obfuscator.verifierDisabler;

import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class JVMFlagLambda extends Lambda implements Function1 {
   public final ClassWrapper rs;
   public final FieldNode rt;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.rs.getType(), this.rt.name, Reflection.getOrCreateKotlinClass(Map.class));
      NumberInstruction.addLdc((IInstructionWrapper)var1, "Flag");
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Map.class), "get", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class));
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object[].class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      VarInstruction.addObjectStoreZero((IInstructionWrapper)var1);
      JumpInstruction.addIfNull((IInstructionWrapper)var1, var1.getLabels().a("is_null"));
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
      var1.getLabels().a("is_null").a();
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.rs.getType(), this.rt.name, Reflection.getOrCreateKotlinClass(Map.class));
      NumberInstruction.addLdc((IInstructionWrapper)var1, "JVMFlag");
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Map.class), "get", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class));
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object[].class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      VarInstruction.addObjectStoreZero((IInstructionWrapper)var1);
      JumpInstruction.addIfNull((IInstructionWrapper)var1, var1.getLabels().a("is_still_null"));
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
      var1.getLabels().a("is_still_null").a();
      NumberInstruction.addNull((IInstructionWrapper)var1);
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
   }

   public JVMFlagLambda(ClassWrapper var1, FieldNode var2) {
      super(1);
      this.rs = var1;
      this.rt = var2;
   }
}
