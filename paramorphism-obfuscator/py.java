package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class py extends Lambda implements Function1 {
   public static final py bce = new py();

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
   }

   public py() {
      super(1);
   }
}
