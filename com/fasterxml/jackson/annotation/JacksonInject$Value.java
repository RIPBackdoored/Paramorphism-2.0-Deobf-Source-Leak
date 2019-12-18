package com.fasterxml.jackson.annotation;

import java.io.Serializable;

public class JacksonInject$Value implements JacksonAnnotationValue, Serializable {
   private static final long serialVersionUID = 1L;
   protected static final JacksonInject$Value EMPTY = new JacksonInject$Value((Object)null, (Boolean)null);
   protected final Object _id;
   protected final Boolean _useInput;

   protected JacksonInject$Value(Object var1, Boolean var2) {
      super();
      this._id = var1;
      this._useInput = var2;
   }

   public Class valueFor() {
      return JacksonInject.class;
   }

   public static JacksonInject$Value empty() {
      return EMPTY;
   }

   public static JacksonInject$Value construct(Object var0, Boolean var1) {
      if ("".equals(var0)) {
         var0 = null;
      }

      return _empty(var0, var1) ? EMPTY : new JacksonInject$Value(var0, var1);
   }

   public static JacksonInject$Value from(JacksonInject var0) {
      return var0 == null ? EMPTY : construct(var0.value(), var0.useInput().asBoolean());
   }

   public static JacksonInject$Value forId(Object var0) {
      return construct(var0, (Boolean)null);
   }

   public JacksonInject$Value withId(Object var1) {
      if (var1 == null) {
         if (this._id == null) {
            return this;
         }
      } else if (var1.equals(this._id)) {
         return this;
      }

      return new JacksonInject$Value(var1, this._useInput);
   }

   public JacksonInject$Value withUseInput(Boolean var1) {
      if (var1 == null) {
         if (this._useInput == null) {
            return this;
         }
      } else if (var1.equals(this._useInput)) {
         return this;
      }

      return new JacksonInject$Value(this._id, var1);
   }

   public Object getId() {
      return this._id;
   }

   public Boolean getUseInput() {
      return this._useInput;
   }

   public boolean hasId() {
      return this._id != null;
   }

   public boolean willUseInput(boolean var1) {
      return this._useInput == null ? var1 : this._useInput;
   }

   public String toString() {
      return String.format("JacksonInject.Value(id=%s,useInput=%s)", this._id, this._useInput);
   }

   public int hashCode() {
      int var1 = 1;
      if (this._id != null) {
         var1 += this._id.hashCode();
      }

      if (this._useInput != null) {
         var1 += this._useInput.hashCode();
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         if (var1.getClass() == this.getClass()) {
            JacksonInject$Value var2 = (JacksonInject$Value)var1;
            if (OptBoolean.equals(this._useInput, var2._useInput)) {
               if (this._id == null) {
                  return var2._id == null;
               }

               return this._id.equals(var2._id);
            }
         }

         return false;
      }
   }

   private static boolean _empty(Object var0, Boolean var1) {
      return var0 == null && var1 == null;
   }
}
