package com.fasterxml.jackson.annotation;

public class JsonFormat$Features {
   private final int _enabled;
   private final int _disabled;
   private static final JsonFormat$Features EMPTY = new JsonFormat$Features(0, 0);

   private JsonFormat$Features(int var1, int var2) {
      super();
      this._enabled = var1;
      this._disabled = var2;
   }

   public static JsonFormat$Features empty() {
      return EMPTY;
   }

   public static JsonFormat$Features construct(JsonFormat var0) {
      return construct(var0.with(), var0.without());
   }

   public static JsonFormat$Features construct(JsonFormat$Feature[] var0, JsonFormat$Feature[] var1) {
      int var2 = 0;
      JsonFormat$Feature[] var3 = var0;
      int var4 = var0.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         JsonFormat$Feature var6 = var3[var5];
         var2 |= 1 << var6.ordinal();
      }

      int var8 = 0;
      JsonFormat$Feature[] var9 = var1;
      var5 = var1.length;

      for(int var10 = 0; var10 < var5; ++var10) {
         JsonFormat$Feature var7 = var9[var10];
         var8 |= 1 << var7.ordinal();
      }

      return new JsonFormat$Features(var2, var8);
   }

   public JsonFormat$Features withOverrides(JsonFormat$Features var1) {
      if (var1 == null) {
         return this;
      } else {
         int var2 = var1._disabled;
         int var3 = var1._enabled;
         if (var2 == 0 && var3 == 0) {
            return this;
         } else if (this._enabled == 0 && this._disabled == 0) {
            return var1;
         } else {
            int var4 = this._enabled & ~var2 | var3;
            int var5 = this._disabled & ~var3 | var2;
            return var4 == this._enabled && var5 == this._disabled ? this : new JsonFormat$Features(var4, var5);
         }
      }
   }

   public JsonFormat$Features with(JsonFormat$Feature... var1) {
      int var2 = this._enabled;
      JsonFormat$Feature[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         JsonFormat$Feature var6 = var3[var5];
         var2 |= 1 << var6.ordinal();
      }

      return var2 == this._enabled ? this : new JsonFormat$Features(var2, this._disabled);
   }

   public JsonFormat$Features without(JsonFormat$Feature... var1) {
      int var2 = this._disabled;
      JsonFormat$Feature[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         JsonFormat$Feature var6 = var3[var5];
         var2 |= 1 << var6.ordinal();
      }

      return var2 == this._disabled ? this : new JsonFormat$Features(this._enabled, var2);
   }

   public Boolean get(JsonFormat$Feature var1) {
      int var2 = 1 << var1.ordinal();
      if ((this._disabled & var2) != 0) {
         return Boolean.FALSE;
      } else {
         return (this._enabled & var2) != 0 ? Boolean.TRUE : null;
      }
   }

   public int hashCode() {
      return this._disabled + this._enabled;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         JsonFormat$Features var2 = (JsonFormat$Features)var1;
         return var2._enabled == this._enabled && var2._disabled == this._disabled;
      }
   }
}
