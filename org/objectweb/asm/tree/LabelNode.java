package org.objectweb.asm.tree;

import java.util.Map;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class LabelNode extends AbstractInsnNode {
   private Label value;

   public LabelNode() {
      super(-1);
   }

   public LabelNode(Label var1) {
      super(-1);
      this.value = var1;
   }

   public int getType() {
      return 8;
   }

   public Label getLabel() {
      if (this.value == null) {
         this.value = new Label();
      }

      return this.value;
   }

   public void accept(MethodVisitor var1) {
      var1.visitLabel(this.getLabel());
   }

   public AbstractInsnNode clone(Map var1) {
      return (AbstractInsnNode)var1.get(this);
   }

   public void resetLabel() {
      this.value = null;
   }
}
