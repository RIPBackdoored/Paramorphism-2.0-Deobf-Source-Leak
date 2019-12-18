package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.objectweb.asm.Type;

public final class qs extends Lambda implements Function0 {
   public final rn bdt;

   public Object invoke() {
      return this.a();
   }

   public final Type a() {
      return Type.getObjectType(rn.b(this.bdt) + "/Dispatcher");
   }

   public qs(rn var1) {
      super(0);
      this.bdt = var1;
   }
}
