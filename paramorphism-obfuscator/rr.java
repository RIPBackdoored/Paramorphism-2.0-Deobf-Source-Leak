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
import java.util.NoSuchElementException;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.SwitchInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class rr extends Lambda implements Function1 {
   public final rv bft;
   public final ClassWrapper bfu;
   public final FieldNode bfv;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.bfu.getType(), this.bfv.name, Type.getType(this.bfv.desc));
      VarInstruction.addIntLoadTwo((IInstructionWrapper)var1);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object[].class));
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 4);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 4);
      JumpInstruction.addIfNull((IInstructionWrapper)var1, var1.getLabels().a("initialize"));
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 4);
      VarInstruction.addIntLoadThree((IInstructionWrapper)var1);
      ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(MethodHandle.class));
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 5);
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a("return_call_site"));
      var1.getLabels().a("initialize").a();
      Iterable var3 = (Iterable)this.bft.bgb;
      boolean var4 = false;
      Collection var6 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10)));
      boolean var7 = false;
      int var8 = 0;
      Iterator var9 = var3.iterator();

      while(var9.hasNext()) {
         Object var10 = var9.next();
         int var11 = var8++;
         boolean var13 = false;
         if (var11 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         ra var15 = (ra)var10;
         boolean var17 = false;
         yg var19 = var1.getLabels().a("container_" + var11);
         var6.add(var19);
      }

      Collection var20 = (Collection)((List)var6);
      var4 = false;
      Object[] var10000 = var20.toArray(new yg[0]);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         yg[] var2 = (yg[])var10000;
         VarInstruction.addIntLoadTwo((IInstructionWrapper)var1);
         SwitchInstruction.addTableSwitch((IInstructionWrapper)var1, 0, CollectionsKt.getLastIndex(this.bft.bgb), var1.getLabels().a("container_default"), Arrays.copyOf(var2, var2.length));
         int var21 = 0;

         for(Iterator var5 = ((Iterable)this.bft.bgb).iterator(); var5.hasNext(); ++var21) {
            ra var22 = (ra)var5.next();
            var1.getLabels().a("container_" + var21).a();
            FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.bfu.getType(), this.bfv.name, Type.getType(this.bfv.desc));
            TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object[].class));
            IntegerInstruction.addInteger((IInstructionWrapper)var1, var21);
            IntegerInstruction.addInteger((IInstructionWrapper)var1, var22.b().size() + 1);
            ArrayVarInstruction.addNewArray((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object.class));
            StackInstruction.addDup((IInstructionWrapper)var1);
            VarInstruction.addObjectStore((IInstructionWrapper)var1, 4);
            ArrayVarInstruction.addObjectArrayStore((IInstructionWrapper)var1);
            VarInstruction.addObjectLoad((IInstructionWrapper)var1, 4);
            VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
            VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
            MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.bfu.getType(), "" + 'b' + var21, var1.be(), Reflection.getOrCreateKotlinClass(Object[].class), Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(MethodType.class));
            JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a("switch_exit"));
         }

         var1.getLabels().a("container_default").a();
         TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(NoSuchElementException.class));
         StackInstruction.addDup((IInstructionWrapper)var1);
         MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(NoSuchElementException.class), "<init>", var1.be());
         JumpInstruction.addAThrow((IInstructionWrapper)var1);
         var1.getLabels().a("switch_exit").a();
         VarInstruction.addObjectLoad((IInstructionWrapper)var1, 4);
         VarInstruction.addIntLoadThree((IInstructionWrapper)var1);
         ArrayVarInstruction.addObjectArrayLoad((IInstructionWrapper)var1);
         TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(MethodHandle.class));
         VarInstruction.addObjectStore((IInstructionWrapper)var1, 5);
         var1.getLabels().a("return_call_site").a();
         TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ConstantCallSite.class));
         StackInstruction.addDup((IInstructionWrapper)var1);
         VarInstruction.addObjectLoad((IInstructionWrapper)var1, 5);
         MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ConstantCallSite.class), "<init>", var1.be(), Reflection.getOrCreateKotlinClass(MethodHandle.class));
         JumpInstruction.addAReturn((IInstructionWrapper)var1);
      }
   }

   public rr(rv var1, ClassWrapper var2, FieldNode var3) {
      super(1);
      this.bft = var1;
      this.bfu = var2;
      this.bfv = var3;
   }
}
