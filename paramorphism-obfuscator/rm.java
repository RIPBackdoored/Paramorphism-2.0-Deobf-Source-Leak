package paramorphism-obfuscator;

import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnList;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class rm extends Lambda implements Function1 {
   public final List bfh;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      Iterator var3 = this.bfh.iterator();

      while(var3.hasNext()) {
         InsnList var2 = (InsnList)var3.next();
         var1.getInstructions().add(var2);
      }

      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public rm(List var1) {
      super(1);
      this.bfh = var1;
   }
}
