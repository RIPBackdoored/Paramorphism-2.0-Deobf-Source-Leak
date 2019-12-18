package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class sc extends Lambda implements Function1 {
   public static final sc bgr = new sc();

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, "java/lang/Object", "<init>", var1.be());
      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public sc() {
      super(1);
   }
}
