package org.objectweb.asm.tree;

import org.objectweb.asm.MethodVisitor;

public class ParameterNode {
   public String name;
   public int access;

   public ParameterNode(String var1, int var2) {
      super();
      this.name = var1;
      this.access = var2;
   }

   public void accept(MethodVisitor var1) {
      var1.visitParameter(this.name, this.access);
   }
}
