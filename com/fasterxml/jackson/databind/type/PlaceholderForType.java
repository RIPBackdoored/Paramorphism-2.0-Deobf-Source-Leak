package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;

public class PlaceholderForType extends TypeBase {
   private static final long serialVersionUID = 1L;
   protected final int _ordinal;
   protected JavaType _actualType;

   public PlaceholderForType(int var1) {
      super(Object.class, TypeBindings.emptyBindings(), TypeFactory.unknownType(), (JavaType[])null, 1, (Object)null, (Object)null, false);
      this._ordinal = var1;
   }

   public JavaType actualType() {
      return this._actualType;
   }

   public void actualType(JavaType var1) {
      this._actualType = var1;
   }

   protected String buildCanonicalName() {
      return this.toString();
   }

   public StringBuilder getGenericSignature(StringBuilder var1) {
      return this.getErasedSignature(var1);
   }

   public StringBuilder getErasedSignature(StringBuilder var1) {
      var1.append('$').append(this._ordinal + 1);
      return var1;
   }

   public JavaType withTypeHandler(Object var1) {
      return (JavaType)this._unsupported();
   }

   public JavaType withContentTypeHandler(Object var1) {
      return (JavaType)this._unsupported();
   }

   public JavaType withValueHandler(Object var1) {
      return (JavaType)this._unsupported();
   }

   public JavaType withContentValueHandler(Object var1) {
      return (JavaType)this._unsupported();
   }

   public JavaType withContentType(JavaType var1) {
      return (JavaType)this._unsupported();
   }

   public JavaType withStaticTyping() {
      return (JavaType)this._unsupported();
   }

   public JavaType refine(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      return (JavaType)this._unsupported();
   }

   protected JavaType _narrow(Class var1) {
      return (JavaType)this._unsupported();
   }

   public boolean isContainerType() {
      return false;
   }

   public String toString() {
      return this.getErasedSignature(new StringBuilder()).toString();
   }

   public boolean equals(Object var1) {
      return var1 == this;
   }

   private Object _unsupported() {
      throw new UnsupportedOperationException("Operation should not be attempted on " + this.getClass().getName());
   }
}
