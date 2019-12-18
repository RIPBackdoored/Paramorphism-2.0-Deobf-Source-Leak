package com.fasterxml.jackson.databind.util;

import java.io.Serializable;

public class NameTransformer$Chained extends NameTransformer implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final NameTransformer _t1;
   protected final NameTransformer _t2;

   public NameTransformer$Chained(NameTransformer var1, NameTransformer var2) {
      super();
      this._t1 = var1;
      this._t2 = var2;
   }

   public String transform(String var1) {
      return this._t1.transform(this._t2.transform(var1));
   }

   public String reverse(String var1) {
      var1 = this._t1.reverse(var1);
      if (var1 != null) {
         var1 = this._t2.reverse(var1);
      }

      return var1;
   }

   public String toString() {
      return "[ChainedTransformer(" + this._t1 + ", " + this._t2 + ")]";
   }
}
