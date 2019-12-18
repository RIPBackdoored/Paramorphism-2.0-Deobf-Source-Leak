package org.objectweb.asm.tree;

import org.objectweb.asm.TypePath;

public class TypeAnnotationNode extends AnnotationNode {
   public int typeRef;
   public TypePath typePath;

   public TypeAnnotationNode(int var1, TypePath var2, String var3) {
      this(458752, var1, var2, var3);
      if (this.getClass() != TypeAnnotationNode.class) {
         throw new IllegalStateException();
      }
   }

   public TypeAnnotationNode(int var1, int var2, TypePath var3, String var4) {
      super(var1, var4);
      this.typeRef = var2;
      this.typePath = var3;
   }
}
