package com.fasterxml.jackson.databind.util;

import java.lang.reflect.Array;

final class ArrayBuilders$1 {
   final Class val$defaultValueType;
   final int val$length;
   final Object val$defaultValue;

   ArrayBuilders$1(Class var1, int var2, Object var3) {
      super();
      this.val$defaultValueType = var1;
      this.val$length = var2;
      this.val$defaultValue = var3;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!ClassUtil.hasClass(var1, this.val$defaultValueType)) {
         return false;
      } else if (Array.getLength(var1) != this.val$length) {
         return false;
      } else {
         for(int var2 = 0; var2 < this.val$length; ++var2) {
            Object var3 = Array.get(this.val$defaultValue, var2);
            Object var4 = Array.get(var1, var2);
            if (var3 != var4 && var3 != null && !var3.equals(var4)) {
               return false;
            }
         }

         return true;
      }
   }
}
