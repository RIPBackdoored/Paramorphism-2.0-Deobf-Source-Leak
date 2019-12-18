package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class qp extends Lambda implements Function0 {
   public final MethodWrapper bdm;
   public final qq bdn;

   public Object invoke() {
      this.a();
      return Unit.INSTANCE;
   }

   public final void a() {
      int var1 = 3;

      for(boolean var2 = false; var1 >= 0; --var1) {
         this.bdn.a();
         IntegerInstruction.addInteger((IInstructionWrapper)this.bdm, 255);
         MathInstruction.addIAnd((IInstructionWrapper)this.bdm);
         IntegerInstruction.addInteger((IInstructionWrapper)this.bdm, 8 * var1);
         MathInstruction.addIShiftLeft((IInstructionWrapper)this.bdm);
      }

      MathInstruction.addIOr((IInstructionWrapper)this.bdm);
      MathInstruction.addIOr((IInstructionWrapper)this.bdm);
      MathInstruction.addIOr((IInstructionWrapper)this.bdm);
   }

   public qp(MethodWrapper var1, qq var2) {
      super(0);
      this.bdm = var1;
      this.bdn = var2;
   }
}
