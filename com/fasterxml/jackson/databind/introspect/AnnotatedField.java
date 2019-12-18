package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public final class AnnotatedField extends AnnotatedMember implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final transient Field _field;
   protected AnnotatedField$Serialization _serialization;

   public AnnotatedField(TypeResolutionContext var1, Field var2, AnnotationMap var3) {
      super(var1, var3);
      this._field = var2;
   }

   public AnnotatedField withAnnotations(AnnotationMap var1) {
      return new AnnotatedField(this._typeContext, this._field, var1);
   }

   protected AnnotatedField(AnnotatedField$Serialization var1) {
      super((TypeResolutionContext)null, (AnnotationMap)null);
      this._field = null;
      this._serialization = var1;
   }

   public Field getAnnotated() {
      return this._field;
   }

   public int getModifiers() {
      return this._field.getModifiers();
   }

   public String getName() {
      return this._field.getName();
   }

   public Class getRawType() {
      return this._field.getType();
   }

   /** @deprecated */
   @Deprecated
   public Type getGenericType() {
      return this._field.getGenericType();
   }

   public JavaType getType() {
      return this._typeContext.resolveType(this._field.getGenericType());
   }

   public Class getDeclaringClass() {
      return this._field.getDeclaringClass();
   }

   public Member getMember() {
      return this._field;
   }

   public void setValue(Object var1, Object var2) throws IllegalArgumentException {
      try {
         this._field.set(var1, var2);
      } catch (IllegalAccessException var4) {
         throw new IllegalArgumentException("Failed to setValue() for field " + this.getFullName() + ": " + var4.getMessage(), var4);
      }

   }

   public Object getValue(Object var1) throws IllegalArgumentException {
      Object var10000;
      try {
         var10000 = this._field.get(var1);
      } catch (IllegalAccessException var3) {
         throw new IllegalArgumentException("Failed to getValue() for field " + this.getFullName() + ": " + var3.getMessage(), var3);
      }

      return var10000;
   }

   public int getAnnotationCount() {
      return this._annotations.size();
   }

   public boolean isTransient() {
      return Modifier.isTransient(this.getModifiers());
   }

   public int hashCode() {
      return this._field.getName().hashCode();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         return ClassUtil.hasClass(var1, this.getClass()) && ((AnnotatedField)var1)._field == this._field;
      }
   }

   public String toString() {
      return "[field " + this.getFullName() + "]";
   }

   Object writeReplace() {
      return new AnnotatedField(new AnnotatedField$Serialization(this._field));
   }

   Object readResolve() {
      Class var1 = this._serialization.clazz;

      AnnotatedField var10000;
      try {
         Field var2 = var1.getDeclaredField(this._serialization.name);
         if (!var2.isAccessible()) {
            ClassUtil.checkAndFixAccess(var2, false);
         }

         var10000 = new AnnotatedField((TypeResolutionContext)null, var2, (AnnotationMap)null);
      } catch (Exception var3) {
         throw new IllegalArgumentException("Could not find method '" + this._serialization.name + "' from Class '" + var1.getName());
      }

      return var10000;
   }

   public Annotated withAnnotations(AnnotationMap var1) {
      return this.withAnnotations(var1);
   }

   public AnnotatedElement getAnnotated() {
      return this.getAnnotated();
   }
}
