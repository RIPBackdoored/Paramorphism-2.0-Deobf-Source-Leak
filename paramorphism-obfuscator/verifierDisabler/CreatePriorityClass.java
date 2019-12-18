package paramorphism-obfuscator.verifierDisabler;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class CreatePriorityClass extends Lambda implements Function1 {
   public static final CreatePriorityClass bgv = new CreatePriorityClass();

   public Object invoke(Object var1) {
      this.a((InstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull InstructionWrapper var1) {
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, "/->", "/*", var1.bi());
      NumberInstruction.addIOne((IInstructionWrapper)var1);
      MathInstruction.addIOr((IInstructionWrapper)var1);
      JumpInstruction.addIfNotEqual((IInstructionWrapper)var1, var1.getLabels().a("skip_corrupt"));
      NumberInstruction.addLdc((IInstructionWrapper)var1, DoubleCompanionObject.INSTANCE.getMAX_VALUE());
      StackInstruction.addSwap((IInstructionWrapper)var1);
      StackInstruction.addPop2((IInstructionWrapper)var1);
      var1.getLabels().a("skip_corrupt").a();
   }

   public CreatePriorityClass() {
      super(1);
   }
}
