package org.objectweb.asm;

public class ClassWriter extends ClassVisitor {
   public static final int COMPUTE_MAXS = 1;
   public static final int COMPUTE_FRAMES = 2;
   private int version;
   private final SymbolTable symbolTable;
   private int accessFlags;
   private int thisClass;
   private int superClass;
   private int interfaceCount;
   private int[] interfaces;
   private FieldWriter firstField;
   private FieldWriter lastField;
   private MethodWriter firstMethod;
   private MethodWriter lastMethod;
   private int numberOfInnerClasses;
   private ByteVector innerClasses;
   private int enclosingClassIndex;
   private int enclosingMethodIndex;
   private int signatureIndex;
   private int sourceFileIndex;
   private ByteVector debugExtension;
   private AnnotationWriter lastRuntimeVisibleAnnotation;
   private AnnotationWriter lastRuntimeInvisibleAnnotation;
   private AnnotationWriter lastRuntimeVisibleTypeAnnotation;
   private AnnotationWriter lastRuntimeInvisibleTypeAnnotation;
   private ModuleWriter moduleWriter;
   private int nestHostClassIndex;
   private int numberOfNestMemberClasses;
   private ByteVector nestMemberClasses;
   private Attribute firstAttribute;
   private int compute;

   public ClassWriter(int var1) {
      this((ClassReader)null, var1);
   }

   public ClassWriter(ClassReader var1, int var2) {
      super(458752);
      this.symbolTable = var1 == null ? new SymbolTable(this) : new SymbolTable(this, var1);
      if ((var2 & 2) != 0) {
         this.compute = 4;
      } else if ((var2 & 1) != 0) {
         this.compute = 1;
      } else {
         this.compute = 0;
      }

   }

   public final void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.version = var1;
      this.accessFlags = var2;
      this.thisClass = this.symbolTable.setMajorVersionAndClassName(var1 & '\uffff', var3);
      if (var4 != null) {
         this.signatureIndex = this.symbolTable.addConstantUtf8(var4);
      }

      this.superClass = var5 == null ? 0 : this.symbolTable.addConstantClass(var5).index;
      if (var6 != null && var6.length > 0) {
         this.interfaceCount = var6.length;
         this.interfaces = new int[this.interfaceCount];

         for(int var7 = 0; var7 < this.interfaceCount; ++var7) {
            this.interfaces[var7] = this.symbolTable.addConstantClass(var6[var7]).index;
         }
      }

      if (this.compute == 1 && (var1 & '\uffff') >= 51) {
         this.compute = 2;
      }

   }

   public final void visitSource(String var1, String var2) {
      if (var1 != null) {
         this.sourceFileIndex = this.symbolTable.addConstantUtf8(var1);
      }

      if (var2 != null) {
         this.debugExtension = (new ByteVector()).encodeUtf8(var2, 0, 0);
      }

   }

   public final ModuleVisitor visitModule(String var1, int var2, String var3) {
      return this.moduleWriter = new ModuleWriter(this.symbolTable, this.symbolTable.addConstantModule(var1).index, var2, var3 == null ? 0 : this.symbolTable.addConstantUtf8(var3));
   }

   public void visitNestHost(String var1) {
      this.nestHostClassIndex = this.symbolTable.addConstantClass(var1).index;
   }

   public final void visitOuterClass(String var1, String var2, String var3) {
      this.enclosingClassIndex = this.symbolTable.addConstantClass(var1).index;
      if (var2 != null && var3 != null) {
         this.enclosingMethodIndex = this.symbolTable.addConstantNameAndType(var2, var3);
      }

   }

   public final AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      return var2 ? (this.lastRuntimeVisibleAnnotation = AnnotationWriter.create(this.symbolTable, var1, this.lastRuntimeVisibleAnnotation)) : (this.lastRuntimeInvisibleAnnotation = AnnotationWriter.create(this.symbolTable, var1, this.lastRuntimeInvisibleAnnotation));
   }

   public final AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return var4 ? (this.lastRuntimeVisibleTypeAnnotation = AnnotationWriter.create(this.symbolTable, var1, var2, var3, this.lastRuntimeVisibleTypeAnnotation)) : (this.lastRuntimeInvisibleTypeAnnotation = AnnotationWriter.create(this.symbolTable, var1, var2, var3, this.lastRuntimeInvisibleTypeAnnotation));
   }

   public final void visitAttribute(Attribute var1) {
      var1.nextAttribute = this.firstAttribute;
      this.firstAttribute = var1;
   }

   public void visitNestMember(String var1) {
      if (this.nestMemberClasses == null) {
         this.nestMemberClasses = new ByteVector();
      }

      ++this.numberOfNestMemberClasses;
      this.nestMemberClasses.putShort(this.symbolTable.addConstantClass(var1).index);
   }

   public final void visitInnerClass(String var1, String var2, String var3, int var4) {
      if (this.innerClasses == null) {
         this.innerClasses = new ByteVector();
      }

      Symbol var5 = this.symbolTable.addConstantClass(var1);
      if (var5.info == 0) {
         ++this.numberOfInnerClasses;
         this.innerClasses.putShort(var5.index);
         this.innerClasses.putShort(var2 == null ? 0 : this.symbolTable.addConstantClass(var2).index);
         this.innerClasses.putShort(var3 == null ? 0 : this.symbolTable.addConstantUtf8(var3));
         this.innerClasses.putShort(var4);
         var5.info = this.numberOfInnerClasses;
      }

   }

   public final FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      FieldWriter var6 = new FieldWriter(this.symbolTable, var1, var2, var3, var4, var5);
      if (this.firstField == null) {
         this.firstField = var6;
      } else {
         this.lastField.fv = var6;
      }

      return this.lastField = var6;
   }

   public final MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodWriter var6 = new MethodWriter(this.symbolTable, var1, var2, var3, var4, var5, this.compute);
      if (this.firstMethod == null) {
         this.firstMethod = var6;
      } else {
         this.lastMethod.mv = var6;
      }

      return this.lastMethod = var6;
   }

   public final void visitEnd() {
   }

   public byte[] toByteArray() {
      int var1 = 24 + 2 * this.interfaceCount;
      int var2 = 0;

      FieldWriter var3;
      for(var3 = this.firstField; var3 != null; var3 = (FieldWriter)var3.fv) {
         ++var2;
         var1 += var3.computeFieldInfoSize();
      }

      int var4 = 0;

      MethodWriter var5;
      for(var5 = this.firstMethod; var5 != null; var5 = (MethodWriter)var5.mv) {
         ++var4;
         var1 += var5.computeMethodInfoSize();
      }

      int var6 = 0;
      if (this.innerClasses != null) {
         ++var6;
         var1 += 8 + this.innerClasses.length;
         this.symbolTable.addConstantUtf8("InnerClasses");
      }

      if (this.enclosingClassIndex != 0) {
         ++var6;
         var1 += 10;
         this.symbolTable.addConstantUtf8("EnclosingMethod");
      }

      if ((this.accessFlags & 4096) != 0 && (this.version & '\uffff') < 49) {
         ++var6;
         var1 += 6;
         this.symbolTable.addConstantUtf8("Synthetic");
      }

      if (this.signatureIndex != 0) {
         ++var6;
         var1 += 8;
         this.symbolTable.addConstantUtf8("Signature");
      }

      if (this.sourceFileIndex != 0) {
         ++var6;
         var1 += 8;
         this.symbolTable.addConstantUtf8("SourceFile");
      }

      if (this.debugExtension != null) {
         ++var6;
         var1 += 6 + this.debugExtension.length;
         this.symbolTable.addConstantUtf8("SourceDebugExtension");
      }

      if ((this.accessFlags & 131072) != 0) {
         ++var6;
         var1 += 6;
         this.symbolTable.addConstantUtf8("Deprecated");
      }

      if (this.lastRuntimeVisibleAnnotation != null) {
         ++var6;
         var1 += this.lastRuntimeVisibleAnnotation.computeAnnotationsSize("RuntimeVisibleAnnotations");
      }

      if (this.lastRuntimeInvisibleAnnotation != null) {
         ++var6;
         var1 += this.lastRuntimeInvisibleAnnotation.computeAnnotationsSize("RuntimeInvisibleAnnotations");
      }

      if (this.lastRuntimeVisibleTypeAnnotation != null) {
         ++var6;
         var1 += this.lastRuntimeVisibleTypeAnnotation.computeAnnotationsSize("RuntimeVisibleTypeAnnotations");
      }

      if (this.lastRuntimeInvisibleTypeAnnotation != null) {
         ++var6;
         var1 += this.lastRuntimeInvisibleTypeAnnotation.computeAnnotationsSize("RuntimeInvisibleTypeAnnotations");
      }

      if (this.symbolTable.computeBootstrapMethodsSize() > 0) {
         ++var6;
         var1 += this.symbolTable.computeBootstrapMethodsSize();
      }

      if (this.moduleWriter != null) {
         var6 += this.moduleWriter.getAttributeCount();
         var1 += this.moduleWriter.computeAttributesSize();
      }

      if (this.nestHostClassIndex != 0) {
         ++var6;
         var1 += 8;
         this.symbolTable.addConstantUtf8("NestHost");
      }

      if (this.nestMemberClasses != null) {
         ++var6;
         var1 += 8 + this.nestMemberClasses.length;
         this.symbolTable.addConstantUtf8("NestMembers");
      }

      if (this.firstAttribute != null) {
         var6 += this.firstAttribute.getAttributeCount();
         var1 += this.firstAttribute.computeAttributesSize(this.symbolTable);
      }

      var1 += this.symbolTable.getConstantPoolLength();
      int var7 = this.symbolTable.getConstantPoolCount();
      if (var7 > 65535) {
         throw new ClassTooLargeException(this.symbolTable.getClassName(), var7);
      } else {
         ByteVector var8 = new ByteVector(var1);
         var8.putInt(-889275714).putInt(this.version);
         this.symbolTable.putConstantPool(var8);
         int var9 = (this.version & '\uffff') < 49 ? 4096 : 0;
         var8.putShort(this.accessFlags & ~var9).putShort(this.thisClass).putShort(this.superClass);
         var8.putShort(this.interfaceCount);

         for(int var10 = 0; var10 < this.interfaceCount; ++var10) {
            var8.putShort(this.interfaces[var10]);
         }

         var8.putShort(var2);

         for(var3 = this.firstField; var3 != null; var3 = (FieldWriter)var3.fv) {
            var3.putFieldInfo(var8);
         }

         var8.putShort(var4);
         boolean var13 = false;
         boolean var11 = false;

         for(var5 = this.firstMethod; var5 != null; var5 = (MethodWriter)var5.mv) {
            var13 |= var5.hasFrames();
            var11 |= var5.hasAsmInstructions();
            var5.putMethodInfo(var8);
         }

         var8.putShort(var6);
         if (this.innerClasses != null) {
            var8.putShort(this.symbolTable.addConstantUtf8("InnerClasses")).putInt(this.innerClasses.length + 2).putShort(this.numberOfInnerClasses).putByteArray(this.innerClasses.data, 0, this.innerClasses.length);
         }

         if (this.enclosingClassIndex != 0) {
            var8.putShort(this.symbolTable.addConstantUtf8("EnclosingMethod")).putInt(4).putShort(this.enclosingClassIndex).putShort(this.enclosingMethodIndex);
         }

         if ((this.accessFlags & 4096) != 0 && (this.version & '\uffff') < 49) {
            var8.putShort(this.symbolTable.addConstantUtf8("Synthetic")).putInt(0);
         }

         if (this.signatureIndex != 0) {
            var8.putShort(this.symbolTable.addConstantUtf8("Signature")).putInt(2).putShort(this.signatureIndex);
         }

         if (this.sourceFileIndex != 0) {
            var8.putShort(this.symbolTable.addConstantUtf8("SourceFile")).putInt(2).putShort(this.sourceFileIndex);
         }

         if (this.debugExtension != null) {
            int var12 = this.debugExtension.length;
            var8.putShort(this.symbolTable.addConstantUtf8("SourceDebugExtension")).putInt(var12).putByteArray(this.debugExtension.data, 0, var12);
         }

         if ((this.accessFlags & 131072) != 0) {
            var8.putShort(this.symbolTable.addConstantUtf8("Deprecated")).putInt(0);
         }

         AnnotationWriter.putAnnotations(this.symbolTable, this.lastRuntimeVisibleAnnotation, this.lastRuntimeInvisibleAnnotation, this.lastRuntimeVisibleTypeAnnotation, this.lastRuntimeInvisibleTypeAnnotation, var8);
         this.symbolTable.putBootstrapMethods(var8);
         if (this.moduleWriter != null) {
            this.moduleWriter.putAttributes(var8);
         }

         if (this.nestHostClassIndex != 0) {
            var8.putShort(this.symbolTable.addConstantUtf8("NestHost")).putInt(2).putShort(this.nestHostClassIndex);
         }

         if (this.nestMemberClasses != null) {
            var8.putShort(this.symbolTable.addConstantUtf8("NestMembers")).putInt(this.nestMemberClasses.length + 2).putShort(this.numberOfNestMemberClasses).putByteArray(this.nestMemberClasses.data, 0, this.nestMemberClasses.length);
         }

         if (this.firstAttribute != null) {
            this.firstAttribute.putAttributes(this.symbolTable, var8);
         }

         return var11 ? this.replaceAsmInstructions(var8.data, var13) : var8.data;
      }
   }

   private byte[] replaceAsmInstructions(byte[] var1, boolean var2) {
      Attribute[] var3 = this.getAttributePrototypes();
      this.firstField = null;
      this.lastField = null;
      this.firstMethod = null;
      this.lastMethod = null;
      this.lastRuntimeVisibleAnnotation = null;
      this.lastRuntimeInvisibleAnnotation = null;
      this.lastRuntimeVisibleTypeAnnotation = null;
      this.lastRuntimeInvisibleTypeAnnotation = null;
      this.moduleWriter = null;
      this.nestHostClassIndex = 0;
      this.numberOfNestMemberClasses = 0;
      this.nestMemberClasses = null;
      this.firstAttribute = null;
      this.compute = var2 ? 3 : 0;
      (new ClassReader(var1, 0, false)).accept(this, var3, (var2 ? 8 : 0) | 256);
      return this.toByteArray();
   }

   private Attribute[] getAttributePrototypes() {
      Attribute$Set var1 = new Attribute$Set();
      var1.addAttributes(this.firstAttribute);

      for(FieldWriter var2 = this.firstField; var2 != null; var2 = (FieldWriter)var2.fv) {
         var2.collectAttributePrototypes(var1);
      }

      for(MethodWriter var3 = this.firstMethod; var3 != null; var3 = (MethodWriter)var3.mv) {
         var3.collectAttributePrototypes(var1);
      }

      return var1.toArray();
   }

   public int newConst(Object var1) {
      return this.symbolTable.addConstant(var1).index;
   }

   public int newUTF8(String var1) {
      return this.symbolTable.addConstantUtf8(var1);
   }

   public int newClass(String var1) {
      return this.symbolTable.addConstantClass(var1).index;
   }

   public int newMethodType(String var1) {
      return this.symbolTable.addConstantMethodType(var1).index;
   }

   public int newModule(String var1) {
      return this.symbolTable.addConstantModule(var1).index;
   }

   public int newPackage(String var1) {
      return this.symbolTable.addConstantPackage(var1).index;
   }

   /** @deprecated */
   @Deprecated
   public int newHandle(int var1, String var2, String var3, String var4) {
      return this.newHandle(var1, var2, var3, var4, var1 == 9);
   }

   public int newHandle(int var1, String var2, String var3, String var4, boolean var5) {
      return this.symbolTable.addConstantMethodHandle(var1, var2, var3, var4, var5).index;
   }

   public int newConstantDynamic(String var1, String var2, Handle var3, Object... var4) {
      return this.symbolTable.addConstantDynamic(var1, var2, var3, var4).index;
   }

   public int newInvokeDynamic(String var1, String var2, Handle var3, Object... var4) {
      return this.symbolTable.addConstantInvokeDynamic(var1, var2, var3, var4).index;
   }

   public int newField(String var1, String var2, String var3) {
      return this.symbolTable.addConstantFieldref(var1, var2, var3).index;
   }

   public int newMethod(String var1, String var2, String var3, boolean var4) {
      return this.symbolTable.addConstantMethodref(var1, var2, var3, var4).index;
   }

   public int newNameType(String var1, String var2) {
      return this.symbolTable.addConstantNameAndType(var1, var2);
   }

   protected String getCommonSuperClass(String var1, String var2) {
      ClassLoader var3 = this.getClassLoader();

      Class var4;
      try {
         var4 = Class.forName(var1.replace('/', '.'), false, var3);
      } catch (ClassNotFoundException var8) {
         throw new TypeNotPresentException(var1, var8);
      }

      Class var5;
      try {
         var5 = Class.forName(var2.replace('/', '.'), false, var3);
      } catch (ClassNotFoundException var7) {
         throw new TypeNotPresentException(var2, var7);
      }

      if (var4.isAssignableFrom(var5)) {
         return var1;
      } else if (var5.isAssignableFrom(var4)) {
         return var2;
      } else if (!var4.isInterface() && !var5.isInterface()) {
         do {
            var4 = var4.getSuperclass();
         } while(!var4.isAssignableFrom(var5));

         return var4.getName().replace('.', '/');
      } else {
         return "java/lang/Object";
      }
   }

   protected ClassLoader getClassLoader() {
      return this.getClass().getClassLoader();
   }
}
