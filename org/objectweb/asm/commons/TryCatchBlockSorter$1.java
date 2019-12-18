package org.objectweb.asm.commons;

import java.util.Comparator;
import org.objectweb.asm.tree.TryCatchBlockNode;

class TryCatchBlockSorter$1 implements Comparator {
   final TryCatchBlockSorter this$0;

   TryCatchBlockSorter$1(TryCatchBlockSorter var1) {
      super();
      this.this$0 = var1;
   }

   public int compare(TryCatchBlockNode var1, TryCatchBlockNode var2) {
      return this.blockLength(var1) - this.blockLength(var2);
   }

   private int blockLength(TryCatchBlockNode var1) {
      int var2 = this.this$0.instructions.indexOf(var1.start);
      int var3 = this.this$0.instructions.indexOf(var1.end);
      return var3 - var2;
   }

   public int compare(Object var1, Object var2) {
      return this.compare((TryCatchBlockNode)var1, (TryCatchBlockNode)var2);
   }
}
