package paramorphism-obfuscator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import paramorphism-obfuscator.access.PublicAccess;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class qz {
   private static final MethodNode a(@NotNull ClassWrapper var0) {
      return ClassWrapper.addMethod(var0, var0.f().a((AccessWrapper)var0.h()), "$", var0.be(), new Object[]{Reflection.getOrCreateKotlinClass(byte[].class), Reflection.getOrCreateKotlinClass(Map.class)}, (String)null, (Type[])null, (Function1)qw.bed, 48, (Object)null);
   }

   private static final double[] a(String var0, int var1) {
      Random var2 = new Random((long)var1);
      double var4 = (double)var0.length() / (double)5;
      boolean var6 = false;
      int var3 = (int)Math.ceil(var4);
      double[] var14 = new double[(4 + var0.length()) / 5 * 5];
      int var5 = 0;

      for(int var15 = var3; var5 < var15; ++var5) {
         int var9 = var5 * 5;
         int var10 = var0.length();
         int var11 = (var5 + 1) * 5;
         boolean var12 = false;
         var10 = Math.min(var10, var11);
         boolean var17 = false;
         if (var0 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         String var7 = var0.substring(var9, var10);
         double[] var8 = new double[var7.length() * 2];
         double var16 = 0.0D;
         var11 = 0;

         int var18;
         for(var18 = ((CharSequence)var7).length(); var11 < var18; ++var11) {
            var16 += (double)1 + var2.nextDouble();
            var8[var11 * 2] = var16;
            var8[var11 * 2 + 1] = (double)var7.charAt(var11);
         }

         double[] var19 = g.a(var8);
         var18 = 0;

         for(int var13 = var19.length; var18 < var13; ++var18) {
            var14[5 * var5 + var18] = var19[var18];
         }
      }

      return var14;
   }

   private static final Map a(String var0, Set var1) {
      boolean var3 = false;
      Map var2 = (Map)(new LinkedHashMap());
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         pq var6 = (pq)var4.next();
         double[] var5 = a(var0, var6.a());
         var2.put(var6.a(), var5);
      }

      return var2;
   }

   private static final FieldNode a(@NotNull ClassWrapper var0, String var1) {
      return ClassWrapper.addField(var0, var0.f().a((AccessWrapper)var0.h()), Reflection.getOrCreateKotlinClass(Map.class), var1, (String)null, (Object)null, 24, (Object)null);
   }

   private static final void a(MethodWrapper var0) {
      boolean var1 = false;
      boolean var2 = false;
      boolean var4 = false;
      TypeInstruction.addNew((IInstructionWrapper)var0, Reflection.getOrCreateKotlinClass(Throwable.class));
      StackInstruction.addDup((IInstructionWrapper)var0);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var0, Reflection.getOrCreateKotlinClass(Throwable.class), "<init>", var0.be());
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var0, Reflection.getOrCreateKotlinClass(Throwable.class), "getStackTrace", Reflection.getOrCreateKotlinClass(StackTraceElement[].class));
      NumberInstruction.addIOne((IInstructionWrapper)var0);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var0);
      VarInstruction.addObjectStoreOne((IInstructionWrapper)var0);
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var0);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var0, Reflection.getOrCreateKotlinClass(StackTraceElement.class), "getClassName", Reflection.getOrCreateKotlinClass(String.class));
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var0, Reflection.getOrCreateKotlinClass(String.class), "hashCode", var0.bi());
      IntegerInstruction.addInteger((IInstructionWrapper)var0, 31);
      MathInstruction.addIMultiply((IInstructionWrapper)var0);
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var0);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var0, Reflection.getOrCreateKotlinClass(StackTraceElement.class), "getMethodName", Reflection.getOrCreateKotlinClass(String.class));
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var0, Reflection.getOrCreateKotlinClass(String.class), "hashCode", var0.bi());
      MathInstruction.addIAdd((IInstructionWrapper)var0);
   }

   private static final FieldNode b(@NotNull ClassWrapper var0, String var1) {
      return ClassWrapper.addField(var0, var0.f().a((AccessWrapper)var0.h()), Reflection.getOrCreateKotlinClass(String.class), var1, (String)null, (Object)null, 24, (Object)null);
   }

   private static final MethodNode a(@NotNull ClassWrapper var0, String var1, String var2, FieldNode var3, Function1 var4) {
      AccessWrapper var10001 = var0.e().a((AccessWrapper)var0.h());
      KClass var10003 = Reflection.getOrCreateKotlinClass(String.class);
      Function1 var10004 = (Function1)(new qu(var0, var3, var4, var2, var1));
      Object[] var5 = new Object[0];
      Object var6 = null;
      Object var7 = null;
      Function1 var8 = var10004;
      return ClassWrapper.addMethod(var0, var10001, var2, var10003, var5, (String)var7, (Type[])var6, var8, 48, (Object)null);
   }

   private static final byte[] a(int param0, Set param1, Map param2) {
      // $FF: Couldn't be decompiled
   }

   private static final InsnList a(@NotNull ClassWrapper var0, String var1, MethodNode var2, int var3, Set var4, Map var5) {
      rs var6 = new rs(var0);
      return (InsnList)xg.a((Function1)(new rd(var0, var3, var4, var5, var6, var1, var2))).getFirst();
   }

   @NotNull
   public static final ClassNode a(@NotNull ra var0, @NotNull lu var1) {
      return xi.a(PublicAccess.bln.a((AccessWrapper)yl.a()), var0.a().getInternalName(), 0, (String)null, (Function1)(new ru(var0, var1)), 12, (Object)null);
   }

   public static final byte[] b(int var0, Set var1, Map var2) {
      return a(var0, var1, var2);
   }

   public static final MethodNode b(ClassWrapper var0) {
      return a(var0);
   }

   public static final FieldNode c(ClassWrapper var0, String var1) {
      return a(var0, var1);
   }

   public static final double[] b(String var0, int var1) {
      return a(var0, var1);
   }

   public static final Map b(String var0, Set var1) {
      return a(var0, var1);
   }

   public static final void b(MethodWrapper var0) {
      a(var0);
   }

   public static final FieldNode d(ClassWrapper var0, String var1) {
      return b(var0, var1);
   }

   public static final MethodNode b(ClassWrapper var0, String var1, String var2, FieldNode var3, Function1 var4) {
      return a(var0, var1, var2, var3, var4);
   }

   public static final InsnList b(ClassWrapper var0, String var1, MethodNode var2, int var3, Set var4, Map var5) {
      return a(var0, var1, var2, var3, var4, var5);
   }
}
