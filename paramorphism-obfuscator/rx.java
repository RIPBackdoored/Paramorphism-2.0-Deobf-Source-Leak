package paramorphism-obfuscator;

import kotlin.Unit;
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
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class rx extends Lambda implements Function1 {
   public final rv bgg;
   public final ClassWrapper bgh;
   public final FieldNode bgi;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      IntegerInstruction.addInteger((IInstructionWrapper)var1, this.bgg.bgb.size());
      ArrayVarInstruction.addNewArray((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object[].class));
      FieldInstruction.addPutStatic((IInstructionWrapper)var1, this.bgh.getType(), this.bgi.name, Type.getType(this.bgi.desc));
      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public rx(rv var1, ClassWrapper var2, FieldNode var3) {
      super(1);
      this.bgg = var1;
      this.bgh = var2;
      this.bgi = var3;
   }
}
