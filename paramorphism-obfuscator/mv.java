package paramorphism-obfuscator;

import java.io.InputStream;
import java.util.Collection;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class mv extends Lambda implements Function1 {
   public final nb vt;
   public final ClassWrapper vu;
   public final FieldNode vv;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      FieldInstruction.addGetField((IInstructionWrapper)var1, this.vu.getType(), this.vv.name, Reflection.getOrCreateKotlinClass(ClassLoader.class));
      yf.a((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), new Object[0], (String)null, (Function1)null, 12, (Object)null);
      NumberInstruction.addLdc((IInstructionWrapper)var1, this.vt.wd.vx.b().getPackedPrefix());
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), "append", Reflection.getOrCreateKotlinClass(StringBuilder.class), Reflection.getOrCreateKotlinClass(String.class));
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, 1);
      NumberInstruction.addLdc((IInstructionWrapper)var1, '.');
      NumberInstruction.addLdc((IInstructionWrapper)var1, '/');
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(String.class), "replace", Reflection.getOrCreateKotlinClass(String.class), var1.bf(), var1.bf());
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), "append", Reflection.getOrCreateKotlinClass(StringBuilder.class), Reflection.getOrCreateKotlinClass(String.class));
      NumberInstruction.addLdc((IInstructionWrapper)var1, this.vt.wd.vx.b().getPackedExtension());
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), "append", Reflection.getOrCreateKotlinClass(StringBuilder.class), Reflection.getOrCreateKotlinClass(String.class));
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), "toString", Reflection.getOrCreateKotlinClass(String.class));
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ClassLoader.class), "getResourceAsStream", Reflection.getOrCreateKotlinClass(InputStream.class), Reflection.getOrCreateKotlinClass(String.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      JumpInstruction.addIfNotNull((IInstructionWrapper)var1, var1.getLabels().a("skip"));
      yf.a((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ClassNotFoundException.class), new Object[]{var1.be(), Reflection.getOrCreateKotlinClass(String.class)}, (String)null, (Function1)nc.wf, 4, (Object)null);
      JumpInstruction.addAThrow((IInstructionWrapper)var1);
      var1.getLabels().a("skip").a();
      byte var2 = 4;
      VarInstruction.addObjectStore((IInstructionWrapper)var1, var2);
      Ref$IntRef var3 = new Ref$IntRef();
      var3.element = -1;
      Pair var4 = xg.a((Function1)(new ni(var3, var2)));
      boolean var5 = false;
      boolean var6 = false;
      boolean var8 = false;
      var1.getInstructions().add((InsnList)var4.getFirst());
      var1.getExceptions().addAll((Collection)var4.getSecond());
      int var9 = this.vt.wd.vz.a(var1, var3.element);
      VarInstruction.addObjectLoadZero((IInstructionWrapper)var1);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, var9);
      NumberInstruction.addIZero((IInstructionWrapper)var1);
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, var9);
      ArrayVarInstruction.addArrayLength((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, this.vu.getType(), "defineClass", Reflection.getOrCreateKotlinClass(Class.class), Reflection.getOrCreateKotlinClass(byte[].class), var1.bi(), var1.bi());
      TypeInstruction.addCheckCast((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Class.class));
      JumpInstruction.addAReturn((IInstructionWrapper)var1);
   }

   public mv(nb var1, ClassWrapper var2, FieldNode var3) {
      super(1);
      this.vt = var1;
      this.vu = var2;
      this.vv = var3;
   }
}
