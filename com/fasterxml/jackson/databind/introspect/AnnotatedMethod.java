package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public final class AnnotatedMethod extends AnnotatedWithParams implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final transient Method _method;
   protected Class[] _paramClasses;
   protected AnnotatedMethod$Serialization _serialization;

   public AnnotatedMethod(TypeResolutionContext var1, Method var2, AnnotationMap var3, AnnotationMap[] var4) {
      super(var1, var3, var4);
      if (var2 == null) {
         throw new IllegalArgumentException("Cannot construct AnnotatedMethod with null Method");
      } else {
         this._method = var2;
      }
   }

   protected AnnotatedMethod(AnnotatedMethod$Serialization var1) {
      super((TypeResolutionContext)null, (AnnotationMap)null, (AnnotationMap[])null);
      this._method = null;
      this._serialization = var1;
   }

   public AnnotatedMethod withAnnotations(AnnotationMap var1) {
      return new AnnotatedMethod(this._typeContext, this._method, var1, this._paramAnnotations);
   }

   public Method getAnnotated() {
      return this._method;
   }

   public int getModifiers() {
      return this._method.getModifiers();
   }

   public String getName() {
      return this._method.getName();
   }

   public JavaType getType() {
      return this._typeContext.resolveType(this._method.getGenericReturnType());
   }

   public Class getRawType() {
      return this._method.getReturnType();
   }

   /** @deprecated */
   @Deprecated
   public Type getGenericType() {
      return this._method.getGenericReturnType();
   }

   public final Object call() throws Exception {
      return this._method.invoke((Object)null);
   }

   public final Object call(Object[] var1) throws Exception {
      return this._method.invoke((Object)null, var1);
   }

   public final Object call1(Object var1) throws Exception {
      return this._method.invoke((Object)null, var1);
   }

   public final Object callOn(Object var1) throws Exception {
      return this._method.invoke(var1, (Object[])null);
   }

   public final Object callOnWith(Object var1, Object... var2) throws Exception {
      return this._method.invoke(var1, var2);
   }

   public int getParameterCount() {
      return this.getRawParameterTypes().length;
   }

   public Class getRawParameterType(int var1) {
      Class[] var2 = this.getRawParameterTypes();
      return var1 >= var2.length ? null : var2[var1];
   }

   public JavaType getParameterType(int var1) {
      Type[] var2 = this._method.getGenericParameterTypes();
      return var1 >= var2.length ? null : this._typeContext.resolveType(var2[var1]);
   }

   /** @deprecated */
   @Deprecated
   public Type getGenericParameterType(int var1) {
      Type[] var2 = this.getGenericParameterTypes();
      return var1 >= var2.length ? null : var2[var1];
   }

   public Class getDeclaringClass() {
      return this._method.getDeclaringClass();
   }

   public Method getMember() {
      return this._method;
   }

   public void setValue(Object var1, Object var2) throws IllegalArgumentException {
      try {
         this._method.invoke(var1, var2);
      } catch (InvocationTargetException | IllegalAccessException var4) {
         throw new IllegalArgumentException("Failed to setValue() with method " + this.getFullName() + ": " + var4.getMessage(), var4);
      }

   }

   public Object getValue(Object var1) throws IllegalArgumentException {
      Object var10000;
      try {
         var10000 = this._method.invoke(var1, (Object[])null);
      } catch (InvocationTargetException | IllegalAccessException var3) {
         throw new IllegalArgumentException("Failed to getValue() with method " + this.getFullName() + ": " + var3.getMessage(), var3);
      }

      return var10000;
   }

   public String getFullName() {
      return String.format("%s(%d params)", super.getFullName(), this.getParameterCount());
   }

   public Class[] getRawParameterTypes() {
      if (this._paramClasses == null) {
         this._paramClasses = this._method.getParameterTypes();
      }

      return this._paramClasses;
   }

   /** @deprecated */
   @Deprecated
   public Type[] getGenericParameterTypes() {
      return this._method.getGenericParameterTypes();
   }

   public Class getRawReturnType() {
      return this._method.getReturnType();
   }

   public boolean hasReturnType() {
      Class var1 = this.getRawReturnType();
      return var1 != Void.TYPE && var1 != Void.class;
   }

   public String toString() {
      return "[method " + this.getFullName() + "]";
   }

   public int hashCode() {
      return this._method.getName().hashCode();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         return ClassUtil.hasClass(var1, this.getClass()) && ((AnnotatedMethod)var1)._method == this._method;
      }
   }

   Object writeReplace() {
      return new AnnotatedMethod(new AnnotatedMethod$Serialization(this._method));
   }

   Object readResolve() {
      Class var1 = this._serialization.clazz;

      AnnotatedMethod var10000;
      try {
         Method var2 = var1.getDeclaredMethod(this._serialization.name, this._serialization.args);
         if (!var2.isAccessible()) {
            ClassUtil.checkAndFixAccess(var2, false);
         }

         var10000 = new AnnotatedMethod((TypeResolutionContext)null, var2, (AnnotationMap)null, (AnnotationMap[])null);
      } catch (Exception var3) {
         throw new IllegalArgumentException("Could not find method '" + this._serialization.name + "' from Class '" + var1.getName());
      }

      return var10000;
   }

   public Member getMember() {
      return this.getMember();
   }

   public Annotated withAnnotations(AnnotationMap var1) {
      return this.withAnnotations(var1);
   }

   public AnnotatedElement getAnnotated() {
      return this.getAnnotated();
   }
}
