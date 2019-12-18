package paramorphism-obfuscator;

import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public final class mn extends Lambda implements Function1 {
   public static final mn vd = new mn();

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      if (var1.b()) {
         ClassNode var2 = var1.d();
         if (mo.b(var2.access)) {
            var2.access |= 1;
         }

         Iterator var4 = var2.methods.iterator();

         while(var4.hasNext()) {
            MethodNode var3 = (MethodNode)var4.next();
            if (mo.b(var3.access)) {
               var3.access |= 1;
            }
         }

         var4 = var2.fields.iterator();

         while(var4.hasNext()) {
            FieldNode var5 = (FieldNode)var4.next();
            if (mo.b(var5.access)) {
               var5.access |= 1;
            }
         }
      }

   }

   public mn() {
      super(1);
   }
}
