package paramorphism-obfuscator;

import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;

public final class ph extends Lambda implements Function1 {
   public static final ph baz = new ph();

   public Object invoke(Object var1) {
      return this.a((AbstractInsnNode)var1);
   }

   @NotNull
   public final InvokeDynamicInsnNode a(AbstractInsnNode var1) {
      if (var1 == null) {
         throw new TypeCastException("null cannot be cast to non-null type org.objectweb.asm.tree.InvokeDynamicInsnNode");
      } else {
         return (InvokeDynamicInsnNode)var1;
      }
   }

   public ph() {
      super(1);
   }
}
