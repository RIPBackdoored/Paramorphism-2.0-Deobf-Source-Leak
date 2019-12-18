package org.objectweb.asm;

final class AnnotationWriter extends AnnotationVisitor {
   private final SymbolTable symbolTable;
   private final boolean useNamedValues;
   private final ByteVector annotation;
   private final int numElementValuePairsOffset;
   private int numElementValuePairs;
   private final AnnotationWriter previousAnnotation;
   private AnnotationWriter nextAnnotation;

   AnnotationWriter(SymbolTable var1, boolean var2, ByteVector var3, AnnotationWriter var4) {
      super(458752);
      this.symbolTable = var1;
      this.useNamedValues = var2;
      this.annotation = var3;
      this.numElementValuePairsOffset = var3.length == 0 ? -1 : var3.length - 2;
      this.previousAnnotation = var4;
      if (var4 != null) {
         var4.nextAnnotation = this;
      }

   }

   static AnnotationWriter create(SymbolTable var0, String var1, AnnotationWriter var2) {
      ByteVector var3 = new ByteVector();
      var3.putShort(var0.addConstantUtf8(var1)).putShort(0);
      return new AnnotationWriter(var0, true, var3, var2);
   }

   static AnnotationWriter create(SymbolTable var0, int var1, TypePath var2, String var3, AnnotationWriter var4) {
      ByteVector var5 = new ByteVector();
      TypeReference.putTarget(var1, var5);
      TypePath.put(var2, var5);
      var5.putShort(var0.addConstantUtf8(var3)).putShort(0);
      return new AnnotationWriter(var0, true, var5, var4);
   }

   public void visit(String var1, Object var2) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(var1));
      }

      if (var2 instanceof String) {
         this.annotation.put12(115, this.symbolTable.addConstantUtf8((String)var2));
      } else if (var2 instanceof Byte) {
         this.annotation.put12(66, this.symbolTable.addConstantInteger((Byte)var2).index);
      } else if (var2 instanceof Boolean) {
         int var3 = (Boolean)var2 ? 1 : 0;
         this.annotation.put12(90, this.symbolTable.addConstantInteger(var3).index);
      } else if (var2 instanceof Character) {
         this.annotation.put12(67, this.symbolTable.addConstantInteger((Character)var2).index);
      } else if (var2 instanceof Short) {
         this.annotation.put12(83, this.symbolTable.addConstantInteger((Short)var2).index);
      } else if (var2 instanceof Type) {
         this.annotation.put12(99, this.symbolTable.addConstantUtf8(((Type)var2).getDescriptor()));
      } else {
         int var5;
         int var6;
         if (var2 instanceof byte[]) {
            byte[] var9 = (byte[])((byte[])var2);
            this.annotation.put12(91, var9.length);
            byte[] var4 = var9;
            var5 = var9.length;

            for(var6 = 0; var6 < var5; ++var6) {
               byte var7 = var4[var6];
               this.annotation.put12(66, this.symbolTable.addConstantInteger(var7).index);
            }
         } else if (var2 instanceof boolean[]) {
            boolean[] var10 = (boolean[])((boolean[])var2);
            this.annotation.put12(91, var10.length);
            boolean[] var12 = var10;
            var5 = var10.length;

            for(var6 = 0; var6 < var5; ++var6) {
               boolean var25 = var12[var6];
               this.annotation.put12(90, this.symbolTable.addConstantInteger(var25 ? 1 : 0).index);
            }
         } else if (var2 instanceof short[]) {
            short[] var11 = (short[])((short[])var2);
            this.annotation.put12(91, var11.length);
            short[] var14 = var11;
            var5 = var11.length;

            for(var6 = 0; var6 < var5; ++var6) {
               short var26 = var14[var6];
               this.annotation.put12(83, this.symbolTable.addConstantInteger(var26).index);
            }
         } else if (var2 instanceof char[]) {
            char[] var13 = (char[])((char[])var2);
            this.annotation.put12(91, var13.length);
            char[] var16 = var13;
            var5 = var13.length;

            for(var6 = 0; var6 < var5; ++var6) {
               char var27 = var16[var6];
               this.annotation.put12(67, this.symbolTable.addConstantInteger(var27).index);
            }
         } else if (var2 instanceof int[]) {
            int[] var15 = (int[])((int[])var2);
            this.annotation.put12(91, var15.length);
            int[] var18 = var15;
            var5 = var15.length;

            for(var6 = 0; var6 < var5; ++var6) {
               int var28 = var18[var6];
               this.annotation.put12(73, this.symbolTable.addConstantInteger(var28).index);
            }
         } else if (var2 instanceof long[]) {
            long[] var17 = (long[])((long[])var2);
            this.annotation.put12(91, var17.length);
            long[] var20 = var17;
            var5 = var17.length;

            for(var6 = 0; var6 < var5; ++var6) {
               long var29 = var20[var6];
               this.annotation.put12(74, this.symbolTable.addConstantLong(var29).index);
            }
         } else if (var2 instanceof float[]) {
            float[] var19 = (float[])((float[])var2);
            this.annotation.put12(91, var19.length);
            float[] var22 = var19;
            var5 = var19.length;

            for(var6 = 0; var6 < var5; ++var6) {
               float var30 = var22[var6];
               this.annotation.put12(70, this.symbolTable.addConstantFloat(var30).index);
            }
         } else if (var2 instanceof double[]) {
            double[] var21 = (double[])((double[])var2);
            this.annotation.put12(91, var21.length);
            double[] var24 = var21;
            var5 = var21.length;

            for(var6 = 0; var6 < var5; ++var6) {
               double var31 = var24[var6];
               this.annotation.put12(68, this.symbolTable.addConstantDouble(var31).index);
            }
         } else {
            Symbol var23 = this.symbolTable.addConstant(var2);
            this.annotation.put12(".s.IFJDCS".charAt(var23.tag), var23.index);
         }
      }

   }

   public void visitEnum(String var1, String var2, String var3) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(var1));
      }

      this.annotation.put12(101, this.symbolTable.addConstantUtf8(var2)).putShort(this.symbolTable.addConstantUtf8(var3));
   }

   public AnnotationVisitor visitAnnotation(String var1, String var2) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(var1));
      }

      this.annotation.put12(64, this.symbolTable.addConstantUtf8(var2)).putShort(0);
      return new AnnotationWriter(this.symbolTable, true, this.annotation, (AnnotationWriter)null);
   }

   public AnnotationVisitor visitArray(String var1) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(var1));
      }

      this.annotation.put12(91, 0);
      return new AnnotationWriter(this.symbolTable, false, this.annotation, (AnnotationWriter)null);
   }

   public void visitEnd() {
      if (this.numElementValuePairsOffset != -1) {
         byte[] var1 = this.annotation.data;
         var1[this.numElementValuePairsOffset] = (byte)(this.numElementValuePairs >>> 8);
         var1[this.numElementValuePairsOffset + 1] = (byte)this.numElementValuePairs;
      }

   }

   int computeAnnotationsSize(String var1) {
      if (var1 != null) {
         this.symbolTable.addConstantUtf8(var1);
      }

      int var2 = 8;

      for(AnnotationWriter var3 = this; var3 != null; var3 = var3.previousAnnotation) {
         var2 += var3.annotation.length;
      }

      return var2;
   }

   static int computeAnnotationsSize(AnnotationWriter var0, AnnotationWriter var1, AnnotationWriter var2, AnnotationWriter var3) {
      int var4 = 0;
      if (var0 != null) {
         var4 += var0.computeAnnotationsSize("RuntimeVisibleAnnotations");
      }

      if (var1 != null) {
         var4 += var1.computeAnnotationsSize("RuntimeInvisibleAnnotations");
      }

      if (var2 != null) {
         var4 += var2.computeAnnotationsSize("RuntimeVisibleTypeAnnotations");
      }

      if (var3 != null) {
         var4 += var3.computeAnnotationsSize("RuntimeInvisibleTypeAnnotations");
      }

      return var4;
   }

   void putAnnotations(int var1, ByteVector var2) {
      int var3 = 2;
      int var4 = 0;
      AnnotationWriter var5 = this;

      AnnotationWriter var6;
      for(var6 = null; var5 != null; var5 = var5.previousAnnotation) {
         var5.visitEnd();
         var3 += var5.annotation.length;
         ++var4;
         var6 = var5;
      }

      var2.putShort(var1);
      var2.putInt(var3);
      var2.putShort(var4);

      for(var5 = var6; var5 != null; var5 = var5.nextAnnotation) {
         var2.putByteArray(var5.annotation.data, 0, var5.annotation.length);
      }

   }

   static void putAnnotations(SymbolTable var0, AnnotationWriter var1, AnnotationWriter var2, AnnotationWriter var3, AnnotationWriter var4, ByteVector var5) {
      if (var1 != null) {
         var1.putAnnotations(var0.addConstantUtf8("RuntimeVisibleAnnotations"), var5);
      }

      if (var2 != null) {
         var2.putAnnotations(var0.addConstantUtf8("RuntimeInvisibleAnnotations"), var5);
      }

      if (var3 != null) {
         var3.putAnnotations(var0.addConstantUtf8("RuntimeVisibleTypeAnnotations"), var5);
      }

      if (var4 != null) {
         var4.putAnnotations(var0.addConstantUtf8("RuntimeInvisibleTypeAnnotations"), var5);
      }

   }

   static int computeParameterAnnotationsSize(String var0, AnnotationWriter[] var1, int var2) {
      int var3 = 7 + 2 * var2;

      for(int var4 = 0; var4 < var2; ++var4) {
         AnnotationWriter var5 = var1[var4];
         var3 += var5 == null ? 0 : var5.computeAnnotationsSize(var0) - 8;
      }

      return var3;
   }

   static void putParameterAnnotations(int var0, AnnotationWriter[] var1, int var2, ByteVector var3) {
      int var4 = 1 + 2 * var2;

      int var5;
      AnnotationWriter var6;
      for(var5 = 0; var5 < var2; ++var5) {
         var6 = var1[var5];
         var4 += var6 == null ? 0 : var6.computeAnnotationsSize((String)null) - 8;
      }

      var3.putShort(var0);
      var3.putInt(var4);
      var3.putByte(var2);

      for(var5 = 0; var5 < var2; ++var5) {
         var6 = var1[var5];
         AnnotationWriter var7 = null;

         int var8;
         for(var8 = 0; var6 != null; var6 = var6.previousAnnotation) {
            var6.visitEnd();
            ++var8;
            var7 = var6;
         }

         var3.putShort(var8);

         for(var6 = var7; var6 != null; var6 = var6.nextAnnotation) {
            var3.putByteArray(var6.annotation.data, 0, var6.annotation.length);
         }
      }

   }
}
