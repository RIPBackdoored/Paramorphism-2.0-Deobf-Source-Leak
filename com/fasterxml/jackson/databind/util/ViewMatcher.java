package com.fasterxml.jackson.databind.util;

import java.io.Serializable;

public class ViewMatcher implements Serializable {
   private static final long serialVersionUID = 1L;
   protected static final ViewMatcher EMPTY = new ViewMatcher();

   public ViewMatcher() {
      super();
   }

   public boolean isVisibleForView(Class var1) {
      return false;
   }

   public static ViewMatcher construct(Class[] var0) {
      if (var0 == null) {
         return EMPTY;
      } else {
         switch(var0.length) {
         case 0:
            return EMPTY;
         case 1:
            return new ViewMatcher$Single(var0[0]);
         default:
            return new ViewMatcher$Multi(var0);
         }
      }
   }
}
