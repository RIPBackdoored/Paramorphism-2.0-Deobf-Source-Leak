package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.JavaType;

public class TypeKey {
   protected int _hashCode;
   protected Class _class;
   protected JavaType _type;
   protected boolean _isTyped;

   public TypeKey() {
      super();
   }

   public TypeKey(TypeKey var1) {
      super();
      this._hashCode = var1._hashCode;
      this._class = var1._class;
      this._type = var1._type;
      this._isTyped = var1._isTyped;
   }

   public TypeKey(Class var1, boolean var2) {
      super();
      this._class = var1;
      this._type = null;
      this._isTyped = var2;
      this._hashCode = var2 ? typedHash(var1) : untypedHash(var1);
   }

   public TypeKey(JavaType var1, boolean var2) {
      super();
      this._type = var1;
      this._class = null;
      this._isTyped = var2;
      this._hashCode = var2 ? typedHash(var1) : untypedHash(var1);
   }

   public static final int untypedHash(Class var0) {
      return var0.getName().hashCode();
   }

   public static final int typedHash(Class var0) {
      return var0.getName().hashCode() + 1;
   }

   public static final int untypedHash(JavaType var0) {
      return var0.hashCode() - 1;
   }

   public static final int typedHash(JavaType var0) {
      return var0.hashCode() - 2;
   }

   public final void resetTyped(Class var1) {
      this._type = null;
      this._class = var1;
      this._isTyped = true;
      this._hashCode = typedHash(var1);
   }

   public final void resetUntyped(Class var1) {
      this._type = null;
      this._class = var1;
      this._isTyped = false;
      this._hashCode = untypedHash(var1);
   }

   public final void resetTyped(JavaType var1) {
      this._type = var1;
      this._class = null;
      this._isTyped = true;
      this._hashCode = typedHash(var1);
   }

   public final void resetUntyped(JavaType var1) {
      this._type = var1;
      this._class = null;
      this._isTyped = false;
      this._hashCode = untypedHash(var1);
   }

   public boolean isTyped() {
      return this._isTyped;
   }

   public Class getRawType() {
      return this._class;
   }

   public JavaType getType() {
      return this._type;
   }

   public final int hashCode() {
      return this._hashCode;
   }

   public final String toString() {
      return this._class != null ? "{class: " + this._class.getName() + ", typed? " + this._isTyped + "}" : "{type: " + this._type + ", typed? " + this._isTyped + "}";
   }

   public final boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (var1 == this) {
         return true;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         TypeKey var2 = (TypeKey)var1;
         if (var2._isTyped == this._isTyped) {
            if (this._class != null) {
               return var2._class == this._class;
            } else {
               return this._type.equals(var2._type);
            }
         } else {
            return false;
         }
      }
   }
}
