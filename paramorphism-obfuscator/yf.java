package paramorphism-obfuscator;

import java.util.Arrays;
import java.util.Collection;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class yf {
   public static final void a(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull Object[] var2, @NotNull String var3, @NotNull Function1 var4) {
      byte var7 = 0;
      boolean var8 = false;
      Object var10000;
      if (var7 <= ArraysKt.getLastIndex(var2)) {
         var10000 = var2[var7];
      } else {
         boolean var10 = false;
         var10000 = Primitives.getVoid();
      }

      Object var5 = var10000;
      Collection var12 = (Collection)ArraysKt.drop(var2, 1);
      var8 = false;
      if (var12 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.util.Collection<T>");
      } else {
         Object[] var11 = var12.toArray(new Object[0]);
         if (var11 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         } else {
            Object[] var6 = var11;
            TypeInstruction.addNew(var0, var1);
            StackInstruction.addDup(var0);
            var4.invoke(var0);
            MethodInstruction.addInvokeSpecial(var0, var1, var3, var5, Arrays.copyOf(var6, var6.length));
         }
      }
   }

   public static void a(IInstructionWrapper var0, Object var1, Object[] var2, String var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = "<init>";
      }

      if ((var5 & 8) != 0) {
         var4 = (Function1)ye.bkt;
      }

      a(var0, var1, var2, var3, var4);
   }
}
