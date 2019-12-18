package paramorphism-obfuscator;

import java.util.HashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class kt extends Lambda implements Function1 {
   public final ClassWrapper sd;
   public final String se;
   public final FieldNode sf;
   public final FieldNode sg;
   public final String sh;
   public final String si;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.sd.getType(), this.se, var1.be());
      TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(HashMap.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(HashMap.class), "<init>", var1.be());
      FieldInstruction.addPutStatic((IInstructionWrapper)var1, this.sd.getType(), this.sf.name, Reflection.getOrCreateKotlinClass(Map.class));
      TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(HashMap.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(HashMap.class), "<init>", var1.be());
      FieldInstruction.addPutStatic((IInstructionWrapper)var1, this.sd.getType(), this.sg.name, Reflection.getOrCreateKotlinClass(Map.class));
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.sd.getType(), this.sg.name, Reflection.getOrCreateKotlinClass(Map.class));
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.sd.getType(), this.sh, var1.be(), Reflection.getOrCreateKotlinClass(Map.class));
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.sd.getType(), this.sf.name, Reflection.getOrCreateKotlinClass(Map.class));
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.sd.getType(), this.sg.name, Reflection.getOrCreateKotlinClass(Map.class));
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.sd.getType(), this.si, var1.be(), Reflection.getOrCreateKotlinClass(Map.class), Reflection.getOrCreateKotlinClass(Map.class));
      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public kt(ClassWrapper var1, String var2, FieldNode var3, FieldNode var4, String var5, String var6) {
      super(1);
      this.sd = var1;
      this.se = var2;
      this.sf = var3;
      this.sg = var4;
      this.sh = var5;
      this.si = var6;
   }
}
