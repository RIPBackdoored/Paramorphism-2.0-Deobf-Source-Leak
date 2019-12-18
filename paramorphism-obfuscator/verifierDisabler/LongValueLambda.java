package paramorphism-obfuscator.verifierDisabler;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class LongValueLambda extends Lambda implements Function1 {
   public static final LongValueLambda rw = new LongValueLambda();

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      NumberInstruction.addITwo((IInstructionWrapper)var1);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Long.class);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Long.class, "longValue", var1.bk());
      JumpInstruction.addLReturn((IInstructionWrapper)var1);
   }

   public LongValueLambda() {
      super(1);
   }
}
