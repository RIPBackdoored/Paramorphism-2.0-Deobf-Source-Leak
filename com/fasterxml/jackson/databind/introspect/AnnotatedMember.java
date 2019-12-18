package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.Collections;

public abstract class AnnotatedMember extends Annotated implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final transient TypeResolutionContext _typeContext;
   protected final transient AnnotationMap _annotations;

   protected AnnotatedMember(TypeResolutionContext var1, AnnotationMap var2) {
      super();
      this._typeContext = var1;
      this._annotations = var2;
   }

   protected AnnotatedMember(AnnotatedMember var1) {
      super();
      this._typeContext = var1._typeContext;
      this._annotations = var1._annotations;
   }

   public abstract Annotated withAnnotations(AnnotationMap var1);

   public abstract Class getDeclaringClass();

   public abstract Member getMember();

   public String getFullName() {
      return this.getDeclaringClass().getName() + "#" + this.getName();
   }

   /** @deprecated */
   @Deprecated
   public TypeResolutionContext getTypeContext() {
      return this._typeContext;
   }

   public final Annotation getAnnotation(Class var1) {
      return this._annotations == null ? null : this._annotations.get(var1);
   }

   public final boolean hasAnnotation(Class var1) {
      return this._annotations == null ? false : this._annotations.has(var1);
   }

   public boolean hasOneOf(Class[] var1) {
      return this._annotations == null ? false : this._annotations.hasOneOf(var1);
   }

   /** @deprecated */
   @Deprecated
   public Iterable annotations() {
      return (Iterable)(this._annotations == null ? Collections.emptyList() : this._annotations.annotations());
   }

   public AnnotationMap getAllAnnotations() {
      return this._annotations;
   }

   public final void fixAccess(boolean var1) {
      Member var2 = this.getMember();
      if (var2 != null) {
         ClassUtil.checkAndFixAccess(var2, var1);
      }

   }

   public abstract void setValue(Object var1, Object var2) throws UnsupportedOperationException, IllegalArgumentException;

   public abstract Object getValue(Object var1) throws UnsupportedOperationException, IllegalArgumentException;
}
