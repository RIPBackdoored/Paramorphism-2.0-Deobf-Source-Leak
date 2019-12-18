package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class SimpleBeanPropertyFilter$SerializeExceptFilter extends SimpleBeanPropertyFilter implements Serializable {
   private static final long serialVersionUID = 1L;
   static final SimpleBeanPropertyFilter$SerializeExceptFilter INCLUDE_ALL = new SimpleBeanPropertyFilter$SerializeExceptFilter();
   protected final Set _propertiesToExclude;

   SimpleBeanPropertyFilter$SerializeExceptFilter() {
      super();
      this._propertiesToExclude = Collections.emptySet();
   }

   public SimpleBeanPropertyFilter$SerializeExceptFilter(Set var1) {
      super();
      this._propertiesToExclude = var1;
   }

   protected boolean include(BeanPropertyWriter var1) {
      return !this._propertiesToExclude.contains(var1.getName());
   }

   protected boolean include(PropertyWriter var1) {
      return !this._propertiesToExclude.contains(var1.getName());
   }
}
