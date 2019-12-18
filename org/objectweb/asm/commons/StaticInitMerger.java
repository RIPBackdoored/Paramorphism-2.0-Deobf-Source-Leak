package org.objectweb.asm.commons;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class StaticInitMerger extends ClassVisitor {
   private String owner;
   private final String renamedClinitMethodPrefix;
   private int numClinitMethods;
   private MethodVisitor mergedClinitVisitor;

   public StaticInitMerger(String var1, ClassVisitor var2) {
      this(458752, var1, var2);
   }

   protected StaticInitMerger(int var1, String var2, ClassVisitor var3) {
      super(var1, var3);
      this.renamedClinitMethodPrefix = var2;
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      super.visit(var1, var2, var3, var4, var5, var6);
      this.owner = var3;
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodVisitor var6;
      if ("<clinit>".equals(var2)) {
         byte var7 = 10;
         String var8 = this.renamedClinitMethodPrefix + this.numClinitMethods++;
         var6 = super.visitMethod(var7, var8, var3, var4, var5);
         if (this.mergedClinitVisitor == null) {
            this.mergedClinitVisitor = super.visitMethod(var7, var2, var3, (String)null, (String[])null);
         }

         this.mergedClinitVisitor.visitMethodInsn(184, this.owner, var8, var3, false);
      } else {
         var6 = super.visitMethod(var1, var2, var3, var4, var5);
      }

      return var6;
   }

   public void visitEnd() {
      if (this.mergedClinitVisitor != null) {
         this.mergedClinitVisitor.visitInsn(177);
         this.mergedClinitVisitor.visitMaxs(0, 0);
      }

      super.visitEnd();
   }
}
