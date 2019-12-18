package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;
import paramorphism-obfuscator.verifierDisabler.NativeLambda;

public final class ky extends Lambda implements Function1 {
   public final NativeLambda td;

   public Object invoke(Object var1) {
      return this.a(((Number)var1).intValue());
   }

   @Nullable
   public final String a(int var1) {
      lu var10000 = this.td.rv;
      return var10000 != null ? var10000.a(-1, var1, oj.zh) : null;
   }

   public ky(NativeLambda var1) {
      super(1);
      this.td = var1;
   }
}
