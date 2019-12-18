package paramorphism-obfuscator;

import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

public final class ns extends Lambda implements Function1 {
   public static final ns xb = new ns();

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      Iterator var3 = var1.d().methods.iterator();

      while(var3.hasNext()) {
         MethodNode var2 = (MethodNode)var3.next();
         Iterator var6 = (Iterator)var2.instructions.iterator();
         boolean var7 = false;
         Iterator var5 = var6;

         while(var5.hasNext()) {
            AbstractInsnNode var4 = (AbstractInsnNode)var5.next();
            if (var4 instanceof LineNumberNode) {
               var2.instructions.remove(var4);
               var1.a(true);
            }
         }
      }

   }

   public ns() {
      super(1);
   }
}
