package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;
import paramorphism-obfuscator.verifierDisabler.NativeLambda;

public final class kr extends Lambda implements Function1 {
   public final NativeLambda sb;

   public Object invoke(Object var1) {
      return this.a(((Number)var1).intValue());
   }

   @Nullable
   public final String a(int var1) {
      lu var10000 = this.sb.rv;
      return var10000 != null ? var10000.a(-2, var1, oj.zg) : null;
   }

   public kr(NativeLambda var1) {
      super(1);
      this.sb = var1;
   }
}
