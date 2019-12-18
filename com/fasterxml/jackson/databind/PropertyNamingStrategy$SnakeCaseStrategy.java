package com.fasterxml.jackson.databind;

public class PropertyNamingStrategy$SnakeCaseStrategy extends PropertyNamingStrategy$PropertyNamingStrategyBase {
   public PropertyNamingStrategy$SnakeCaseStrategy() {
      super();
   }

   public String translate(String var1) {
      if (var1 == null) {
         return var1;
      } else {
         int var2 = var1.length();
         StringBuilder var3 = new StringBuilder(var2 * 2);
         int var4 = 0;
         boolean var5 = false;

         for(int var6 = 0; var6 < var2; ++var6) {
            char var7 = var1.charAt(var6);
            if (var6 > 0 || var7 != '_') {
               if (Character.isUpperCase(var7)) {
                  if (!var5 && var4 > 0 && var3.charAt(var4 - 1) != '_') {
                     var3.append('_');
                     ++var4;
                  }

                  var7 = Character.toLowerCase(var7);
                  var5 = true;
               } else {
                  var5 = false;
               }

               var3.append(var7);
               ++var4;
            }
         }

         return var4 > 0 ? var3.toString() : var1;
      }
   }
}
