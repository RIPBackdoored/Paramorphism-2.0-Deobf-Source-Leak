package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class qk extends Lambda implements Function0 {
   public final MethodWrapper bdf;
   public final qq bdg;

   public Object invoke() {
      this.a();
      return Unit.INSTANCE;
   }

   public final void a() {
      this.bdg.a();
      IntegerInstruction.addInteger((IInstructionWrapper)this.bdf, 255);
      MathInstruction.addIAnd((IInstructionWrapper)this.bdf);
      IntegerInstruction.addInteger((IInstructionWrapper)this.bdf, 8);
      MathInstruction.addIShiftLeft((IInstructionWrapper)this.bdf);
      this.bdg.a();
      IntegerInstruction.addInteger((IInstructionWrapper)this.bdf, 255);
      MathInstruction.addIAnd((IInstructionWrapper)this.bdf);
      MathInstruction.addIOr((IInstructionWrapper)this.bdf);
   }

   public qk(MethodWrapper var1, qq var2) {
      super(0);
      this.bdf = var1;
      this.bdg = var2;
   }
}
