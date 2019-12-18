package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class ni extends Lambda implements Function1 {
   public final Ref$IntRef wo;
   public final int wp;

   public Object invoke(Object var1) {
      this.a((InstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull InstructionWrapper var1) {
      this.wo.element = lk.a((IInstructionWrapper)var1, this.wp);
   }

   public ni(Ref$IntRef var1, int var2) {
      super(1);
      this.wo = var1;
      this.wp = var2;
   }
}
