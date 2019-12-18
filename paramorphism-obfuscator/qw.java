package paramorphism-obfuscator;

import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class qw extends Lambda implements Function1 {
   public static final qw bed = new qw();

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      boolean var3 = false;
      boolean var4 = false;
      MethodWrapper var5 = var1;
      boolean var6 = false;
      int var7 = 0;

      byte var8;
      for(var8 = 4; var7 < var8; ++var7) {
         VarInstruction.addObjectLoadZero((IInstructionWrapper)var5);
         IntegerInstruction.addInteger((IInstructionWrapper)var5, var7);
         ArrayVarInstruction.addByteArrayLoad((IInstructionWrapper)var5);
         IntegerInstruction.addInteger((IInstructionWrapper)var5, 255);
         MathInstruction.addIAnd((IInstructionWrapper)var5);
         IntegerInstruction.addInteger((IInstructionWrapper)var5, 8 * (3 - var7));
         MathInstruction.addIShiftLeft((IInstructionWrapper)var5);
      }

      var7 = 0;

      for(var8 = 3; var7 < var8; ++var7) {
         MathInstruction.addIOr((IInstructionWrapper)var5);
      }

      VarInstruction.addIntStore((IInstructionWrapper)var1, 9);
      var3 = false;
      var4 = false;
      var5 = var1;
      var6 = false;
      var7 = 0;

      for(var8 = 4; var7 < var8; ++var7) {
         VarInstruction.addObjectLoadZero((IInstructionWrapper)var5);
         IntegerInstruction.addInteger((IInstructionWrapper)var5, var7 + 4);
         ArrayVarInstruction.addByteArrayLoad((IInstructionWrapper)var5);
         IntegerInstruction.addInteger((IInstructionWrapper)var5, 255);
         MathInstruction.addIAnd((IInstructionWrapper)var5);
         IntegerInstruction.addInteger((IInstructionWrapper)var5, 8 * (3 - var7));
         MathInstruction.addIShiftLeft((IInstructionWrapper)var5);
      }

      var7 = 0;

      for(var8 = 3; var7 < var8; ++var7) {
         MathInstruction.addIOr((IInstructionWrapper)var5);
      }

      VarInstruction.addIntStore((IInstructionWrapper)var1, 5);
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 2);
      var1.getLabels().a("for_each_caller").a();
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 2);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 5);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 40);
      MathInstruction.addIMultiply((IInstructionWrapper)var1);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 4);
      MathInstruction.addIAdd((IInstructionWrapper)var1);
      MathInstruction.addIMultiply((IInstructionWrapper)var1);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 8);
      MathInstruction.addIAdd((IInstructionWrapper)var1);
      VarInstruction.addIntStoreThree((IInstructionWrapper)var1);
      var3 = false;
      var4 = false;
      var5 = var1;
      var6 = false;
      var7 = 0;

      for(var8 = 4; var7 < var8; ++var7) {
         VarInstruction.addObjectLoadZero((IInstructionWrapper)var5);
         VarInstruction.addIntLoadThree((IInstructionWrapper)var5);
         IntegerInstruction.addInteger((IInstructionWrapper)var5, var7);
         MathInstruction.addIAdd((IInstructionWrapper)var5);
         ArrayVarInstruction.addByteArrayLoad((IInstructionWrapper)var5);
         IntegerInstruction.addInteger((IInstructionWrapper)var5, 255);
         MathInstruction.addIAnd((IInstructionWrapper)var5);
         IntegerInstruction.addInteger((IInstructionWrapper)var5, 8 * (3 - var7));
         MathInstruction.addIShiftLeft((IInstructionWrapper)var5);
      }

      var7 = 0;

      for(var8 = 3; var7 < var8; ++var7) {
         MathInstruction.addIOr((IInstructionWrapper)var5);
      }

      VarInstruction.addIntStore((IInstructionWrapper)var1, 4);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 5);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 5);
      MathInstruction.addIMultiply((IInstructionWrapper)var1);
      ArrayVarInstruction.addIndexInt((IInstructionWrapper)var1, var1.bl());
      VarInstruction.addObjectStore((IInstructionWrapper)var1, 8);
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      VarInstruction.addIntStore((IInstructionWrapper)var1, 6);
      var1.getLabels().a("for_each_section").a();
      int var2 = 0;

      for(byte var9 = 5; var2 < var9; ++var2) {
         VarInstruction.addObjectLoad((IInstructionWrapper)var1, 8);
         VarInstruction.addIntLoad((IInstructionWrapper)var1, 6);
         IntegerInstruction.addInteger((IInstructionWrapper)var1, 5);
         MathInstruction.addIMultiply((IInstructionWrapper)var1);
         IntegerInstruction.addInteger((IInstructionWrapper)var1, var2);
         MathInstruction.addIAdd((IInstructionWrapper)var1);
         VarInstruction.addIntStore((IInstructionWrapper)var1, 7);
         VarInstruction.addIntLoad((IInstructionWrapper)var1, 7);
         VarInstruction.addIntLoadThree((IInstructionWrapper)var1);
         VarInstruction.addIntLoad((IInstructionWrapper)var1, 7);
         IntegerInstruction.addInteger((IInstructionWrapper)var1, 8);
         MathInstruction.addIMultiply((IInstructionWrapper)var1);
         IntegerInstruction.addInteger((IInstructionWrapper)var1, 4);
         MathInstruction.addIAdd((IInstructionWrapper)var1);
         MathInstruction.addIAdd((IInstructionWrapper)var1);
         VarInstruction.addIntStore((IInstructionWrapper)var1, 7);
         int var10 = 0;

         byte var11;
         for(var11 = 8; var10 < var11; ++var10) {
            VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
            VarInstruction.addIntLoad((IInstructionWrapper)var1, 7);
            IntegerInstruction.addInteger((IInstructionWrapper)var1, var10);
            MathInstruction.addIAdd((IInstructionWrapper)var1);
            ArrayVarInstruction.addByteArrayLoad((IInstructionWrapper)var1);
            IntegerInstruction.addInteger((IInstructionWrapper)var1, 255);
            MathInstruction.addIAnd((IInstructionWrapper)var1);
            MathInstruction.addIntToLong((IInstructionWrapper)var1);
            IntegerInstruction.addInteger((IInstructionWrapper)var1, 8 * (7 - var10));
            MathInstruction.addLShiftLeft((IInstructionWrapper)var1);
         }

         var10 = 0;

         for(var11 = 7; var10 < var11; ++var10) {
            MathInstruction.addLOr((IInstructionWrapper)var1);
         }

         MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, "java/lang/Double", "longBitsToDouble", var1.bl(), var1.bk());
         ArrayVarInstruction.addDoubleArrayStore((IInstructionWrapper)var1);
      }

      MathInstruction.addIIncrementOne((IInstructionWrapper)var1, 6);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 6);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 5);
      JumpInstruction.addIfIntCompareLess((IInstructionWrapper)var1, var1.getLabels().a("for_each_section"));
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 1);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 4);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, "java/lang/Integer", "valueOf", "java/lang/Integer", var1.bi());
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 8);
      MethodInstruction.addInvokeInterface((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Map.class), "put", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class));
      StackInstruction.addPop((IInstructionWrapper)var1);
      MathInstruction.addIIncrementOne((IInstructionWrapper)var1, 2);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 2);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, 9);
      JumpInstruction.addIfIntCompareLess((IInstructionWrapper)var1, var1.getLabels().a("for_each_caller"));
      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public qw() {
      super(1);
   }
}
