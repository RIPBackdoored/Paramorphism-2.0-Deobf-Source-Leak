package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.JavaType;

public class ReferenceType extends SimpleType {
   private static final long serialVersionUID = 1L;
   protected final JavaType _referencedType;
   protected final JavaType _anchorType;

   protected ReferenceType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4, JavaType var5, JavaType var6, Object var7, Object var8, boolean var9) {
      super(var1, var2, var3, var4, var5.hashCode(), var7, var8, var9);
      this._referencedType = var5;
      this._anchorType = (JavaType)(var6 == null ? this : var6);
   }

   protected ReferenceType(TypeBase var1, JavaType var2) {
      super(var1);
      this._referencedType = var2;
      this._anchorType = this;
   }

   public static ReferenceType upgradeFrom(JavaType var0, JavaType var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Missing referencedType");
      } else if (var0 instanceof TypeBase) {
         return new ReferenceType((TypeBase)var0, var1);
      } else {
         throw new IllegalArgumentException("Cannot upgrade from an instance of " + var0.getClass());
      }
   }

   public static ReferenceType construct(Class var0, TypeBindings var1, JavaType var2, JavaType[] var3, JavaType var4) {
      return new ReferenceType(var0, var1, var2, var3, var4, (JavaType)null, (Object)null, (Object)null, false);
   }

   /** @deprecated */
   @Deprecated
   public static ReferenceType construct(Class var0, JavaType var1) {
      return new ReferenceType(var0, TypeBindings.emptyBindings(), (JavaType)null, (JavaType[])null, (JavaType)null, var1, (Object)null, (Object)null, false);
   }

   public JavaType withContentType(JavaType var1) {
      return this._referencedType == var1 ? this : new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, var1, this._anchorType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public ReferenceType withTypeHandler(Object var1) {
      return var1 == this._typeHandler ? this : new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, this._referencedType, this._anchorType, this._valueHandler, var1, this._asStatic);
   }

   public ReferenceType withContentTypeHandler(Object var1) {
      return var1 == this._referencedType.getTypeHandler() ? this : new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, this._referencedType.withTypeHandler(var1), this._anchorType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public ReferenceType withValueHandler(Object var1) {
      return var1 == this._valueHandler ? this : new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, this._referencedType, this._anchorType, var1, this._typeHandler, this._asStatic);
   }

   public ReferenceType withContentValueHandler(Object var1) {
      if (var1 == this._referencedType.getValueHandler()) {
         return this;
      } else {
         JavaType var2 = this._referencedType.withValueHandler(var1);
         return new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, var2, this._anchorType, this._valueHandler, this._typeHandler, this._asStatic);
      }
   }

   public ReferenceType withStaticTyping() {
      return this._asStatic ? this : new ReferenceType(this._class, this._bindings, this._superClass, this._superInterfaces, this._referencedType.withStaticTyping(), this._anchorType, this._valueHandler, this._typeHandler, true);
   }

   public JavaType refine(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      return new ReferenceType(var1, this._bindings, var3, var4, this._referencedType, this._anchorType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   protected String buildCanonicalName() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this._class.getName());
      var1.append('<');
      var1.append(this._referencedType.toCanonical());
      return var1.toString();
   }

   /** @deprecated */
   @Deprecated
   protected JavaType _narrow(Class var1) {
      return new ReferenceType(var1, this._bindings, this._superClass, this._superInterfaces, this._referencedType, this._anchorType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public JavaType getContentType() {
      return this._referencedType;
   }

   public JavaType getReferencedType() {
      return this._referencedType;
   }

   public boolean hasContentType() {
      return true;
   }

   public boolean isReferenceType() {
      return true;
   }

   public StringBuilder getErasedSignature(StringBuilder var1) {
      return _classSignature(this._class, var1, true);
   }

   public StringBuilder getGenericSignature(StringBuilder var1) {
      _classSignature(this._class, var1, false);
      var1.append('<');
      var1 = this._referencedType.getGenericSignature(var1);
      var1.append(">;");
      return var1;
   }

   public JavaType getAnchorType() {
      return this._anchorType;
   }

   public boolean isAnchorType() {
      return this._anchorType == this;
   }

   public String toString() {
      return (new StringBuilder(40)).append("[reference type, class ").append(this.buildCanonicalName()).append('<').append(this._referencedType).append('>').append(']').toString();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         ReferenceType var2 = (ReferenceType)var1;
         return var2._class != this._class ? false : this._referencedType.equals(var2._referencedType);
      }
   }

   public SimpleType withStaticTyping() {
      return this.withStaticTyping();
   }

   public SimpleType withContentValueHandler(Object var1) {
      return this.withContentValueHandler(var1);
   }

   public SimpleType withValueHandler(Object var1) {
      return this.withValueHandler(var1);
   }

   public JavaType withContentTypeHandler(Object var1) {
      return this.withContentTypeHandler(var1);
   }

   public SimpleType withTypeHandler(Object var1) {
      return this.withTypeHandler(var1);
   }

   public JavaType withStaticTyping() {
      return this.withStaticTyping();
   }

   public JavaType withContentValueHandler(Object var1) {
      return this.withContentValueHandler(var1);
   }

   public JavaType withValueHandler(Object var1) {
      return this.withValueHandler(var1);
   }

   public JavaType withTypeHandler(Object var1) {
      return this.withTypeHandler(var1);
   }

   public ResolvedType getReferencedType() {
      return this.getReferencedType();
   }

   public ResolvedType getContentType() {
      return this.getContentType();
   }
}
