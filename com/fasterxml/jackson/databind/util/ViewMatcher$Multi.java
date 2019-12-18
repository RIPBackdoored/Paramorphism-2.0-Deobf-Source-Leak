package com.fasterxml.jackson.databind.util;

import java.io.Serializable;

final class ViewMatcher$Multi extends ViewMatcher implements Serializable {
   private static final long serialVersionUID = 1L;
   private final Class[] _views;

   public ViewMatcher$Multi(Class[] var1) {
      super();
      this._views = var1;
   }

   public boolean isVisibleForView(Class var1) {
      int var2 = 0;

      for(int var3 = this._views.length; var2 < var3; ++var2) {
         Class var4 = this._views[var2];
         if (var1 == var4 || var4.isAssignableFrom(var1)) {
            return true;
         }
      }

      return false;
   }
}
