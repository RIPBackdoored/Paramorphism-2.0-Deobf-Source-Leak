package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;

final class TypeBindings$AsKey {
   private final Class _raw;
   private final JavaType[] _params;
   private final int _hash;

   public TypeBindings$AsKey(Class var1, JavaType[] var2, int var3) {
      super();
      this._raw = var1;
      this._params = var2;
      this._hash = var3;
   }

   public int hashCode() {
      return this._hash;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         TypeBindings$AsKey var2 = (TypeBindings$AsKey)var1;
         if (this._hash == var2._hash && this._raw == var2._raw) {
            JavaType[] var3 = var2._params;
            int var4 = this._params.length;
            if (var4 == var3.length) {
               for(int var5 = 0; var5 < var4; ++var5) {
                  if (!this._params[var5].equals(var3[var5])) {
                     return false;
                  }
               }

               return true;
            }
         }

         return false;
      }
   }

   public String toString() {
      return this._raw.getName() + "<>";
   }
}
