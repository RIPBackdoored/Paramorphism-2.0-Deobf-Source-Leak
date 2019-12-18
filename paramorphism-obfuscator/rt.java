package paramorphism-obfuscator;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class rt extends Lambda implements Function1 {
   public final rv bfx;
   public final ra bfy;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      int var2 = 0;

      for(Iterator var3 = ((Iterable)this.bfy.b().keySet()).iterator(); var3.hasNext(); ++var2) {
         var3.next();
         VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
         IntegerInstruction.addInteger((IInstructionWrapper)var1, var2);
         VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
         NumberInstruction.addLdc((IInstructionWrapper)var1, this.bfy.a());
         IInstructionWrapper var10000 = (IInstructionWrapper)var1;
         String var10001 = this.bfx.bgc.a(-1, var2, oj.zh);
         if (var10001 == null) {
            Intrinsics.throwNpe();
         }

         NumberInstruction.addLdc(var10000, var10001);
         VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
         MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Lookup.class), "findStatic", Reflection.getOrCreateKotlinClass(MethodHandle.class), Reflection.getOrCreateKotlinClass(Class.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class));
         ArrayVarInstruction.addObjectArrayStore((IInstructionWrapper)var1);
      }

      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public rt(rv var1, ra var2) {
      super(1);
      this.bfx = var1;
      this.bfy = var2;
   }
}
