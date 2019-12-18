package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

public class MethodNode extends MethodVisitor {
   public int access;
   public String name;
   public String desc;
   public String signature;
   public List exceptions;
   public List parameters;
   public List visibleAnnotations;
   public List invisibleAnnotations;
   public List visibleTypeAnnotations;
   public List invisibleTypeAnnotations;
   public List attrs;
   public Object annotationDefault;
   public int visibleAnnotableParameterCount;
   public List[] visibleParameterAnnotations;
   public int invisibleAnnotableParameterCount;
   public List[] invisibleParameterAnnotations;
   public InsnList instructions;
   public List tryCatchBlocks;
   public int maxStack;
   public int maxLocals;
   public List localVariables;
   public List visibleLocalVariableAnnotations;
   public List invisibleLocalVariableAnnotations;
   private boolean visited;

   public MethodNode() {
      this(458752);
      if (this.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int var1) {
      super(var1);
      this.instructions = new InsnList();
   }

   public MethodNode(int var1, String var2, String var3, String var4, String[] var5) {
      this(458752, var1, var2, var3, var4, var5);
      if (this.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      super(var1);
      this.access = var2;
      this.name = var3;
      this.desc = var4;
      this.signature = var5;
      this.exceptions = Util.asArrayList((Object[])var6);
      if ((var2 & 1024) == 0) {
         this.localVariables = new ArrayList(5);
      }

      this.tryCatchBlocks = new ArrayList();
      this.instructions = new InsnList();
   }

   public void visitParameter(String var1, int var2) {
      if (this.parameters == null) {
         this.parameters = new ArrayList(5);
      }

      this.parameters.add(new ParameterNode(var1, var2));
   }

   public AnnotationVisitor visitAnnotationDefault() {
      return new AnnotationNode(new MethodNode$1(this, 0));
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationNode var3 = new AnnotationNode(var1);
      if (var2) {
         if (this.visibleAnnotations == null) {
            this.visibleAnnotations = new ArrayList(1);
         }

         this.visibleAnnotations.add(var3);
      } else {
         if (this.invisibleAnnotations == null) {
            this.invisibleAnnotations = new ArrayList(1);
         }

         this.invisibleAnnotations.add(var3);
      }

      return var3;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      TypeAnnotationNode var5 = new TypeAnnotationNode(var1, var2, var3);
      if (var4) {
         if (this.visibleTypeAnnotations == null) {
            this.visibleTypeAnnotations = new ArrayList(1);
         }

         this.visibleTypeAnnotations.add(var5);
      } else {
         if (this.invisibleTypeAnnotations == null) {
            this.invisibleTypeAnnotations = new ArrayList(1);
         }

         this.invisibleTypeAnnotations.add(var5);
      }

      return var5;
   }

   public void visitAnnotableParameterCount(int var1, boolean var2) {
      if (var2) {
         this.visibleAnnotableParameterCount = var1;
      } else {
         this.invisibleAnnotableParameterCount = var1;
      }

   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      AnnotationNode var4 = new AnnotationNode(var2);
      int var5;
      if (var3) {
         if (this.visibleParameterAnnotations == null) {
            var5 = Type.getArgumentTypes(this.desc).length;
            this.visibleParameterAnnotations = (List[])(new List[var5]);
         }

         if (this.visibleParameterAnnotations[var1] == null) {
            this.visibleParameterAnnotations[var1] = new ArrayList(1);
         }

         this.visibleParameterAnnotations[var1].add(var4);
      } else {
         if (this.invisibleParameterAnnotations == null) {
            var5 = Type.getArgumentTypes(this.desc).length;
            this.invisibleParameterAnnotations = (List[])(new List[var5]);
         }

         if (this.invisibleParameterAnnotations[var1] == null) {
            this.invisibleParameterAnnotations[var1] = new ArrayList(1);
         }

         this.invisibleParameterAnnotations[var1].add(var4);
      }

      return var4;
   }

   public void visitAttribute(Attribute var1) {
      if (this.attrs == null) {
         this.attrs = new ArrayList(1);
      }

      this.attrs.add(var1);
   }

   public void visitCode() {
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      this.instructions.add((AbstractInsnNode)(new FrameNode(var1, var2, var3 == null ? null : this.getLabelNodes(var3), var4, var5 == null ? null : this.getLabelNodes(var5))));
   }

   public void visitInsn(int var1) {
      this.instructions.add((AbstractInsnNode)(new InsnNode(var1)));
   }

   public void visitIntInsn(int var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new IntInsnNode(var1, var2)));
   }

   public void visitVarInsn(int var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new VarInsnNode(var1, var2)));
   }

   public void visitTypeInsn(int var1, String var2) {
      this.instructions.add((AbstractInsnNode)(new TypeInsnNode(var1, var2)));
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.instructions.add((AbstractInsnNode)(new FieldInsnNode(var1, var2, var3, var4)));
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680 && (var1 & 256) == 0) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         int var6 = var1 & -257;
         this.instructions.add((AbstractInsnNode)(new MethodInsnNode(var6, var2, var3, var4, var5)));
      }
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.instructions.add((AbstractInsnNode)(new InvokeDynamicInsnNode(var1, var2, var3, var4)));
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.instructions.add((AbstractInsnNode)(new JumpInsnNode(var1, this.getLabelNode(var2))));
   }

   public void visitLabel(Label var1) {
      this.instructions.add((AbstractInsnNode)this.getLabelNode(var1));
   }

   public void visitLdcInsn(Object var1) {
      this.instructions.add((AbstractInsnNode)(new LdcInsnNode(var1)));
   }

   public void visitIincInsn(int var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new IincInsnNode(var1, var2)));
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.instructions.add((AbstractInsnNode)(new TableSwitchInsnNode(var1, var2, this.getLabelNode(var3), this.getLabelNodes(var4))));
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.instructions.add((AbstractInsnNode)(new LookupSwitchInsnNode(this.getLabelNode(var1), var2, this.getLabelNodes(var3))));
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new MultiANewArrayInsnNode(var1, var2)));
   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AbstractInsnNode var5;
      for(var5 = this.instructions.getLast(); var5.getOpcode() == -1; var5 = var5.getPrevious()) {
      }

      TypeAnnotationNode var6 = new TypeAnnotationNode(var1, var2, var3);
      if (var4) {
         if (var5.visibleTypeAnnotations == null) {
            var5.visibleTypeAnnotations = new ArrayList(1);
         }

         var5.visibleTypeAnnotations.add(var6);
      } else {
         if (var5.invisibleTypeAnnotations == null) {
            var5.invisibleTypeAnnotations = new ArrayList(1);
         }

         var5.invisibleTypeAnnotations.add(var6);
      }

      return var6;
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      if (this.tryCatchBlocks == null) {
         this.tryCatchBlocks = new ArrayList(1);
      }

      this.tryCatchBlocks.add(new TryCatchBlockNode(this.getLabelNode(var1), this.getLabelNode(var2), this.getLabelNode(var3), var4));
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      TryCatchBlockNode var5 = (TryCatchBlockNode)this.tryCatchBlocks.get((var1 & 16776960) >> 8);
      TypeAnnotationNode var6 = new TypeAnnotationNode(var1, var2, var3);
      if (var4) {
         if (var5.visibleTypeAnnotations == null) {
            var5.visibleTypeAnnotations = new ArrayList(1);
         }

         var5.visibleTypeAnnotations.add(var6);
      } else {
         if (var5.invisibleTypeAnnotations == null) {
            var5.invisibleTypeAnnotations = new ArrayList(1);
         }

         var5.invisibleTypeAnnotations.add(var6);
      }

      return var6;
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      if (this.localVariables == null) {
         this.localVariables = new ArrayList(1);
      }

      this.localVariables.add(new LocalVariableNode(var1, var2, var3, this.getLabelNode(var4), this.getLabelNode(var5), var6));
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      LocalVariableAnnotationNode var8 = new LocalVariableAnnotationNode(var1, var2, this.getLabelNodes(var3), this.getLabelNodes(var4), var5, var6);
      if (var7) {
         if (this.visibleLocalVariableAnnotations == null) {
            this.visibleLocalVariableAnnotations = new ArrayList(1);
         }

         this.visibleLocalVariableAnnotations.add(var8);
      } else {
         if (this.invisibleLocalVariableAnnotations == null) {
            this.invisibleLocalVariableAnnotations = new ArrayList(1);
         }

         this.invisibleLocalVariableAnnotations.add(var8);
      }

      return var8;
   }

   public void visitLineNumber(int var1, Label var2) {
      this.instructions.add((AbstractInsnNode)(new LineNumberNode(var1, this.getLabelNode(var2))));
   }

   public void visitMaxs(int var1, int var2) {
      this.maxStack = var1;
      this.maxLocals = var2;
   }

   public void visitEnd() {
   }

   protected LabelNode getLabelNode(Label var1) {
      if (!(var1.info instanceof LabelNode)) {
         var1.info = new LabelNode();
      }

      return (LabelNode)var1.info;
   }

   private LabelNode[] getLabelNodes(Label[] var1) {
      LabelNode[] var2 = new LabelNode[var1.length];
      int var3 = 0;

      for(int var4 = var1.length; var3 < var4; ++var3) {
         var2[var3] = this.getLabelNode(var1[var3]);
      }

      return var2;
   }

   private Object[] getLabelNodes(Object[] var1) {
      Object[] var2 = new Object[var1.length];
      int var3 = 0;

      for(int var4 = var1.length; var3 < var4; ++var3) {
         Object var5 = var1[var3];
         if (var5 instanceof Label) {
            var5 = this.getLabelNode((Label)var5);
         }

         var2[var3] = var5;
      }

      return var2;
   }

   public void check(int var1) {
      int var2;
      AbstractInsnNode var5;
      Object var6;
      if (var1 == 262144) {
         if (this.parameters != null && !this.parameters.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.visibleTypeAnnotations != null && !this.visibleTypeAnnotations.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.invisibleTypeAnnotations != null && !this.invisibleTypeAnnotations.isEmpty()) {
            throw new UnsupportedClassVersionException();
         }

         if (this.tryCatchBlocks != null) {
            for(var2 = this.tryCatchBlocks.size() - 1; var2 >= 0; --var2) {
               TryCatchBlockNode var3 = (TryCatchBlockNode)this.tryCatchBlocks.get(var2);
               if (var3.visibleTypeAnnotations != null && !var3.visibleTypeAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }

               if (var3.invisibleTypeAnnotations != null && !var3.invisibleTypeAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }
            }
         }

         var2 = this.instructions.size() - 1;

         while(true) {
            if (var2 < 0) {
               if (this.visibleLocalVariableAnnotations != null && !this.visibleLocalVariableAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }

               if (this.invisibleLocalVariableAnnotations != null && !this.invisibleLocalVariableAnnotations.isEmpty()) {
                  throw new UnsupportedClassVersionException();
               }
               break;
            }

            var5 = this.instructions.get(var2);
            if (var5.visibleTypeAnnotations != null && !var5.visibleTypeAnnotations.isEmpty()) {
               throw new UnsupportedClassVersionException();
            }

            if (var5.invisibleTypeAnnotations != null && !var5.invisibleTypeAnnotations.isEmpty()) {
               throw new UnsupportedClassVersionException();
            }

            if (var5 instanceof MethodInsnNode) {
               boolean var4 = ((MethodInsnNode)var5).itf;
               if (var4 != (var5.opcode == 185)) {
                  throw new UnsupportedClassVersionException();
               }
            } else if (var5 instanceof LdcInsnNode) {
               var6 = ((LdcInsnNode)var5).cst;
               if (var6 instanceof Handle || var6 instanceof Type && ((Type)var6).getSort() == 11) {
                  throw new UnsupportedClassVersionException();
               }
            }

            --var2;
         }
      }

      if (var1 != 458752) {
         for(var2 = this.instructions.size() - 1; var2 >= 0; --var2) {
            var5 = this.instructions.get(var2);
            if (var5 instanceof LdcInsnNode) {
               var6 = ((LdcInsnNode)var5).cst;
               if (var6 instanceof ConstantDynamic) {
                  throw new UnsupportedClassVersionException();
               }
            }
         }
      }

   }

   public void accept(ClassVisitor var1) {
      String[] var2 = this.exceptions == null ? null : (String[])this.exceptions.toArray(new String[0]);
      MethodVisitor var3 = var1.visitMethod(this.access, this.name, this.desc, this.signature, var2);
      if (var3 != null) {
         this.accept(var3);
      }

   }

   public void accept(MethodVisitor var1) {
      int var2;
      int var3;
      if (this.parameters != null) {
         var2 = 0;

         for(var3 = this.parameters.size(); var2 < var3; ++var2) {
            ((ParameterNode)this.parameters.get(var2)).accept(var1);
         }
      }

      if (this.annotationDefault != null) {
         AnnotationVisitor var8 = var1.visitAnnotationDefault();
         AnnotationNode.accept(var8, (String)null, this.annotationDefault);
         if (var8 != null) {
            var8.visitEnd();
         }
      }

      AnnotationNode var4;
      if (this.visibleAnnotations != null) {
         var2 = 0;

         for(var3 = this.visibleAnnotations.size(); var2 < var3; ++var2) {
            var4 = (AnnotationNode)this.visibleAnnotations.get(var2);
            var4.accept(var1.visitAnnotation(var4.desc, true));
         }
      }

      if (this.invisibleAnnotations != null) {
         var2 = 0;

         for(var3 = this.invisibleAnnotations.size(); var2 < var3; ++var2) {
            var4 = (AnnotationNode)this.invisibleAnnotations.get(var2);
            var4.accept(var1.visitAnnotation(var4.desc, false));
         }
      }

      TypeAnnotationNode var9;
      if (this.visibleTypeAnnotations != null) {
         var2 = 0;

         for(var3 = this.visibleTypeAnnotations.size(); var2 < var3; ++var2) {
            var9 = (TypeAnnotationNode)this.visibleTypeAnnotations.get(var2);
            var9.accept(var1.visitTypeAnnotation(var9.typeRef, var9.typePath, var9.desc, true));
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         var2 = 0;

         for(var3 = this.invisibleTypeAnnotations.size(); var2 < var3; ++var2) {
            var9 = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(var2);
            var9.accept(var1.visitTypeAnnotation(var9.typeRef, var9.typePath, var9.desc, false));
         }
      }

      if (this.visibleAnnotableParameterCount > 0) {
         var1.visitAnnotableParameterCount(this.visibleAnnotableParameterCount, true);
      }

      int var5;
      int var6;
      AnnotationNode var7;
      List var10;
      if (this.visibleParameterAnnotations != null) {
         var2 = 0;

         for(var3 = this.visibleParameterAnnotations.length; var2 < var3; ++var2) {
            var10 = this.visibleParameterAnnotations[var2];
            if (var10 != null) {
               var5 = 0;

               for(var6 = var10.size(); var5 < var6; ++var5) {
                  var7 = (AnnotationNode)var10.get(var5);
                  var7.accept(var1.visitParameterAnnotation(var2, var7.desc, true));
               }
            }
         }
      }

      if (this.invisibleAnnotableParameterCount > 0) {
         var1.visitAnnotableParameterCount(this.invisibleAnnotableParameterCount, false);
      }

      if (this.invisibleParameterAnnotations != null) {
         var2 = 0;

         for(var3 = this.invisibleParameterAnnotations.length; var2 < var3; ++var2) {
            var10 = this.invisibleParameterAnnotations[var2];
            if (var10 != null) {
               var5 = 0;

               for(var6 = var10.size(); var5 < var6; ++var5) {
                  var7 = (AnnotationNode)var10.get(var5);
                  var7.accept(var1.visitParameterAnnotation(var2, var7.desc, false));
               }
            }
         }
      }

      if (this.visited) {
         this.instructions.resetLabels();
      }

      if (this.attrs != null) {
         var2 = 0;

         for(var3 = this.attrs.size(); var2 < var3; ++var2) {
            var1.visitAttribute((Attribute)this.attrs.get(var2));
         }
      }

      if (this.instructions.size() > 0) {
         var1.visitCode();
         if (this.tryCatchBlocks != null) {
            var2 = 0;

            for(var3 = this.tryCatchBlocks.size(); var2 < var3; ++var2) {
               ((TryCatchBlockNode)this.tryCatchBlocks.get(var2)).updateIndex(var2);
               ((TryCatchBlockNode)this.tryCatchBlocks.get(var2)).accept(var1);
            }
         }

         this.instructions.accept(var1);
         if (this.localVariables != null) {
            var2 = 0;

            for(var3 = this.localVariables.size(); var2 < var3; ++var2) {
               ((LocalVariableNode)this.localVariables.get(var2)).accept(var1);
            }
         }

         if (this.visibleLocalVariableAnnotations != null) {
            var2 = 0;

            for(var3 = this.visibleLocalVariableAnnotations.size(); var2 < var3; ++var2) {
               ((LocalVariableAnnotationNode)this.visibleLocalVariableAnnotations.get(var2)).accept(var1, true);
            }
         }

         if (this.invisibleLocalVariableAnnotations != null) {
            var2 = 0;

            for(var3 = this.invisibleLocalVariableAnnotations.size(); var2 < var3; ++var2) {
               ((LocalVariableAnnotationNode)this.invisibleLocalVariableAnnotations.get(var2)).accept(var1, false);
            }
         }

         var1.visitMaxs(this.maxStack, this.maxLocals);
         this.visited = true;
      }

      var1.visitEnd();
   }
}
