package paramorphism-obfuscator.verifierDisabler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class HotspotLambda extends Lambda implements Function1 {
   public final ClassWrapper rn;
   public final String ro;
   public final FieldNode rp;
   public final String rq;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      String[] var2 = new String[]{"gHotSpotVMStructs", "gHotSpotVMStructEntryTypeNameOffset", "gHotSpotVMStructEntryFieldNameOffset", "gHotSpotVMStructEntryTypeStringOffset", "gHotSpotVMStructEntryIsStaticOffset", "gHotSpotVMStructEntryOffsetOffset", "gHotSpotVMStructEntryAddressOffset", "gHotSpotVMStructEntryArrayStride"};
      String[] var5 = var2;
      int var6 = var2.length;

      for(int var3 = 0; var3 < var6; ++var3) {
         String var4 = var5[var3];
         NumberInstruction.addLdc((IInstructionWrapper)var1, var4);
         MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.rn.getType(), this.ro, var1.bk(), Reflection.getOrCreateKotlinClass(String.class));
         VarInstruction.addLongStore((IInstructionWrapper)var1, 1 + 2 * var3);
      }

      var1.getLabels().a(8).a();
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.rn.getType(), this.rp.name, "sun/misc/Unsafe");
      VarInstruction.addLongLoadOne((IInstructionWrapper)var1);
      VarInstruction.addLongLoadThree((IInstructionWrapper)var1);
      MathInstruction.addLAdd((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, "sun/misc/Unsafe", "getLong", var1.bk(), var1.bk());
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.rn.getType(), this.rq, Reflection.getOrCreateKotlinClass(String.class), var1.bk());
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 17);
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.rn.getType(), this.rp.name, "sun/misc/Unsafe");
      VarInstruction.addLongLoadOne((IInstructionWrapper)var1);
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 5);
      MathInstruction.addLAdd((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, "sun/misc/Unsafe", "getLong", var1.bk(), var1.bk());
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.rn.getType(), this.rq, Reflection.getOrCreateKotlinClass(String.class), var1.bk());
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 18);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 18);
      JumpInstruction.addIfNotNull((IInstructionWrapper)var1, var1.getLabels().a(11));
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a(12));
      var1.getLabels().a(11).a();
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.rn.getType(), this.rp.name, "sun/misc/Unsafe");
      VarInstruction.addLongLoadOne((IInstructionWrapper)var1);
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 7);
      MathInstruction.addLAdd((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, "sun/misc/Unsafe", "getLong", var1.bk(), var1.bk());
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.rn.getType(), this.rq, Reflection.getOrCreateKotlinClass(String.class), var1.bk());
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 19);
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.rn.getType(), this.rp.name, "sun/misc/Unsafe");
      VarInstruction.addLongLoadOne((IInstructionWrapper)var1);
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 9);
      MathInstruction.addLAdd((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, "sun/misc/Unsafe", "getInt", var1.bi(), var1.bk());
      JumpInstruction.addIfEqual((IInstructionWrapper)var1, var1.getLabels().a(14));
      NumberInstruction.addIOne((IInstructionWrapper)var1);
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a(15));
      var1.getLabels().a(14).a();
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      var1.getLabels().a(15).a();
      VarInstruction.addIntStore((IInstructionWrapper)var1, 20);
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.rn.getType(), this.rp.name, "sun/misc/Unsafe");
      VarInstruction.addLongLoadOne((IInstructionWrapper)var1);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 20);
      JumpInstruction.addIfEqual((IInstructionWrapper)var1, var1.getLabels().a(17));
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 13);
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a(18));
      var1.getLabels().a(17).a();
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 11);
      var1.getLabels().a(18).a();
      MathInstruction.addLAdd((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, "sun/misc/Unsafe", "getLong", var1.bk(), var1.bk());
      VarInstruction.addLongStore((IInstructionWrapper)var1, 21);
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 17);
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Map.class), "get", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class));
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Set.class));
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 23);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 23);
      JumpInstruction.addIfNotNull((IInstructionWrapper)var1, var1.getLabels().a(21));
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 17);
      TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(HashSet.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(HashSet.class), "<init>", var1.be());
      StackInstruction.addDup((IInstructionWrapper)var1);
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 23);
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Map.class), "put", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class));
      StackInstruction.addPop((IInstructionWrapper)var1);
      var1.getLabels().a(21).a();
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 23);
      NumberInstruction.addIFour((IInstructionWrapper)var1);
      ArrayVarInstruction.addNewArray((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 18);
      ArrayVarInstruction.addObjectArrayStore((IInstructionWrapper)var1);
      StackInstruction.addDup((IInstructionWrapper)var1);
      NumberInstruction.addIOne((IInstructionWrapper)var1);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 19);
      ArrayVarInstruction.addObjectArrayStore((IInstructionWrapper)var1);
      StackInstruction.addDup((IInstructionWrapper)var1);
      NumberInstruction.addITwo((IInstructionWrapper)var1);
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 21);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, Long.class, "valueOf", Long.class, var1.bk());
      ArrayVarInstruction.addObjectArrayStore((IInstructionWrapper)var1);
      StackInstruction.addDup((IInstructionWrapper)var1);
      NumberInstruction.addIThree((IInstructionWrapper)var1);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 20);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, Boolean.class, "valueOf", Boolean.class, var1.bm());
      ArrayVarInstruction.addObjectArrayStore((IInstructionWrapper)var1);
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Set.class), "add", var1.bm(), Reflection.getOrCreateKotlinClass(Object.class));
      StackInstruction.addPop((IInstructionWrapper)var1);
      VarInstruction.addLongLoadOne((IInstructionWrapper)var1);
      VarInstruction.addLongLoad((IInstructionWrapper)var1, 15);
      MathInstruction.addLAdd((IInstructionWrapper)var1);
      VarInstruction.addLongStoreOne((IInstructionWrapper)var1);
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a(8));
      var1.getLabels().a(12).a();
      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public HotspotLambda(ClassWrapper var1, String var2, FieldNode var3, String var4) {
      super(1);
      this.rn = var1;
      this.ro = var2;
      this.rp = var3;
      this.rq = var4;
   }
}
