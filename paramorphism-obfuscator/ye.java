package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class ye extends Lambda implements Function1 {
   public static final ye bkt = new ye();

   public Object invoke(Object var1) {
      this.a((IInstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull IInstructionWrapper var1) {
   }

   public ye() {
      super(1);
   }
}
