package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;

public class ResolvedRecursiveType extends TypeBase {
   private static final long serialVersionUID = 1L;
   protected JavaType _referencedType;

   public ResolvedRecursiveType(Class var1, TypeBindings var2) {
      super(var1, var2, (JavaType)null, (JavaType[])null, 0, (Object)null, (Object)null, false);
   }

   public void setReference(JavaType var1) {
      if (this._referencedType != null) {
         throw new IllegalStateException("Trying to re-set self reference; old value = " + this._referencedType + ", new = " + var1);
      } else {
         this._referencedType = var1;
      }
   }

   public JavaType getSuperClass() {
      return this._referencedType != null ? this._referencedType.getSuperClass() : super.getSuperClass();
   }

   public JavaType getSelfReferencedType() {
      return this._referencedType;
   }

   public StringBuilder getGenericSignature(StringBuilder var1) {
      return this._referencedType.getGenericSignature(var1);
   }

   public StringBuilder getErasedSignature(StringBuilder var1) {
      return this._referencedType.getErasedSignature(var1);
   }

   public JavaType withContentType(JavaType var1) {
      return this;
   }

   public JavaType withTypeHandler(Object var1) {
      return this;
   }

   public JavaType withContentTypeHandler(Object var1) {
      return this;
   }

   public JavaType withValueHandler(Object var1) {
      return this;
   }

   public JavaType withContentValueHandler(Object var1) {
      return this;
   }

   public JavaType withStaticTyping() {
      return this;
   }

   /** @deprecated */
   @Deprecated
   protected JavaType _narrow(Class var1) {
      return this;
   }

   public JavaType refine(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      return null;
   }

   public boolean isContainerType() {
      return false;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder(40)).append("[recursive type; ");
      if (this._referencedType == null) {
         var1.append("UNRESOLVED");
      } else {
         var1.append(this._referencedType.getRawClass().getName());
      }

      return var1.toString();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         return var1.getClass() == this.getClass() ? false : false;
      }
   }
}
