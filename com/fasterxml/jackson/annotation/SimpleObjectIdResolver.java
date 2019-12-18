package com.fasterxml.jackson.annotation;

import java.util.HashMap;
import java.util.Map;

public class SimpleObjectIdResolver implements ObjectIdResolver {
   protected Map _items;

   public SimpleObjectIdResolver() {
      super();
   }

   public void bindItem(ObjectIdGenerator$IdKey var1, Object var2) {
      if (this._items == null) {
         this._items = new HashMap();
      } else if (this._items.containsKey(var1)) {
         throw new IllegalStateException("Already had POJO for id (" + var1.key.getClass().getName() + ") [" + var1 + "]");
      }

      this._items.put(var1, var2);
   }

   public Object resolveId(ObjectIdGenerator$IdKey var1) {
      return this._items == null ? null : this._items.get(var1);
   }

   public boolean canUseFor(ObjectIdResolver var1) {
      return var1.getClass() == this.getClass();
   }

   public ObjectIdResolver newForDeserialization(Object var1) {
      return new SimpleObjectIdResolver();
   }
}
