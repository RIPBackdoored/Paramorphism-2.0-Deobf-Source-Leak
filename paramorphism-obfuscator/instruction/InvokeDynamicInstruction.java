package paramorphism-obfuscator.instruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import paramorphism-obfuscator.Primitives;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class InvokeDynamicInstruction {
   public static final void addInvokeDynamic(@NotNull IInstructionWrapper var0, @NotNull String var1, @NotNull Object var2, @NotNull Object[] var3, @NotNull Handle var4, @NotNull Object[] var5) {
      Type var18 = Primitives.getType(var2);
      boolean var8 = false;
      Collection var10 = (Collection)(new ArrayList(var3.length));
      boolean var11 = false;
      Object[] var12 = var3;
      int var13 = var3.length;

      for(int var14 = 0; var14 < var13; ++var14) {
         Object var15 = var12[var14];
         boolean var17 = false;
         Type var20 = Primitives.getType(var15);
         var10.add(var20);
      }

      List var19 = (List)var10;
      Collection var7 = (Collection)var19;
      var8 = false;
      Object[] var10000 = var7.toArray(new Type[0]);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         Object[] var21 = var10000;
         String var6 = Type.getMethodDescriptor(var18, (Type[])Arrays.copyOf((Type[])var21, ((Type[])var21).length));
         var0.getInstructions().add((AbstractInsnNode)(new InvokeDynamicInsnNode(var1, var6, var4, Arrays.copyOf(var5, var5.length))));
      }
   }

   @NotNull
   public static final Handle getStaticHandle(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3, @NotNull Object... var4) {
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
         return new Handle(Opcodes.H_INVOKESTATIC, Primitives.getType(var1).getInternalName(), var2, var5, false);
      }
   }

   @NotNull
   public static final Handle getVirtualHandle(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3, @NotNull Object... var4) {
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
         return new Handle(Opcodes.H_INVOKEVIRTUAL, Primitives.getType(var1).getInternalName(), var2, var5, false);
      }
   }

   @NotNull
   public static final Handle getSpecialHandle(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3, @NotNull Object... var4) {
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
         return new Handle(Opcodes.H_INVOKESPECIAL, Primitives.getType(var1).getInternalName(), var2, var5, false);
      }
   }

   @NotNull
   public static final Handle getInterfaceHandle(@NotNull IInstructionWrapper var0, @NotNull Object var1, @NotNull String var2, @NotNull Object var3, @NotNull Object... var4) {
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
         return new Handle(Opcodes.H_INVOKEINTERFACE, Primitives.getType(var1).getInternalName(), var2, var5, true);
      }
   }
}
