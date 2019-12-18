package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import java.io.Serializable;
import java.util.Set;

public class SimpleBeanPropertyFilter$FilterExceptFilter extends SimpleBeanPropertyFilter implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final Set _propertiesToInclude;

   public SimpleBeanPropertyFilter$FilterExceptFilter(Set var1) {
      super();
      this._propertiesToInclude = var1;
   }

   protected boolean include(BeanPropertyWriter var1) {
      return this._propertiesToInclude.contains(var1.getName());
   }

   protected boolean include(PropertyWriter var1) {
      return this._propertiesToInclude.contains(var1.getName());
   }
}
