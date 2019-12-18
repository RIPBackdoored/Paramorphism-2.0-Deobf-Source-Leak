package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.util.ClassUtil;

public class UnresolvedId {
   private final Object _id;
   private final JsonLocation _location;
   private final Class _type;

   public UnresolvedId(Object var1, Class var2, JsonLocation var3) {
      super();
      this._id = var1;
      this._type = var2;
      this._location = var3;
   }

   public Object getId() {
      return this._id;
   }

   public Class getType() {
      return this._type;
   }

   public JsonLocation getLocation() {
      return this._location;
   }

   public String toString() {
      return String.format("Object id [%s] (for %s) at %s", this._id, ClassUtil.nameOf(this._type), this._location);
   }
}
