package com.fasterxml.jackson.annotation;

import java.io.Serializable;

public class JsonInclude$Value implements JacksonAnnotationValue, Serializable {
   private static final long serialVersionUID = 1L;
   protected static final JsonInclude$Value EMPTY;
   protected final JsonInclude$Include _valueInclusion;
   protected final JsonInclude$Include _contentInclusion;
   protected final Class _valueFilter;
   protected final Class _contentFilter;

   public JsonInclude$Value(JsonInclude var1) {
      this(var1.value(), var1.content(), var1.valueFilter(), var1.contentFilter());
   }

   protected JsonInclude$Value(JsonInclude$Include var1, JsonInclude$Include var2, Class var3, Class var4) {
      super();
      this._valueInclusion = var1 == null ? JsonInclude$Include.USE_DEFAULTS : var1;
      this._contentInclusion = var2 == null ? JsonInclude$Include.USE_DEFAULTS : var2;
      this._valueFilter = var3 == Void.class ? null : var3;
      this._contentFilter = var4 == Void.class ? null : var4;
   }

   public static JsonInclude$Value empty() {
      return EMPTY;
   }

   public static JsonInclude$Value merge(JsonInclude$Value var0, JsonInclude$Value var1) {
      return var0 == null ? var1 : var0.withOverrides(var1);
   }

   public static JsonInclude$Value mergeAll(JsonInclude$Value... var0) {
      JsonInclude$Value var1 = null;
      JsonInclude$Value[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         JsonInclude$Value var5 = var2[var4];
         if (var5 != null) {
            var1 = var1 == null ? var5 : var1.withOverrides(var5);
         }
      }

      return var1;
   }

   protected Object readResolve() {
      return this._valueInclusion == JsonInclude$Include.USE_DEFAULTS && this._contentInclusion == JsonInclude$Include.USE_DEFAULTS && this._valueFilter == null && this._contentFilter == null ? EMPTY : this;
   }

   public JsonInclude$Value withOverrides(JsonInclude$Value var1) {
      if (var1 != null && var1 != EMPTY) {
         JsonInclude$Include var2 = var1._valueInclusion;
         JsonInclude$Include var3 = var1._contentInclusion;
         Class var4 = var1._valueFilter;
         Class var5 = var1._contentFilter;
         boolean var6 = var2 != this._valueInclusion && var2 != JsonInclude$Include.USE_DEFAULTS;
         boolean var7 = var3 != this._contentInclusion && var3 != JsonInclude$Include.USE_DEFAULTS;
         boolean var8 = var4 != this._valueFilter || var5 != this._valueFilter;
         if (var6) {
            return var7 ? new JsonInclude$Value(var2, var3, var4, var5) : new JsonInclude$Value(var2, this._contentInclusion, var4, var5);
         } else if (var7) {
            return new JsonInclude$Value(this._valueInclusion, var3, var4, var5);
         } else {
            return var8 ? new JsonInclude$Value(this._valueInclusion, this._contentInclusion, var4, var5) : this;
         }
      } else {
         return this;
      }
   }

   public static JsonInclude$Value construct(JsonInclude$Include var0, JsonInclude$Include var1) {
      return var0 != JsonInclude$Include.USE_DEFAULTS && var0 != null || var1 != JsonInclude$Include.USE_DEFAULTS && var1 != null ? new JsonInclude$Value(var0, var1, (Class)null, (Class)null) : EMPTY;
   }

   public static JsonInclude$Value construct(JsonInclude$Include var0, JsonInclude$Include var1, Class var2, Class var3) {
      if (var2 == Void.class) {
         var2 = null;
      }

      if (var3 == Void.class) {
         var3 = null;
      }

      return (var0 == JsonInclude$Include.USE_DEFAULTS || var0 == null) && (var1 == JsonInclude$Include.USE_DEFAULTS || var1 == null) && var2 == null && var3 == null ? EMPTY : new JsonInclude$Value(var0, var1, var2, var3);
   }

   public static JsonInclude$Value from(JsonInclude var0) {
      if (var0 == null) {
         return EMPTY;
      } else {
         JsonInclude$Include var1 = var0.value();
         JsonInclude$Include var2 = var0.content();
         if (var1 == JsonInclude$Include.USE_DEFAULTS && var2 == JsonInclude$Include.USE_DEFAULTS) {
            return EMPTY;
         } else {
            Class var3 = var0.valueFilter();
            if (var3 == Void.class) {
               var3 = null;
            }

            Class var4 = var0.contentFilter();
            if (var4 == Void.class) {
               var4 = null;
            }

            return new JsonInclude$Value(var1, var2, var3, var4);
         }
      }
   }

   public JsonInclude$Value withValueInclusion(JsonInclude$Include var1) {
      return var1 == this._valueInclusion ? this : new JsonInclude$Value(var1, this._contentInclusion, this._valueFilter, this._contentFilter);
   }

   public JsonInclude$Value withValueFilter(Class var1) {
      JsonInclude$Include var2;
      if (var1 != null && var1 != Void.class) {
         var2 = JsonInclude$Include.CUSTOM;
      } else {
         var2 = JsonInclude$Include.USE_DEFAULTS;
         var1 = null;
      }

      return construct(var2, this._contentInclusion, var1, this._contentFilter);
   }

   public JsonInclude$Value withContentFilter(Class var1) {
      JsonInclude$Include var2;
      if (var1 != null && var1 != Void.class) {
         var2 = JsonInclude$Include.CUSTOM;
      } else {
         var2 = JsonInclude$Include.USE_DEFAULTS;
         var1 = null;
      }

      return construct(this._valueInclusion, var2, this._valueFilter, var1);
   }

   public JsonInclude$Value withContentInclusion(JsonInclude$Include var1) {
      return var1 == this._contentInclusion ? this : new JsonInclude$Value(this._valueInclusion, var1, this._valueFilter, this._contentFilter);
   }

   public Class valueFor() {
      return JsonInclude.class;
   }

   public JsonInclude$Include getValueInclusion() {
      return this._valueInclusion;
   }

   public JsonInclude$Include getContentInclusion() {
      return this._contentInclusion;
   }

   public Class getValueFilter() {
      return this._valueFilter;
   }

   public Class getContentFilter() {
      return this._contentFilter;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(80);
      var1.append("JsonInclude.Value(value=").append(this._valueInclusion).append(",content=").append(this._contentInclusion);
      if (this._valueFilter != null) {
         var1.append(",valueFilter=").append(this._valueFilter.getName()).append(".class");
      }

      if (this._contentFilter != null) {
         var1.append(",contentFilter=").append(this._contentFilter.getName()).append(".class");
      }

      return var1.append(')').toString();
   }

   public int hashCode() {
      return (this._valueInclusion.hashCode() << 2) + this._contentInclusion.hashCode();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         JsonInclude$Value var2 = (JsonInclude$Value)var1;
         return var2._valueInclusion == this._valueInclusion && var2._contentInclusion == this._contentInclusion && var2._valueFilter == this._valueFilter && var2._contentFilter == this._contentFilter;
      }
   }

   static {
      EMPTY = new JsonInclude$Value(JsonInclude$Include.USE_DEFAULTS, JsonInclude$Include.USE_DEFAULTS, (Class)null, (Class)null);
   }
}
