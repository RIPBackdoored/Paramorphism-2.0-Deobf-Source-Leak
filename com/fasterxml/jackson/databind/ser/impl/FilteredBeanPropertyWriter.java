package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

public abstract class FilteredBeanPropertyWriter {
   public FilteredBeanPropertyWriter() {
      super();
   }

   public static BeanPropertyWriter constructViewBased(BeanPropertyWriter var0, Class[] var1) {
      return (BeanPropertyWriter)(var1.length == 1 ? new FilteredBeanPropertyWriter$SingleView(var0, var1[0]) : new FilteredBeanPropertyWriter$MultiView(var0, var1));
   }
}
