package paramorphism-obfuscator.strategies;

import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class JumpReplacer extends Lambda implements Function1 {
   public final LabelNode bgk;
   public final List bgl;
   public final List bgm;

   public Object invoke(Object var1) {
      this.a((InstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull InstructionWrapper var1) {
      JumpInstruction.addGoto((IInstructionWrapper)var1, this.bgk);
      Iterator var3 = CollectionsKt.zip((Iterable)this.bgl, (Iterable)this.bgm).iterator();

      while(var3.hasNext()) {
         Pair var2 = (Pair)var3.next();
         LabelNode var4 = (LabelNode)var2.component1();
         LabelNode var5 = (LabelNode)var2.component2();
         var1.getInstructions().add((AbstractInsnNode)var4);
         NumberInstruction.addNull((IInstructionWrapper)var1);
         JumpInstruction.addIfNull((IInstructionWrapper)var1, var5);
         NumberInstruction.addNull((IInstructionWrapper)var1);
         JumpInstruction.addAThrow((IInstructionWrapper)var1);
      }

   }

   public JumpReplacer(LabelNode var1, List var2, List var3) {
      super(1);
      this.bgk = var1;
      this.bgl = var2;
      this.bgm = var3;
   }
}
