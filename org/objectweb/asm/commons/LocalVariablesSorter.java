package org.objectweb.asm.commons;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

public class LocalVariablesSorter extends MethodVisitor {
   private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
   private int[] remappedVariableIndices;
   private Object[] remappedLocalTypes;
   protected final int firstLocal;
   protected int nextLocal;

   public LocalVariablesSorter(int var1, String var2, MethodVisitor var3) {
      this(458752, var1, var2, var3);
      if (this.getClass() != LocalVariablesSorter.class) {
         throw new IllegalStateException();
      }
   }

   protected LocalVariablesSorter(int var1, int var2, String var3, MethodVisitor var4) {
      super(var1, var4);
      this.remappedVariableIndices = new int[40];
      this.remappedLocalTypes = new Object[20];
      this.nextLocal = (8 & var2) == 0 ? 1 : 0;
      Type[] var5 = Type.getArgumentTypes(var3);
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Type var8 = var5[var7];
         this.nextLocal += var8.getSize();
      }

      this.firstLocal = this.nextLocal;
   }

   public void visitVarInsn(int var1, int var2) {
      Type var3;
      switch(var1) {
      case 21:
      case 54:
         var3 = Type.INT_TYPE;
         break;
      case 22:
      case 55:
         var3 = Type.LONG_TYPE;
         break;
      case 23:
      case 56:
         var3 = Type.FLOAT_TYPE;
         break;
      case 24:
      case 57:
         var3 = Type.DOUBLE_TYPE;
         break;
      case 25:
      case 58:
      case 169:
         var3 = OBJECT_TYPE;
         break;
      default:
         throw new IllegalArgumentException("Invalid opcode " + var1);
      }

      super.visitVarInsn(var1, this.remap(var2, var3));
   }

   public void visitIincInsn(int var1, int var2) {
      super.visitIincInsn(this.remap(var1, Type.INT_TYPE), var2);
   }

   public void visitMaxs(int var1, int var2) {
      super.visitMaxs(var1, this.nextLocal);
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      int var7 = this.remap(var6, Type.getType(var2));
      super.visitLocalVariable(var1, var2, var3, var4, var5, var7);
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      Type var8 = Type.getType(var6);
      int[] var9 = new int[var5.length];

      for(int var10 = 0; var10 < var9.length; ++var10) {
         var9[var10] = this.remap(var5[var10], var8);
      }

      return super.visitLocalVariableAnnotation(var1, var2, var3, var4, var9, var6, var7);
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      if (var1 != -1) {
         throw new IllegalArgumentException("LocalVariablesSorter only accepts expanded frames (see ClassReader.EXPAND_FRAMES)");
      } else {
         Object[] var6 = new Object[this.remappedLocalTypes.length];
         System.arraycopy(this.remappedLocalTypes, 0, var6, 0, var6.length);
         this.updateNewLocals(this.remappedLocalTypes);
         int var7 = 0;

         int var8;
         for(var8 = 0; var8 < var2; ++var8) {
            Object var9 = var3[var8];
            if (var9 != Opcodes.TOP) {
               Type var10 = OBJECT_TYPE;
               if (var9 == Opcodes.INTEGER) {
                  var10 = Type.INT_TYPE;
               } else if (var9 == Opcodes.FLOAT) {
                  var10 = Type.FLOAT_TYPE;
               } else if (var9 == Opcodes.LONG) {
                  var10 = Type.LONG_TYPE;
               } else if (var9 == Opcodes.DOUBLE) {
                  var10 = Type.DOUBLE_TYPE;
               } else if (var9 instanceof String) {
                  var10 = Type.getObjectType((String)var9);
               }

               this.setFrameLocal(this.remap(var7, var10), var9);
            }

            var7 += var9 != Opcodes.LONG && var9 != Opcodes.DOUBLE ? 1 : 2;
         }

         var7 = 0;
         var8 = 0;
         int var11 = 0;

         while(true) {
            while(var7 < this.remappedLocalTypes.length) {
               Object var12 = this.remappedLocalTypes[var7];
               var7 += var12 != Opcodes.LONG && var12 != Opcodes.DOUBLE ? 1 : 2;
               if (var12 != null && var12 != Opcodes.TOP) {
                  this.remappedLocalTypes[var8++] = var12;
                  var11 = var8;
               } else {
                  this.remappedLocalTypes[var8++] = Opcodes.TOP;
               }
            }

            super.visitFrame(var1, var11, this.remappedLocalTypes, var4, var5);
            this.remappedLocalTypes = var6;
            return;
         }
      }
   }

   public int newLocal(Type var1) {
      Object var2;
      switch(var1.getSort()) {
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
         var2 = Opcodes.INTEGER;
         break;
      case 6:
         var2 = Opcodes.FLOAT;
         break;
      case 7:
         var2 = Opcodes.LONG;
         break;
      case 8:
         var2 = Opcodes.DOUBLE;
         break;
      case 9:
         var2 = var1.getDescriptor();
         break;
      case 10:
         var2 = var1.getInternalName();
         break;
      default:
         throw new AssertionError();
      }

      int var3 = this.newLocalMapping(var1);
      this.setLocalType(var3, var1);
      this.setFrameLocal(var3, var2);
      return var3;
   }

   protected void updateNewLocals(Object[] var1) {
   }

   protected void setLocalType(int var1, Type var2) {
   }

   private void setFrameLocal(int var1, Object var2) {
      int var3 = this.remappedLocalTypes.length;
      if (var1 >= var3) {
         Object[] var4 = new Object[Math.max(2 * var3, var1 + 1)];
         System.arraycopy(this.remappedLocalTypes, 0, var4, 0, var3);
         this.remappedLocalTypes = var4;
      }

      this.remappedLocalTypes[var1] = var2;
   }

   private int remap(int var1, Type var2) {
      if (var1 + var2.getSize() <= this.firstLocal) {
         return var1;
      } else {
         int var3 = 2 * var1 + var2.getSize() - 1;
         int var4 = this.remappedVariableIndices.length;
         if (var3 >= var4) {
            int[] var5 = new int[Math.max(2 * var4, var3 + 1)];
            System.arraycopy(this.remappedVariableIndices, 0, var5, 0, var4);
            this.remappedVariableIndices = var5;
         }

         int var6 = this.remappedVariableIndices[var3];
         if (var6 == 0) {
            var6 = this.newLocalMapping(var2);
            this.setLocalType(var6, var2);
            this.remappedVariableIndices[var3] = var6 + 1;
         } else {
            --var6;
         }

         return var6;
      }
   }

   protected int newLocalMapping(Type var1) {
      int var2 = this.nextLocal;
      this.nextLocal += var1.getSize();
      return var2;
   }
}
