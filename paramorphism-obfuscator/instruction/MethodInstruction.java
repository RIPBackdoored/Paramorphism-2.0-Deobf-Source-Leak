package paramorphism-obfuscator.instruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import paramorphism-obfuscator.Primitives;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class MethodInstruction {
   public static final void addInvokeVirtual(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3, @NotNull Object... var4) {
      Type var17 = Primitives.getType(var3);
      boolean var7 = false;
      Collection var9 = (Collection)(new ArrayList(var4.length));
      boolean var10 = false;
      Object[] var11 = var4;
      int var12 = var4.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         Object var14 = var11[var13];
         boolean var16 = false;
         Type var19 = Primitives.getType(var14);
         var9.add(var19);
      }

      List var18 = (List)var9;
      Collection var6 = (Collection)var18;
      var7 = false;
      Object[] var10000 = var6.toArray(new Type[0]);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         Object[] var20 = var10000;
         String var5 = Type.getMethodDescriptor(var17, (Type[])Arrays.copyOf((Type[])var20, ((Type[])var20).length));
         var0.getInstructions().add((AbstractInsnNode)(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Primitives.getType(var1).getInternalName(), var2, var5)));
      }
   }

   public static final void addInvokeSpecial(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3, @NotNull Object... var4) {
      Type var17 = Primitives.getType(var3);
      boolean var7 = false;
      Collection var9 = (Collection)(new ArrayList(var4.length));
      boolean var10 = false;
      Object[] var11 = var4;
      int var12 = var4.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         Object var14 = var11[var13];
         boolean var16 = false;
         Type var19 = Primitives.getType(var14);
         var9.add(var19);
      }

      List var18 = (List)var9;
      Collection var6 = (Collection)var18;
      var7 = false;
      Object[] var10000 = var6.toArray(new Type[0]);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         Object[] var20 = var10000;
         String var5 = Type.getMethodDescriptor(var17, (Type[])Arrays.copyOf((Type[])var20, ((Type[])var20).length));
         var0.getInstructions().add((AbstractInsnNode)(new MethodInsnNode(Opcodes.INVOKESPECIAL, Primitives.getType(var1).getInternalName(), var2, var5)));
      }
   }

   public static final void addInvokeStatic(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3, @NotNull Object... var4) {
      Type var17 = Primitives.getType(var3);
      boolean var7 = false;
      Collection var9 = (Collection)(new ArrayList(var4.length));
      boolean var10 = false;
      Object[] var11 = var4;
      int var12 = var4.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         Object var14 = var11[var13];
         boolean var16 = false;
         Type var19 = Primitives.getType(var14);
         var9.add(var19);
      }

      List var18 = (List)var9;
      Collection var6 = (Collection)var18;
      var7 = false;
      Object[] var10000 = var6.toArray(new Type[0]);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         Object[] var20 = var10000;
         String var5 = Type.getMethodDescriptor(var17, (Type[])Arrays.copyOf((Type[])var20, ((Type[])var20).length));
         var0.getInstructions().add((AbstractInsnNode)(new MethodInsnNode(Opcodes.INVOKESTATIC, Primitives.getType(var1).getInternalName(), var2, var5)));
      }
   }

   public static final void addInvokeInterface(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3, @NotNull Object... var4) {
      Type var17 = Primitives.getType(var3);
      boolean var7 = false;
      Collection var9 = (Collection)(new ArrayList(var4.length));
      boolean var10 = false;
      Object[] var11 = var4;
      int var12 = var4.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         Object var14 = var11[var13];
         boolean var16 = false;
         Type var19 = Primitives.getType(var14);
         var9.add(var19);
      }

      List var18 = (List)var9;
      Collection var6 = (Collection)var18;
      var7 = false;
      Object[] var10000 = var6.toArray(new Type[0]);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         Object[] var20 = var10000;
         String var5 = Type.getMethodDescriptor(var17, (Type[])Arrays.copyOf((Type[])var20, ((Type[])var20).length));
         var0.getInstructions().add((AbstractInsnNode)(new MethodInsnNode(Opcodes.INVOKEINTERFACE, Primitives.getType(var1).getInternalName(), var2, var5)));
      }
   }
}
