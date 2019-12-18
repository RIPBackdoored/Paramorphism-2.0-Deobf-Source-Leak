package paramorphism-obfuscator.instruction;

import java.util.Arrays;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import paramorphism-obfuscator.yj;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class SwitchInstruction {
   public static final void addTableSwitch(@NotNull IInstructionWrapper var0, int var1, int var2, @NotNull Object var3, @NotNull Object... var4) {
      int var6 = var4.length;
      boolean var7 = false;
      LabelNode[] var8 = new LabelNode[var6];
      int var9 = 0;

      for(int var10 = var8.length; var9 < var10; ++var9) {
         boolean var12 = false;
         LabelNode var15 = yj.a(var4[var9]);
         var8[var9] = var15;
      }

      var0.getInstructions().add((AbstractInsnNode)(new TableSwitchInsnNode(var1, var2, yj.a(var3), (LabelNode[])Arrays.copyOf(var8, var8.length))));
   }

   public static final void addLookupSwitch(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull Pair... var2) {
      int var4 = var2.length;
      boolean var5 = false;
      int[] var6 = new int[var4];
      int var7 = 0;

      int var8;
      for(var8 = var6.length; var7 < var8; ++var7) {
         boolean var10 = false;
         int var14 = ((Number)var2[var7].getFirst()).intValue();
         var6[var7] = var14;
      }

      int var15 = var2.length;
      boolean var16 = false;
      LabelNode[] var17 = new LabelNode[var15];
      var8 = 0;

      for(int var9 = var17.length; var8 < var9; ++var8) {
         boolean var11 = false;
         LabelNode var18 = yj.a(var2[var8].getSecond());
         var17[var8] = var18;
      }

      var0.getInstructions().add((AbstractInsnNode)(new LookupSwitchInsnNode(yj.a(var1), var6, var17)));
   }
}
