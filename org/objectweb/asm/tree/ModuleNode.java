package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ModuleVisitor;

public class ModuleNode extends ModuleVisitor {
   public String name;
   public int access;
   public String version;
   public String mainClass;
   public List packages;
   public List requires;
   public List exports;
   public List opens;
   public List uses;
   public List provides;

   public ModuleNode(String var1, int var2, String var3) {
      super(458752);
      if (this.getClass() != ModuleNode.class) {
         throw new IllegalStateException();
      } else {
         this.name = var1;
         this.access = var2;
         this.version = var3;
      }
   }

   public ModuleNode(int var1, String var2, int var3, String var4, List var5, List var6, List var7, List var8, List var9) {
      super(var1);
      this.name = var2;
      this.access = var3;
      this.version = var4;
      this.requires = var5;
      this.exports = var6;
      this.opens = var7;
      this.uses = var8;
      this.provides = var9;
   }

   public void visitMainClass(String var1) {
      this.mainClass = var1;
   }

   public void visitPackage(String var1) {
      if (this.packages == null) {
         this.packages = new ArrayList(5);
      }

      this.packages.add(var1);
   }

   public void visitRequire(String var1, int var2, String var3) {
      if (this.requires == null) {
         this.requires = new ArrayList(5);
      }

      this.requires.add(new ModuleRequireNode(var1, var2, var3));
   }

   public void visitExport(String var1, int var2, String... var3) {
      if (this.exports == null) {
         this.exports = new ArrayList(5);
      }

      this.exports.add(new ModuleExportNode(var1, var2, Util.asArrayList((Object[])var3)));
   }

   public void visitOpen(String var1, int var2, String... var3) {
      if (this.opens == null) {
         this.opens = new ArrayList(5);
      }

      this.opens.add(new ModuleOpenNode(var1, var2, Util.asArrayList((Object[])var3)));
   }

   public void visitUse(String var1) {
      if (this.uses == null) {
         this.uses = new ArrayList(5);
      }

      this.uses.add(var1);
   }

   public void visitProvide(String var1, String... var2) {
      if (this.provides == null) {
         this.provides = new ArrayList(5);
      }

      this.provides.add(new ModuleProvideNode(var1, Util.asArrayList((Object[])var2)));
   }

   public void visitEnd() {
   }

   public void accept(ClassVisitor var1) {
      ModuleVisitor var2 = var1.visitModule(this.name, this.access, this.version);
      if (var2 != null) {
         if (this.mainClass != null) {
            var2.visitMainClass(this.mainClass);
         }

         int var3;
         int var4;
         if (this.packages != null) {
            var3 = 0;

            for(var4 = this.packages.size(); var3 < var4; ++var3) {
               var2.visitPackage((String)this.packages.get(var3));
            }
         }

         if (this.requires != null) {
            var3 = 0;

            for(var4 = this.requires.size(); var3 < var4; ++var3) {
               ((ModuleRequireNode)this.requires.get(var3)).accept(var2);
            }
         }

         if (this.exports != null) {
            var3 = 0;

            for(var4 = this.exports.size(); var3 < var4; ++var3) {
               ((ModuleExportNode)this.exports.get(var3)).accept(var2);
            }
         }

         if (this.opens != null) {
            var3 = 0;

            for(var4 = this.opens.size(); var3 < var4; ++var3) {
               ((ModuleOpenNode)this.opens.get(var3)).accept(var2);
            }
         }

         if (this.uses != null) {
            var3 = 0;

            for(var4 = this.uses.size(); var3 < var4; ++var3) {
               var2.visitUse((String)this.uses.get(var3));
            }
         }

         if (this.provides != null) {
            var3 = 0;

            for(var4 = this.provides.size(); var3 < var4; ++var3) {
               ((ModuleProvideNode)this.provides.get(var3)).accept(var2);
            }
         }

      }
   }
}
