package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

public abstract class FilterProvider {
   public FilterProvider() {
      super();
   }

   /** @deprecated */
   @Deprecated
   public abstract BeanPropertyFilter findFilter(Object var1);

   public PropertyFilter findPropertyFilter(Object var1, Object var2) {
      BeanPropertyFilter var3 = this.findFilter(var1);
      return var3 == null ? null : SimpleBeanPropertyFilter.from(var3);
   }
}
