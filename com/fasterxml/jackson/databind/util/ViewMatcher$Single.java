package com.fasterxml.jackson.databind.util;

final class ViewMatcher$Single extends ViewMatcher {
   private static final long serialVersionUID = 1L;
   private final Class _view;

   public ViewMatcher$Single(Class var1) {
      super();
      this._view = var1;
   }

   public boolean isVisibleForView(Class var1) {
      return var1 == this._view || this._view.isAssignableFrom(var1);
   }
}
