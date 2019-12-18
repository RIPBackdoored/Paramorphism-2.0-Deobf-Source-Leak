package paramorphism-obfuscator.verifierDisabler;

import java.lang.reflect.Field;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class PutVolatileLambda extends Lambda implements Function1 {
   public final JVMDllLambda sc;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ClassLoader.class), "getSystemClassLoader", Reflection.getOrCreateKotlinClass(ClassLoader.class));
      NumberInstruction.addLdc((IInstructionWrapper)var1, "jdk.internal.module.IllegalAccessLogger");
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ClassLoader.class), "loadClass", Reflection.getOrCreateKotlinClass(Class.class), Reflection.getOrCreateKotlinClass(String.class));
      VarInstruction.addObjectStoreOne((IInstructionWrapper)var1);
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
      NumberInstruction.addLdc((IInstructionWrapper)var1, "logger");
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Class.class), "getDeclaredField", Reflection.getOrCreateKotlinClass(Field.class), Reflection.getOrCreateKotlinClass(String.class));
      VarInstruction.addObjectStoreTwo((IInstructionWrapper)var1);
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.sc.rx.getType(), this.sc.sa.name, "sun/misc/Unsafe");
      VarInstruction.addObjectLoadOne((IInstructionWrapper)var1);
      FieldInstruction.addGetStatic((IInstructionWrapper)var1, this.sc.rx.getType(), this.sc.sa.name, "sun/misc/Unsafe");
      VarInstruction.addObjectLoadTwo((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, "sun/misc/Unsafe", "staticFieldOffset", var1.bk(), Reflection.getOrCreateKotlinClass(Field.class));
      NumberInstruction.addNull((IInstructionWrapper)var1);
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, "sun/misc/Unsafe", "putObjectVolatile", var1.be(), Reflection.getOrCreateKotlinClass(Object.class), var1.bk(), Reflection.getOrCreateKotlinClass(Object.class));
   }

   public PutVolatileLambda(JVMDllLambda var1) {
      super(1);
      this.sc = var1;
   }
}
