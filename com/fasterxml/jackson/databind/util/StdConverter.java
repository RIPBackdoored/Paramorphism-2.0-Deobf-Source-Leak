package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.reflect.Type;

public abstract class StdConverter implements Converter {
   public StdConverter() {
      super();
   }

   public abstract Object convert(Object var1);

   public JavaType getInputType(TypeFactory var1) {
      return this._findConverterType(var1).containedType(0);
   }

   public JavaType getOutputType(TypeFactory var1) {
      return this._findConverterType(var1).containedType(1);
   }

   protected JavaType _findConverterType(TypeFactory var1) {
      JavaType var2 = var1.constructType((Type)this.getClass());
      JavaType var3 = var2.findSuperType(Converter.class);
      if (var3 != null && var3.containedTypeCount() >= 2) {
         return var3;
      } else {
         throw new IllegalStateException("Cannot find OUT type parameter for Converter of type " + this.getClass().getName());
      }
   }
}
