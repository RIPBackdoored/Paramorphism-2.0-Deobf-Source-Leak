package paramorphism-obfuscator;

import java.io.InputStream;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class lj extends Lambda implements Function1 {
   public final int tw;

   public Object invoke(Object var1) {
      this.a((IInstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull IInstructionWrapper var1) {
      IntegerInstruction.addInteger(var1, 8192);
      VarInstruction.addObjectLoad(var1, this.tw);
      MethodInstruction.addInvokeVirtual(var1, Reflection.getOrCreateKotlinClass(InputStream.class), "available", Primitives.getInt());
      MethodInstruction.addInvokeStatic(var1, Reflection.getOrCreateKotlinClass(Math.class), "max", Primitives.getInt(), Primitives.getInt(), Primitives.getInt());
   }

   public lj(int var1) {
      super(1);
      this.tw = var1;
   }
}
