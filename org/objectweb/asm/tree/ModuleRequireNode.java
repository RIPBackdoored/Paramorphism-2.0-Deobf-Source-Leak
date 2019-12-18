package org.objectweb.asm.tree;

import org.objectweb.asm.ModuleVisitor;

public class ModuleRequireNode {
   public String module;
   public int access;
   public String version;

   public ModuleRequireNode(String var1, int var2, String var3) {
      super();
      this.module = var1;
      this.access = var2;
      this.version = var3;
   }

   public void accept(ModuleVisitor var1) {
      var1.visitRequire(this.module, this.access, this.version);
   }
}
