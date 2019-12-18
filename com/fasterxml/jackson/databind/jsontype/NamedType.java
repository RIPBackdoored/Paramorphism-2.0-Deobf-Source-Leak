package com.fasterxml.jackson.databind.jsontype;

import java.io.Serializable;

public final class NamedType implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final Class _class;
   protected final int _hashCode;
   protected String _name;

   public NamedType(Class var1) {
      this(var1, (String)null);
   }

   public NamedType(Class var1, String var2) {
      super();
      this._class = var1;
      this._hashCode = var1.getName().hashCode();
      this.setName(var2);
   }

   public Class getType() {
      return this._class;
   }

   public String getName() {
      return this._name;
   }

   public void setName(String var1) {
      this._name = var1 != null && var1.length() != 0 ? var1 : null;
   }

   public boolean hasName() {
      return this._name != null;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         return this._class == ((NamedType)var1)._class;
      }
   }

   public int hashCode() {
      return this._hashCode;
   }

   public String toString() {
      return "[NamedType, class " + this._class.getName() + ", name: " + (this._name == null ? "null" : "'" + this._name + "'") + "]";
   }
}
