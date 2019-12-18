package paramorphism-obfuscator;

import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class qi extends Lambda implements Function1 {
   public final px bcx;
   public final ClassWrapper bcy;
   public final FieldNode bcz;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      IntegerInstruction.addInteger((IInstructionWrapper)var1, this.bcx.bcc.size());
      ArrayVarInstruction.addNewArray((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object.class));
      FieldInstruction.addPutStatic((IInstructionWrapper)var1, this.bcy.getType(), this.bcz.name, Reflection.getOrCreateKotlinClass(Object[].class));
      int var2 = 0;

      for(Iterator var4 = ((Iterable)this.bcx.bcc).iterator(); var4.hasNext(); ++var2) {
         qm var3 = (qm)var4.next();
         FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.bcy.getType(), this.bcz.name, Reflection.getOrCreateKotlinClass(Object[].class));
         IntegerInstruction.addInteger((IInstructionWrapper)var1, var2);
         ClassNode var5 = pw.b(var3, var2, this.bcx.bca.name, this.bcx.bcb);
         this.bcx.bcd.add(var5);
         TypeInstruction.addNew((IInstructionWrapper)var1, var5.name);
         StackInstruction.addDup((IInstructionWrapper)var1);
         MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, var5.name, "<init>", var1.be());
         ArrayVarInstruction.addObjectArrayStore((IInstructionWrapper)var1);
      }

      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public qi(px var1, ClassWrapper var2, FieldNode var3) {
      super(1);
      this.bcx = var1;
      this.bcy = var2;
      this.bcz = var3;
   }
}
