package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;

public class VirtualAnnotatedMember extends AnnotatedMember implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final Class _declaringClass;
   protected final JavaType _type;
   protected final String _name;

   public VirtualAnnotatedMember(TypeResolutionContext var1, Class var2, String var3, JavaType var4) {
      super(var1, (AnnotationMap)null);
      this._declaringClass = var2;
      this._type = var4;
      this._name = var3;
   }

   public Annotated withAnnotations(AnnotationMap var1) {
      return this;
   }

   public Field getAnnotated() {
      return null;
   }

   public int getModifiers() {
      return 0;
   }

   public String getName() {
      return this._name;
   }

   public Class getRawType() {
      return this._type.getRawClass();
   }

   public JavaType getType() {
      return this._type;
   }

   public Class getDeclaringClass() {
      return this._declaringClass;
   }

   public Member getMember() {
      return null;
   }

   public void setValue(Object var1, Object var2) throws IllegalArgumentException {
      throw new IllegalArgumentException("Cannot set virtual property '" + this._name + "'");
   }

   public Object getValue(Object var1) throws IllegalArgumentException {
      throw new IllegalArgumentException("Cannot get virtual property '" + this._name + "'");
   }

   public int getAnnotationCount() {
      return 0;
   }

   public int hashCode() {
      return this._name.hashCode();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!ClassUtil.hasClass(var1, this.getClass())) {
         return false;
      } else {
         VirtualAnnotatedMember var2 = (VirtualAnnotatedMember)var1;
         return var2._declaringClass == this._declaringClass && var2._name.equals(this._name);
      }
   }

   public String toString() {
      return "[virtual " + this.getFullName() + "]";
   }

   public AnnotatedElement getAnnotated() {
      return this.getAnnotated();
   }
}
