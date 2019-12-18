package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AnnotatedClassResolver {
   private static final Annotations NO_ANNOTATIONS = AnnotationCollector.emptyAnnotations();
   private final MapperConfig _config;
   private final AnnotationIntrospector _intr;
   private final ClassIntrospector$MixInResolver _mixInResolver;
   private final TypeBindings _bindings;
   private final JavaType _type;
   private final Class _class;
   private final Class _primaryMixin;

   AnnotatedClassResolver(MapperConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      super();
      this._config = var1;
      this._type = var2;
      this._class = var2.getRawClass();
      this._mixInResolver = var3;
      this._bindings = var2.getBindings();
      this._intr = var1.isAnnotationProcessingEnabled() ? var1.getAnnotationIntrospector() : null;
      this._primaryMixin = this._config.findMixInClassFor(this._class);
   }

   AnnotatedClassResolver(MapperConfig var1, Class var2, ClassIntrospector$MixInResolver var3) {
      super();
      this._config = var1;
      this._type = null;
      this._class = var2;
      this._mixInResolver = var3;
      this._bindings = TypeBindings.emptyBindings();
      if (var1 == null) {
         this._intr = null;
         this._primaryMixin = null;
      } else {
         this._intr = var1.isAnnotationProcessingEnabled() ? var1.getAnnotationIntrospector() : null;
         this._primaryMixin = this._config.findMixInClassFor(this._class);
      }

   }

   public static AnnotatedClass resolve(MapperConfig var0, JavaType var1, ClassIntrospector$MixInResolver var2) {
      return var1.isArrayType() && skippableArray(var0, var1.getRawClass()) ? createArrayType(var0, var1.getRawClass()) : (new AnnotatedClassResolver(var0, var1, var2)).resolveFully();
   }

   public static AnnotatedClass resolveWithoutSuperTypes(MapperConfig var0, Class var1) {
      return resolveWithoutSuperTypes(var0, (Class)var1, var0);
   }

   public static AnnotatedClass resolveWithoutSuperTypes(MapperConfig var0, JavaType var1, ClassIntrospector$MixInResolver var2) {
      return var1.isArrayType() && skippableArray(var0, var1.getRawClass()) ? createArrayType(var0, var1.getRawClass()) : (new AnnotatedClassResolver(var0, var1, var2)).resolveWithoutSuperTypes();
   }

   public static AnnotatedClass resolveWithoutSuperTypes(MapperConfig var0, Class var1, ClassIntrospector$MixInResolver var2) {
      return var1.isArray() && skippableArray(var0, var1) ? createArrayType(var0, var1) : (new AnnotatedClassResolver(var0, var1, var2)).resolveWithoutSuperTypes();
   }

   private static boolean skippableArray(MapperConfig var0, Class var1) {
      return var0 == null || var0.findMixInClassFor(var1) == null;
   }

   static AnnotatedClass createPrimordial(Class var0) {
      return new AnnotatedClass(var0);
   }

   static AnnotatedClass createArrayType(MapperConfig var0, Class var1) {
      return new AnnotatedClass(var1);
   }

   AnnotatedClass resolveFully() {
      List var1 = ClassUtil.findSuperTypes(this._type, (Class)null, false);
      return new AnnotatedClass(this._type, this._class, var1, this._primaryMixin, this.resolveClassAnnotations(var1), this._bindings, this._intr, this._mixInResolver, this._config.getTypeFactory());
   }

   AnnotatedClass resolveWithoutSuperTypes() {
      List var1 = Collections.emptyList();
      return new AnnotatedClass((JavaType)null, this._class, var1, this._primaryMixin, this.resolveClassAnnotations(var1), this._bindings, this._intr, this._config, this._config.getTypeFactory());
   }

   private Annotations resolveClassAnnotations(List var1) {
      if (this._intr == null) {
         return NO_ANNOTATIONS;
      } else {
         AnnotationCollector var2 = AnnotationCollector.emptyCollector();
         if (this._primaryMixin != null) {
            var2 = this._addClassMixIns(var2, this._class, this._primaryMixin);
         }

         var2 = this._addAnnotationsIfNotPresent(var2, ClassUtil.findClassAnnotations(this._class));

         JavaType var4;
         for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = this._addAnnotationsIfNotPresent(var2, ClassUtil.findClassAnnotations(var4.getRawClass()))) {
            var4 = (JavaType)var3.next();
            if (this._mixInResolver != null) {
               Class var5 = var4.getRawClass();
               var2 = this._addClassMixIns(var2, var5, this._mixInResolver.findMixInClassFor(var5));
            }
         }

         if (this._mixInResolver != null) {
            var2 = this._addClassMixIns(var2, Object.class, this._mixInResolver.findMixInClassFor(Object.class));
         }

         return var2.asAnnotations();
      }
   }

   private AnnotationCollector _addClassMixIns(AnnotationCollector var1, Class var2, Class var3) {
      if (var3 != null) {
         var1 = this._addAnnotationsIfNotPresent(var1, ClassUtil.findClassAnnotations(var3));

         Class var5;
         for(Iterator var4 = ClassUtil.findSuperClasses(var3, var2, false).iterator(); var4.hasNext(); var1 = this._addAnnotationsIfNotPresent(var1, ClassUtil.findClassAnnotations(var5))) {
            var5 = (Class)var4.next();
         }
      }

      return var1;
   }

   private AnnotationCollector _addAnnotationsIfNotPresent(AnnotationCollector var1, Annotation[] var2) {
      if (var2 != null) {
         Annotation[] var3 = var2;
         int var4 = var2.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Annotation var6 = var3[var5];
            if (!var1.isPresent(var6)) {
               var1 = var1.addOrOverride(var6);
               if (this._intr.isAnnotationBundle(var6)) {
                  var1 = this._addFromBundleIfNotPresent(var1, var6);
               }
            }
         }
      }

      return var1;
   }

   private AnnotationCollector _addFromBundleIfNotPresent(AnnotationCollector var1, Annotation var2) {
      Annotation[] var3 = ClassUtil.findClassAnnotations(var2.annotationType());
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation var6 = var3[var5];
         if (!(var6 instanceof Target) && !(var6 instanceof Retention) && !var1.isPresent(var6)) {
            var1 = var1.addOrOverride(var6);
            if (this._intr.isAnnotationBundle(var6)) {
               var1 = this._addFromBundleIfNotPresent(var1, var6);
            }
         }
      }

      return var1;
   }
}
