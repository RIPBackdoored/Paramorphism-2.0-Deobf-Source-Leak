package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;

public final class pf extends Lambda implements Function1 {
   public static final pf bay = new pf();

   public Object invoke(Object var1) {
      return this.a((AbstractInsnNode)var1);
   }

   public final boolean a(AbstractInsnNode var1) {
      return var1 instanceof InvokeDynamicInsnNode;
   }

   public pf() {
      super(1);
   }
}
