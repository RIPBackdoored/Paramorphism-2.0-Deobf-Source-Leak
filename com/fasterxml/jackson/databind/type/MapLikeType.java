package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.JavaType;
import java.lang.reflect.TypeVariable;
import java.util.Map;

public class MapLikeType extends TypeBase {
   private static final long serialVersionUID = 1L;
   protected final JavaType _keyType;
   protected final JavaType _valueType;

   protected MapLikeType(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4, JavaType var5, JavaType var6, Object var7, Object var8, boolean var9) {
      super(var1, var2, var3, var4, var5.hashCode() ^ var6.hashCode(), var7, var8, var9);
      this._keyType = var5;
      this._valueType = var6;
   }

   protected MapLikeType(TypeBase var1, JavaType var2, JavaType var3) {
      super(var1);
      this._keyType = var2;
      this._valueType = var3;
   }

   public static MapLikeType upgradeFrom(JavaType var0, JavaType var1, JavaType var2) {
      if (var0 instanceof TypeBase) {
         return new MapLikeType((TypeBase)var0, var1, var2);
      } else {
         throw new IllegalArgumentException("Cannot upgrade from an instance of " + var0.getClass());
      }
   }

   /** @deprecated */
   @Deprecated
   public static MapLikeType construct(Class var0, JavaType var1, JavaType var2) {
      TypeVariable[] var3 = var0.getTypeParameters();
      TypeBindings var4;
      if (var3 != null && var3.length == 2) {
         var4 = TypeBindings.create(var0, var1, var2);
      } else {
         var4 = TypeBindings.emptyBindings();
      }

      return new MapLikeType(var0, var4, _bogusSuperClass(var0), (JavaType[])null, var1, var2, (Object)null, (Object)null, false);
   }

   /** @deprecated */
   @Deprecated
   protected JavaType _narrow(Class var1) {
      return new MapLikeType(var1, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public MapLikeType withKeyType(JavaType var1) {
      return var1 == this._keyType ? this : new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, var1, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public JavaType withContentType(JavaType var1) {
      return this._valueType == var1 ? this : new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, var1, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public MapLikeType withTypeHandler(Object var1) {
      return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, this._valueHandler, var1, this._asStatic);
   }

   public MapLikeType withContentTypeHandler(Object var1) {
      return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.withTypeHandler(var1), this._valueHandler, this._typeHandler, this._asStatic);
   }

   public MapLikeType withValueHandler(Object var1) {
      return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, var1, this._typeHandler, this._asStatic);
   }

   public MapLikeType withContentValueHandler(Object var1) {
      return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.withValueHandler(var1), this._valueHandler, this._typeHandler, this._asStatic);
   }

   public JavaType withHandlersFrom(JavaType var1) {
      Object var2 = super.withHandlersFrom(var1);
      JavaType var3 = var1.getKeyType();
      JavaType var4;
      if (var2 instanceof MapLikeType && var3 != null) {
         var4 = this._keyType.withHandlersFrom(var3);
         if (var4 != this._keyType) {
            var2 = ((MapLikeType)var2).withKeyType(var4);
         }
      }

      var4 = var1.getContentType();
      if (var4 != null) {
         JavaType var5 = this._valueType.withHandlersFrom(var4);
         if (var5 != this._valueType) {
            var2 = ((JavaType)var2).withContentType(var5);
         }
      }

      return (JavaType)var2;
   }

   public MapLikeType withStaticTyping() {
      return this._asStatic ? this : new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
   }

   public JavaType refine(Class var1, TypeBindings var2, JavaType var3, JavaType[] var4) {
      return new MapLikeType(var1, var2, var3, var4, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   protected String buildCanonicalName() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this._class.getName());
      if (this._keyType != null) {
         var1.append('<');
         var1.append(this._keyType.toCanonical());
         var1.append(',');
         var1.append(this._valueType.toCanonical());
         var1.append('>');
      }

      return var1.toString();
   }

   public boolean isContainerType() {
      return true;
   }

   public boolean isMapLikeType() {
      return true;
   }

   public JavaType getKeyType() {
      return this._keyType;
   }

   public JavaType getContentType() {
      return this._valueType;
   }

   public Object getContentValueHandler() {
      return this._valueType.getValueHandler();
   }

   public Object getContentTypeHandler() {
      return this._valueType.getTypeHandler();
   }

   public boolean hasHandlers() {
      return super.hasHandlers() || this._valueType.hasHandlers() || this._keyType.hasHandlers();
   }

   public StringBuilder getErasedSignature(StringBuilder var1) {
      return _classSignature(this._class, var1, true);
   }

   public StringBuilder getGenericSignature(StringBuilder var1) {
      _classSignature(this._class, var1, false);
      var1.append('<');
      this._keyType.getGenericSignature(var1);
      this._valueType.getGenericSignature(var1);
      var1.append(">;");
      return var1;
   }

   public MapLikeType withKeyTypeHandler(Object var1) {
      return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.withTypeHandler(var1), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public MapLikeType withKeyValueHandler(Object var1) {
      return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.withValueHandler(var1), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
   }

   public boolean isTrueMapType() {
      return Map.class.isAssignableFrom(this._class);
   }

   public String toString() {
      return String.format("[map-like type; class %s, %s -> %s]", this._class.getName(), this._keyType, this._valueType);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         MapLikeType var2 = (MapLikeType)var1;
         return this._class == var2._class && this._keyType.equals(var2._keyType) && this._valueType.equals(var2._valueType);
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

   public ResolvedType getKeyType() {
      return this.getKeyType();
   }
}
