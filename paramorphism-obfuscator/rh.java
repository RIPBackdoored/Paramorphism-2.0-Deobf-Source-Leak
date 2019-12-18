package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class rh extends Lambda implements Function1 {
   public final rq bew;

   public rh(rq var1) {
      super(1);
      this.bew = var1;
   }

   public Object invoke(Object var1) {
      this.a((InstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull InstructionWrapper var1) {
      this.bew.bfs.a((IInstructionWrapper)var1);
   }
}
