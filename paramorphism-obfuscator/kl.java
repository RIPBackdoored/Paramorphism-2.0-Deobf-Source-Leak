package paramorphism-obfuscator;

import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class kl extends Lambda implements Function1 {
   public static final kl rr = new kl();

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 6);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Set.class));
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
   }

   public kl() {
      super(1);
   }
}
