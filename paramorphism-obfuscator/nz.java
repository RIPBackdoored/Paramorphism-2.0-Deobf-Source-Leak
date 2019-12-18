package paramorphism-obfuscator;

import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

public final class nz extends Lambda implements Function0 {
   public final MethodNode xo;

   public Object invoke() {
      return this.a();
   }

   public final int a() {
      Integer var10000 = lp.a(this.xo, new int[0], false, 2, (Object)null);
      if (var10000 != null) {
         int var1 = var10000;
         this.xo.instructions.insert((InsnList)xg.a(new InsnList(), (List)null, (Function1)(new nw(var1)), 1, (Object)null).getFirst());
         return var1;
      } else {
         String var2 = "Could not find free local slot";
         boolean var3 = false;
         throw (Throwable)(new IllegalStateException(var2.toString()));
      }
   }

   public nz(MethodNode var1) {
      super(0);
      this.xo = var1;
   }
}
