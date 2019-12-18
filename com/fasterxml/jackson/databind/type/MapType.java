package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import java.lang.reflect.TypeVariable;

public final class MapType extends MapLikeType {
   private static final long serialVersionUID = 1L;

   private MapType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4, JavaType var5, JavaType var6, Object var7, Object var8, boolean var9) {
      super(var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   protected MapType(TypeBase var1, JavaType var2, JavaType var3) {
      super(var1, var2, var3);
   }

   public static MapType construct(Class var0, TypeBindings var1, JavaType var2, JavaType[] var3, JavaType var4, JavaType var5) {
      return new MapType(var0, var1, var2, var3, var4, var5, (Object)null, (Object)null, false);
   }

   /** @deprecated */
   @Deprecated
   public static MapType construct(Class var0, JavaType var1, JavaType var2) {
      TypeVariable[] var3 = var0.getTypeParameters();
      TypeBindings var4;
      if (var3 != null && var3.length == 2) {
         var4 = TypeBindings.create(var0, var1, var2);
      } else {
         var4 = TypeBindings.emptyBindings();
      }

      return new MapType(var0, var4, _bogusSuperClass(var0), (JavaType[])null, var1, var2, (Object)null, (Object)null, false);
   }

   /** @deprecated */
   @Deprecated
   protected JavaType _narrow(Class var1) {
      return new MapType(var1, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public MapType withTypeHandler(Object var1) {
      return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, this._valueHandler, var1, this._asStatic);
   }

   public MapType withContentTypeHandler(Object var1) {
      return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.withTypeHandler(var1), this._valueHandler, this._typeHandler, this._asStatic);
   }

   public MapType withValueHandler(Object var1) {
      return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, var1, this._typeHandler, this._asStatic);
   }

   public MapType withContentValueHandler(Object var1) {
      return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.withValueHandler(var1), this._valueHandler, this._typeHandler, this._asStatic);
   }

   public MapType withStaticTyping() {
      return this._asStatic ? this : new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.withStaticTyping(), this._valueType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
   }

   public JavaType withContentType(JavaType var1) {
      return this._valueType == var1 ? this : new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, var1, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public MapType withKeyType(JavaType var1) {
      return var1 == this._keyType ? this : new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, var1, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public JavaType refine(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      return new MapType(var1, var2, var3, var4, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public MapType withKeyTypeHandler(Object var1) {
      return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.withTypeHandler(var1), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public MapType withKeyValueHandler(Object var1) {
      return new MapType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.withValueHandler(var1), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public String toString() {
      return "[map type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
   }

   public MapLikeType withKeyValueHandler(Object var1) {
      return this.withKeyValueHandler(var1);
   }

   public MapLikeType withKeyTypeHandler(Object var1) {
      return this.withKeyTypeHandler(var1);
   }

   public MapLikeType withStaticTyping() {
      return this.withStaticTyping();
   }

   public MapLikeType withContentValueHandler(Object var1) {
      return this.withContentValueHandler(var1);
   }

   public MapLikeType withValueHandler(Object var1) {
      return this.withValueHandler(var1);
   }

   public MapLikeType withContentTypeHandler(Object var1) {
      return this.withContentTypeHandler(var1);
   }

   public MapLikeType withTypeHandler(Object var1) {
      return this.withTypeHandler(var1);
   }

   public MapLikeType withKeyType(JavaType var1) {
      return this.withKeyType(var1);
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
