package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class nw extends Lambda implements Function1 {
   public final int xg;

   public Object invoke(Object var1) {
      this.a((InstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull InstructionWrapper var1) {
      yf.a((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Error.class), new Object[0], (String)null, (Function1)null, 12, (Object)null);
      VarInstruction.addObjectStore((IInstructionWrapper)var1, this.xg);
   }

   public nw(int var1) {
      super(1);
      this.xg = var1;
   }
}
