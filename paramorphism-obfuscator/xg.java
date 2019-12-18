package paramorphism-obfuscator;

import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnList;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class xg {
   @NotNull
   public static final Pair a(@NotNull Function1 var0) {
      InsnList var5 = new InsnList();
      boolean var2 = false;
      List var6 = (List)(new ArrayList());
      InstructionWrapper var1 = new InstructionWrapper(var5, var6);
      var0.invoke(var1);
      return new Pair(var1.getInstructions(), var1.getExceptions());
   }

   @NotNull
   public static final Pair a(@NotNull InsnList var0, @NotNull List var1, @NotNull Function1 var2) {
      InstructionWrapper var3 = new InstructionWrapper(var0, var1);
      var2.invoke(var3);
      return new Pair(var3.getInstructions(), var3.getExceptions());
   }

   @NotNull
   public static Pair a(InsnList var0, List var1, Function1 var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         boolean var5 = false;
         var1 = (List)(new ArrayList());
      }

      return a(var0, var1, var2);
   }
}
