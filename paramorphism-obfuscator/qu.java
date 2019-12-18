package paramorphism-obfuscator;

import java.util.Map;
import java.util.Random;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
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
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class qu extends Lambda implements Function1 {
   public final ClassWrapper bdx;
   public final FieldNode bdy;
   public final Function1 bdz;
   public final String bea;
   public final String beb;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.bdx.getType(), this.bdy.name, Reflection.getOrCreateKotlinClass(String.class));
      JumpInstruction.addIfNull((IInstructionWrapper)var1, var1.getLabels().a("calculate"));
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.bdx.getType(), this.bdy.name, Reflection.getOrCreateKotlinClass(String.class));
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
      var1.getLabels().a("calculate").a();
      TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StringBuilder.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), "<init>", var1.be());
      VarInstruction.addObjectStoreZero((IInstructionWrapper)var1);
      this.bdz.invoke(var1);
      VarInstruction.addIntStoreTwo((IInstructionWrapper)var1);
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.bdx.getType(), this.bea, Reflection.getOrCreateKotlinClass(Map.class));
      VarInstruction.addIntLoadTwo((IInstructionWrapper)var1);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, "java/lang/Integer", "valueOf", "java/lang/Integer", var1.bi());
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Map.class), "get", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class));
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(double[].class));
      VarInstruction.addObjectStoreThree((IInstructionWrapper)var1);
      boolean var2 = false;
      TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Random.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      VarInstruction.addIntLoadTwo((IInstructionWrapper)var1);
      MathInstruction.addIntToLong((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Random.class), "<init>", var1.be(), var1.bk());
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 4);
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 5);
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 6);
      var1.getLabels().a(7).a();
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 6);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 3);
      ArrayVarInstruction.addArrayLength((IInstructionWrapper)var1);
      JumpInstruction.addIfIntCompareGreaterEqual((IInstructionWrapper)var1, var1.getLabels().a(8));
      NumberInstruction.addDZero((IInstructionWrapper)var1);
      VarInstruction.addDoubleStore((IInstructionWrapper)var1, 7);
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 9);
      var1.getLabels().a(11).a();
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 9);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 5);
      JumpInstruction.addIfIntCompareGreaterEqual((IInstructionWrapper)var1, var1.getLabels().a(12));
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 5);
      NumberInstruction.addLdc((IInstructionWrapper)var1, this.beb.length());
      JumpInstruction.addIfIntCompareLess((IInstructionWrapper)var1, var1.getLabels().a(14));
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a(8));
      var1.getLabels().a(14).a();
      VarInstruction.addDoubleLoad((IInstructionWrapper)var1, 7);
      NumberInstruction.addDOne((IInstructionWrapper)var1);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 4);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Random.class), "nextDouble", var1.bl());
      MathInstruction.addDAdd((IInstructionWrapper)var1);
      MathInstruction.addDAdd((IInstructionWrapper)var1);
      VarInstruction.addDoubleStore((IInstructionWrapper)var1, 7);
      VarInstruction.addObjectLoadThree((IInstructionWrapper)var1);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 6);
      ArrayVarInstruction.addDoubleArrayLoad((IInstructionWrapper)var1);
      int var3 = 1;

      byte var4;
      for(var4 = 4; var3 <= var4; ++var3) {
         VarInstruction.addDoubleLoad((IInstructionWrapper)var1, 7);
         VarInstruction.addObjectLoadThree((IInstructionWrapper)var1);
         VarInstruction.addIntLoad((IInstructionWrapper)var1, 6);
         IntegerInstruction.addInteger((IInstructionWrapper)var1, var3);
         MathInstruction.addIAdd((IInstructionWrapper)var1);
         ArrayVarInstruction.addDoubleArrayLoad((IInstructionWrapper)var1);
      }

      var3 = 1;

      for(var4 = 4; var3 <= var4; ++var3) {
         MathInstruction.addDMultiply((IInstructionWrapper)var1);
         MathInstruction.addDAdd((IInstructionWrapper)var1);
      }

      VarInstruction.addDoubleStore((IInstructionWrapper)var1, 10);
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      VarInstruction.addDoubleLoad((IInstructionWrapper)var1, 10);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, "java/lang/Math", "round", var1.bk(), var1.bl());
      MathInstruction.addLongToInt((IInstructionWrapper)var1);
      MathInstruction.addIntToChar((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), "append", Reflection.getOrCreateKotlinClass(StringBuilder.class), var1.bf());
      StackInstruction.addPop((IInstructionWrapper)var1);
      MathInstruction.addIIncrementOne((IInstructionWrapper)var1, 5);
      MathInstruction.addIIncrementOne((IInstructionWrapper)var1, 9);
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a(11));
      var1.getLabels().a(12).a();
      MathInstruction.addIIncrement((IInstructionWrapper)var1, 6, 5);
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a(7));
      var1.getLabels().a(8).a();
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), "toString", Reflection.getOrCreateKotlinClass(String.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      FieldInstruction.addPutStatic((IInstructionWrapper)var1, this.bdx.getType(), this.bdy.name, Reflection.getOrCreateKotlinClass(String.class));
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
   }

   public qu(ClassWrapper var1, FieldNode var2, Function1 var3, String var4, String var5) {
      super(1);
      this.bdx = var1;
      this.bdy = var2;
      this.bdz = var3;
      this.bea = var4;
      this.beb = var5;
   }
}
