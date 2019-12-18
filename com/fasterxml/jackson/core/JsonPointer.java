package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.NumberInput;

public class JsonPointer {
   public static final char SEPARATOR = '/';
   protected static final JsonPointer EMPTY = new JsonPointer();
   protected final JsonPointer _nextSegment;
   protected JsonPointer _head;
   protected final String _asString;
   protected final String _matchingPropertyName;
   protected final int _matchingElementIndex;

   protected JsonPointer() {
      super();
      this._nextSegment = null;
      this._matchingPropertyName = "";
      this._matchingElementIndex = -1;
      this._asString = "";
   }

   protected JsonPointer(String var1, String var2, JsonPointer var3) {
      super();
      this._asString = var1;
      this._nextSegment = var3;
      this._matchingPropertyName = var2;
      this._matchingElementIndex = _parseIndex(var2);
   }

   protected JsonPointer(String var1, String var2, int var3, JsonPointer var4) {
      super();
      this._asString = var1;
      this._nextSegment = var4;
      this._matchingPropertyName = var2;
      this._matchingElementIndex = var3;
   }

   public static JsonPointer compile(String var0) throws IllegalArgumentException {
      if (var0 != null && var0.length() != 0) {
         if (var0.charAt(0) != '/') {
            throw new IllegalArgumentException("Invalid input: JSON Pointer expression must start with '/': \"" + var0 + "\"");
         } else {
            return _parseTail(var0);
         }
      } else {
         return EMPTY;
      }
   }

   public static JsonPointer valueOf(String var0) {
      return compile(var0);
   }

   public static JsonPointer forPath(JsonStreamContext var0, boolean var1) {
      if (var0 == null) {
         return EMPTY;
      } else {
         if (!var0.hasPathSegment() && (!var1 || !var0.inRoot() || !var0.hasCurrentIndex())) {
            var0 = var0.getParent();
         }

         JsonPointer var2;
         for(var2 = null; var0 != null; var0 = var0.getParent()) {
            if (var0.inObject()) {
               String var5 = var0.getCurrentName();
               if (var5 == null) {
                  var5 = "";
               }

               var2 = new JsonPointer(_fullPath(var2, var5), var5, var2);
            } else if (var0.inArray() || var1) {
               int var3 = var0.getCurrentIndex();
               String var4 = String.valueOf(var3);
               var2 = new JsonPointer(_fullPath(var2, var4), var4, var3, var2);
            }
         }

         if (var2 == null) {
            return EMPTY;
         } else {
            return var2;
         }
      }
   }

   private static String _fullPath(JsonPointer var0, String var1) {
      if (var0 == null) {
         StringBuilder var4 = new StringBuilder(var1.length() + 1);
         var4.append('/');
         _appendEscaped(var4, var1);
         return var4.toString();
      } else {
         String var2 = var0._asString;
         StringBuilder var3 = new StringBuilder(var1.length() + 1 + var2.length());
         var3.append('/');
         _appendEscaped(var3, var1);
         var3.append(var2);
         return var3.toString();
      }
   }

   private static void _appendEscaped(StringBuilder var0, String var1) {
      int var2 = 0;

      for(int var3 = var1.length(); var2 < var3; ++var2) {
         char var4 = var1.charAt(var2);
         if (var4 == '/') {
            var0.append("~1");
         } else if (var4 == '~') {
            var0.append("~0");
         } else {
            var0.append(var4);
         }
      }

   }

   public boolean matches() {
      return this._nextSegment == null;
   }

   public String getMatchingProperty() {
      return this._matchingPropertyName;
   }

   public int getMatchingIndex() {
      return this._matchingElementIndex;
   }

   public boolean mayMatchProperty() {
      return this._matchingPropertyName != null;
   }

   public boolean mayMatchElement() {
      return this._matchingElementIndex >= 0;
   }

   public JsonPointer last() {
      JsonPointer var1 = this;
      if (this == EMPTY) {
         return null;
      } else {
         JsonPointer var2;
         while((var2 = var1._nextSegment) != EMPTY) {
            var1 = var2;
         }

         return var1;
      }
   }

   public JsonPointer append(JsonPointer var1) {
      if (this == EMPTY) {
         return var1;
      } else if (var1 == EMPTY) {
         return this;
      } else {
         String var2 = this._asString;
         if (var2.endsWith("/")) {
            var2 = var2.substring(0, var2.length() - 1);
         }

         return compile(var2 + var1._asString);
      }
   }

   public boolean matchesProperty(String var1) {
      return this._nextSegment != null && this._matchingPropertyName.equals(var1);
   }

   public JsonPointer matchProperty(String var1) {
      return this._nextSegment != null && this._matchingPropertyName.equals(var1) ? this._nextSegment : null;
   }

   public boolean matchesElement(int var1) {
      return var1 == this._matchingElementIndex && var1 >= 0;
   }

   public JsonPointer matchElement(int var1) {
      return var1 == this._matchingElementIndex && var1 >= 0 ? this._nextSegment : null;
   }

   public JsonPointer tail() {
      return this._nextSegment;
   }

   public JsonPointer head() {
      JsonPointer var1 = this._head;
      if (var1 == null) {
         if (this != EMPTY) {
            var1 = this._constructHead();
         }

         this._head = var1;
      }

      return var1;
   }

   public String toString() {
      return this._asString;
   }

   public int hashCode() {
      return this._asString.hashCode();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         return !(var1 instanceof JsonPointer) ? false : this._asString.equals(((JsonPointer)var1)._asString);
      }
   }

   private static final int _parseIndex(String var0) {
      int var1 = var0.length();
      if (var1 != 0 && var1 <= 10) {
         char var2 = var0.charAt(0);
         if (var2 <= '0') {
            return var1 == 1 && var2 == '0' ? 0 : -1;
         } else if (var2 > '9') {
            return -1;
         } else {
            for(int var3 = 1; var3 < var1; ++var3) {
               var2 = var0.charAt(var3);
               if (var2 > '9' || var2 < '0') {
                  return -1;
               }
            }

            if (var1 == 10) {
               long var5 = NumberInput.parseLong(var0);
               if (var5 > 0L) {
                  return -1;
               }
            }

            return NumberInput.parseInt(var0);
         }
      } else {
         return -1;
      }
   }

   protected static JsonPointer _parseTail(String var0) {
      int var1 = var0.length();
      int var2 = 1;

      char var3;
      do {
         if (var2 >= var1) {
            return new JsonPointer(var0, var0.substring(1), EMPTY);
         }

         var3 = var0.charAt(var2);
         if (var3 == '/') {
            return new JsonPointer(var0, var0.substring(1, var2), _parseTail(var0.substring(var2)));
         }

         ++var2;
      } while(var3 != '~' || var2 >= var1);

      return _parseQuotedTail(var0, var2);
   }

   protected static JsonPointer _parseQuotedTail(String var0, int var1) {
      int var2 = var0.length();
      StringBuilder var3 = new StringBuilder(Math.max(16, var2));
      if (var1 > 2) {
         var3.append(var0, 1, var1 - 1);
      }

      _appendEscape(var3, var0.charAt(var1++));

      while(true) {
         while(var1 < var2) {
            char var4 = var0.charAt(var1);
            if (var4 == '/') {
               return new JsonPointer(var0, var3.toString(), _parseTail(var0.substring(var1)));
            }

            ++var1;
            if (var4 == '~' && var1 < var2) {
               _appendEscape(var3, var0.charAt(var1++));
            } else {
               var3.append(var4);
            }
         }

         return new JsonPointer(var0, var3.toString(), EMPTY);
      }
   }

   protected JsonPointer _constructHead() {
      JsonPointer var1 = this.last();
      if (var1 == this) {
         return EMPTY;
      } else {
         int var2 = var1._asString.length();
         JsonPointer var3 = this._nextSegment;
         return new JsonPointer(this._asString.substring(0, this._asString.length() - var2), this._matchingPropertyName, this._matchingElementIndex, var3._constructHead(var2, var1));
      }
   }

   protected JsonPointer _constructHead(int var1, JsonPointer var2) {
      if (this == var2) {
         return EMPTY;
      } else {
         JsonPointer var3 = this._nextSegment;
         String var4 = this._asString;
         return new JsonPointer(var4.substring(0, var4.length() - var1), this._matchingPropertyName, this._matchingElementIndex, var3._constructHead(var1, var2));
      }
   }

   private static void _appendEscape(StringBuilder var0, char var1) {
      if (var1 == '0') {
         var1 = '~';
      } else if (var1 == '1') {
         var1 = '/';
      } else {
         var0.append('~');
      }

      var0.append(var1);
   }
}
