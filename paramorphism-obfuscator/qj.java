package paramorphism-obfuscator;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import paramorphism-obfuscator.instruction.InvokeDynamicInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class qj extends Lambda implements Function1 {
   public final ps bda;
   public final int bdb;
   public final Set bdc;
   public final pt bdd;
   public final AbstractInsnNode bde;

   public Object invoke(Object var1) {
      this.a((InstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull InstructionWrapper var1) {
      long var2 = (long)this.bdb << 32 | (long)CollectionsKt.indexOf((Iterable)this.bdc, this.bdd);
      Type var4 = Type.getReturnType(((MethodInsnNode)this.bde).desc);
      Type[] var6 = Type.getArgumentTypes(((MethodInsnNode)this.bde).desc);
      boolean var7 = false;
      boolean var8 = false;
      boolean var10 = false;
      Type[] var5 = ((MethodInsnNode)this.bde).getOpcode() != 184 ? (Type[])ArraysKt.plus(new Type[]{var1.a(((MethodInsnNode)this.bde).owner)}, var6) : var6;
      Type var22 = var1.a(Reflection.getOrCreateKotlinClass(CallSite.class));
      Object[] var24 = new Object[]{Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class), var1.bk(), var1.bi()};
      boolean var9 = false;
      Collection var11 = (Collection)(new ArrayList(var24.length));
      boolean var12 = false;
      Object[] var13 = var24;
      int var14 = var24.length;

      for(int var15 = 0; var15 < var14; ++var15) {
         Object var16 = var13[var15];
         boolean var18 = false;
         Type var20 = var1.a(var16);
         var11.add(var20);
      }

      Collection var25 = (Collection)((List)var11);
      var9 = false;
      Object[] var10000 = var25.toArray(new Type[0]);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         Type[] var23 = (Type[])var10000;
         IInstructionWrapper var29 = (IInstructionWrapper)var1;
         Handle var10003;
         switch(((MethodInsnNode)this.bde).getOpcode()) {
         case 182:
            var10003 = InvokeDynamicInstruction.getVirtualHandle((IInstructionWrapper)var1, qc.c(this.bda.bbm), "bootstrap", var22, Arrays.copyOf(var23, var23.length));
            break;
         case 183:
            var10003 = InvokeDynamicInstruction.getSpecialHandle((IInstructionWrapper)var1, qc.c(this.bda.bbm), "bootstrap", var22, Arrays.copyOf(var23, var23.length));
            break;
         case 184:
            var10003 = InvokeDynamicInstruction.getStaticHandle((IInstructionWrapper)var1, qc.c(this.bda.bbm), "bootstrap", var22, Arrays.copyOf(var23, var23.length));
            break;
         case 185:
            var10003 = InvokeDynamicInstruction.getInterfaceHandle((IInstructionWrapper)var1, qc.c(this.bda.bbm), "bootstrap", var22, Arrays.copyOf(var23, var23.length));
            break;
         default:
            String var26 = "Unsupported method call instruction opcode";
            String var30 = "call";
            var9 = false;
            throw (Throwable)(new IllegalStateException(var26.toString()));
         }

         Object[] var10004 = new Object[]{var2, null};
         byte var10007;
         switch(((MethodInsnNode)this.bde).getOpcode()) {
         case 182:
         case 185:
            var10007 = 2;
            break;
         case 183:
            var10007 = 3;
            break;
         case 184:
            var10007 = 1;
            break;
         default:
            var10007 = 0;
         }

         var10004[1] = Integer.valueOf(var10007);
         var24 = Arrays.copyOf(var5, var5.length);
         Object[] var27 = var10004;
         Handle var28 = var10003;
         InvokeDynamicInstruction.addInvokeDynamic(var29, "call", var4, var24, var28, var27);
      }
   }

   public qj(ps var1, int var2, Set var3, pt var4, AbstractInsnNode var5) {
      super(1);
      this.bda = var1;
      this.bdb = var2;
      this.bdc = var3;
      this.bdd = var4;
      this.bde = var5;
   }
}
