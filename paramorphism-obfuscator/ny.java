package paramorphism-obfuscator;

import kotlin.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class ny extends Lambda implements Function1 {
   public final LabelNode xi;
   public final AbstractInsnNode xj;
   public final LabelNode xk;
   public final Lazy xl;
   public final KProperty xm;
   public final LabelNode xn;

   public Object invoke(Object var1) {
      this.a((InstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull InstructionWrapper var1) {
      var1.getInstructions().add((AbstractInsnNode)this.xi);
      var1.getInstructions().add((AbstractInsnNode)(new JumpInsnNode(((JumpInsnNode)this.xj).getOpcode(), this.xk)));
      IInstructionWrapper var10000 = (IInstructionWrapper)var1;
      Lazy var2 = this.xl;
      Object var3 = null;
      KProperty var4 = this.xm;
      IInstructionWrapper var6 = var10000;
      boolean var5 = false;
      Object var7 = var2.getValue();
      VarInstruction.addObjectLoad(var6, ((Number)var7).intValue());
      JumpInstruction.addIfNotNull((IInstructionWrapper)var1, this.xn);
      var1.getLabels().a("guard_begin").a();
      var10000 = (IInstructionWrapper)var1;
      var2 = this.xl;
      var3 = null;
      var4 = this.xm;
      var6 = var10000;
      var5 = false;
      var7 = var2.getValue();
      VarInstruction.addObjectLoad(var6, ((Number)var7).intValue());
      JumpInstruction.addAThrow((IInstructionWrapper)var1);
      var1.getLabels().a("handle_begin").a();
      JumpInstruction.addAThrow((IInstructionWrapper)var1);
      var1.getLabels().a("guard_end").a();
      var1.getExceptions().add(new TryCatchBlockNode(yj.a(var1.getLabels().a("guard_begin")), yj.a(var1.getLabels().a("guard_end")), yj.a(var1.getLabels().a("handle_begin")), (String)null));
   }

   public ny(LabelNode var1, AbstractInsnNode var2, LabelNode var3, Lazy var4, KProperty var5, LabelNode var6) {
      super(1);
      this.xi = var1;
      this.xj = var2;
      this.xk = var3;
      this.xl = var4;
      this.xm = var5;
      this.xn = var6;
   }
}
