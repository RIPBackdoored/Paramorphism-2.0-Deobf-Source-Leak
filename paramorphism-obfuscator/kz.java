package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class kz extends Lambda implements Function1 {
   public static final kz te = new kz();

   public Object invoke(Object var1) {
      this.a((xs)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull xs var1) {
      StackInstruction.addPop((IInstructionWrapper)var1);
   }

   public kz() {
      super(1);
   }
}
