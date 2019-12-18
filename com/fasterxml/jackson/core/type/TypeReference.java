package com.fasterxml.jackson.core.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference implements Comparable {
   protected final Type _type;

   protected TypeReference() {
      super();
      Type var1 = this.getClass().getGenericSuperclass();
      if (var1 instanceof Class) {
         throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
      } else {
         this._type = ((ParameterizedType)var1).getActualTypeArguments()[0];
      }
   }

   public Type getType() {
      return this._type;
   }

   public int compareTo(TypeReference var1) {
      return 0;
   }

   public int compareTo(Object var1) {
      return this.compareTo((TypeReference)var1);
   }
}
