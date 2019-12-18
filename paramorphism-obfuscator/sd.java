package paramorphism-obfuscator;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.InvokeDynamicInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class sd extends Lambda implements Function1 {
   public final sb bgs;
   public final ClassWrapper bgt;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      var1.setMaxLocals(2);
      var1.setMaxStack(3);
      NumberInstruction.addIOne((IInstructionWrapper)var1);
      FieldInstruction.addPutStatic((IInstructionWrapper)var1, this.bgt.getType(), "guard", var1.bi());
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.bgs.bgp.name, "run", var1.be());
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      IInstructionWrapper var10000 = (IInstructionWrapper)var1;
      Type var10002 = var1.be();
      Handle var8 = InvokeDynamicInstruction.getStaticHandle((IInstructionWrapper)var1, this.bgt.getType(), "link", Reflection.getOrCreateKotlinClass(CallSite.class), Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class));
      Type var7 = var10002;
      String var6 = "main";
      IInstructionWrapper var5 = var10000;
      Object[] var9 = new Object[0];
      Object[] var2 = new Object[]{Reflection.getOrCreateKotlinClass(String[].class)};
      InvokeDynamicInstruction.addInvokeDynamic(var5, var6, var7, var2, var8, var9);
      JumpInstruction.addDuplicateReturn((IInstructionWrapper)var1);
   }

   public sd(sb var1, ClassWrapper var2) {
      super(1);
      this.bgs = var1;
      this.bgt = var2;
   }
}
