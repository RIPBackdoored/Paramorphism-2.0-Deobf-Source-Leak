package com.fasterxml.jackson.annotation;

import java.io.Serializable;

public class JsonAutoDetect$Value implements JacksonAnnotationValue, Serializable {
   private static final long serialVersionUID = 1L;
   private static final JsonAutoDetect$Visibility DEFAULT_FIELD_VISIBILITY;
   protected static final JsonAutoDetect$Value DEFAULT;
   protected static final JsonAutoDetect$Value NO_OVERRIDES;
   protected final JsonAutoDetect$Visibility _fieldVisibility;
   protected final JsonAutoDetect$Visibility _getterVisibility;
   protected final JsonAutoDetect$Visibility _isGetterVisibility;
   protected final JsonAutoDetect$Visibility _setterVisibility;
   protected final JsonAutoDetect$Visibility _creatorVisibility;

   private JsonAutoDetect$Value(JsonAutoDetect$Visibility var1, JsonAutoDetect$Visibility var2, JsonAutoDetect$Visibility var3, JsonAutoDetect$Visibility var4, JsonAutoDetect$Visibility var5) {
      super();
      this._fieldVisibility = var1;
      this._getterVisibility = var2;
      this._isGetterVisibility = var3;
      this._setterVisibility = var4;
      this._creatorVisibility = var5;
   }

   public static JsonAutoDetect$Value defaultVisibility() {
      return DEFAULT;
   }

   public static JsonAutoDetect$Value noOverrides() {
      return NO_OVERRIDES;
   }

   public static JsonAutoDetect$Value from(JsonAutoDetect var0) {
      return construct(var0.fieldVisibility(), var0.getterVisibility(), var0.isGetterVisibility(), var0.setterVisibility(), var0.creatorVisibility());
   }

   public static JsonAutoDetect$Value construct(PropertyAccessor var0, JsonAutoDetect$Visibility var1) {
      // $FF: Couldn't be decompiled
   }

   public static JsonAutoDetect$Value construct(JsonAutoDetect$Visibility var0, JsonAutoDetect$Visibility var1, JsonAutoDetect$Visibility var2, JsonAutoDetect$Visibility var3, JsonAutoDetect$Visibility var4) {
      JsonAutoDetect$Value var5 = _predefined(var0, var1, var2, var3, var4);
      if (var5 == null) {
         var5 = new JsonAutoDetect$Value(var0, var1, var2, var3, var4);
      }

      return var5;
   }

   public JsonAutoDetect$Value withFieldVisibility(JsonAutoDetect$Visibility var1) {
      return construct(var1, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
   }

   public JsonAutoDetect$Value withGetterVisibility(JsonAutoDetect$Visibility var1) {
      return construct(this._fieldVisibility, var1, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
   }

   public JsonAutoDetect$Value withIsGetterVisibility(JsonAutoDetect$Visibility var1) {
      return construct(this._fieldVisibility, this._getterVisibility, var1, this._setterVisibility, this._creatorVisibility);
   }

   public JsonAutoDetect$Value withSetterVisibility(JsonAutoDetect$Visibility var1) {
      return construct(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, var1, this._creatorVisibility);
   }

   public JsonAutoDetect$Value withCreatorVisibility(JsonAutoDetect$Visibility var1) {
      return construct(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, var1);
   }

   public static JsonAutoDetect$Value merge(JsonAutoDetect$Value var0, JsonAutoDetect$Value var1) {
      return var0 == null ? var1 : var0.withOverrides(var1);
   }

   public JsonAutoDetect$Value withOverrides(JsonAutoDetect$Value var1) {
      if (var1 != null && var1 != NO_OVERRIDES && var1 != this) {
         if (_equals(this, var1)) {
            return this;
         } else {
            JsonAutoDetect$Visibility var2 = var1._fieldVisibility;
            if (var2 == JsonAutoDetect$Visibility.DEFAULT) {
               var2 = this._fieldVisibility;
            }

            JsonAutoDetect$Visibility var3 = var1._getterVisibility;
            if (var3 == JsonAutoDetect$Visibility.DEFAULT) {
               var3 = this._getterVisibility;
            }

            JsonAutoDetect$Visibility var4 = var1._isGetterVisibility;
            if (var4 == JsonAutoDetect$Visibility.DEFAULT) {
               var4 = this._isGetterVisibility;
            }

            JsonAutoDetect$Visibility var5 = var1._setterVisibility;
            if (var5 == JsonAutoDetect$Visibility.DEFAULT) {
               var5 = this._setterVisibility;
            }

            JsonAutoDetect$Visibility var6 = var1._creatorVisibility;
            if (var6 == JsonAutoDetect$Visibility.DEFAULT) {
               var6 = this._creatorVisibility;
            }

            return construct(var2, var3, var4, var5, var6);
         }
      } else {
         return this;
      }
   }

   public Class valueFor() {
      return JsonAutoDetect.class;
   }

   public JsonAutoDetect$Visibility getFieldVisibility() {
      return this._fieldVisibility;
   }

   public JsonAutoDetect$Visibility getGetterVisibility() {
      return this._getterVisibility;
   }

   public JsonAutoDetect$Visibility getIsGetterVisibility() {
      return this._isGetterVisibility;
   }

   public JsonAutoDetect$Visibility getSetterVisibility() {
      return this._setterVisibility;
   }

   public JsonAutoDetect$Visibility getCreatorVisibility() {
      return this._creatorVisibility;
   }

   protected Object readResolve() {
      JsonAutoDetect$Value var1 = _predefined(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
      return var1 == null ? this : var1;
   }

   public String toString() {
      return String.format("JsonAutoDetect.Value(fields=%s,getters=%s,isGetters=%s,setters=%s,creators=%s)", this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
   }

   public int hashCode() {
      return 1 + this._fieldVisibility.ordinal() ^ 3 * this._getterVisibility.ordinal() - 7 * this._isGetterVisibility.ordinal() + 11 * this._setterVisibility.ordinal() ^ 13 * this._creatorVisibility.ordinal();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         return var1.getClass() == this.getClass() && _equals(this, (JsonAutoDetect$Value)var1);
      }
   }

   private static JsonAutoDetect$Value _predefined(JsonAutoDetect$Visibility var0, JsonAutoDetect$Visibility var1, JsonAutoDetect$Visibility var2, JsonAutoDetect$Visibility var3, JsonAutoDetect$Visibility var4) {
      if (var0 == DEFAULT_FIELD_VISIBILITY) {
         if (var1 == DEFAULT._getterVisibility && var2 == DEFAULT._isGetterVisibility && var3 == DEFAULT._setterVisibility && var4 == DEFAULT._creatorVisibility) {
            return DEFAULT;
         }
      } else if (var0 == JsonAutoDetect$Visibility.DEFAULT && var1 == JsonAutoDetect$Visibility.DEFAULT && var2 == JsonAutoDetect$Visibility.DEFAULT && var3 == JsonAutoDetect$Visibility.DEFAULT && var4 == JsonAutoDetect$Visibility.DEFAULT) {
         return NO_OVERRIDES;
      }

      return null;
   }

   private static boolean _equals(JsonAutoDetect$Value var0, JsonAutoDetect$Value var1) {
      return var0._fieldVisibility == var1._fieldVisibility && var0._getterVisibility == var1._getterVisibility && var0._isGetterVisibility == var1._isGetterVisibility && var0._setterVisibility == var1._setterVisibility && var0._creatorVisibility == var1._creatorVisibility;
   }

   static {
      DEFAULT_FIELD_VISIBILITY = JsonAutoDetect$Visibility.PUBLIC_ONLY;
      DEFAULT = new JsonAutoDetect$Value(DEFAULT_FIELD_VISIBILITY, JsonAutoDetect$Visibility.PUBLIC_ONLY, JsonAutoDetect$Visibility.PUBLIC_ONLY, JsonAutoDetect$Visibility.ANY, JsonAutoDetect$Visibility.PUBLIC_ONLY);
      NO_OVERRIDES = new JsonAutoDetect$Value(JsonAutoDetect$Visibility.DEFAULT, JsonAutoDetect$Visibility.DEFAULT, JsonAutoDetect$Visibility.DEFAULT, JsonAutoDetect$Visibility.DEFAULT, JsonAutoDetect$Visibility.DEFAULT);
   }
}
