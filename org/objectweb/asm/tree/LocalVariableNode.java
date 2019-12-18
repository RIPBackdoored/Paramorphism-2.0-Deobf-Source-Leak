package org.objectweb.asm.tree;

import org.objectweb.asm.MethodVisitor;

public class LocalVariableNode {
   public String name;
   public String desc;
   public String signature;
   public LabelNode start;
   public LabelNode end;
   public int index;

   public LocalVariableNode(String var1, String var2, String var3, LabelNode var4, LabelNode var5, int var6) {
      super();
      this.name = var1;
      this.desc = var2;
      this.signature = var3;
      this.start = var4;
      this.end = var5;
      this.index = var6;
   }

   public void accept(MethodVisitor var1) {
      var1.visitLocalVariable(this.name, this.desc, this.signature, this.start.getLabel(), this.end.getLabel(), this.index);
   }
}
