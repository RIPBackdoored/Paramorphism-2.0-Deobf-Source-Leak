package paramorphism-obfuscator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import kotlin.collections.ArraysKt;
import kotlin.collections.SetsKt;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class lp {
   private static final Integer[] uc = new Integer[]{22, 55, 24, 57};

   @Nullable
   public static final Integer a(@NotNull MethodNode var0, @NotNull int[] var1, boolean var2) {
      Integer[] var10000 = ArraysKt.toTypedArray(var1);
      Set var3 = SetsKt.mutableSetOf((Integer[])Arrays.copyOf(var10000, var10000.length));
      boolean var4 = (var0.access & 8) == 0;
      if (var4) {
         var3.add(0);
      }

      int var5 = var4 ? 0 : 1;
      Type[] var8 = Type.getArgumentTypes(var0.desc);
      int var9 = var8.length;

      int var10;
      for(int var7 = 0; var7 < var9; ++var7) {
         Type var6 = var8[var7];
         var10 = 1;
         int var11 = var6.getSize();
         if (var10 <= var11) {
            while(true) {
               var3.add(var5);
               ++var5;
               if (var10 == var11) {
                  break;
               }

               ++var10;
            }
         }
      }

      ListIterator var15 = var0.instructions.iterator();

      while(var15.hasNext()) {
         AbstractInsnNode var12 = (AbstractInsnNode)var15.next();
         if (var12 instanceof VarInsnNode) {
            var3.add(((VarInsnNode)var12).var);
            if (ArraysKt.contains(uc, ((VarInsnNode)var12).getOpcode())) {
               var3.add(((VarInsnNode)var12).var + 1);
            }
         }

         if (var12 instanceof IincInsnNode) {
            var3.add(((IincInsnNode)var12).var);
         }
      }

      byte var13 = 0;
      Iterable var14 = (Iterable)(new IntRange(var13, 65535));
      boolean var16 = false;
      Iterator var17 = var14.iterator();

      Object var20;
      while(true) {
         if (!var17.hasNext()) {
            var20 = null;
            break;
         }

         Object var18 = var17.next();
         var10 = ((Number)var18).intValue();
         boolean var19 = false;
         if (!var3.contains(var10) && (!var2 || !var3.contains(var10 + 1))) {
            var20 = var18;
            break;
         }
      }

      return (Integer)var20;
   }

   public static Integer a(MethodNode var0, int[] var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return a(var0, var1, var2);
   }
}
