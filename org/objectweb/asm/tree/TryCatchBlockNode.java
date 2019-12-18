package org.objectweb.asm.tree;

import java.util.List;
import org.objectweb.asm.MethodVisitor;

public class TryCatchBlockNode {
   public LabelNode start;
   public LabelNode end;
   public LabelNode handler;
   public String type;
   public List visibleTypeAnnotations;
   public List invisibleTypeAnnotations;

   public TryCatchBlockNode(LabelNode var1, LabelNode var2, LabelNode var3, String var4) {
      super();
      this.start = var1;
      this.end = var2;
      this.handler = var3;
      this.type = var4;
   }

   public void updateIndex(int var1) {
      int var2 = 1107296256 | var1 << 8;
      int var3;
      int var4;
      if (this.visibleTypeAnnotations != null) {
         var3 = 0;

         for(var4 = this.visibleTypeAnnotations.size(); var3 < var4; ++var3) {
            ((TypeAnnotationNode)this.visibleTypeAnnotations.get(var3)).typeRef = var2;
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         var3 = 0;

         for(var4 = this.invisibleTypeAnnotations.size(); var3 < var4; ++var3) {
            ((TypeAnnotationNode)this.invisibleTypeAnnotations.get(var3)).typeRef = var2;
         }
      }

   }

   public void accept(MethodVisitor var1) {
      var1.visitTryCatchBlock(this.start.getLabel(), this.end.getLabel(), this.handler == null ? null : this.handler.getLabel(), this.type);
      int var2;
      int var3;
      TypeAnnotationNode var4;
      if (this.visibleTypeAnnotations != null) {
         var2 = 0;

         for(var3 = this.visibleTypeAnnotations.size(); var2 < var3; ++var2) {
            var4 = (TypeAnnotationNode)this.visibleTypeAnnotations.get(var2);
            var4.accept(var1.visitTryCatchAnnotation(var4.typeRef, var4.typePath, var4.desc, true));
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         var2 = 0;

         for(var3 = this.invisibleTypeAnnotations.size(); var2 < var3; ++var2) {
            var4 = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(var2);
            var4.accept(var1.visitTryCatchAnnotation(var4.typeRef, var4.typePath, var4.desc, false));
         }
      }

   }
}
