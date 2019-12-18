package org.objectweb.asm.tree;

import java.util.List;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;

public class LocalVariableAnnotationNode extends TypeAnnotationNode {
   public List start;
   public List end;
   public List index;

   public LocalVariableAnnotationNode(int var1, TypePath var2, LabelNode[] var3, LabelNode[] var4, int[] var5, String var6) {
      this(458752, var1, var2, var3, var4, var5, var6);
   }

   public LocalVariableAnnotationNode(int var1, int var2, TypePath var3, LabelNode[] var4, LabelNode[] var5, int[] var6, String var7) {
      super(var1, var2, var3, var7);
      this.start = Util.asArrayList((Object[])var4);
      this.end = Util.asArrayList((Object[])var5);
      this.index = Util.asArrayList(var6);
   }

   public void accept(MethodVisitor var1, boolean var2) {
      Label[] var3 = new Label[this.start.size()];
      Label[] var4 = new Label[this.end.size()];
      int[] var5 = new int[this.index.size()];
      int var6 = 0;

      for(int var7 = var3.length; var6 < var7; ++var6) {
         var3[var6] = ((LabelNode)this.start.get(var6)).getLabel();
         var4[var6] = ((LabelNode)this.end.get(var6)).getLabel();
         var5[var6] = (Integer)this.index.get(var6);
      }

      this.accept(var1.visitLocalVariableAnnotation(this.typeRef, this.typePath, var3, var4, var5, this.desc, var2));
   }
}
