package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.objectweb.asm.Type;

public final class qa extends Lambda implements Function0 {
   public final qc bcg;

   public Object invoke() {
      return this.a();
   }

   public final Type a() {
      return Type.getObjectType(qc.b(this.bcg) + "/Dispatcher️");
   }

   public qa(qc var1) {
      super(0);
      this.bcg = var1;
   }
}
