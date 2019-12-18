package com.fasterxml.jackson.databind.util;

final class NameTransformer$3 extends NameTransformer {
   final String val$suffix;

   NameTransformer$3(String var1) {
      super();
      this.val$suffix = var1;
   }

   public String transform(String var1) {
      return var1 + this.val$suffix;
   }

   public String reverse(String var1) {
      return var1.endsWith(this.val$suffix) ? var1.substring(0, var1.length() - this.val$suffix.length()) : null;
   }

   public String toString() {
      return "[SuffixTransformer('" + this.val$suffix + "')]";
   }
}
