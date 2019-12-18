package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public abstract class AnnotatedWithParams extends AnnotatedMember {
   private static final long serialVersionUID = 1L;
   protected final AnnotationMap[] _paramAnnotations;

   protected AnnotatedWithParams(TypeResolutionContext var1, AnnotationMap var2, AnnotationMap[] var3) {
      super(var1, var2);
      this._paramAnnotations = var3;
   }

   protected AnnotatedWithParams(AnnotatedWithParams var1, AnnotationMap[] var2) {
      super(var1);
      this._paramAnnotations = var2;
   }

   public final void addOrOverrideParam(int var1, Annotation var2) {
      AnnotationMap var3 = this._paramAnnotations[var1];
      if (var3 == null) {
         var3 = new AnnotationMap();
         this._paramAnnotations[var1] = var3;
      }

      var3.add(var2);
   }

   protected AnnotatedParameter replaceParameterAnnotations(int var1, AnnotationMap var2) {
      this._paramAnnotations[var1] = var2;
      return this.getParameter(var1);
   }

   public final AnnotationMap getParameterAnnotations(int var1) {
      return this._paramAnnotations != null && var1 >= 0 && var1 < this._paramAnnotations.length ? this._paramAnnotations[var1] : null;
   }

   public final AnnotatedParameter getParameter(int var1) {
      return new AnnotatedParameter(this, this.getParameterType(var1), this._typeContext, this.getParameterAnnotations(var1), var1);
   }

   public abstract int getParameterCount();

   public abstract Class getRawParameterType(int var1);

   public abstract JavaType getParameterType(int var1);

   /** @deprecated */
   @Deprecated
   public abstract Type getGenericParameterType(int var1);

   public final int getAnnotationCount() {
      return this._annotations.size();
   }

   public abstract Object call() throws Exception;

   public abstract Object call(Object[] var1) throws Exception;

   public abstract Object call1(Object var1) throws Exception;
}
