package com.fasterxml.jackson.databind.util;

final class NameTransformer$2 extends NameTransformer {
   final String val$prefix;

   NameTransformer$2(String var1) {
      super();
      this.val$prefix = var1;
   }

   public String transform(String var1) {
      return this.val$prefix + var1;
   }

   public String reverse(String var1) {
      return var1.startsWith(this.val$prefix) ? var1.substring(this.val$prefix.length()) : null;
   }

   public String toString() {
      return "[PrefixTransformer('" + this.val$prefix + "')]";
   }
}
