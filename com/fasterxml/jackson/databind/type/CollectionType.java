package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import java.lang.reflect.TypeVariable;

public final class CollectionType extends CollectionLikeType {
   private static final long serialVersionUID = 1L;

   private CollectionType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4, JavaType var5, Object var6, Object var7, boolean var8) {
      super(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   protected CollectionType(TypeBase var1, JavaType var2) {
      super(var1, var2);
   }

   public static CollectionType construct(Class var0, TypeBindings var1, JavaType var2, JavaType[] var3, JavaType var4) {
      return new CollectionType(var0, var1, var2, var3, var4, (Object)null, (Object)null, false);
   }

   /** @deprecated */
   @Deprecated
   public static CollectionType construct(Class var0, JavaType var1) {
      TypeVariable[] var2 = var0.getTypeParameters();
      TypeBindings var3;
      if (var2 != null && var2.length == 1) {
         var3 = TypeBindings.create(var0, var1);
      } else {
         var3 = TypeBindings.emptyBindings();
      }

      return new CollectionType(var0, var3, _bogusSuperClass(var0), (JavaType[])null, var1, (Object)null, (Object)null, false);
   }

   /** @deprecated */
   @Deprecated
   protected JavaType _narrow(Class var1) {
      return new CollectionType(var1, this._bindings, this._superClass, this._superInterfaces, this._elementType, (Object)null, (Object)null, this._asStatic);
   }

   public JavaType withContentType(JavaType var1) {
      return this._elementType == var1 ? this : new CollectionType(this._class, this._bindings, this._superClass, this._superInterfaces, var1, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public CollectionType withTypeHandler(Object var1) {
      return new CollectionType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType, this._valueHandler, var1, this._asStatic);
   }

   public CollectionType withContentTypeHandler(Object var1) {
      return new CollectionType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType.withTypeHandler(var1), this._valueHandler, this._typeHandler, this._asStatic);
   }

   public CollectionType withValueHandler(Object var1) {
      return new CollectionType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType, var1, this._typeHandler, this._asStatic);
   }

   public CollectionType withContentValueHandler(Object var1) {
      return new CollectionType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType.withValueHandler(var1), this._valueHandler, this._typeHandler, this._asStatic);
   }

   public CollectionType withStaticTyping() {
      return this._asStatic ? this : new CollectionType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
   }

   public JavaType refine(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      return new CollectionType(var1, var2, var3, var4, this._elementType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public String toString() {
      return "[collection type; class " + this._class.getName() + ", contains " + this._elementType + "]";
   }

   public CollectionLikeType withStaticTyping() {
      return this.withStaticTyping();
   }

   public CollectionLikeType withContentValueHandler(Object var1) {
      return this.withContentValueHandler(var1);
   }

   public CollectionLikeType withValueHandler(Object var1) {
      return this.withValueHandler(var1);
   }

   public CollectionLikeType withContentTypeHandler(Object var1) {
      return this.withContentTypeHandler(var1);
   }

   public CollectionLikeType withTypeHandler(Object var1) {
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

   public JavaType withContentTypeHandler(Object var1) {
      return this.withContentTypeHandler(var1);
   }

   public JavaType withTypeHandler(Object var1) {
      return this.withTypeHandler(var1);
   }
}
