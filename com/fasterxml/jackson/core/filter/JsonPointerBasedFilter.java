package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonPointer;

public class JsonPointerBasedFilter extends TokenFilter {
   protected final JsonPointer _pathToMatch;

   public JsonPointerBasedFilter(String var1) {
      this(JsonPointer.compile(var1));
   }

   public JsonPointerBasedFilter(JsonPointer var1) {
      super();
      this._pathToMatch = var1;
   }

   public TokenFilter includeElement(int var1) {
      JsonPointer var2 = this._pathToMatch.matchElement(var1);
      if (var2 == null) {
         return null;
      } else {
         return (TokenFilter)(var2.matches() ? TokenFilter.INCLUDE_ALL : new JsonPointerBasedFilter(var2));
      }
   }

   public TokenFilter includeProperty(String var1) {
      JsonPointer var2 = this._pathToMatch.matchProperty(var1);
      if (var2 == null) {
         return null;
      } else {
         return (TokenFilter)(var2.matches() ? TokenFilter.INCLUDE_ALL : new JsonPointerBasedFilter(var2));
      }
   }

   public TokenFilter filterStartArray() {
      return this;
   }

   public TokenFilter filterStartObject() {
      return this;
   }

   protected boolean _includeScalar() {
      return this._pathToMatch.matches();
   }

   public String toString() {
      return "[JsonPointerFilter at: " + this._pathToMatch + "]";
   }
}
