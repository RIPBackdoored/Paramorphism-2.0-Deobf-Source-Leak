package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import java.util.HashMap;

class PropertyBasedCreator$CaseInsensitiveMap extends HashMap {
   private static final long serialVersionUID = 1L;

   PropertyBasedCreator$CaseInsensitiveMap() {
      super();
   }

   public SettableBeanProperty get(Object var1) {
      return (SettableBeanProperty)super.get(((String)var1).toLowerCase());
   }

   public SettableBeanProperty put(String var1, SettableBeanProperty var2) {
      var1 = var1.toLowerCase();
      return (SettableBeanProperty)super.put(var1, var2);
   }

   public Object put(Object var1, Object var2) {
      return this.put((String)var1, (SettableBeanProperty)var2);
   }

   public Object get(Object var1) {
      return this.get(var1);
   }
}
