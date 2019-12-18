package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class xi {
   @NotNull
   public static final ClassNode a(@NotNull AccessWrapper var0, @NotNull String var1, int var2, @NotNull String var3, @NotNull Function1 var4) {
      ClassWrapper var5 = new ClassWrapper(var0, var1, var2, var3);
      var4.invoke(var5);
      return var5.getClassNode();
   }

   @NotNull
   public static ClassNode a(AccessWrapper var0, String var1, int var2, String var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var2 = 49;
      }

      if ((var5 & 8) != 0) {
         var3 = "java/lang/Object";
      }

      return a(var0, var1, var2, var3, var4);
   }

   @NotNull
   public static final ClassNode a(@NotNull ClassNode var0, @NotNull Function1 var1) {
      ClassWrapper var2 = new ClassWrapper(var0);
      var1.invoke(var2);
      return var2.getClassNode();
   }
}
