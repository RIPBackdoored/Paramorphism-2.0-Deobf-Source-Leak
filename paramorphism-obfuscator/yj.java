package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;

public final class yj {
   @NotNull
   public static final LabelNode a(@NotNull Object var0) {
      LabelNode var10000;
      if (var0 instanceof LabelNode) {
         var10000 = (LabelNode)var0;
      } else if (var0 instanceof Label) {
         var10000 = new LabelNode((Label)var0);
      } else {
         if (!(var0 instanceof yg)) {
            String var2 = "Non label-like object passed to coerceLabel()";
            boolean var3 = false;
            throw (Throwable)(new IllegalStateException(var2.toString()));
         }

         var10000 = ((yg)var0).b();
      }

      return var10000;
   }
}
