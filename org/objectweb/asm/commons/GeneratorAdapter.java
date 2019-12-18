package org.objectweb.asm.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class GeneratorAdapter extends LocalVariablesSorter {
   private static final String CLASS_DESCRIPTOR = "Ljava/lang/Class;";
   private static final Type BYTE_TYPE = Type.getObjectType("java/lang/Byte");
   private static final Type BOOLEAN_TYPE = Type.getObjectType("java/lang/Boolean");
   private static final Type SHORT_TYPE = Type.getObjectType("java/lang/Short");
   private static final Type CHARACTER_TYPE = Type.getObjectType("java/lang/Character");
   private static final Type INTEGER_TYPE = Type.getObjectType("java/lang/Integer");
   private static final Type FLOAT_TYPE = Type.getObjectType("java/lang/Float");
   private static final Type LONG_TYPE = Type.getObjectType("java/lang/Long");
   private static final Type DOUBLE_TYPE = Type.getObjectType("java/lang/Double");
   private static final Type NUMBER_TYPE = Type.getObjectType("java/lang/Number");
   private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
   private static final Method BOOLEAN_VALUE = Method.getMethod("boolean booleanValue()");
   private static final Method CHAR_VALUE = Method.getMethod("char charValue()");
   private static final Method INT_VALUE = Method.getMethod("int intValue()");
   private static final Method FLOAT_VALUE = Method.getMethod("float floatValue()");
   private static final Method LONG_VALUE = Method.getMethod("long longValue()");
   private static final Method DOUBLE_VALUE = Method.getMethod("double doubleValue()");
   public static final int ADD = 96;
   public static final int SUB = 100;
   public static final int MUL = 104;
   public static final int DIV = 108;
   public static final int REM = 112;
   public static final int NEG = 116;
   public static final int SHL = 120;
   public static final int SHR = 122;
   public static final int USHR = 124;
   public static final int AND = 126;
   public static final int OR = 128;
   public static final int XOR = 130;
   public static final int EQ = 153;
   public static final int NE = 154;
   public static final int LT = 155;
   public static final int GE = 156;
   public static final int GT = 157;
   public static final int LE = 158;
   private final int access;
   private final String name;
   private final Type returnType;
   private final Type[] argumentTypes;
   private final List localTypes;

   public GeneratorAdapter(MethodVisitor var1, int var2, String var3, String var4) {
      this(458752, var1, var2, var3, var4);
      if (this.getClass() != GeneratorAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected GeneratorAdapter(int var1, MethodVisitor var2, int var3, String var4, String var5) {
      super(var1, var3, var5, var2);
      this.localTypes = new ArrayList();
      this.access = var3;
      this.name = var4;
      this.returnType = Type.getReturnType(var5);
      this.argumentTypes = Type.getArgumentTypes(var5);
   }

   public GeneratorAdapter(int var1, Method var2, MethodVisitor var3) {
      this(var3, var1, var2.getName(), var2.getDescriptor());
   }

   public GeneratorAdapter(int var1, Method var2, String var3, Type[] var4, ClassVisitor var5) {
      this(var1, var2, var5.visitMethod(var1, var2.getName(), var2.getDescriptor(), var3, var4 == null ? null : getInternalNames(var4)));
   }

   private static String[] getInternalNames(Type[] var0) {
      String[] var1 = new String[var0.length];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var1[var2] = var0[var2].getInternalName();
      }

      return var1;
   }

   public int getAccess() {
      return this.access;
   }

   public String getName() {
      return this.name;
   }

   public Type getReturnType() {
      return this.returnType;
   }

   public Type[] getArgumentTypes() {
      return (Type[])this.argumentTypes.clone();
   }

   public void push(boolean var1) {
      this.push(var1 ? 1 : 0);
   }

   public void push(int var1) {
      if (var1 >= -1 && var1 <= 5) {
         this.mv.visitInsn(3 + var1);
      } else if (var1 >= -128 && var1 <= 127) {
         this.mv.visitIntInsn(16, var1);
      } else if (var1 >= -32768 && var1 <= 32767) {
         this.mv.visitIntInsn(17, var1);
      } else {
         this.mv.visitLdcInsn(var1);
      }

   }

   public void push(long var1) {
      if (var1 != 0L && var1 != 1L) {
         this.mv.visitLdcInsn(var1);
      } else {
         this.mv.visitInsn(9 + (int)var1);
      }

   }

   public void push(float var1) {
      int var2 = Float.floatToIntBits(var1);
      if ((long)var2 != 0L && var2 != 1065353216 && var2 != 1073741824) {
         this.mv.visitLdcInsn(var1);
      } else {
         this.mv.visitInsn(11 + (int)var1);
      }

   }

   public void push(double var1) {
      long var3 = Double.doubleToLongBits(var1);
      if (var3 != 0L && var3 != 4607182418800017408L) {
         this.mv.visitLdcInsn(var1);
      } else {
         this.mv.visitInsn(14 + (int)var1);
      }

   }

   public void push(String var1) {
      if (var1 == null) {
         this.mv.visitInsn(1);
      } else {
         this.mv.visitLdcInsn(var1);
      }

   }

   public void push(Type var1) {
      if (var1 == null) {
         this.mv.visitInsn(1);
      } else {
         switch(var1.getSort()) {
         case 1:
            this.mv.visitFieldInsn(178, "java/lang/Boolean", "TYPE", "Ljava/lang/Class;");
            break;
         case 2:
            this.mv.visitFieldInsn(178, "java/lang/Character", "TYPE", "Ljava/lang/Class;");
            break;
         case 3:
            this.mv.visitFieldInsn(178, "java/lang/Byte", "TYPE", "Ljava/lang/Class;");
            break;
         case 4:
            this.mv.visitFieldInsn(178, "java/lang/Short", "TYPE", "Ljava/lang/Class;");
            break;
         case 5:
            this.mv.visitFieldInsn(178, "java/lang/Integer", "TYPE", "Ljava/lang/Class;");
            break;
         case 6:
            this.mv.visitFieldInsn(178, "java/lang/Float", "TYPE", "Ljava/lang/Class;");
            break;
         case 7:
            this.mv.visitFieldInsn(178, "java/lang/Long", "TYPE", "Ljava/lang/Class;");
            break;
         case 8:
            this.mv.visitFieldInsn(178, "java/lang/Double", "TYPE", "Ljava/lang/Class;");
            break;
         default:
            this.mv.visitLdcInsn(var1);
         }
      }

   }

   public void push(Handle var1) {
      if (var1 == null) {
         this.mv.visitInsn(1);
      } else {
         this.mv.visitLdcInsn(var1);
      }

   }

   public void push(ConstantDynamic var1) {
      if (var1 == null) {
         this.mv.visitInsn(1);
      } else {
         this.mv.visitLdcInsn(var1);
      }

   }

   private int getArgIndex(int var1) {
      int var2 = (this.access & 8) == 0 ? 1 : 0;

      for(int var3 = 0; var3 < var1; ++var3) {
         var2 += this.argumentTypes[var3].getSize();
      }

      return var2;
   }

   private void loadInsn(Type var1, int var2) {
      this.mv.visitVarInsn(var1.getOpcode(21), var2);
   }

   private void storeInsn(Type var1, int var2) {
      this.mv.visitVarInsn(var1.getOpcode(54), var2);
   }

   public void loadThis() {
      if ((this.access & 8) != 0) {
         throw new IllegalStateException("no 'this' pointer within static method");
      } else {
         this.mv.visitVarInsn(25, 0);
      }
   }

   public void loadArg(int var1) {
      this.loadInsn(this.argumentTypes[var1], this.getArgIndex(var1));
   }

   public void loadArgs(int var1, int var2) {
      int var3 = this.getArgIndex(var1);

      for(int var4 = 0; var4 < var2; ++var4) {
         Type var5 = this.argumentTypes[var1 + var4];
         this.loadInsn(var5, var3);
         var3 += var5.getSize();
      }

   }

   public void loadArgs() {
      this.loadArgs(0, this.argumentTypes.length);
   }

   public void loadArgArray() {
      this.push(this.argumentTypes.length);
      this.newArray(OBJECT_TYPE);

      for(int var1 = 0; var1 < this.argumentTypes.length; ++var1) {
         this.dup();
         this.push(var1);
         this.loadArg(var1);
         this.box(this.argumentTypes[var1]);
         this.arrayStore(OBJECT_TYPE);
      }

   }

   public void storeArg(int var1) {
      this.storeInsn(this.argumentTypes[var1], this.getArgIndex(var1));
   }

   public Type getLocalType(int var1) {
      return (Type)this.localTypes.get(var1 - this.firstLocal);
   }

   protected void setLocalType(int var1, Type var2) {
      int var3 = var1 - this.firstLocal;

      while(this.localTypes.size() < var3 + 1) {
         this.localTypes.add((Object)null);
      }

      this.localTypes.set(var3, var2);
   }

   public void loadLocal(int var1) {
      this.loadInsn(this.getLocalType(var1), var1);
   }

   public void loadLocal(int var1, Type var2) {
      this.setLocalType(var1, var2);
      this.loadInsn(var2, var1);
   }

   public void storeLocal(int var1) {
      this.storeInsn(this.getLocalType(var1), var1);
   }

   public void storeLocal(int var1, Type var2) {
      this.setLocalType(var1, var2);
      this.storeInsn(var2, var1);
   }

   public void arrayLoad(Type var1) {
      this.mv.visitInsn(var1.getOpcode(46));
   }

   public void arrayStore(Type var1) {
      this.mv.visitInsn(var1.getOpcode(79));
   }

   public void pop() {
      this.mv.visitInsn(87);
   }

   public void pop2() {
      this.mv.visitInsn(88);
   }

   public void dup() {
      this.mv.visitInsn(89);
   }

   public void dup2() {
      this.mv.visitInsn(92);
   }

   public void dupX1() {
      this.mv.visitInsn(90);
   }

   public void dupX2() {
      this.mv.visitInsn(91);
   }

   public void dup2X1() {
      this.mv.visitInsn(93);
   }

   public void dup2X2() {
      this.mv.visitInsn(94);
   }

   public void swap() {
      this.mv.visitInsn(95);
   }

   public void swap(Type var1, Type var2) {
      if (var2.getSize() == 1) {
         if (var1.getSize() == 1) {
            this.swap();
         } else {
            this.dupX2();
            this.pop();
         }
      } else if (var1.getSize() == 1) {
         this.dup2X1();
         this.pop2();
      } else {
         this.dup2X2();
         this.pop2();
      }

   }

   public void math(int var1, Type var2) {
      this.mv.visitInsn(var2.getOpcode(var1));
   }

   public void not() {
      this.mv.visitInsn(4);
      this.mv.visitInsn(130);
   }

   public void iinc(int var1, int var2) {
      this.mv.visitIincInsn(var1, var2);
   }

   public void cast(Type var1, Type var2) {
      if (var1 != var2) {
         if (var1.getSort() < 1 || var1.getSort() > 8 || var2.getSort() < 1 || var2.getSort() > 8) {
            throw new IllegalArgumentException("Cannot cast from " + var1 + " to " + var2);
         }

         if (var1 == Type.DOUBLE_TYPE) {
            if (var2 == Type.FLOAT_TYPE) {
               this.mv.visitInsn(144);
            } else if (var2 == Type.LONG_TYPE) {
               this.mv.visitInsn(143);
            } else {
               this.mv.visitInsn(142);
               this.cast(Type.INT_TYPE, var2);
            }
         } else if (var1 == Type.FLOAT_TYPE) {
            if (var2 == Type.DOUBLE_TYPE) {
               this.mv.visitInsn(141);
            } else if (var2 == Type.LONG_TYPE) {
               this.mv.visitInsn(140);
            } else {
               this.mv.visitInsn(139);
               this.cast(Type.INT_TYPE, var2);
            }
         } else if (var1 == Type.LONG_TYPE) {
            if (var2 == Type.DOUBLE_TYPE) {
               this.mv.visitInsn(138);
            } else if (var2 == Type.FLOAT_TYPE) {
               this.mv.visitInsn(137);
            } else {
               this.mv.visitInsn(136);
               this.cast(Type.INT_TYPE, var2);
            }
         } else if (var2 == Type.BYTE_TYPE) {
            this.mv.visitInsn(145);
         } else if (var2 == Type.CHAR_TYPE) {
            this.mv.visitInsn(146);
         } else if (var2 == Type.DOUBLE_TYPE) {
            this.mv.visitInsn(135);
         } else if (var2 == Type.FLOAT_TYPE) {
            this.mv.visitInsn(134);
         } else if (var2 == Type.LONG_TYPE) {
            this.mv.visitInsn(133);
         } else if (var2 == Type.SHORT_TYPE) {
            this.mv.visitInsn(147);
         }
      }

   }

   private static Type getBoxedType(Type var0) {
      switch(var0.getSort()) {
      case 1:
         return BOOLEAN_TYPE;
      case 2:
         return CHARACTER_TYPE;
      case 3:
         return BYTE_TYPE;
      case 4:
         return SHORT_TYPE;
      case 5:
         return INTEGER_TYPE;
      case 6:
         return FLOAT_TYPE;
      case 7:
         return LONG_TYPE;
      case 8:
         return DOUBLE_TYPE;
      default:
         return var0;
      }
   }

   public void box(Type var1) {
      if (var1.getSort() != 10 && var1.getSort() != 9) {
         if (var1 == Type.VOID_TYPE) {
            this.push((String)null);
         } else {
            Type var2 = getBoxedType(var1);
            this.newInstance(var2);
            if (var1.getSize() == 2) {
               this.dupX2();
               this.dupX2();
               this.pop();
            } else {
               this.dupX1();
               this.swap();
            }

            this.invokeConstructor(var2, new Method("<init>", Type.VOID_TYPE, new Type[]{var1}));
         }

      }
   }

   public void valueOf(Type var1) {
      if (var1.getSort() != 10 && var1.getSort() != 9) {
         if (var1 == Type.VOID_TYPE) {
            this.push((String)null);
         } else {
            Type var2 = getBoxedType(var1);
            this.invokeStatic(var2, new Method("valueOf", var2, new Type[]{var1}));
         }

      }
   }

   public void unbox(Type var1) {
      Type var2 = NUMBER_TYPE;
      Method var3;
      switch(var1.getSort()) {
      case 0:
         return;
      case 1:
         var2 = BOOLEAN_TYPE;
         var3 = BOOLEAN_VALUE;
         break;
      case 2:
         var2 = CHARACTER_TYPE;
         var3 = CHAR_VALUE;
         break;
      case 3:
      case 4:
      case 5:
         var3 = INT_VALUE;
         break;
      case 6:
         var3 = FLOAT_VALUE;
         break;
      case 7:
         var3 = LONG_VALUE;
         break;
      case 8:
         var3 = DOUBLE_VALUE;
         break;
      default:
         var3 = null;
      }

      if (var3 == null) {
         this.checkCast(var1);
      } else {
         this.checkCast(var2);
         this.invokeVirtual(var2, var3);
      }

   }

   public Label newLabel() {
      return new Label();
   }

   public void mark(Label var1) {
      this.mv.visitLabel(var1);
   }

   public Label mark() {
      Label var1 = new Label();
      this.mv.visitLabel(var1);
      return var1;
   }

   public void ifCmp(Type var1, int var2, Label var3) {
      switch(var1.getSort()) {
      case 6:
         this.mv.visitInsn(var2 != 156 && var2 != 157 ? 150 : 149);
         break;
      case 7:
         this.mv.visitInsn(148);
         break;
      case 8:
         this.mv.visitInsn(var2 != 156 && var2 != 157 ? 152 : 151);
         break;
      case 9:
      case 10:
         if (var2 == 153) {
            this.mv.visitJumpInsn(165, var3);
            return;
         }

         if (var2 == 154) {
            this.mv.visitJumpInsn(166, var3);
            return;
         }

         throw new IllegalArgumentException("Bad comparison for type " + var1);
      default:
         boolean var4 = true;
         short var5;
         switch(var2) {
         case 153:
            var5 = 159;
            break;
         case 154:
            var5 = 160;
            break;
         case 155:
            var5 = 161;
            break;
         case 156:
            var5 = 162;
            break;
         case 157:
            var5 = 163;
            break;
         case 158:
            var5 = 164;
            break;
         default:
            throw new IllegalArgumentException("Bad comparison mode " + var2);
         }

         this.mv.visitJumpInsn(var5, var3);
         return;
      }

      this.mv.visitJumpInsn(var2, var3);
   }

   public void ifICmp(int var1, Label var2) {
      this.ifCmp(Type.INT_TYPE, var1, var2);
   }

   public void ifZCmp(int var1, Label var2) {
      this.mv.visitJumpInsn(var1, var2);
   }

   public void ifNull(Label var1) {
      this.mv.visitJumpInsn(198, var1);
   }

   public void ifNonNull(Label var1) {
      this.mv.visitJumpInsn(199, var1);
   }

   public void goTo(Label var1) {
      this.mv.visitJumpInsn(167, var1);
   }

   public void ret(int var1) {
      this.mv.visitVarInsn(169, var1);
   }

   public void tableSwitch(int[] var1, TableSwitchGenerator var2) {
      float var3;
      if (var1.length == 0) {
         var3 = 0.0F;
      } else {
         var3 = (float)var1.length / (float)(var1[var1.length - 1] - var1[0] + 1);
      }

      this.tableSwitch(var1, var2, var3 >= 0.5F);
   }

   public void tableSwitch(int[] var1, TableSwitchGenerator var2, boolean var3) {
      for(int var4 = 1; var4 < var1.length; ++var4) {
         if (var1[var4] < var1[var4 - 1]) {
            throw new IllegalArgumentException("keys must be sorted in ascending order");
         }
      }

      Label var13 = this.newLabel();
      Label var5 = this.newLabel();
      if (var1.length > 0) {
         int var6 = var1.length;
         int var8;
         if (var3) {
            int var7 = var1[0];
            var8 = var1[var6 - 1];
            int var9 = var8 - var7 + 1;
            Label[] var10 = new Label[var9];
            Arrays.fill(var10, var13);

            int var11;
            for(var11 = 0; var11 < var6; ++var11) {
               var10[var1[var11] - var7] = this.newLabel();
            }

            this.mv.visitTableSwitchInsn(var7, var8, var13, var10);

            for(var11 = 0; var11 < var9; ++var11) {
               Label var12 = var10[var11];
               if (var12 != var13) {
                  this.mark(var12);
                  var2.generateCase(var11 + var7, var5);
               }
            }
         } else {
            Label[] var14 = new Label[var6];

            for(var8 = 0; var8 < var6; ++var8) {
               var14[var8] = this.newLabel();
            }

            this.mv.visitLookupSwitchInsn(var13, var1, var14);

            for(var8 = 0; var8 < var6; ++var8) {
               this.mark(var14[var8]);
               var2.generateCase(var1[var8], var5);
            }
         }
      }

      this.mark(var13);
      var2.generateDefault();
      this.mark(var5);
   }

   public void returnValue() {
      this.mv.visitInsn(this.returnType.getOpcode(172));
   }

   private void fieldInsn(int var1, Type var2, String var3, Type var4) {
      this.mv.visitFieldInsn(var1, var2.getInternalName(), var3, var4.getDescriptor());
   }

   public void getStatic(Type var1, String var2, Type var3) {
      this.fieldInsn(178, var1, var2, var3);
   }

   public void putStatic(Type var1, String var2, Type var3) {
      this.fieldInsn(179, var1, var2, var3);
   }

   public void getField(Type var1, String var2, Type var3) {
      this.fieldInsn(180, var1, var2, var3);
   }

   public void putField(Type var1, String var2, Type var3) {
      this.fieldInsn(181, var1, var2, var3);
   }

   private void invokeInsn(int var1, Type var2, Method var3, boolean var4) {
      String var5 = var2.getSort() == 9 ? var2.getDescriptor() : var2.getInternalName();
      this.mv.visitMethodInsn(var1, var5, var3.getName(), var3.getDescriptor(), var4);
   }

   public void invokeVirtual(Type var1, Method var2) {
      this.invokeInsn(182, var1, var2, false);
   }

   public void invokeConstructor(Type var1, Method var2) {
      this.invokeInsn(183, var1, var2, false);
   }

   public void invokeStatic(Type var1, Method var2) {
      this.invokeInsn(184, var1, var2, false);
   }

   public void invokeInterface(Type var1, Method var2) {
      this.invokeInsn(185, var1, var2, true);
   }

   public void invokeDynamic(String var1, String var2, Handle var3, Object... var4) {
      this.mv.visitInvokeDynamicInsn(var1, var2, var3, var4);
   }

   private void typeInsn(int var1, Type var2) {
      this.mv.visitTypeInsn(var1, var2.getInternalName());
   }

   public void newInstance(Type var1) {
      this.typeInsn(187, var1);
   }

   public void newArray(Type var1) {
      byte var2;
      switch(var1.getSort()) {
      case 1:
         var2 = 4;
         break;
      case 2:
         var2 = 5;
         break;
      case 3:
         var2 = 8;
         break;
      case 4:
         var2 = 9;
         break;
      case 5:
         var2 = 10;
         break;
      case 6:
         var2 = 6;
         break;
      case 7:
         var2 = 11;
         break;
      case 8:
         var2 = 7;
         break;
      default:
         this.typeInsn(189, var1);
         return;
      }

      this.mv.visitIntInsn(188, var2);
   }

   public void arrayLength() {
      this.mv.visitInsn(190);
   }

   public void throwException() {
      this.mv.visitInsn(191);
   }

   public void throwException(Type var1, String var2) {
      this.newInstance(var1);
      this.dup();
      this.push(var2);
      this.invokeConstructor(var1, Method.getMethod("void <init> (String)"));
      this.throwException();
   }

   public void checkCast(Type var1) {
      if (!var1.equals(OBJECT_TYPE)) {
         this.typeInsn(192, var1);
      }

   }

   public void instanceOf(Type var1) {
      this.typeInsn(193, var1);
   }

   public void monitorEnter() {
      this.mv.visitInsn(194);
   }

   public void monitorExit() {
      this.mv.visitInsn(195);
   }

   public void endMethod() {
      if ((this.access & 1024) == 0) {
         this.mv.visitMaxs(0, 0);
      }

      this.mv.visitEnd();
   }

   public void catchException(Label var1, Label var2, Type var3) {
      Label var4 = new Label();
      if (var3 == null) {
         this.mv.visitTryCatchBlock(var1, var2, var4, (String)null);
      } else {
         this.mv.visitTryCatchBlock(var1, var2, var4, var3.getInternalName());
      }

      this.mark(var4);
   }
}
