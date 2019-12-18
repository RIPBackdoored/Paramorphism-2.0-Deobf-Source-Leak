package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.util.TypeKey;

final class ReadOnlyClassToSerializerMap$Bucket {
   public final JsonSerializer value;
   public final ReadOnlyClassToSerializerMap$Bucket next;
   protected final Class _class;
   protected final JavaType _type;
   protected final boolean _isTyped;

   public ReadOnlyClassToSerializerMap$Bucket(ReadOnlyClassToSerializerMap$Bucket var1, TypeKey var2, JsonSerializer var3) {
      super();
      this.next = var1;
      this.value = var3;
      this._isTyped = var2.isTyped();
      this._class = var2.getRawType();
      this._type = var2.getType();
   }

   public boolean matchesTyped(Class var1) {
      return this._class == var1 && this._isTyped;
   }

   public boolean matchesUntyped(Class var1) {
      return this._class == var1 && !this._isTyped;
   }

   public boolean matchesTyped(JavaType var1) {
      return this._isTyped && var1.equals(this._type);
   }

   public boolean matchesUntyped(JavaType var1) {
      return !this._isTyped && var1.equals(this._type);
   }
}
