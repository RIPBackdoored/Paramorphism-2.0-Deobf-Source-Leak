package paramorphism-obfuscator;

import java.lang.reflect.Method;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.IntegerInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class nh extends Lambda implements Function1 {
   public final nb wm;
   public final ClassWrapper wn;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      yf.a((IInstructionWrapper)var1, this.wn.getType(), new Object[]{var1.be(), Reflection.getOrCreateKotlinClass(ClassLoader.class)}, (String)null, (Function1)(new ne(this)), 4, (Object)null);
      NumberInstruction.addLdc((IInstructionWrapper)var1, this.wm.we);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ClassLoader.class), "loadClass", Reflection.getOrCreateKotlinClass(Class.class), Reflection.getOrCreateKotlinClass(String.class));
      VarInstruction.addObjectStoreOne((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
      NumberInstruction.addLdc((IInstructionWrapper)var1, "main");
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 1);
      ArrayVarInstruction.addNewArray((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Class.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 0);
      NumberInstruction.addLdc((IInstructionWrapper)var1, var1.a(Reflection.getOrCreateKotlinClass(String[].class)));
      ArrayVarInstruction.addObjectArrayStore((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Class.class), "getDeclaredMethod", Reflection.getOrCreateKotlinClass(Method.class), Reflection.getOrCreateKotlinClass(String.class), "[Ljava/lang/Class;");
      NumberInstruction.addNull((IInstructionWrapper)var1);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 1);
      ArrayVarInstruction.addNewArray((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Object.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      IntegerInstruction.addInteger((IInstructionWrapper)var1, 0);
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      ArrayVarInstruction.addObjectArrayStore((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Method.class), "invoke", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object[].class));
      StackInstruction.addPop((IInstructionWrapper)var1);
      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public nh(nb var1, ClassWrapper var2) {
      super(1);
      this.wm = var1;
      this.wn = var2;
   }
}
