package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class rp extends Lambda implements Function1 {
   public final boolean bfn;
   public final Object bfo;
   public final Type bfp;
   public final FieldNode bfq;

   public Object invoke(Object var1) {
      this.a((IInstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull IInstructionWrapper var1) {
      if (!this.bfn) {
         VarInstruction.addObjectLoadZero(var1);
      }

      NumberInstruction.addLdc(var1, this.bfo);
      if (this.bfn) {
         FieldInstruction.addPutStatic(var1, this.bfp, this.bfq.name, Type.getType(this.bfq.desc));
      } else {
         FieldInstruction.addPutField(var1, this.bfp, this.bfq.name, Type.getType(this.bfq.desc));
      }

   }

   public rp(boolean var1, Object var2, Type var3, FieldNode var4) {
      super(1);
      this.bfn = var1;
      this.bfo = var2;
      this.bfp = var3;
      this.bfq = var4;
   }
}
