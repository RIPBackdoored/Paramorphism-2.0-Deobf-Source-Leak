package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.MethodVisitor;

public abstract class AbstractInsnNode {
   public static final int INSN = 0;
   public static final int INT_INSN = 1;
   public static final int VAR_INSN = 2;
   public static final int TYPE_INSN = 3;
   public static final int FIELD_INSN = 4;
   public static final int METHOD_INSN = 5;
   public static final int INVOKE_DYNAMIC_INSN = 6;
   public static final int JUMP_INSN = 7;
   public static final int LABEL = 8;
   public static final int LDC_INSN = 9;
   public static final int IINC_INSN = 10;
   public static final int TABLESWITCH_INSN = 11;
   public static final int LOOKUPSWITCH_INSN = 12;
   public static final int MULTIANEWARRAY_INSN = 13;
   public static final int FRAME = 14;
   public static final int LINE = 15;
   protected int opcode;
   public List visibleTypeAnnotations;
   public List invisibleTypeAnnotations;
   AbstractInsnNode previousInsn;
   AbstractInsnNode nextInsn;
   int index;

   protected AbstractInsnNode(int var1) {
      super();
      this.opcode = var1;
      this.index = -1;
   }

   public int getOpcode() {
      return this.opcode;
   }

   public abstract int getType();

   public AbstractInsnNode getPrevious() {
      return this.previousInsn;
   }

   public AbstractInsnNode getNext() {
      return this.nextInsn;
   }

   public abstract void accept(MethodVisitor var1);

   protected final void acceptAnnotations(MethodVisitor var1) {
      int var2;
      int var3;
      TypeAnnotationNode var4;
      if (this.visibleTypeAnnotations != null) {
         var2 = 0;

         for(var3 = this.visibleTypeAnnotations.size(); var2 < var3; ++var2) {
            var4 = (TypeAnnotationNode)this.visibleTypeAnnotations.get(var2);
            var4.accept(var1.visitInsnAnnotation(var4.typeRef, var4.typePath, var4.desc, true));
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         var2 = 0;

         for(var3 = this.invisibleTypeAnnotations.size(); var2 < var3; ++var2) {
            var4 = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(var2);
            var4.accept(var1.visitInsnAnnotation(var4.typeRef, var4.typePath, var4.desc, false));
         }
      }

   }

   public abstract AbstractInsnNode clone(Map var1);

   static LabelNode clone(LabelNode var0, Map var1) {
      return (LabelNode)var1.get(var0);
   }

   static LabelNode[] clone(List var0, Map var1) {
      LabelNode[] var2 = new LabelNode[var0.size()];
      int var3 = 0;

      for(int var4 = var2.length; var3 < var4; ++var3) {
         var2[var3] = (LabelNode)var1.get(var0.get(var3));
      }

      return var2;
   }

   protected final AbstractInsnNode cloneAnnotations(AbstractInsnNode var1) {
      int var2;
      int var3;
      TypeAnnotationNode var4;
      TypeAnnotationNode var5;
      if (var1.visibleTypeAnnotations != null) {
         this.visibleTypeAnnotations = new ArrayList();
         var2 = 0;

         for(var3 = var1.visibleTypeAnnotations.size(); var2 < var3; ++var2) {
            var4 = (TypeAnnotationNode)var1.visibleTypeAnnotations.get(var2);
            var5 = new TypeAnnotationNode(var4.typeRef, var4.typePath, var4.desc);
            var4.accept(var5);
            this.visibleTypeAnnotations.add(var5);
         }
      }

      if (var1.invisibleTypeAnnotations != null) {
         this.invisibleTypeAnnotations = new ArrayList();
         var2 = 0;

         for(var3 = var1.invisibleTypeAnnotations.size(); var2 < var3; ++var2) {
            var4 = (TypeAnnotationNode)var1.invisibleTypeAnnotations.get(var2);
            var5 = new TypeAnnotationNode(var4.typeRef, var4.typePath, var4.desc);
            var4.accept(var5);
            this.invisibleTypeAnnotations.add(var5);
         }
      }

      return this;
   }
}
