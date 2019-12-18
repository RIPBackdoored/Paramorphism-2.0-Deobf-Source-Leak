package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

class CollectorBase {
   protected static final AnnotationMap[] NO_ANNOTATION_MAPS = new AnnotationMap[0];
   protected static final Annotation[] NO_ANNOTATIONS = new Annotation[0];
   protected final AnnotationIntrospector _intr;

   protected CollectorBase(AnnotationIntrospector var1) {
      super();
      this._intr = var1;
   }

   protected final AnnotationCollector collectAnnotations(Annotation[] var1) {
      AnnotationCollector var2 = AnnotationCollector.emptyCollector();
      int var3 = 0;

      for(int var4 = var1.length; var3 < var4; ++var3) {
         Annotation var5 = var1[var3];
         var2 = var2.addOrOverride(var5);
         if (this._intr.isAnnotationBundle(var5)) {
            var2 = this.collectFromBundle(var2, var5);
         }
      }

      return var2;
   }

   protected final AnnotationCollector collectAnnotations(AnnotationCollector var1, Annotation[] var2) {
      int var3 = 0;

      for(int var4 = var2.length; var3 < var4; ++var3) {
         Annotation var5 = var2[var3];
         var1 = var1.addOrOverride(var5);
         if (this._intr.isAnnotationBundle(var5)) {
            var1 = this.collectFromBundle(var1, var5);
         }
      }

      return var1;
   }

   protected final AnnotationCollector collectFromBundle(AnnotationCollector var1, Annotation var2) {
      Annotation[] var3 = ClassUtil.findClassAnnotations(var2.annotationType());
      int var4 = 0;

      for(int var5 = var3.length; var4 < var5; ++var4) {
         Annotation var6 = var3[var4];
         if (!_ignorableAnnotation(var6)) {
            if (this._intr.isAnnotationBundle(var6)) {
               if (!var1.isPresent(var6)) {
                  var1 = var1.addOrOverride(var6);
                  var1 = this.collectFromBundle(var1, var6);
               }
            } else {
               var1 = var1.addOrOverride(var6);
            }
         }
      }

      return var1;
   }

   protected final AnnotationCollector collectDefaultAnnotations(AnnotationCollector var1, Annotation[] var2) {
      int var3 = 0;

      for(int var4 = var2.length; var3 < var4; ++var3) {
         Annotation var5 = var2[var3];
         if (!var1.isPresent(var5)) {
            var1 = var1.addOrOverride(var5);
            if (this._intr.isAnnotationBundle(var5)) {
               var1 = this.collectDefaultFromBundle(var1, var5);
            }
         }
      }

      return var1;
   }

   protected final AnnotationCollector collectDefaultFromBundle(AnnotationCollector var1, Annotation var2) {
      Annotation[] var3 = ClassUtil.findClassAnnotations(var2.annotationType());
      int var4 = 0;

      for(int var5 = var3.length; var4 < var5; ++var4) {
         Annotation var6 = var3[var4];
         if (!_ignorableAnnotation(var6) && !var1.isPresent(var6)) {
            var1 = var1.addOrOverride(var6);
            if (this._intr.isAnnotationBundle(var6)) {
               var1 = this.collectFromBundle(var1, var6);
            }
         }
      }

      return var1;
   }

   protected static final boolean _ignorableAnnotation(Annotation var0) {
      return var0 instanceof Target || var0 instanceof Retention;
   }

   static AnnotationMap _emptyAnnotationMap() {
      return new AnnotationMap();
   }

   static AnnotationMap[] _emptyAnnotationMaps(int var0) {
      if (var0 == 0) {
         return NO_ANNOTATION_MAPS;
      } else {
         AnnotationMap[] var1 = new AnnotationMap[var0];

         for(int var2 = 0; var2 < var0; ++var2) {
            var1[var2] = _emptyAnnotationMap();
         }

         return var1;
      }
   }
}
