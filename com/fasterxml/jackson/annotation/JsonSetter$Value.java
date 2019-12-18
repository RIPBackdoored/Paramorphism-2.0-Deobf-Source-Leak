package com.fasterxml.jackson.annotation;

import java.io.Serializable;

public class JsonSetter$Value implements JacksonAnnotationValue, Serializable {
   private static final long serialVersionUID = 1L;
   private final Nulls _nulls;
   private final Nulls _contentNulls;
   protected static final JsonSetter$Value EMPTY;

   protected JsonSetter$Value(Nulls var1, Nulls var2) {
      super();
      this._nulls = var1;
      this._contentNulls = var2;
   }

   public Class valueFor() {
      return JsonSetter.class;
   }

   protected Object readResolve() {
      return _empty(this._nulls, this._contentNulls) ? EMPTY : this;
   }

   public static JsonSetter$Value from(JsonSetter var0) {
      return var0 == null ? EMPTY : construct(var0.nulls(), var0.contentNulls());
   }

   public static JsonSetter$Value construct(Nulls var0, Nulls var1) {
      if (var0 == null) {
         var0 = Nulls.DEFAULT;
      }

      if (var1 == null) {
         var1 = Nulls.DEFAULT;
      }

      return _empty(var0, var1) ? EMPTY : new JsonSetter$Value(var0, var1);
   }

   public static JsonSetter$Value empty() {
      return EMPTY;
   }

   public static JsonSetter$Value merge(JsonSetter$Value var0, JsonSetter$Value var1) {
      return var0 == null ? var1 : var0.withOverrides(var1);
   }

   public static JsonSetter$Value forValueNulls(Nulls var0) {
      return construct(var0, Nulls.DEFAULT);
   }

   public static JsonSetter$Value forValueNulls(Nulls var0, Nulls var1) {
      return construct(var0, var1);
   }

   public static JsonSetter$Value forContentNulls(Nulls var0) {
      return construct(Nulls.DEFAULT, var0);
   }

   public JsonSetter$Value withOverrides(JsonSetter$Value var1) {
      if (var1 != null && var1 != EMPTY) {
         Nulls var2 = var1._nulls;
         Nulls var3 = var1._contentNulls;
         if (var2 == Nulls.DEFAULT) {
            var2 = this._nulls;
         }

         if (var3 == Nulls.DEFAULT) {
            var3 = this._contentNulls;
         }

         return var2 == this._nulls && var3 == this._contentNulls ? this : construct(var2, var3);
      } else {
         return this;
      }
   }

   public JsonSetter$Value withValueNulls(Nulls var1) {
      if (var1 == null) {
         var1 = Nulls.DEFAULT;
      }

      return var1 == this._nulls ? this : construct(var1, this._contentNulls);
   }

   public JsonSetter$Value withValueNulls(Nulls var1, Nulls var2) {
      if (var1 == null) {
         var1 = Nulls.DEFAULT;
      }

      if (var2 == null) {
         var2 = Nulls.DEFAULT;
      }

      return var1 == this._nulls && var2 == this._contentNulls ? this : construct(var1, var2);
   }

   public JsonSetter$Value withContentNulls(Nulls var1) {
      if (var1 == null) {
         var1 = Nulls.DEFAULT;
      }

      return var1 == this._contentNulls ? this : construct(this._nulls, var1);
   }

   public Nulls getValueNulls() {
      return this._nulls;
   }

   public Nulls getContentNulls() {
      return this._contentNulls;
   }

   public Nulls nonDefaultValueNulls() {
      return this._nulls == Nulls.DEFAULT ? null : this._nulls;
   }

   public Nulls nonDefaultContentNulls() {
      return this._contentNulls == Nulls.DEFAULT ? null : this._contentNulls;
   }

   public String toString() {
      return String.format("JsonSetter.Value(valueNulls=%s,contentNulls=%s)", this._nulls, this._contentNulls);
   }

   public int hashCode() {
      return this._nulls.ordinal() + (this._contentNulls.ordinal() << 2);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         JsonSetter$Value var2 = (JsonSetter$Value)var1;
         return var2._nulls == this._nulls && var2._contentNulls == this._contentNulls;
      }
   }

   private static boolean _empty(Nulls var0, Nulls var1) {
      return var0 == Nulls.DEFAULT && var1 == Nulls.DEFAULT;
   }

   static {
      EMPTY = new JsonSetter$Value(Nulls.DEFAULT, Nulls.DEFAULT);
   }
}
