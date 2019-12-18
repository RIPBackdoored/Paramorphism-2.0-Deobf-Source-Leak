package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class nm extends Lambda implements Function1 {
   public final Ref$IntRef wu;
   public final int wv;

   public Object invoke(Object var1) {
      this.a((InstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull InstructionWrapper var1) {
      this.wu.element = lk.a((IInstructionWrapper)var1, this.wv);
   }

   public nm(Ref$IntRef var1, int var2) {
      super(1);
      this.wu = var1;
      this.wv = var2;
   }
}
