package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class qx extends Lambda implements Function1 {
   public final rw bee;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      this.bee.bgf.a((IInstructionWrapper)var1);
      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public qx(rw var1) {
      super(1);
      this.bee = var1;
   }
}
