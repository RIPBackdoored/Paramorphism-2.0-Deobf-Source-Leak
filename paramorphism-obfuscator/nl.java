package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class nl extends Lambda implements Function1 {
   public final ClassWrapper ws;
   public final FieldNode wt;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ClassLoader.class), "<init>", var1.be(), Reflection.getOrCreateKotlinClass(ClassLoader.class));
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
      FieldInstruction.addPutField((IInstructionWrapper)var1, this.ws.getType(), this.wt.name, Reflection.getOrCreateKotlinClass(ClassLoader.class));
      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public nl(ClassWrapper var1, FieldNode var2) {
      super(1);
      this.ws = var1;
      this.wt = var2;
   }
}
