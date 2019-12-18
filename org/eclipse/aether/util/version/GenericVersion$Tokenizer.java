package org.eclipse.aether.util.version;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

final class GenericVersion$Tokenizer {
   private static final Integer QUALIFIER_ALPHA = -5;
   private static final Integer QUALIFIER_BETA = -4;
   private static final Integer QUALIFIER_MILESTONE = -3;
   private static final Map QUALIFIERS;
   private final String version;
   private int index;
   private String token;
   private boolean number;
   private boolean terminatedByNumber;

   GenericVersion$Tokenizer(String var1) {
      super();
      this.version = var1.length() > 0 ? var1 : "0";
   }

   public boolean next() {
      int var1 = this.version.length();
      if (this.index >= var1) {
         return false;
      } else {
         int var2 = -2;
         int var3 = this.index;
         int var4 = var1;

         for(this.terminatedByNumber = false; this.index < var1; ++this.index) {
            char var5 = this.version.charAt(this.index);
            if (var5 == '.' || var5 == '-' || var5 == '_') {
               var4 = this.index++;
               break;
            }

            int var6 = Character.digit(var5, 10);
            if (var6 >= 0) {
               if (var2 == -1) {
                  var4 = this.index;
                  this.terminatedByNumber = true;
                  break;
               }

               if (var2 == 0) {
                  ++var3;
               }

               var2 = var2 <= 0 && var6 <= 0 ? 0 : 1;
            } else {
               if (var2 >= 0) {
                  var4 = this.index;
                  break;
               }

               var2 = -1;
            }
         }

         if (var4 - var3 > 0) {
            this.token = this.version.substring(var3, var4);
            this.number = var2 >= 0;
         } else {
            this.token = "0";
            this.number = true;
         }

         return true;
      }
   }

   public String toString() {
      return String.valueOf(this.token);
   }

   public GenericVersion$Item toItem() {
      if (!this.number) {
         if (this.index >= this.version.length()) {
            if ("min".equalsIgnoreCase(this.token)) {
               return GenericVersion$Item.MIN;
            }

            if ("max".equalsIgnoreCase(this.token)) {
               return GenericVersion$Item.MAX;
            }
         }

         if (this.terminatedByNumber && this.token.length() == 1) {
            switch(this.token.charAt(0)) {
            case 'A':
            case 'a':
               return new GenericVersion$Item(2, QUALIFIER_ALPHA);
            case 'B':
            case 'b':
               return new GenericVersion$Item(2, QUALIFIER_BETA);
            case 'M':
            case 'm':
               return new GenericVersion$Item(2, QUALIFIER_MILESTONE);
            }
         }

         Integer var1 = (Integer)QUALIFIERS.get(this.token);
         return var1 != null ? new GenericVersion$Item(2, var1) : new GenericVersion$Item(3, this.token.toLowerCase(Locale.ENGLISH));
      } else {
         GenericVersion$Item var10000;
         try {
            if (this.token.length() < 10) {
               var10000 = new GenericVersion$Item(4, Integer.parseInt(this.token));
               return var10000;
            }

            var10000 = new GenericVersion$Item(5, new BigInteger(this.token));
         } catch (NumberFormatException var2) {
            throw new IllegalStateException(var2);
         }

         return var10000;
      }
   }

   static {
      QUALIFIERS = new TreeMap(String.CASE_INSENSITIVE_ORDER);
      QUALIFIERS.put("alpha", QUALIFIER_ALPHA);
      QUALIFIERS.put("beta", QUALIFIER_BETA);
      QUALIFIERS.put("milestone", QUALIFIER_MILESTONE);
      QUALIFIERS.put("cr", -2);
      QUALIFIERS.put("rc", -2);
      QUALIFIERS.put("snapshot", -1);
      QUALIFIERS.put("ga", 0);
      QUALIFIERS.put("final", 0);
      QUALIFIERS.put("release", 0);
      QUALIFIERS.put("", 0);
      QUALIFIERS.put("sp", 1);
   }
}
