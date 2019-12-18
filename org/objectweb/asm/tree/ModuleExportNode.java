package org.objectweb.asm.tree;

import java.util.List;
import org.objectweb.asm.ModuleVisitor;

public class ModuleExportNode {
   public String packaze;
   public int access;
   public List modules;

   public ModuleExportNode(String var1, int var2, List var3) {
      super();
      this.packaze = var1;
      this.access = var2;
      this.modules = var3;
   }

   public void accept(ModuleVisitor var1) {
      var1.visitExport(this.packaze, this.access, this.modules == null ? null : (String[])this.modules.toArray(new String[0]));
   }
}
