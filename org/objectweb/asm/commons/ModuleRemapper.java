package org.objectweb.asm.commons;

import org.objectweb.asm.ModuleVisitor;

public class ModuleRemapper extends ModuleVisitor {
   protected final Remapper remapper;

   public ModuleRemapper(ModuleVisitor var1, Remapper var2) {
      this(458752, var1, var2);
   }

   protected ModuleRemapper(int var1, ModuleVisitor var2, Remapper var3) {
      super(var1, var2);
      this.remapper = var3;
   }

   public void visitMainClass(String var1) {
      super.visitMainClass(this.remapper.mapType(var1));
   }

   public void visitPackage(String var1) {
      super.visitPackage(this.remapper.mapPackageName(var1));
   }

   public void visitRequire(String var1, int var2, String var3) {
      super.visitRequire(this.remapper.mapModuleName(var1), var2, var3);
   }

   public void visitExport(String var1, int var2, String... var3) {
      String[] var4 = null;
      if (var3 != null) {
         var4 = new String[var3.length];

         for(int var5 = 0; var5 < var3.length; ++var5) {
            var4[var5] = this.remapper.mapModuleName(var3[var5]);
         }
      }

      super.visitExport(this.remapper.mapPackageName(var1), var2, var4);
   }

   public void visitOpen(String var1, int var2, String... var3) {
      String[] var4 = null;
      if (var3 != null) {
         var4 = new String[var3.length];

         for(int var5 = 0; var5 < var3.length; ++var5) {
            var4[var5] = this.remapper.mapModuleName(var3[var5]);
         }
      }

      super.visitOpen(this.remapper.mapPackageName(var1), var2, var4);
   }

   public void visitUse(String var1) {
      super.visitUse(this.remapper.mapType(var1));
   }

   public void visitProvide(String var1, String... var2) {
      String[] var3 = new String[var2.length];

      for(int var4 = 0; var4 < var2.length; ++var4) {
         var3[var4] = this.remapper.mapType(var2[var4]);
      }

      super.visitProvide(this.remapper.mapType(var1), var3);
   }
}
