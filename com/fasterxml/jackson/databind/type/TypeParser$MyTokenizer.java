package com.fasterxml.jackson.databind.type;

import java.util.StringTokenizer;

final class TypeParser$MyTokenizer extends StringTokenizer {
   protected final String _input;
   protected int _index;
   protected String _pushbackToken;

   public TypeParser$MyTokenizer(String var1) {
      super(var1, "<,>", true);
      this._input = var1;
   }

   public boolean hasMoreTokens() {
      return this._pushbackToken != null || super.hasMoreTokens();
   }

   public String nextToken() {
      String var1;
      if (this._pushbackToken != null) {
         var1 = this._pushbackToken;
         this._pushbackToken = null;
      } else {
         var1 = super.nextToken();
         this._index += var1.length();
         var1 = var1.trim();
      }

      return var1;
   }

   public void pushBack(String var1) {
      this._pushbackToken = var1;
   }

   public String getAllInput() {
      return this._input;
   }

   public String getRemainingInput() {
      return this._input.substring(this._index);
   }
}
