package paramorphism-obfuscator;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public final class np extends Lambda implements Function1 {
   public static final np wy = new np();

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      ClassNode var2 = var1.d();
      Iterator var4 = var2.methods.iterator();

      while(var4.hasNext()) {
         MethodNode var3 = (MethodNode)var4.next();
         Collection var5 = (Collection)var3.localVariables;
         boolean var6 = false;
         boolean var7 = false;
         if (var5 != null && !var5.isEmpty()) {
            var3.localVariables = CollectionsKt.emptyList();
            var1.a(true);
         }
      }

   }

   public np() {
      super(1);
   }
}
