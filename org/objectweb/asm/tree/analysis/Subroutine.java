package org.objectweb.asm.tree.analysis;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;

final class Subroutine {
   final LabelNode start;
   final boolean[] localsUsed;
   final List callers;

   Subroutine(LabelNode var1, int var2, JumpInsnNode var3) {
      super();
      this.start = var1;
      this.localsUsed = new boolean[var2];
      this.callers = new ArrayList();
      this.callers.add(var3);
   }

   Subroutine(Subroutine var1) {
      super();
      this.start = var1.start;
      this.localsUsed = new boolean[var1.localsUsed.length];
      this.callers = new ArrayList(var1.callers);
      System.arraycopy(var1.localsUsed, 0, this.localsUsed, 0, var1.localsUsed.length);
   }

   public boolean merge(Subroutine var1) {
      boolean var2 = false;

      int var3;
      for(var3 = 0; var3 < this.localsUsed.length; ++var3) {
         if (var1.localsUsed[var3] && !this.localsUsed[var3]) {
            this.localsUsed[var3] = true;
            var2 = true;
         }
      }

      if (var1.start == this.start) {
         for(var3 = 0; var3 < var1.callers.size(); ++var3) {
            JumpInsnNode var4 = (JumpInsnNode)var1.callers.get(var3);
            if (!this.callers.contains(var4)) {
               this.callers.add(var4);
               var2 = true;
            }
         }
      }

      return var2;
   }
}
