package com.fasterxml.jackson.databind.util;

final class NameTransformer$1 extends NameTransformer {
   final String val$prefix;
   final String val$suffix;

   NameTransformer$1(String var1, String var2) {
      super();
      this.val$prefix = var1;
      this.val$suffix = var2;
   }

   public String transform(String var1) {
      return this.val$prefix + var1 + this.val$suffix;
   }

   public String reverse(String var1) {
      if (var1.startsWith(this.val$prefix)) {
         String var2 = var1.substring(this.val$prefix.length());
         if (var2.endsWith(this.val$suffix)) {
            return var2.substring(0, var2.length() - this.val$suffix.length());
         }
      }

      return null;
   }

   public String toString() {
      return "[PreAndSuffixTransformer('" + this.val$prefix + "','" + this.val$suffix + "')]";
   }
}
