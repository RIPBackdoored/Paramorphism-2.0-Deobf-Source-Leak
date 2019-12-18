package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class nj extends Lambda implements Function1 {
   public final mw wq;

   public nj(mw var1) {
      super(1);
      this.wq = var1;
   }

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, this.wq.vw);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(byte[].class), "clone", Reflection.getOrCreateKotlinClass(Object.class));
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(byte[].class));
   }
}
