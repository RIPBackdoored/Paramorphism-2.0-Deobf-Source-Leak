package paramorphism-obfuscator;

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.SwitchInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class pv extends Lambda implements Function1 {
   public final ClassWrapper bbx;
   public final FieldNode bby;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 4);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 7);
      TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Throwable.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Throwable.class), "<init>", var1.be());
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Throwable.class), "getStackTrace", Reflection.getOrCreateKotlinClass(StackTraceElement[].class));
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 5);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 5);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 5);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StackTraceElement.class), "getClassName", Reflection.getOrCreateKotlinClass(String.class));
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(String.class), "hashCode", var1.bi());
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 31);
      MathInstruction.addIMultiply((IInstructionWrapper)var1);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 5);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StackTraceElement.class), "getMethodName", Reflection.getOrCreateKotlinClass(String.class));
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(String.class), "hashCode", var1.bi());
      MathInstruction.addIAdd((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 6);
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      FieldInstruction.addGetField((IInstructionWrapper)var1, this.bbx.getType(), this.bby.name, Reflection.getOrCreateKotlinClass(Map.class));
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 6);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, "java/lang/Integer", "valueOf", "java/lang/Integer", var1.bi());
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Map.class), "get", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class));
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(String[][].class));
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 3);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 8);
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 8);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 0);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Class.class), "forName", Reflection.getOrCreateKotlinClass(Class.class), Reflection.getOrCreateKotlinClass(String.class));
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 8);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 1);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 7);
      IInstructionWrapper var10000 = (IInstructionWrapper)var1;
      yg var10003 = var1.getLabels().a("default");
      byte var2 = 1;
      Iterable var17 = (Iterable)(new IntRange(var2, 3));
      yg var14 = var10003;
      byte var13 = 3;
      byte var12 = 1;
      IInstructionWrapper var11 = var10000;
      boolean var3 = false;
      Collection var5 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var17, 10)));
      boolean var6 = false;
      Iterator var7 = var17.iterator();

      while(var7.hasNext()) {
         int var8 = ((IntIterator)var7).nextInt();
         boolean var10 = false;
         yg var16 = var1.getLabels().a("switch_" + var8);
         var5.add(var16);
      }

      List var15 = (List)var5;
      Collection var18 = (Collection)var15;
      var3 = false;
      Object[] var20 = var18.toArray(new yg[0]);
      if (var20 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         Object[] var19 = var20;
         SwitchInstruction.addTableSwitch(var11, var12, var13, var14, Arrays.copyOf(var19, var19.length));
         var1.getLabels().a("default").a();
         StackInstruction.addPop2((IInstructionWrapper)var1);
         StackInstruction.addPop2((IInstructionWrapper)var1);
         NumberInstruction.addNull((IInstructionWrapper)var1);
         JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a("tail"));
         var1.getLabels().a("switch_1").a();
         MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Lookup.class), "findStatic", Reflection.getOrCreateKotlinClass(MethodHandle.class), Reflection.getOrCreateKotlinClass(Class.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class));
         JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a("tail"));
         var1.getLabels().a("switch_2").a();
         MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Lookup.class), "findVirtual", Reflection.getOrCreateKotlinClass(MethodHandle.class), Reflection.getOrCreateKotlinClass(Class.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class));
         JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a("tail"));
         var1.getLabels().a("switch_3").a();
         MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Lookup.class), "findSpecial", Reflection.getOrCreateKotlinClass(MethodHandle.class), Reflection.getOrCreateKotlinClass(Class.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class));
         JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a("tail"));
         var1.getLabels().a("tail").a();
         TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ConstantCallSite.class));
         StackInstruction.addDupX1((IInstructionWrapper)var1);
         StackInstruction.addSwap((IInstructionWrapper)var1);
         MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ConstantCallSite.class), "<init>", var1.be(), Reflection.getOrCreateKotlinClass(MethodHandle.class));
         JumpInstruction.addAReturn((IInstructionWrapper)var1);
      }
   }

   public pv(ClassWrapper var1, FieldNode var2) {
      super(1);
      this.bbx = var1;
      this.bby = var2;
   }
}
