package org.objectweb.asm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassReader {
   public static final int SKIP_CODE = 1;
   public static final int SKIP_DEBUG = 2;
   public static final int SKIP_FRAMES = 4;
   public static final int EXPAND_FRAMES = 8;
   static final int EXPAND_ASM_INSNS = 256;
   private static final int INPUT_STREAM_DATA_CHUNK_SIZE = 4096;
   /** @deprecated */
   @Deprecated
   public final byte[] b;
   final byte[] classFileBuffer;
   private final int[] cpInfoOffsets;
   private final String[] constantUtf8Values;
   private final ConstantDynamic[] constantDynamicValues;
   private final int[] bootstrapMethodOffsets;
   private final int maxStringLength;
   public final int header;

   public ClassReader(byte[] var1) {
      this(var1, 0, var1.length);
   }

   public ClassReader(byte[] var1, int var2, int var3) {
      this(var1, var2, true);
   }

   ClassReader(byte[] var1, int var2, boolean var3) {
      super();
      this.classFileBuffer = var1;
      this.b = var1;
      if (var3 && this.readShort(var2 + 6) > 57) {
         throw new IllegalArgumentException("Unsupported class file major version " + this.readShort(var2 + 6));
      } else {
         int var4 = this.readUnsignedShort(var2 + 8);
         this.cpInfoOffsets = new int[var4];
         this.constantUtf8Values = new String[var4];
         int var5 = 1;
         int var6 = var2 + 10;
         int var7 = 0;
         boolean var8 = false;

         boolean var9;
         int var10;
         for(var9 = false; var5 < var4; var6 += var10) {
            this.cpInfoOffsets[var5++] = var6 + 1;
            switch(var1[var6]) {
            case 1:
               var10 = 3 + this.readUnsignedShort(var6 + 1);
               if (var10 > var7) {
                  var7 = var10;
               }
               break;
            case 2:
            case 13:
            case 14:
            default:
               throw new IllegalArgumentException();
            case 3:
            case 4:
            case 9:
            case 10:
            case 11:
            case 12:
               var10 = 5;
               break;
            case 5:
            case 6:
               var10 = 9;
               ++var5;
               break;
            case 7:
            case 8:
            case 16:
            case 19:
            case 20:
               var10 = 3;
               break;
            case 15:
               var10 = 4;
               break;
            case 17:
               var10 = 5;
               var8 = true;
               var9 = true;
               break;
            case 18:
               var10 = 5;
               var8 = true;
            }
         }

         this.maxStringLength = var7;
         this.header = var6;
         this.constantDynamicValues = var9 ? new ConstantDynamic[var4] : null;
         this.bootstrapMethodOffsets = var8 ? this.readBootstrapMethodsAttribute(var7) : null;
      }
   }

   public ClassReader(InputStream var1) throws IOException {
      this(readStream(var1, false));
   }

   public ClassReader(String var1) throws IOException {
      this(readStream(ClassLoader.getSystemResourceAsStream(var1.replace('.', '/') + ".class"), true));
   }

   private static byte[] readStream(InputStream var0, boolean var1) throws IOException {
      if (var0 == null) {
         throw new IOException("Class not found");
      } else {
         boolean var16 = false;

         byte[] var6;
         try {
            var16 = true;
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            Throwable var3 = null;
            boolean var22 = false;

            try {
               var22 = true;
               byte[] var4 = new byte[4096];

               int var5;
               while((var5 = var0.read(var4, 0, var4.length)) != -1) {
                  var2.write(var4, 0, var5);
               }

               var2.flush();
               var6 = var2.toByteArray();
               var22 = false;
            } catch (Throwable var25) {
               var3 = var25;
               throw var25;
            } finally {
               if (var22) {
                  if (var2 != null) {
                     if (var3 != null) {
                        try {
                           var2.close();
                        } catch (Throwable var23) {
                        }
                     } else {
                        var2.close();
                     }
                  }

               }
            }

            if (var2 != null) {
               if (var3 != null) {
                  try {
                     var2.close();
                     var16 = false;
                  } catch (Throwable var24) {
                     var16 = false;
                  }
               } else {
                  var2.close();
                  var16 = false;
               }
            } else {
               var16 = false;
            }
         } finally {
            if (var16) {
               if (var1) {
                  var0.close();
               }

            }
         }

         if (var1) {
            var0.close();
         }

         return var6;
      }
   }

   public int getAccess() {
      return this.readUnsignedShort(this.header);
   }

   public String getClassName() {
      return this.readClass(this.header + 2, new char[this.maxStringLength]);
   }

   public String getSuperName() {
      return this.readClass(this.header + 4, new char[this.maxStringLength]);
   }

   public String[] getInterfaces() {
      int var1 = this.header + 6;
      int var2 = this.readUnsignedShort(var1);
      String[] var3 = new String[var2];
      if (var2 > 0) {
         char[] var4 = new char[this.maxStringLength];

         for(int var5 = 0; var5 < var2; ++var5) {
            var1 += 2;
            var3[var5] = this.readClass(var1, var4);
         }
      }

      return var3;
   }

   public void accept(ClassVisitor var1, int var2) {
      this.accept(var1, new Attribute[0], var2);
   }

   public void accept(ClassVisitor var1, Attribute[] var2, int var3) {
      Context var4 = new Context();
      var4.attributePrototypes = var2;
      var4.parsingOptions = var3;
      var4.charBuffer = new char[this.maxStringLength];
      char[] var5 = var4.charBuffer;
      int var6 = this.header;
      int var7 = this.readUnsignedShort(var6);
      String var8 = this.readClass(var6 + 2, var5);
      String var9 = this.readClass(var6 + 4, var5);
      String[] var10 = new String[this.readUnsignedShort(var6 + 6)];
      var6 += 8;

      int var11;
      for(var11 = 0; var11 < var10.length; ++var11) {
         var10[var11] = this.readClass(var6, var5);
         var6 += 2;
      }

      var11 = 0;
      int var12 = 0;
      String var13 = null;
      String var14 = null;
      String var15 = null;
      int var16 = 0;
      int var17 = 0;
      int var18 = 0;
      int var19 = 0;
      int var20 = 0;
      int var21 = 0;
      String var22 = null;
      String var23 = null;
      int var24 = 0;
      Attribute var25 = null;
      int var26 = this.getFirstAttributeOffset();

      int var27;
      for(var27 = this.readUnsignedShort(var26 - 2); var27 > 0; --var27) {
         String var28 = this.readUTF8(var26, var5);
         int var29 = this.readInt(var26 + 2);
         var26 += 6;
         if ("SourceFile".equals(var28)) {
            var14 = this.readUTF8(var26, var5);
         } else if ("InnerClasses".equals(var28)) {
            var11 = var26;
         } else if ("EnclosingMethod".equals(var28)) {
            var12 = var26;
         } else if ("NestHost".equals(var28)) {
            var23 = this.readClass(var26, var5);
         } else if ("NestMembers".equals(var28)) {
            var24 = var26;
         } else if ("Signature".equals(var28)) {
            var13 = this.readUTF8(var26, var5);
         } else if ("RuntimeVisibleAnnotations".equals(var28)) {
            var16 = var26;
         } else if ("RuntimeVisibleTypeAnnotations".equals(var28)) {
            var18 = var26;
         } else if ("Deprecated".equals(var28)) {
            var7 |= 131072;
         } else if ("Synthetic".equals(var28)) {
            var7 |= 4096;
         } else if ("SourceDebugExtension".equals(var28)) {
            var15 = this.readUtf(var26, var29, new char[var29]);
         } else if ("RuntimeInvisibleAnnotations".equals(var28)) {
            var17 = var26;
         } else if ("RuntimeInvisibleTypeAnnotations".equals(var28)) {
            var19 = var26;
         } else if ("Module".equals(var28)) {
            var20 = var26;
         } else if ("ModuleMainClass".equals(var28)) {
            var22 = this.readClass(var26, var5);
         } else if ("ModulePackages".equals(var28)) {
            var21 = var26;
         } else if (!"BootstrapMethods".equals(var28)) {
            Attribute var30 = this.readAttribute(var2, var28, var26, var29, var5, -1, (Label[])null);
            var30.nextAttribute = var25;
            var25 = var30;
         }

         var26 += var29;
      }

      var1.visit(this.readInt(this.cpInfoOffsets[1] - 7), var7, var8, var13, var9, var10);
      if ((var3 & 2) == 0 && (var14 != null || var15 != null)) {
         var1.visitSource(var14, var15);
      }

      if (var20 != 0) {
         this.readModuleAttributes(var1, var4, var20, var21, var22);
      }

      if (var23 != null) {
         var1.visitNestHost(var23);
      }

      int var32;
      String var33;
      if (var12 != 0) {
         String var31 = this.readClass(var12, var5);
         var32 = this.readUnsignedShort(var12 + 2);
         var33 = var32 == 0 ? null : this.readUTF8(this.cpInfoOffsets[var32], var5);
         String var34 = var32 == 0 ? null : this.readUTF8(this.cpInfoOffsets[var32] + 2, var5);
         var1.visitOuterClass(var31, var33, var34);
      }

      if (var16 != 0) {
         var27 = this.readUnsignedShort(var16);

         for(var32 = var16 + 2; var27-- > 0; var32 = this.readElementValues(var1.visitAnnotation(var33, true), var32, true, var5)) {
            var33 = this.readUTF8(var32, var5);
            var32 += 2;
         }
      }

      if (var17 != 0) {
         var27 = this.readUnsignedShort(var17);

         for(var32 = var17 + 2; var27-- > 0; var32 = this.readElementValues(var1.visitAnnotation(var33, false), var32, true, var5)) {
            var33 = this.readUTF8(var32, var5);
            var32 += 2;
         }
      }

      if (var18 != 0) {
         var27 = this.readUnsignedShort(var18);

         for(var32 = var18 + 2; var27-- > 0; var32 = this.readElementValues(var1.visitTypeAnnotation(var4.currentTypeAnnotationTarget, var4.currentTypeAnnotationTargetPath, var33, true), var32, true, var5)) {
            var32 = this.readTypeAnnotationTarget(var4, var32);
            var33 = this.readUTF8(var32, var5);
            var32 += 2;
         }
      }

      if (var19 != 0) {
         var27 = this.readUnsignedShort(var19);

         for(var32 = var19 + 2; var27-- > 0; var32 = this.readElementValues(var1.visitTypeAnnotation(var4.currentTypeAnnotationTarget, var4.currentTypeAnnotationTargetPath, var33, false), var32, true, var5)) {
            var32 = this.readTypeAnnotationTarget(var4, var32);
            var33 = this.readUTF8(var32, var5);
            var32 += 2;
         }
      }

      while(var25 != null) {
         Attribute var35 = var25.nextAttribute;
         var25.nextAttribute = null;
         var1.visitAttribute(var25);
         var25 = var35;
      }

      if (var24 != 0) {
         var27 = this.readUnsignedShort(var24);

         for(var32 = var24 + 2; var27-- > 0; var32 += 2) {
            var1.visitNestMember(this.readClass(var32, var5));
         }
      }

      if (var11 != 0) {
         var27 = this.readUnsignedShort(var11);

         for(var32 = var11 + 2; var27-- > 0; var32 += 8) {
            var1.visitInnerClass(this.readClass(var32, var5), this.readClass(var32 + 2, var5), this.readUTF8(var32 + 4, var5), this.readUnsignedShort(var32 + 6));
         }
      }

      var27 = this.readUnsignedShort(var6);

      for(var6 += 2; var27-- > 0; var6 = this.readField(var1, var4, var6)) {
      }

      var32 = this.readUnsignedShort(var6);

      for(var6 += 2; var32-- > 0; var6 = this.readMethod(var1, var4, var6)) {
      }

      var1.visitEnd();
   }

   private void readModuleAttributes(ClassVisitor var1, Context var2, int var3, int var4, String var5) {
      char[] var6 = var2.charBuffer;
      String var8 = this.readModule(var3, var6);
      int var9 = this.readUnsignedShort(var3 + 2);
      String var10 = this.readUTF8(var3 + 4, var6);
      int var7 = var3 + 6;
      ModuleVisitor var11 = var1.visitModule(var8, var9, var10);
      if (var11 != null) {
         if (var5 != null) {
            var11.visitMainClass(var5);
         }

         int var12;
         int var13;
         if (var4 != 0) {
            var12 = this.readUnsignedShort(var4);

            for(var13 = var4 + 2; var12-- > 0; var13 += 2) {
               var11.visitPackage(this.readPackage(var13, var6));
            }
         }

         var12 = this.readUnsignedShort(var7);
         var7 += 2;

         int var14;
         String var15;
         while(var12-- > 0) {
            String var21 = this.readModule(var7, var6);
            var14 = this.readUnsignedShort(var7 + 2);
            var15 = this.readUTF8(var7 + 4, var6);
            var7 += 6;
            var11.visitRequire(var21, var14, var15);
         }

         var13 = this.readUnsignedShort(var7);

         int var16;
         String[] var17;
         int var18;
         String var22;
         int var23;
         for(var7 += 2; var13-- > 0; var11.visitExport(var22, var23, var17)) {
            var22 = this.readPackage(var7, var6);
            var23 = this.readUnsignedShort(var7 + 2);
            var16 = this.readUnsignedShort(var7 + 4);
            var7 += 6;
            var17 = null;
            if (var16 != 0) {
               var17 = new String[var16];

               for(var18 = 0; var18 < var16; ++var18) {
                  var17[var18] = this.readModule(var7, var6);
                  var7 += 2;
               }
            }
         }

         var14 = this.readUnsignedShort(var7);

         String[] var26;
         for(var7 += 2; var14-- > 0; var11.visitOpen(var15, var16, var26)) {
            var15 = this.readPackage(var7, var6);
            var16 = this.readUnsignedShort(var7 + 2);
            int var24 = this.readUnsignedShort(var7 + 4);
            var7 += 6;
            var26 = null;
            if (var24 != 0) {
               var26 = new String[var24];

               for(int var19 = 0; var19 < var24; ++var19) {
                  var26[var19] = this.readModule(var7, var6);
                  var7 += 2;
               }
            }
         }

         var23 = this.readUnsignedShort(var7);

         for(var7 += 2; var23-- > 0; var7 += 2) {
            var11.visitUse(this.readClass(var7, var6));
         }

         var16 = this.readUnsignedShort(var7);
         var7 += 2;

         while(var16-- > 0) {
            String var25 = this.readClass(var7, var6);
            var18 = this.readUnsignedShort(var7 + 2);
            var7 += 4;
            String[] var27 = new String[var18];

            for(int var20 = 0; var20 < var18; ++var20) {
               var27[var20] = this.readClass(var7, var6);
               var7 += 2;
            }

            var11.visitProvide(var25, var27);
         }

         var11.visitEnd();
      }
   }

   private int readField(ClassVisitor var1, Context var2, int var3) {
      char[] var4 = var2.charBuffer;
      int var6 = this.readUnsignedShort(var3);
      String var7 = this.readUTF8(var3 + 2, var4);
      String var8 = this.readUTF8(var3 + 4, var4);
      int var5 = var3 + 6;
      Object var9 = null;
      String var10 = null;
      int var11 = 0;
      int var12 = 0;
      int var13 = 0;
      int var14 = 0;
      Attribute var15 = null;
      int var16 = this.readUnsignedShort(var5);

      int var18;
      int var19;
      for(var5 += 2; var16-- > 0; var5 += var18) {
         String var17 = this.readUTF8(var5, var4);
         var18 = this.readInt(var5 + 2);
         var5 += 6;
         if ("ConstantValue".equals(var17)) {
            var19 = this.readUnsignedShort(var5);
            var9 = var19 == 0 ? null : this.readConst(var19, var4);
         } else if ("Signature".equals(var17)) {
            var10 = this.readUTF8(var5, var4);
         } else if ("Deprecated".equals(var17)) {
            var6 |= 131072;
         } else if ("Synthetic".equals(var17)) {
            var6 |= 4096;
         } else if ("RuntimeVisibleAnnotations".equals(var17)) {
            var11 = var5;
         } else if ("RuntimeVisibleTypeAnnotations".equals(var17)) {
            var13 = var5;
         } else if ("RuntimeInvisibleAnnotations".equals(var17)) {
            var12 = var5;
         } else if ("RuntimeInvisibleTypeAnnotations".equals(var17)) {
            var14 = var5;
         } else {
            Attribute var22 = this.readAttribute(var2.attributePrototypes, var17, var5, var18, var4, -1, (Label[])null);
            var22.nextAttribute = var15;
            var15 = var22;
         }
      }

      FieldVisitor var21 = var1.visitField(var6, var7, var8, var10, var9);
      if (var21 == null) {
         return var5;
      } else {
         String var20;
         if (var11 != 0) {
            var18 = this.readUnsignedShort(var11);

            for(var19 = var11 + 2; var18-- > 0; var19 = this.readElementValues(var21.visitAnnotation(var20, true), var19, true, var4)) {
               var20 = this.readUTF8(var19, var4);
               var19 += 2;
            }
         }

         if (var12 != 0) {
            var18 = this.readUnsignedShort(var12);

            for(var19 = var12 + 2; var18-- > 0; var19 = this.readElementValues(var21.visitAnnotation(var20, false), var19, true, var4)) {
               var20 = this.readUTF8(var19, var4);
               var19 += 2;
            }
         }

         if (var13 != 0) {
            var18 = this.readUnsignedShort(var13);

            for(var19 = var13 + 2; var18-- > 0; var19 = this.readElementValues(var21.visitTypeAnnotation(var2.currentTypeAnnotationTarget, var2.currentTypeAnnotationTargetPath, var20, true), var19, true, var4)) {
               var19 = this.readTypeAnnotationTarget(var2, var19);
               var20 = this.readUTF8(var19, var4);
               var19 += 2;
            }
         }

         if (var14 != 0) {
            var18 = this.readUnsignedShort(var14);

            for(var19 = var14 + 2; var18-- > 0; var19 = this.readElementValues(var21.visitTypeAnnotation(var2.currentTypeAnnotationTarget, var2.currentTypeAnnotationTargetPath, var20, false), var19, true, var4)) {
               var19 = this.readTypeAnnotationTarget(var2, var19);
               var20 = this.readUTF8(var19, var4);
               var19 += 2;
            }
         }

         while(var15 != null) {
            Attribute var23 = var15.nextAttribute;
            var15.nextAttribute = null;
            var21.visitAttribute(var15);
            var15 = var23;
         }

         var21.visitEnd();
         return var5;
      }
   }

   private int readMethod(ClassVisitor var1, Context var2, int var3) {
      char[] var4 = var2.charBuffer;
      var2.currentMethodAccessFlags = this.readUnsignedShort(var3);
      var2.currentMethodName = this.readUTF8(var3 + 2, var4);
      var2.currentMethodDescriptor = this.readUTF8(var3 + 4, var4);
      int var5 = var3 + 6;
      int var6 = 0;
      int var7 = 0;
      String[] var8 = null;
      boolean var9 = false;
      int var10 = 0;
      int var11 = 0;
      int var12 = 0;
      int var13 = 0;
      int var14 = 0;
      int var15 = 0;
      int var16 = 0;
      int var17 = 0;
      int var18 = 0;
      Attribute var19 = null;
      int var20 = this.readUnsignedShort(var5);

      int var22;
      int var27;
      for(var5 += 2; var20-- > 0; var5 += var22) {
         String var21 = this.readUTF8(var5, var4);
         var22 = this.readInt(var5 + 2);
         var5 += 6;
         if ("Code".equals(var21)) {
            if ((var2.parsingOptions & 1) == 0) {
               var6 = var5;
            }
         } else if ("Exceptions".equals(var21)) {
            var7 = var5;
            var8 = new String[this.readUnsignedShort(var5)];
            var27 = var5 + 2;

            for(int var24 = 0; var24 < var8.length; ++var24) {
               var8[var24] = this.readClass(var27, var4);
               var27 += 2;
            }
         } else if ("Signature".equals(var21)) {
            var10 = this.readUnsignedShort(var5);
         } else if ("Deprecated".equals(var21)) {
            var2.currentMethodAccessFlags |= 131072;
         } else if ("RuntimeVisibleAnnotations".equals(var21)) {
            var11 = var5;
         } else if ("RuntimeVisibleTypeAnnotations".equals(var21)) {
            var15 = var5;
         } else if ("AnnotationDefault".equals(var21)) {
            var17 = var5;
         } else if ("Synthetic".equals(var21)) {
            var9 = true;
            var2.currentMethodAccessFlags |= 4096;
         } else if ("RuntimeInvisibleAnnotations".equals(var21)) {
            var12 = var5;
         } else if ("RuntimeInvisibleTypeAnnotations".equals(var21)) {
            var16 = var5;
         } else if ("RuntimeVisibleParameterAnnotations".equals(var21)) {
            var13 = var5;
         } else if ("RuntimeInvisibleParameterAnnotations".equals(var21)) {
            var14 = var5;
         } else if ("MethodParameters".equals(var21)) {
            var18 = var5;
         } else {
            Attribute var23 = this.readAttribute(var2.attributePrototypes, var21, var5, var22, var4, -1, (Label[])null);
            var23.nextAttribute = var19;
            var19 = var23;
         }
      }

      MethodVisitor var25 = var1.visitMethod(var2.currentMethodAccessFlags, var2.currentMethodName, var2.currentMethodDescriptor, var10 == 0 ? null : this.readUtf(var10, var4), var8);
      if (var25 == null) {
         return var5;
      } else {
         if (var25 instanceof MethodWriter) {
            MethodWriter var26 = (MethodWriter)var25;
            if (var26.canCopyMethodAttributes(this, var9, (var2.currentMethodAccessFlags & 131072) != 0, this.readUnsignedShort(var3 + 4), var10, var7)) {
               var26.setMethodAttributesSource(var3, var5 - var3);
               return var5;
            }
         }

         if (var18 != 0) {
            var22 = this.readByte(var18);

            for(var27 = var18 + 1; var22-- > 0; var27 += 4) {
               var25.visitParameter(this.readUTF8(var27, var4), this.readUnsignedShort(var27 + 2));
            }
         }

         if (var17 != 0) {
            AnnotationVisitor var28 = var25.visitAnnotationDefault();
            this.readElementValue(var28, var17, (String)null, var4);
            if (var28 != null) {
               var28.visitEnd();
            }
         }

         String var29;
         if (var11 != 0) {
            var22 = this.readUnsignedShort(var11);

            for(var27 = var11 + 2; var22-- > 0; var27 = this.readElementValues(var25.visitAnnotation(var29, true), var27, true, var4)) {
               var29 = this.readUTF8(var27, var4);
               var27 += 2;
            }
         }

         if (var12 != 0) {
            var22 = this.readUnsignedShort(var12);

            for(var27 = var12 + 2; var22-- > 0; var27 = this.readElementValues(var25.visitAnnotation(var29, false), var27, true, var4)) {
               var29 = this.readUTF8(var27, var4);
               var27 += 2;
            }
         }

         if (var15 != 0) {
            var22 = this.readUnsignedShort(var15);

            for(var27 = var15 + 2; var22-- > 0; var27 = this.readElementValues(var25.visitTypeAnnotation(var2.currentTypeAnnotationTarget, var2.currentTypeAnnotationTargetPath, var29, true), var27, true, var4)) {
               var27 = this.readTypeAnnotationTarget(var2, var27);
               var29 = this.readUTF8(var27, var4);
               var27 += 2;
            }
         }

         if (var16 != 0) {
            var22 = this.readUnsignedShort(var16);

            for(var27 = var16 + 2; var22-- > 0; var27 = this.readElementValues(var25.visitTypeAnnotation(var2.currentTypeAnnotationTarget, var2.currentTypeAnnotationTargetPath, var29, false), var27, true, var4)) {
               var27 = this.readTypeAnnotationTarget(var2, var27);
               var29 = this.readUTF8(var27, var4);
               var27 += 2;
            }
         }

         if (var13 != 0) {
            this.readParameterAnnotations(var25, var2, var13, true);
         }

         if (var14 != 0) {
            this.readParameterAnnotations(var25, var2, var14, false);
         }

         while(var19 != null) {
            Attribute var30 = var19.nextAttribute;
            var19.nextAttribute = null;
            var25.visitAttribute(var19);
            var19 = var30;
         }

         if (var6 != 0) {
            var25.visitCode();
            this.readCode(var25, var2, var6);
         }

         var25.visitEnd();
         return var5;
      }
   }

   private void readCode(MethodVisitor var1, Context var2, int var3) {
      byte[] var5 = this.classFileBuffer;
      char[] var6 = var2.charBuffer;
      int var7 = this.readUnsignedShort(var3);
      int var8 = this.readUnsignedShort(var3 + 2);
      int var9 = this.readInt(var3 + 4);
      int var4 = var3 + 8;
      int var10 = var4;
      int var11 = var4 + var9;
      Label[] var12 = var2.currentMethodLabels = new Label[var9 + 1];

      int var13;
      int var14;
      int var15;
      label419:
      while(var4 < var11) {
         var13 = var4 - var10;
         var14 = var5[var4] & 255;
         switch(var14) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
         case 67:
         case 68:
         case 69:
         case 70:
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 83:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 99:
         case 100:
         case 101:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         case 115:
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 128:
         case 129:
         case 130:
         case 131:
         case 133:
         case 134:
         case 135:
         case 136:
         case 137:
         case 138:
         case 139:
         case 140:
         case 141:
         case 142:
         case 143:
         case 144:
         case 145:
         case 146:
         case 147:
         case 148:
         case 149:
         case 150:
         case 151:
         case 152:
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
         case 190:
         case 191:
         case 194:
         case 195:
            ++var4;
            break;
         case 16:
         case 18:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 169:
         case 188:
            var4 += 2;
            break;
         case 17:
         case 19:
         case 20:
         case 132:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 187:
         case 189:
         case 192:
         case 193:
            var4 += 3;
            break;
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 159:
         case 160:
         case 161:
         case 162:
         case 163:
         case 164:
         case 165:
         case 166:
         case 167:
         case 168:
         case 198:
         case 199:
            this.createLabel(var13 + this.readShort(var4 + 1), var12);
            var4 += 3;
            break;
         case 170:
            var4 += 4 - (var13 & 3);
            this.createLabel(var13 + this.readInt(var4), var12);
            var15 = this.readInt(var4 + 8) - this.readInt(var4 + 4) + 1;
            var4 += 12;

            while(true) {
               if (var15-- <= 0) {
                  continue label419;
               }

               this.createLabel(var13 + this.readInt(var4), var12);
               var4 += 4;
            }
         case 171:
            var4 += 4 - (var13 & 3);
            this.createLabel(var13 + this.readInt(var4), var12);
            int var16 = this.readInt(var4 + 4);
            var4 += 8;

            while(true) {
               if (var16-- <= 0) {
                  continue label419;
               }

               this.createLabel(var13 + this.readInt(var4 + 4), var12);
               var4 += 8;
            }
         case 185:
         case 186:
            var4 += 5;
            break;
         case 196:
            switch(var5[var4 + 1] & 255) {
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 169:
               var4 += 4;
               continue;
            case 132:
               var4 += 6;
               continue;
            default:
               throw new IllegalArgumentException();
            }
         case 197:
            var4 += 4;
            break;
         case 200:
         case 201:
         case 220:
            this.createLabel(var13 + this.readInt(var4 + 1), var12);
            var4 += 5;
            break;
         case 202:
         case 203:
         case 204:
         case 205:
         case 206:
         case 207:
         case 208:
         case 209:
         case 210:
         case 211:
         case 212:
         case 213:
         case 214:
         case 215:
         case 216:
         case 217:
         case 218:
         case 219:
            this.createLabel(var13 + this.readUnsignedShort(var4 + 1), var12);
            var4 += 3;
            break;
         default:
            throw new IllegalArgumentException();
         }
      }

      var13 = this.readUnsignedShort(var4);
      var4 += 2;

      while(var13-- > 0) {
         Label var41 = this.createLabel(this.readUnsignedShort(var4), var12);
         Label var42 = this.createLabel(this.readUnsignedShort(var4 + 2), var12);
         Label var43 = this.createLabel(this.readUnsignedShort(var4 + 4), var12);
         String var17 = this.readUTF8(this.cpInfoOffsets[this.readUnsignedShort(var4 + 6)], var6);
         var4 += 8;
         var1.visitTryCatchBlock(var41, var42, var43, var17);
      }

      var14 = 0;
      var15 = 0;
      boolean var44 = true;
      int var45 = 0;
      int var18 = 0;
      int[] var19 = null;
      int[] var20 = null;
      Attribute var21 = null;
      int var22 = this.readUnsignedShort(var4);

      int var24;
      int var26;
      int var27;
      int var47;
      for(var4 += 2; var22-- > 0; var4 += var24) {
         String var23 = this.readUTF8(var4, var6);
         var24 = this.readInt(var4 + 2);
         var4 += 6;
         int var28;
         if ("LocalVariableTable".equals(var23)) {
            if ((var2.parsingOptions & 2) == 0) {
               var45 = var4;
               var26 = this.readUnsignedShort(var4);

               for(var47 = var4 + 2; var26-- > 0; var47 += 10) {
                  var27 = this.readUnsignedShort(var47);
                  this.createDebugLabel(var27, var12);
                  var28 = this.readUnsignedShort(var47 + 2);
                  this.createDebugLabel(var27 + var28, var12);
               }
            }
         } else if ("LocalVariableTypeTable".equals(var23)) {
            var18 = var4;
         } else if ("LineNumberTable".equals(var23)) {
            if ((var2.parsingOptions & 2) == 0) {
               var26 = this.readUnsignedShort(var4);
               var47 = var4 + 2;

               while(var26-- > 0) {
                  var27 = this.readUnsignedShort(var47);
                  var28 = this.readUnsignedShort(var47 + 2);
                  var47 += 4;
                  this.createDebugLabel(var27, var12);
                  var12[var27].addLineNumber(var28);
               }
            }
         } else if ("RuntimeVisibleTypeAnnotations".equals(var23)) {
            var19 = this.readTypeAnnotations(var1, var2, var4, true);
         } else if ("RuntimeInvisibleTypeAnnotations".equals(var23)) {
            var20 = this.readTypeAnnotations(var1, var2, var4, false);
         } else if ("StackMapTable".equals(var23)) {
            if ((var2.parsingOptions & 4) == 0) {
               var14 = var4 + 2;
               var15 = var4 + var24;
            }
         } else if ("StackMap".equals(var23)) {
            if ((var2.parsingOptions & 4) == 0) {
               var14 = var4 + 2;
               var15 = var4 + var24;
               var44 = false;
            }
         } else {
            Attribute var25 = this.readAttribute(var2.attributePrototypes, var23, var4, var24, var6, var3, var12);
            var25.nextAttribute = var21;
            var21 = var25;
         }
      }

      boolean var46 = (var2.parsingOptions & 8) != 0;
      if (var14 != 0) {
         var2.currentFrameOffset = -1;
         var2.currentFrameType = 0;
         var2.currentFrameLocalCount = 0;
         var2.currentFrameLocalCountDelta = 0;
         var2.currentFrameLocalTypes = new Object[var8];
         var2.currentFrameStackCount = 0;
         var2.currentFrameStackTypes = new Object[var7];
         if (var46) {
            this.computeImplicitFrame(var2);
         }

         for(var24 = var14; var24 < var15 - 2; ++var24) {
            if (var5[var24] == 8) {
               var47 = this.readUnsignedShort(var24 + 1);
               if (var47 >= 0 && var47 < var9 && (var5[var10 + var47] & 255) == 187) {
                  this.createLabel(var47, var12);
               }
            }
         }
      }

      if (var46 && (var2.parsingOptions & 256) != 0) {
         var1.visitFrame(-1, var8, (Object[])null, 0, (Object[])null);
      }

      var24 = 0;
      var47 = this.getTypeAnnotationBytecodeOffset(var19, 0);
      var26 = 0;
      var27 = this.getTypeAnnotationBytecodeOffset(var20, 0);
      boolean var48 = false;
      int var29 = (var2.parsingOptions & 256) == 0 ? 33 : 0;
      var4 = var10;

      int var32;
      String var35;
      int var52;
      int var53;
      String var55;
      String var59;
      while(var4 < var11) {
         int var30 = var4 - var10;
         Label var31 = var12[var30];
         if (var31 != null) {
            var31.accept(var1, (var2.parsingOptions & 2) == 0);
         }

         while(var14 != 0 && (var2.currentFrameOffset == var30 || var2.currentFrameOffset == -1)) {
            if (var2.currentFrameOffset != -1) {
               if (var44 && !var46) {
                  var1.visitFrame(var2.currentFrameType, var2.currentFrameLocalCountDelta, var2.currentFrameLocalTypes, var2.currentFrameStackCount, var2.currentFrameStackTypes);
               } else {
                  var1.visitFrame(-1, var2.currentFrameLocalCount, var2.currentFrameLocalTypes, var2.currentFrameStackCount, var2.currentFrameStackTypes);
               }

               var48 = false;
            }

            if (var14 < var15) {
               var14 = this.readStackMapFrame(var14, var44, var46, var2);
            } else {
               var14 = 0;
            }
         }

         if (var48) {
            if ((var2.parsingOptions & 8) != 0) {
               var1.visitFrame(256, 0, (Object[])null, 0, (Object[])null);
            }

            var48 = false;
         }

         var32 = var5[var4] & 255;
         Label var33;
         String var36;
         int var37;
         Label[] var57;
         switch(var32) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 79:
         case 80:
         case 81:
         case 82:
         case 83:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 99:
         case 100:
         case 101:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         case 115:
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 128:
         case 129:
         case 130:
         case 131:
         case 133:
         case 134:
         case 135:
         case 136:
         case 137:
         case 138:
         case 139:
         case 140:
         case 141:
         case 142:
         case 143:
         case 144:
         case 145:
         case 146:
         case 147:
         case 148:
         case 149:
         case 150:
         case 151:
         case 152:
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
         case 190:
         case 191:
         case 194:
         case 195:
            var1.visitInsn(var32);
            ++var4;
            break;
         case 16:
         case 188:
            var1.visitIntInsn(var32, var5[var4 + 1]);
            var4 += 2;
            break;
         case 17:
            var1.visitIntInsn(var32, this.readShort(var4 + 1));
            var4 += 3;
            break;
         case 18:
            var1.visitLdcInsn(this.readConst(var5[var4 + 1] & 255, var6));
            var4 += 2;
            break;
         case 19:
         case 20:
            var1.visitLdcInsn(this.readConst(this.readUnsignedShort(var4 + 1), var6));
            var4 += 3;
            break;
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 169:
            var1.visitVarInsn(var32, var5[var4 + 1] & 255);
            var4 += 2;
            break;
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
            var32 -= 26;
            var1.visitVarInsn(21 + (var32 >> 2), var32 & 3);
            ++var4;
            break;
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
         case 67:
         case 68:
         case 69:
         case 70:
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
         case 78:
            var32 -= 59;
            var1.visitVarInsn(54 + (var32 >> 2), var32 & 3);
            ++var4;
            break;
         case 132:
            var1.visitIincInsn(var5[var4 + 1] & 255, var5[var4 + 2]);
            var4 += 3;
            break;
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 159:
         case 160:
         case 161:
         case 162:
         case 163:
         case 164:
         case 165:
         case 166:
         case 167:
         case 168:
         case 198:
         case 199:
            var1.visitJumpInsn(var32, var12[var30 + this.readShort(var4 + 1)]);
            var4 += 3;
            break;
         case 170:
            var4 += 4 - (var30 & 3);
            var33 = var12[var30 + this.readInt(var4)];
            var53 = this.readInt(var4 + 4);
            int var56 = this.readInt(var4 + 8);
            var4 += 12;
            var57 = new Label[var56 - var53 + 1];

            for(var37 = 0; var37 < var57.length; ++var37) {
               var57[var37] = var12[var30 + this.readInt(var4)];
               var4 += 4;
            }

            var1.visitTableSwitchInsn(var53, var56, var33, var57);
            break;
         case 171:
            var4 += 4 - (var30 & 3);
            var33 = var12[var30 + this.readInt(var4)];
            var53 = this.readInt(var4 + 4);
            var4 += 8;
            int[] var54 = new int[var53];
            var57 = new Label[var53];

            for(var37 = 0; var37 < var53; ++var37) {
               var54[var37] = this.readInt(var4);
               var57[var37] = var12[var30 + this.readInt(var4 + 4)];
               var4 += 8;
            }

            var1.visitLookupSwitchInsn(var33, var54, var57);
            break;
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 185:
            var52 = this.cpInfoOffsets[this.readUnsignedShort(var4 + 1)];
            var53 = this.cpInfoOffsets[this.readUnsignedShort(var52 + 2)];
            var35 = this.readClass(var52, var6);
            var36 = this.readUTF8(var53, var6);
            var59 = this.readUTF8(var53 + 2, var6);
            if (var32 < 182) {
               var1.visitFieldInsn(var32, var35, var36, var59);
            } else {
               boolean var60 = var5[var52 - 1] == 11;
               var1.visitMethodInsn(var32, var35, var36, var59, var60);
            }

            if (var32 == 185) {
               var4 += 5;
            } else {
               var4 += 3;
            }
            break;
         case 186:
            var52 = this.cpInfoOffsets[this.readUnsignedShort(var4 + 1)];
            var53 = this.cpInfoOffsets[this.readUnsignedShort(var52 + 2)];
            var35 = this.readUTF8(var53, var6);
            var36 = this.readUTF8(var53 + 2, var6);
            var37 = this.bootstrapMethodOffsets[this.readUnsignedShort(var52)];
            Handle var38 = (Handle)this.readConst(this.readUnsignedShort(var37), var6);
            Object[] var39 = new Object[this.readUnsignedShort(var37 + 2)];
            var37 += 4;

            for(int var40 = 0; var40 < var39.length; ++var40) {
               var39[var40] = this.readConst(this.readUnsignedShort(var37), var6);
               var37 += 2;
            }

            var1.visitInvokeDynamicInsn(var35, var36, var38, var39);
            var4 += 5;
            break;
         case 187:
         case 189:
         case 192:
         case 193:
            var1.visitTypeInsn(var32, this.readClass(var4 + 1, var6));
            var4 += 3;
            break;
         case 196:
            var32 = var5[var4 + 1] & 255;
            if (var32 == 132) {
               var1.visitIincInsn(this.readUnsignedShort(var4 + 2), this.readShort(var4 + 4));
               var4 += 6;
            } else {
               var1.visitVarInsn(var32, this.readUnsignedShort(var4 + 2));
               var4 += 4;
            }
            break;
         case 197:
            var1.visitMultiANewArrayInsn(this.readClass(var4 + 1, var6), var5[var4 + 3] & 255);
            var4 += 4;
            break;
         case 200:
         case 201:
            var1.visitJumpInsn(var32 - var29, var12[var30 + this.readInt(var4 + 1)]);
            var4 += 5;
            break;
         case 202:
         case 203:
         case 204:
         case 205:
         case 206:
         case 207:
         case 208:
         case 209:
         case 210:
         case 211:
         case 212:
         case 213:
         case 214:
         case 215:
         case 216:
         case 217:
         case 218:
         case 219:
            var32 = var32 < 218 ? var32 - 49 : var32 - 20;
            var33 = var12[var30 + this.readUnsignedShort(var4 + 1)];
            if (var32 != 167 && var32 != 168) {
               var32 = var32 < 167 ? (var32 + 1 ^ 1) - 1 : var32 ^ 1;
               Label var34 = this.createLabel(var30 + 3, var12);
               var1.visitJumpInsn(var32, var34);
               var1.visitJumpInsn(200, var33);
               var48 = true;
            } else {
               var1.visitJumpInsn(var32 + 33, var33);
            }

            var4 += 3;
            break;
         case 220:
            var1.visitJumpInsn(200, var12[var30 + this.readInt(var4 + 1)]);
            var48 = true;
            var4 += 5;
            break;
         default:
            throw new AssertionError();
         }

         while(var19 != null && var24 < var19.length && var47 <= var30) {
            if (var47 == var30) {
               var52 = this.readTypeAnnotationTarget(var2, var19[var24]);
               var55 = this.readUTF8(var52, var6);
               var52 += 2;
               this.readElementValues(var1.visitInsnAnnotation(var2.currentTypeAnnotationTarget, var2.currentTypeAnnotationTargetPath, var55, true), var52, true, var6);
            }

            ++var24;
            var47 = this.getTypeAnnotationBytecodeOffset(var19, var24);
         }

         while(var20 != null && var26 < var20.length && var27 <= var30) {
            if (var27 == var30) {
               var52 = this.readTypeAnnotationTarget(var2, var20[var26]);
               var55 = this.readUTF8(var52, var6);
               var52 += 2;
               this.readElementValues(var1.visitInsnAnnotation(var2.currentTypeAnnotationTarget, var2.currentTypeAnnotationTargetPath, var55, false), var52, true, var6);
            }

            ++var26;
            var27 = this.getTypeAnnotationBytecodeOffset(var20, var26);
         }
      }

      if (var12[var9] != null) {
         var1.visitLabel(var12[var9]);
      }

      int[] var49;
      int var50;
      if (var45 != 0 && (var2.parsingOptions & 2) == 0) {
         var49 = null;
         if (var18 != 0) {
            var49 = new int[this.readUnsignedShort(var18) * 3];
            var4 = var18 + 2;

            for(var50 = var49.length; var50 > 0; var4 += 10) {
               --var50;
               var49[var50] = var4 + 6;
               --var50;
               var49[var50] = this.readUnsignedShort(var4 + 8);
               --var50;
               var49[var50] = this.readUnsignedShort(var4);
            }
         }

         var50 = this.readUnsignedShort(var45);

         int var58;
         for(var4 = var45 + 2; var50-- > 0; var1.visitLocalVariable(var55, var35, var59, var12[var32], var12[var32 + var52], var58)) {
            var32 = this.readUnsignedShort(var4);
            var52 = this.readUnsignedShort(var4 + 2);
            var55 = this.readUTF8(var4 + 4, var6);
            var35 = this.readUTF8(var4 + 6, var6);
            var58 = this.readUnsignedShort(var4 + 8);
            var4 += 10;
            var59 = null;
            if (var49 != null) {
               for(int var61 = 0; var61 < var49.length; var61 += 3) {
                  if (var49[var61] == var32 && var49[var61 + 1] == var58) {
                     var59 = this.readUTF8(var49[var61 + 2], var6);
                     break;
                  }
               }
            }
         }
      }

      if (var19 != null) {
         var49 = var19;
         var50 = var19.length;

         for(var32 = 0; var32 < var50; ++var32) {
            var52 = var49[var32];
            var53 = this.readByte(var52);
            if (var53 == 64 || var53 == 65) {
               var4 = this.readTypeAnnotationTarget(var2, var52);
               var35 = this.readUTF8(var4, var6);
               var4 += 2;
               this.readElementValues(var1.visitLocalVariableAnnotation(var2.currentTypeAnnotationTarget, var2.currentTypeAnnotationTargetPath, var2.currentLocalVariableAnnotationRangeStarts, var2.currentLocalVariableAnnotationRangeEnds, var2.currentLocalVariableAnnotationRangeIndices, var35, true), var4, true, var6);
            }
         }
      }

      if (var20 != null) {
         var49 = var20;
         var50 = var20.length;

         for(var32 = 0; var32 < var50; ++var32) {
            var52 = var49[var32];
            var53 = this.readByte(var52);
            if (var53 == 64 || var53 == 65) {
               var4 = this.readTypeAnnotationTarget(var2, var52);
               var35 = this.readUTF8(var4, var6);
               var4 += 2;
               this.readElementValues(var1.visitLocalVariableAnnotation(var2.currentTypeAnnotationTarget, var2.currentTypeAnnotationTargetPath, var2.currentLocalVariableAnnotationRangeStarts, var2.currentLocalVariableAnnotationRangeEnds, var2.currentLocalVariableAnnotationRangeIndices, var35, false), var4, true, var6);
            }
         }
      }

      while(var21 != null) {
         Attribute var51 = var21.nextAttribute;
         var21.nextAttribute = null;
         var1.visitAttribute(var21);
         var21 = var51;
      }

      var1.visitMaxs(var7, var8);
   }

   protected Label readLabel(int var1, Label[] var2) {
      if (var2[var1] == null) {
         var2[var1] = new Label();
      }

      return var2[var1];
   }

   private Label createLabel(int var1, Label[] var2) {
      Label var3 = this.readLabel(var1, var2);
      var3.flags = (short)(var3.flags & -2);
      return var3;
   }

   private void createDebugLabel(int var1, Label[] var2) {
      if (var2[var1] == null) {
         Label var10000 = this.readLabel(var1, var2);
         var10000.flags = (short)(var10000.flags | 1);
      }

   }

   private int[] readTypeAnnotations(MethodVisitor var1, Context var2, int var3, boolean var4) {
      char[] var5 = var2.charBuffer;
      int[] var7 = new int[this.readUnsignedShort(var3)];
      int var6 = var3 + 2;

      for(int var8 = 0; var8 < var7.length; ++var8) {
         int var9;
         int var10;
         var7[var8] = var6;
         var9 = this.readInt(var6);
         label32:
         switch(var9 >>> 24) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 19:
         case 20:
         case 21:
         case 22:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         default:
            throw new IllegalArgumentException();
         case 16:
         case 17:
         case 18:
         case 23:
         case 66:
         case 67:
         case 68:
         case 69:
         case 70:
            var6 += 3;
            break;
         case 64:
         case 65:
            var10 = this.readUnsignedShort(var6 + 1);
            var6 += 3;

            while(true) {
               if (var10-- <= 0) {
                  break label32;
               }

               int var11 = this.readUnsignedShort(var6);
               int var12 = this.readUnsignedShort(var6 + 2);
               var6 += 6;
               this.createLabel(var11, var2.currentMethodLabels);
               this.createLabel(var11 + var12, var2.currentMethodLabels);
            }
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
            var6 += 4;
         }

         var10 = this.readByte(var6);
         if (var9 >>> 24 == 66) {
            TypePath var13 = var10 == 0 ? null : new TypePath(this.classFileBuffer, var6);
            var6 += 1 + 2 * var10;
            String var14 = this.readUTF8(var6, var5);
            var6 += 2;
            var6 = this.readElementValues(var1.visitTryCatchAnnotation(var9 & -256, var13, var14, var4), var6, true, var5);
         } else {
            var6 += 3 + 2 * var10;
            var6 = this.readElementValues((AnnotationVisitor)null, var6, true, var5);
         }
      }

      return var7;
   }

   private int getTypeAnnotationBytecodeOffset(int[] var1, int var2) {
      return var1 != null && var2 < var1.length && this.readByte(var1[var2]) >= 67 ? this.readUnsignedShort(var1[var2] + 1) : -1;
   }

   private int readTypeAnnotationTarget(Context var1, int var2) {
      int var3;
      int var4;
      int var5;
      var4 = this.readInt(var2);
      label26:
      switch(var4 >>> 24) {
      case 0:
      case 1:
      case 22:
         var4 &= -65536;
         var3 = var2 + 2;
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      default:
         throw new IllegalArgumentException();
      case 16:
      case 17:
      case 18:
      case 23:
      case 66:
         var4 &= -256;
         var3 = var2 + 3;
         break;
      case 19:
      case 20:
      case 21:
         var4 &= -16777216;
         var3 = var2 + 1;
         break;
      case 64:
      case 65:
         var4 &= -16777216;
         var5 = this.readUnsignedShort(var2 + 1);
         var3 = var2 + 3;
         var1.currentLocalVariableAnnotationRangeStarts = new Label[var5];
         var1.currentLocalVariableAnnotationRangeEnds = new Label[var5];
         var1.currentLocalVariableAnnotationRangeIndices = new int[var5];
         int var6 = 0;

         while(true) {
            if (var6 >= var5) {
               break label26;
            }

            int var7 = this.readUnsignedShort(var3);
            int var8 = this.readUnsignedShort(var3 + 2);
            int var9 = this.readUnsignedShort(var3 + 4);
            var3 += 6;
            var1.currentLocalVariableAnnotationRangeStarts[var6] = this.createLabel(var7, var1.currentMethodLabels);
            var1.currentLocalVariableAnnotationRangeEnds[var6] = this.createLabel(var7 + var8, var1.currentMethodLabels);
            var1.currentLocalVariableAnnotationRangeIndices[var6] = var9;
            ++var6;
         }
      case 67:
      case 68:
      case 69:
      case 70:
         var4 &= -16777216;
         var3 = var2 + 3;
         break;
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
         var4 &= -16776961;
         var3 = var2 + 4;
      }

      var1.currentTypeAnnotationTarget = var4;
      var5 = this.readByte(var3);
      var1.currentTypeAnnotationTargetPath = var5 == 0 ? null : new TypePath(this.classFileBuffer, var3);
      return var3 + 1 + 2 * var5;
   }

   private void readParameterAnnotations(MethodVisitor var1, Context var2, int var3, boolean var4) {
      int var5 = var3 + 1;
      int var6 = this.classFileBuffer[var3] & 255;
      var1.visitAnnotableParameterCount(var6, var4);
      char[] var7 = var2.charBuffer;

      for(int var8 = 0; var8 < var6; ++var8) {
         int var9 = this.readUnsignedShort(var5);

         String var10;
         for(var5 += 2; var9-- > 0; var5 = this.readElementValues(var1.visitParameterAnnotation(var8, var10, var4), var5, true, var7)) {
            var10 = this.readUTF8(var5, var7);
            var5 += 2;
         }
      }

   }

   private int readElementValues(AnnotationVisitor var1, int var2, boolean var3, char[] var4) {
      int var6 = this.readUnsignedShort(var2);
      int var5 = var2 + 2;
      if (var3) {
         while(var6-- > 0) {
            String var7 = this.readUTF8(var5, var4);
            var5 = this.readElementValue(var1, var5 + 2, var7, var4);
         }
      } else {
         while(var6-- > 0) {
            var5 = this.readElementValue(var1, var5, (String)null, var4);
         }
      }

      if (var1 != null) {
         var1.visitEnd();
      }

      return var5;
   }

   private int readElementValue(AnnotationVisitor var1, int var2, String var3, char[] var4) {
      if (var1 == null) {
         switch(this.classFileBuffer[var2] & 255) {
         case 64:
            return this.readElementValues((AnnotationVisitor)null, var2 + 3, true, var4);
         case 91:
            return this.readElementValues((AnnotationVisitor)null, var2 + 1, false, var4);
         case 101:
            return var2 + 5;
         default:
            return var2 + 3;
         }
      } else {
         int var5 = var2 + 1;
         switch(this.classFileBuffer[var2] & 255) {
         case 64:
            var5 = this.readElementValues(var1.visitAnnotation(var3, this.readUTF8(var5, var4)), var5 + 2, true, var4);
            break;
         case 65:
         case 69:
         case 71:
         case 72:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 100:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         default:
            throw new IllegalArgumentException();
         case 66:
            var1.visit(var3, (byte)this.readInt(this.cpInfoOffsets[this.readUnsignedShort(var5)]));
            var5 += 2;
            break;
         case 67:
            var1.visit(var3, (char)this.readInt(this.cpInfoOffsets[this.readUnsignedShort(var5)]));
            var5 += 2;
            break;
         case 68:
         case 70:
         case 73:
         case 74:
            var1.visit(var3, this.readConst(this.readUnsignedShort(var5), var4));
            var5 += 2;
            break;
         case 83:
            var1.visit(var3, (short)this.readInt(this.cpInfoOffsets[this.readUnsignedShort(var5)]));
            var5 += 2;
            break;
         case 90:
            var1.visit(var3, this.readInt(this.cpInfoOffsets[this.readUnsignedShort(var5)]) == 0 ? Boolean.FALSE : Boolean.TRUE);
            var5 += 2;
            break;
         case 91:
            int var6 = this.readUnsignedShort(var5);
            var5 += 2;
            if (var6 == 0) {
               return this.readElementValues(var1.visitArray(var3), var5 - 2, false, var4);
            }

            switch(this.classFileBuffer[var5] & 255) {
            case 66:
               byte[] var7 = new byte[var6];

               for(int var16 = 0; var16 < var6; ++var16) {
                  var7[var16] = (byte)this.readInt(this.cpInfoOffsets[this.readUnsignedShort(var5 + 1)]);
                  var5 += 3;
               }

               var1.visit(var3, var7);
               return var5;
            case 67:
               char[] var18 = new char[var6];

               for(int var19 = 0; var19 < var6; ++var19) {
                  var18[var19] = (char)this.readInt(this.cpInfoOffsets[this.readUnsignedShort(var5 + 1)]);
                  var5 += 3;
               }

               var1.visit(var3, var18);
               return var5;
            case 68:
               double[] var22 = new double[var6];

               for(int var15 = 0; var15 < var6; ++var15) {
                  var22[var15] = Double.longBitsToDouble(this.readLong(this.cpInfoOffsets[this.readUnsignedShort(var5 + 1)]));
                  var5 += 3;
               }

               var1.visit(var3, var22);
               return var5;
            case 69:
            case 71:
            case 72:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            default:
               var5 = this.readElementValues(var1.visitArray(var3), var5 - 2, false, var4);
               return var5;
            case 70:
               float[] var21 = new float[var6];

               for(int var14 = 0; var14 < var6; ++var14) {
                  var21[var14] = Float.intBitsToFloat(this.readInt(this.cpInfoOffsets[this.readUnsignedShort(var5 + 1)]));
                  var5 += 3;
               }

               var1.visit(var3, var21);
               return var5;
            case 73:
               int[] var11 = new int[var6];

               for(int var20 = 0; var20 < var6; ++var20) {
                  var11[var20] = this.readInt(this.cpInfoOffsets[this.readUnsignedShort(var5 + 1)]);
                  var5 += 3;
               }

               var1.visit(var3, var11);
               return var5;
            case 74:
               long[] var12 = new long[var6];

               for(int var13 = 0; var13 < var6; ++var13) {
                  var12[var13] = this.readLong(this.cpInfoOffsets[this.readUnsignedShort(var5 + 1)]);
                  var5 += 3;
               }

               var1.visit(var3, var12);
               return var5;
            case 83:
               short[] var17 = new short[var6];

               for(int var10 = 0; var10 < var6; ++var10) {
                  var17[var10] = (short)this.readInt(this.cpInfoOffsets[this.readUnsignedShort(var5 + 1)]);
                  var5 += 3;
               }

               var1.visit(var3, var17);
               return var5;
            case 90:
               boolean[] var8 = new boolean[var6];

               for(int var9 = 0; var9 < var6; ++var9) {
                  var8[var9] = this.readInt(this.cpInfoOffsets[this.readUnsignedShort(var5 + 1)]) != 0;
                  var5 += 3;
               }

               var1.visit(var3, var8);
               return var5;
            }
         case 99:
            var1.visit(var3, Type.getType(this.readUTF8(var5, var4)));
            var5 += 2;
            break;
         case 101:
            var1.visitEnum(var3, this.readUTF8(var5, var4), this.readUTF8(var5 + 2, var4));
            var5 += 4;
            break;
         case 115:
            var1.visit(var3, this.readUTF8(var5, var4));
            var5 += 2;
         }

         return var5;
      }
   }

   private void computeImplicitFrame(Context var1) {
      String var2 = var1.currentMethodDescriptor;
      Object[] var3 = var1.currentFrameLocalTypes;
      int var4 = 0;
      if ((var1.currentMethodAccessFlags & 8) == 0) {
         if ("<init>".equals(var1.currentMethodName)) {
            var3[var4++] = Opcodes.UNINITIALIZED_THIS;
         } else {
            var3[var4++] = this.readClass(this.header + 2, var1.charBuffer);
         }
      }

      int var5 = 1;

      while(true) {
         int var6 = var5;
         switch(var2.charAt(var5++)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            var3[var4++] = Opcodes.INTEGER;
            break;
         case 'D':
            var3[var4++] = Opcodes.DOUBLE;
            break;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            var1.currentFrameLocalCount = var4;
            return;
         case 'F':
            var3[var4++] = Opcodes.FLOAT;
            break;
         case 'J':
            var3[var4++] = Opcodes.LONG;
            break;
         case 'L':
            while(var2.charAt(var5) != ';') {
               ++var5;
            }

            var3[var4++] = var2.substring(var6 + 1, var5++);
            break;
         case '[':
            while(var2.charAt(var5) == '[') {
               ++var5;
            }

            if (var2.charAt(var5) == 'L') {
               ++var5;

               while(var2.charAt(var5) != ';') {
                  ++var5;
               }
            }

            int var10001 = var4++;
            ++var5;
            var3[var10001] = var2.substring(var6, var5);
         }
      }
   }

   private int readStackMapFrame(int var1, boolean var2, boolean var3, Context var4) {
      int var5 = var1;
      char[] var6 = var4.charBuffer;
      Label[] var7 = var4.currentMethodLabels;
      int var8;
      if (var2) {
         var5 = var1 + 1;
         var8 = this.classFileBuffer[var1] & 255;
      } else {
         var8 = 255;
         var4.currentFrameOffset = -1;
      }

      var4.currentFrameLocalCountDelta = 0;
      int var9;
      if (var8 < 64) {
         var9 = var8;
         var4.currentFrameType = 3;
         var4.currentFrameStackCount = 0;
      } else if (var8 < 128) {
         var9 = var8 - 64;
         var5 = this.readVerificationTypeInfo(var5, var4.currentFrameStackTypes, 0, var6, var7);
         var4.currentFrameType = 4;
         var4.currentFrameStackCount = 1;
      } else {
         if (var8 < 247) {
            throw new IllegalArgumentException();
         }

         var9 = this.readUnsignedShort(var5);
         var5 += 2;
         if (var8 == 247) {
            var5 = this.readVerificationTypeInfo(var5, var4.currentFrameStackTypes, 0, var6, var7);
            var4.currentFrameType = 4;
            var4.currentFrameStackCount = 1;
         } else if (var8 >= 248 && var8 < 251) {
            var4.currentFrameType = 2;
            var4.currentFrameLocalCountDelta = 251 - var8;
            var4.currentFrameLocalCount -= var4.currentFrameLocalCountDelta;
            var4.currentFrameStackCount = 0;
         } else if (var8 == 251) {
            var4.currentFrameType = 3;
            var4.currentFrameStackCount = 0;
         } else {
            int var10;
            int var11;
            if (var8 < 255) {
               var10 = var3 ? var4.currentFrameLocalCount : 0;

               for(var11 = var8 - 251; var11 > 0; --var11) {
                  var5 = this.readVerificationTypeInfo(var5, var4.currentFrameLocalTypes, var10++, var6, var7);
               }

               var4.currentFrameType = 1;
               var4.currentFrameLocalCountDelta = var8 - 251;
               var4.currentFrameLocalCount += var4.currentFrameLocalCountDelta;
               var4.currentFrameStackCount = 0;
            } else {
               var10 = this.readUnsignedShort(var5);
               var5 += 2;
               var4.currentFrameType = 0;
               var4.currentFrameLocalCountDelta = var10;
               var4.currentFrameLocalCount = var10;

               for(var11 = 0; var11 < var10; ++var11) {
                  var5 = this.readVerificationTypeInfo(var5, var4.currentFrameLocalTypes, var11, var6, var7);
               }

               var11 = this.readUnsignedShort(var5);
               var5 += 2;
               var4.currentFrameStackCount = var11;

               for(int var12 = 0; var12 < var11; ++var12) {
                  var5 = this.readVerificationTypeInfo(var5, var4.currentFrameStackTypes, var12, var6, var7);
               }
            }
         }
      }

      var4.currentFrameOffset += var9 + 1;
      this.createLabel(var4.currentFrameOffset, var7);
      return var5;
   }

   private int readVerificationTypeInfo(int var1, Object[] var2, int var3, char[] var4, Label[] var5) {
      int var6 = var1 + 1;
      int var7 = this.classFileBuffer[var1] & 255;
      switch(var7) {
      case 0:
         var2[var3] = Opcodes.TOP;
         break;
      case 1:
         var2[var3] = Opcodes.INTEGER;
         break;
      case 2:
         var2[var3] = Opcodes.FLOAT;
         break;
      case 3:
         var2[var3] = Opcodes.DOUBLE;
         break;
      case 4:
         var2[var3] = Opcodes.LONG;
         break;
      case 5:
         var2[var3] = Opcodes.NULL;
         break;
      case 6:
         var2[var3] = Opcodes.UNINITIALIZED_THIS;
         break;
      case 7:
         var2[var3] = this.readClass(var6, var4);
         var6 += 2;
         break;
      case 8:
         var2[var3] = this.createLabel(this.readUnsignedShort(var6), var5);
         var6 += 2;
         break;
      default:
         throw new IllegalArgumentException();
      }

      return var6;
   }

   final int getFirstAttributeOffset() {
      int var1 = this.header + 8 + this.readUnsignedShort(this.header + 6) * 2;
      int var2 = this.readUnsignedShort(var1);
      var1 += 2;

      int var3;
      while(var2-- > 0) {
         var3 = this.readUnsignedShort(var1 + 6);

         for(var1 += 8; var3-- > 0; var1 += 6 + this.readInt(var1 + 2)) {
         }
      }

      var3 = this.readUnsignedShort(var1);
      var1 += 2;

      while(var3-- > 0) {
         int var4 = this.readUnsignedShort(var1 + 6);

         for(var1 += 8; var4-- > 0; var1 += 6 + this.readInt(var1 + 2)) {
         }
      }

      return var1 + 2;
   }

   private int[] readBootstrapMethodsAttribute(int var1) {
      char[] var2 = new char[var1];
      int var3 = this.getFirstAttributeOffset();
      Object var4 = null;

      for(int var5 = this.readUnsignedShort(var3 - 2); var5 > 0; --var5) {
         String var6 = this.readUTF8(var3, var2);
         int var7 = this.readInt(var3 + 2);
         var3 += 6;
         if ("BootstrapMethods".equals(var6)) {
            int[] var10 = new int[this.readUnsignedShort(var3)];
            int var8 = var3 + 2;

            for(int var9 = 0; var9 < var10.length; ++var9) {
               var10[var9] = var8;
               var8 += 4 + this.readUnsignedShort(var8 + 2) * 2;
            }

            return var10;
         }

         var3 += var7;
      }

      throw new IllegalArgumentException();
   }

   private Attribute readAttribute(Attribute[] var1, String var2, int var3, int var4, char[] var5, int var6, Label[] var7) {
      Attribute[] var8 = var1;
      int var9 = var1.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         Attribute var11 = var8[var10];
         if (var11.type.equals(var2)) {
            return var11.read(this, var3, var4, var5, var6, var7);
         }
      }

      return (new Attribute(var2)).read(this, var3, var4, (char[])null, -1, (Label[])null);
   }

   public int getItemCount() {
      return this.cpInfoOffsets.length;
   }

   public int getItem(int var1) {
      return this.cpInfoOffsets[var1];
   }

   public int getMaxStringLength() {
      return this.maxStringLength;
   }

   public int readByte(int var1) {
      return this.classFileBuffer[var1] & 255;
   }

   public int readUnsignedShort(int var1) {
      byte[] var2 = this.classFileBuffer;
      return (var2[var1] & 255) << 8 | var2[var1 + 1] & 255;
   }

   public short readShort(int var1) {
      byte[] var2 = this.classFileBuffer;
      return (short)((var2[var1] & 255) << 8 | var2[var1 + 1] & 255);
   }

   public int readInt(int var1) {
      byte[] var2 = this.classFileBuffer;
      return (var2[var1] & 255) << 24 | (var2[var1 + 1] & 255) << 16 | (var2[var1 + 2] & 255) << 8 | var2[var1 + 3] & 255;
   }

   public long readLong(int var1) {
      long var2 = (long)this.readInt(var1);
      long var4 = (long)this.readInt(var1 + 4) & 4294967295L;
      return var2 << 32 | var4;
   }

   public String readUTF8(int var1, char[] var2) {
      int var3 = this.readUnsignedShort(var1);
      return var1 != 0 && var3 != 0 ? this.readUtf(var3, var2) : null;
   }

   final String readUtf(int var1, char[] var2) {
      String var3 = this.constantUtf8Values[var1];
      if (var3 != null) {
         return var3;
      } else {
         int var4 = this.cpInfoOffsets[var1];
         return this.constantUtf8Values[var1] = this.readUtf(var4 + 2, this.readUnsignedShort(var4), var2);
      }
   }

   private String readUtf(int var1, int var2, char[] var3) {
      int var4 = var1;
      int var5 = var1 + var2;
      int var6 = 0;
      byte[] var7 = this.classFileBuffer;

      while(var4 < var5) {
         byte var8 = var7[var4++];
         if ((var8 & 128) == 0) {
            var3[var6++] = (char)(var8 & 127);
         } else if ((var8 & 224) == 192) {
            var3[var6++] = (char)(((var8 & 31) << 6) + (var7[var4++] & 63));
         } else {
            var3[var6++] = (char)(((var8 & 15) << 12) + ((var7[var4++] & 63) << 6) + (var7[var4++] & 63));
         }
      }

      return new String(var3, 0, var6);
   }

   private String readStringish(int var1, char[] var2) {
      return this.readUTF8(this.cpInfoOffsets[this.readUnsignedShort(var1)], var2);
   }

   public String readClass(int var1, char[] var2) {
      return this.readStringish(var1, var2);
   }

   public String readModule(int var1, char[] var2) {
      return this.readStringish(var1, var2);
   }

   public String readPackage(int var1, char[] var2) {
      return this.readStringish(var1, var2);
   }

   private ConstantDynamic readConstantDynamic(int var1, char[] var2) {
      ConstantDynamic var3 = this.constantDynamicValues[var1];
      if (var3 != null) {
         return var3;
      } else {
         int var4 = this.cpInfoOffsets[var1];
         int var5 = this.cpInfoOffsets[this.readUnsignedShort(var4 + 2)];
         String var6 = this.readUTF8(var5, var2);
         String var7 = this.readUTF8(var5 + 2, var2);
         int var8 = this.bootstrapMethodOffsets[this.readUnsignedShort(var4)];
         Handle var9 = (Handle)this.readConst(this.readUnsignedShort(var8), var2);
         Object[] var10 = new Object[this.readUnsignedShort(var8 + 2)];
         var8 += 4;

         for(int var11 = 0; var11 < var10.length; ++var11) {
            var10[var11] = this.readConst(this.readUnsignedShort(var8), var2);
            var8 += 2;
         }

         return this.constantDynamicValues[var1] = new ConstantDynamic(var6, var7, var9, var10);
      }
   }

   public Object readConst(int var1, char[] var2) {
      int var3 = this.cpInfoOffsets[var1];
      switch(this.classFileBuffer[var3 - 1]) {
      case 3:
         return this.readInt(var3);
      case 4:
         return Float.intBitsToFloat(this.readInt(var3));
      case 5:
         return this.readLong(var3);
      case 6:
         return Double.longBitsToDouble(this.readLong(var3));
      case 7:
         return Type.getObjectType(this.readUTF8(var3, var2));
      case 8:
         return this.readUTF8(var3, var2);
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      default:
         throw new IllegalArgumentException();
      case 15:
         int var4 = this.readByte(var3);
         int var5 = this.cpInfoOffsets[this.readUnsignedShort(var3 + 1)];
         int var6 = this.cpInfoOffsets[this.readUnsignedShort(var5 + 2)];
         String var7 = this.readClass(var5, var2);
         String var8 = this.readUTF8(var6, var2);
         String var9 = this.readUTF8(var6 + 2, var2);
         boolean var10 = this.classFileBuffer[var5 - 1] == 11;
         return new Handle(var4, var7, var8, var9, var10);
      case 16:
         return Type.getMethodType(this.readUTF8(var3, var2));
      case 17:
         return this.readConstantDynamic(var1, var2);
      }
   }
}
