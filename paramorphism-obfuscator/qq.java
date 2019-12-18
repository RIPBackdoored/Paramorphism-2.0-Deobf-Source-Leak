package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class qq extends Lambda implements Function0 {
   public final MethodWrapper bdo;

   public Object invoke() {
      this.a();
      return Unit.INSTANCE;
   }

   public final void a() {
      VarInstruction.addObjectLoadOne((IInstructionWrapper)this.bdo);
      VarInstruction.addIntLoadTwo((IInstructionWrapper)this.bdo);
      MathInstruction.addIIncrementOne((IInstructionWrapper)this.bdo, 2);
      ArrayVarInstruction.addByteArrayLoad((IInstructionWrapper)this.bdo);
   }

   public qq(MethodWrapper var1) {
      super(0);
      this.bdo = var1;
   }
}
