package org.objectweb.asm.commons;

import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.TypePath;

public class ClassRemapper extends ClassVisitor {
   protected final Remapper remapper;
   protected String className;

   public ClassRemapper(ClassVisitor var1, Remapper var2) {
      this(458752, var1, var2);
   }

   protected ClassRemapper(int var1, ClassVisitor var2, Remapper var3) {
      super(var1, var2);
      this.remapper = var3;
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.className = var3;
      super.visit(var1, var2, this.remapper.mapType(var3), this.remapper.mapSignature(var4, false), this.remapper.mapType(var5), var6 == null ? null : this.remapper.mapTypes(var6));
   }

   public ModuleVisitor visitModule(String var1, int var2, String var3) {
      ModuleVisitor var4 = super.visitModule(this.remapper.mapModuleName(var1), var2, var3);
      return var4 == null ? null : this.createModuleRemapper(var4);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationVisitor var3 = super.visitAnnotation(this.remapper.mapDesc(var1), var2);
      return var3 == null ? null : this.createAnnotationRemapper(var3);
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitTypeAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);
      return var5 == null ? null : this.createAnnotationRemapper(var5);
   }

   public void visitAttribute(Attribute var1) {
      if (var1 instanceof ModuleHashesAttribute) {
         ModuleHashesAttribute var2 = (ModuleHashesAttribute)var1;
         List var3 = var2.modules;

         for(int var4 = 0; var4 < var3.size(); ++var4) {
            var3.set(var4, this.remapper.mapModuleName((String)var3.get(var4)));
         }
      }

      super.visitAttribute(var1);
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      FieldVisitor var6 = super.visitField(var1, this.remapper.mapFieldName(this.className, var2, var3), this.remapper.mapDesc(var3), this.remapper.mapSignature(var4, true), var5 == null ? null : this.remapper.mapValue(var5));
      return var6 == null ? null : this.createFieldRemapper(var6);
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      String var6 = this.remapper.mapMethodDesc(var3);
      MethodVisitor var7 = super.visitMethod(var1, this.remapper.mapMethodName(this.className, var2, var3), var6, this.remapper.mapSignature(var4, false), var5 == null ? null : this.remapper.mapTypes(var5));
      return var7 == null ? null : this.createMethodRemapper(var7);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      super.visitInnerClass(this.remapper.mapType(var1), var2 == null ? null : this.remapper.mapType(var2), var3 == null ? null : this.remapper.mapInnerClassName(var1, var2, var3), var4);
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      super.visitOuterClass(this.remapper.mapType(var1), var2 == null ? null : this.remapper.mapMethodName(var1, var2, var3), var3 == null ? null : this.remapper.mapMethodDesc(var3));
   }

   public void visitNestHost(String var1) {
      super.visitNestHost(this.remapper.mapType(var1));
   }

   public void visitNestMember(String var1) {
      super.visitNestMember(this.remapper.mapType(var1));
   }

   protected FieldVisitor createFieldRemapper(FieldVisitor var1) {
      return new FieldRemapper(this.api, var1, this.remapper);
   }

   protected MethodVisitor createMethodRemapper(MethodVisitor var1) {
      return new MethodRemapper(this.api, var1, this.remapper);
   }

   protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor var1) {
      return new AnnotationRemapper(this.api, var1, this.remapper);
   }

   protected ModuleVisitor createModuleRemapper(ModuleVisitor var1) {
      return new ModuleRemapper(this.api, var1, this.remapper);
   }
}
