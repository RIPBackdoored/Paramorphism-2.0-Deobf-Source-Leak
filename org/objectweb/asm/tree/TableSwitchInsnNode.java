package org.objectweb.asm.tree;

import java.util.List;
import java.util.Map;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class TableSwitchInsnNode extends AbstractInsnNode {
   public int min;
   public int max;
   public LabelNode dflt;
   public List labels;

   public TableSwitchInsnNode(int var1, int var2, LabelNode var3, LabelNode... var4) {
      super(170);
      this.min = var1;
      this.max = var2;
      this.dflt = var3;
      this.labels = Util.asArrayList((Object[])var4);
   }

   public int getType() {
      return 11;
   }

   public void accept(MethodVisitor var1) {
      Label[] var2 = new Label[this.labels.size()];
      int var3 = 0;

      for(int var4 = var2.length; var3 < var4; ++var3) {
         var2[var3] = ((LabelNode)this.labels.get(var3)).getLabel();
      }

      var1.visitTableSwitchInsn(this.min, this.max, this.dflt.getLabel(), var2);
      this.acceptAnnotations(var1);
   }

   public AbstractInsnNode clone(Map var1) {
      return (new TableSwitchInsnNode(this.min, this.max, clone(this.dflt, var1), clone(this.labels, var1))).cloneAnnotations(this);
   }
}
