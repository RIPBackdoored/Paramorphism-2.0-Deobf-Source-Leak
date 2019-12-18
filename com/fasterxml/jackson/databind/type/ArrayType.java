package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.JavaType;
import java.lang.reflect.Array;

public final class ArrayType extends TypeBase {
   private static final long serialVersionUID = 1L;
   protected final JavaType _componentType;
   protected final Object _emptyArray;

   protected ArrayType(JavaType var1, TypeBindings var2, Object var3, Object var4, Object var5, boolean var6) {
      super(var3.getClass(), var2, (JavaType)null, (JavaType[])null, var1.hashCode(), var4, var5, var6);
      this._componentType = var1;
      this._emptyArray = var3;
   }

   public static ArrayType construct(JavaType var0, TypeBindings var1) {
      return construct(var0, var1, (Object)null, (Object)null);
   }

   public static ArrayType construct(JavaType var0, TypeBindings var1, Object var2, Object var3) {
      Object var4 = Array.newInstance(var0.getRawClass(), 0);
      return new ArrayType(var0, var1, var4, var2, var3, false);
   }

   public JavaType withContentType(JavaType var1) {
      Object var2 = Array.newInstance(var1.getRawClass(), 0);
      return new ArrayType(var1, this._bindings, var2, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public ArrayType withTypeHandler(Object var1) {
      return var1 == this._typeHandler ? this : new ArrayType(this._componentType, this._bindings, this._emptyArray, this._valueHandler, var1, this._asStatic);
   }

   public ArrayType withContentTypeHandler(Object var1) {
      return var1 == this._componentType.getTypeHandler() ? this : new ArrayType(this._componentType.withTypeHandler(var1), this._bindings, this._emptyArray, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public ArrayType withValueHandler(Object var1) {
      return var1 == this._valueHandler ? this : new ArrayType(this._componentType, this._bindings, this._emptyArray, var1, this._typeHandler, this._asStatic);
   }

   public ArrayType withContentValueHandler(Object var1) {
      return var1 == this._componentType.getValueHandler() ? this : new ArrayType(this._componentType.withValueHandler(var1), this._bindings, this._emptyArray, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public ArrayType withStaticTyping() {
      return this._asStatic ? this : new ArrayType(this._componentType.withStaticTyping(), this._bindings, this._emptyArray, this._valueHandler, this._typeHandler, true);
   }

   /** @deprecated */
   @Deprecated
   protected JavaType _narrow(Class var1) {
      return this._reportUnsupported();
   }

   public JavaType refine(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      return null;
   }

   private JavaType _reportUnsupported() {
      throw new UnsupportedOperationException("Cannot narrow or widen array types");
   }

   public boolean isArrayType() {
      return true;
   }

   public boolean isAbstract() {
      return false;
   }

   public boolean isConcrete() {
      return true;
   }

   public boolean hasGenericTypes() {
      return this._componentType.hasGenericTypes();
   }

   public boolean isContainerType() {
      return true;
   }

   public JavaType getContentType() {
      return this._componentType;
   }

   public Object getContentValueHandler() {
      return this._componentType.getValueHandler();
   }

   public Object getContentTypeHandler() {
      return this._componentType.getTypeHandler();
   }

   public boolean hasHandlers() {
      return super.hasHandlers() || this._componentType.hasHandlers();
   }

   public StringBuilder getGenericSignature(StringBuilder var1) {
      var1.append('[');
      return this._componentType.getGenericSignature(var1);
   }

   public StringBuilder getErasedSignature(StringBuilder var1) {
      var1.append('[');
      return this._componentType.getErasedSignature(var1);
   }

   public String toString() {
      return "[array type, component type: " + this._componentType + "]";
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         ArrayType var2 = (ArrayType)var1;
         return this._componentType.equals(var2._componentType);
      }
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

   public JavaType withContentTypeHandler(Object var1) {
      return this.withContentTypeHandler(var1);
   }

   public JavaType withTypeHandler(Object var1) {
      return this.withTypeHandler(var1);
   }

   public ResolvedType getContentType() {
      return this.getContentType();
   }
}
