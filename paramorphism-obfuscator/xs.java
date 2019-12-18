package paramorphism-obfuscator;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnList;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.LabelsWrapper;

public final class xs implements IInstructionWrapper, xl, yi {
   @NotNull
   private final List bkl;
   @NotNull
   private final LabelsWrapper bkm;
   @NotNull
   private final InsnList bkn;
   private final IInstructionWrapper bko;

   @NotNull
   public List c() {
      return this.bkl;
   }

   @NotNull
   public LabelsWrapper a() {
      return this.bkm;
   }

   @NotNull
   public InsnList b() {
      return this.bkn;
   }

   public xs(@NotNull InsnList var1, @NotNull IInstructionWrapper var2) {
      super();
      this.bkn = var1;
      this.bko = var2;
      this.bkl = ((xl)this.bko).c();
      this.bkm = ((yi)this.bko).a().a(this.bko);
   }
}
