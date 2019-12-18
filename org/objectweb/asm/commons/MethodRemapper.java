package org.objectweb.asm.commons;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;

public class MethodRemapper extends MethodVisitor {
   protected final Remapper remapper;

   public MethodRemapper(MethodVisitor var1, Remapper var2) {
      this(458752, var1, var2);
   }

   protected MethodRemapper(int var1, MethodVisitor var2, Remapper var3) {
      super(var1, var2);
      this.remapper = var3;
   }

   public AnnotationVisitor visitAnnotationDefault() {
      AnnotationVisitor var1 = super.visitAnnotationDefault();
      return (AnnotationVisitor)(var1 == null ? var1 : new AnnotationRemapper(this.api, var1, this.remapper));
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationVisitor var3 = super.visitAnnotation(this.remapper.mapDesc(var1), var2);
      return (AnnotationVisitor)(var3 == null ? var3 : new AnnotationRemapper(this.api, var3, this.remapper));
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitTypeAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);
      return (AnnotationVisitor)(var5 == null ? var5 : new AnnotationRemapper(this.api, var5, this.remapper));
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      AnnotationVisitor var4 = super.visitParameterAnnotation(var1, this.remapper.mapDesc(var2), var3);
      return (AnnotationVisitor)(var4 == null ? var4 : new AnnotationRemapper(this.api, var4, this.remapper));
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      super.visitFrame(var1, var2, this.remapFrameTypes(var2, var3), var4, this.remapFrameTypes(var4, var5));
   }

   private Object[] remapFrameTypes(int var1, Object[] var2) {
      if (var2 == null) {
         return var2;
      } else {
         Object[] var3 = null;

         for(int var4 = 0; var4 < var1; ++var4) {
            if (var2[var4] instanceof String) {
               if (var3 == null) {
                  var3 = new Object[var1];
                  System.arraycopy(var2, 0, var3, 0, var1);
               }

               var3[var4] = this.remapper.mapType((String)var2[var4]);
            }
         }

         return var3 == null ? var2 : var3;
      }
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      super.visitFieldInsn(var1, this.remapper.mapType(var2), this.remapper.mapFieldName(var2, var3, var4), this.remapper.mapDesc(var4));
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680 && (var1 & 256) == 0) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         super.visitMethodInsn(var1, this.remapper.mapType(var2), this.remapper.mapMethodName(var2, var3, var4), this.remapper.mapMethodDesc(var4), var5);
      }
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      Object[] var5 = new Object[var4.length];

      for(int var6 = 0; var6 < var4.length; ++var6) {
         var5[var6] = this.remapper.mapValue(var4[var6]);
      }

      super.visitInvokeDynamicInsn(this.remapper.mapInvokeDynamicMethodName(var1, var2), this.remapper.mapMethodDesc(var2), (Handle)this.remapper.mapValue(var3), var5);
   }

   public void visitTypeInsn(int var1, String var2) {
      super.visitTypeInsn(var1, this.remapper.mapType(var2));
   }

   public void visitLdcInsn(Object var1) {
      super.visitLdcInsn(this.remapper.mapValue(var1));
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      super.visitMultiANewArrayInsn(this.remapper.mapDesc(var1), var2);
   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitInsnAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);
      return (AnnotationVisitor)(var5 == null ? var5 : new AnnotationRemapper(this.api, var5, this.remapper));
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      super.visitTryCatchBlock(var1, var2, var3, var4 == null ? null : this.remapper.mapType(var4));
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitTryCatchAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);
      return (AnnotationVisitor)(var5 == null ? var5 : new AnnotationRemapper(this.api, var5, this.remapper));
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      super.visitLocalVariable(var1, this.remapper.mapDesc(var2), this.remapper.mapSignature(var3, true), var4, var5, var6);
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      AnnotationVisitor var8 = super.visitLocalVariableAnnotation(var1, var2, var3, var4, var5, this.remapper.mapDesc(var6), var7);
      return (AnnotationVisitor)(var8 == null ? var8 : new AnnotationRemapper(this.api, var8, this.remapper));
   }
}
