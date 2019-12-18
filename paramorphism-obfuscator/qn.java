package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

public final class qn extends Lambda implements Function1 {
   public final int bdk;

   public Object invoke(Object var1) {
      return this.a(((Number)var1).byteValue());
   }

   public final byte a(byte var1) {
      return (byte)(var1 ^ this.bdk);
   }

   public qn(int var1) {
      super(1);
      this.bdk = var1;
   }
}
