package org.objectweb.asm.commons;

import java.util.Collections;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

public class TryCatchBlockSorter extends MethodNode {
   public TryCatchBlockSorter(MethodVisitor var1, int var2, String var3, String var4, String var5, String[] var6) {
      this(458752, var1, var2, var3, var4, var5, var6);
      if (this.getClass() != TryCatchBlockSorter.class) {
         throw new IllegalStateException();
      }
   }

   protected TryCatchBlockSorter(int var1, MethodVisitor var2, int var3, String var4, String var5, String var6, String[] var7) {
      super(var1, var3, var4, var5, var6, var7);
      this.mv = var2;
   }

   public void visitEnd() {
      Collections.sort(this.tryCatchBlocks, new TryCatchBlockSorter$1(this));

      for(int var1 = 0; var1 < this.tryCatchBlocks.size(); ++var1) {
         ((TryCatchBlockNode)this.tryCatchBlocks.get(var1)).updateIndex(var1);
      }

      if (this.mv != null) {
         this.accept(this.mv);
      }

   }
}
