package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class xt {
   private final IInstructionWrapper bkp;
   @NotNull
   private final LabelNode bkq;
   @NotNull
   private final LabelNode bkr;
   @NotNull
   private final LabelNode bks;

   @NotNull
   public final xt a(@NotNull Object var1, boolean var2, @NotNull Function1 var3) {
      InsnList var4 = new InsnList();
      LabelNode var5 = new LabelNode();
      var4.add((AbstractInsnNode)var5);
      xs var6 = new xs(var4, this.bkp);
      var3.invoke(var6);
      if (!var2) {
         var4.add((AbstractInsnNode)(new JumpInsnNode(Opcodes.GOTO, this.bks)));
      }

      this.bkp.getInstructions().insertBefore((AbstractInsnNode)this.bks, var4);
      ((xl)this.bkp).c().add(new TryCatchBlockNode(this.bkq, this.bkr, var5, Primitives.getType(var1).getInternalName()));
      return this;
   }

   @NotNull
   public static xt a(xt var0, Object var1, boolean var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      return var0.a(var1, var2, var3);
   }

   @NotNull
   public final LabelNode a() {
      return this.bkq;
   }

   @NotNull
   public final LabelNode b() {
      return this.bkr;
   }

   @NotNull
   public final LabelNode c() {
      return this.bks;
   }

   public xt(@NotNull IInstructionWrapper var1, @NotNull LabelNode var2, @NotNull LabelNode var3, @NotNull LabelNode var4) {
      super();
      this.bkp = var1;
      this.bkq = var2;
      this.bkr = var3;
      this.bks = var4;
   }
}
