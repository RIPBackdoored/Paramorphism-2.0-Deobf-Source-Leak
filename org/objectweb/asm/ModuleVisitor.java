package org.objectweb.asm;

public abstract class ModuleVisitor {
   protected final int api;
   protected ModuleVisitor mv;

   public ModuleVisitor(int var1) {
      this(var1, (ModuleVisitor)null);
   }

   public ModuleVisitor(int var1, ModuleVisitor var2) {
      super();
      if (var1 != 458752 && var1 != 393216) {
         throw new IllegalArgumentException("Unsupported api " + var1);
      } else {
         this.api = var1;
         this.mv = var2;
      }
   }

   public void visitMainClass(String var1) {
      if (this.mv != null) {
         this.mv.visitMainClass(var1);
      }

   }

   public void visitPackage(String var1) {
      if (this.mv != null) {
         this.mv.visitPackage(var1);
      }

   }

   public void visitRequire(String var1, int var2, String var3) {
      if (this.mv != null) {
         this.mv.visitRequire(var1, var2, var3);
      }

   }

   public void visitExport(String var1, int var2, String... var3) {
      if (this.mv != null) {
         this.mv.visitExport(var1, var2, var3);
      }

   }

   public void visitOpen(String var1, int var2, String... var3) {
      if (this.mv != null) {
         this.mv.visitOpen(var1, var2, var3);
      }

   }

   public void visitUse(String var1) {
      if (this.mv != null) {
         this.mv.visitUse(var1);
      }

   }

   public void visitProvide(String var1, String... var2) {
      if (this.mv != null) {
         this.mv.visitProvide(var1, var2);
      }

   }

   public void visitEnd() {
      if (this.mv != null) {
         this.mv.visitEnd();
      }

   }
}
