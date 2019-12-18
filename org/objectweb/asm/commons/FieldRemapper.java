package org.objectweb.asm.commons;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;

public class FieldRemapper extends FieldVisitor {
   protected final Remapper remapper;

   public FieldRemapper(FieldVisitor var1, Remapper var2) {
      this(458752, var1, var2);
   }

   protected FieldRemapper(int var1, FieldVisitor var2, Remapper var3) {
      super(var1, var2);
      this.remapper = var3;
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationVisitor var3 = super.visitAnnotation(this.remapper.mapDesc(var1), var2);
      return var3 == null ? null : new AnnotationRemapper(this.api, var3, this.remapper);
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AnnotationVisitor var5 = super.visitTypeAnnotation(var1, var2, this.remapper.mapDesc(var3), var4);
      return var5 == null ? null : new AnnotationRemapper(this.api, var5, this.remapper);
   }
}
