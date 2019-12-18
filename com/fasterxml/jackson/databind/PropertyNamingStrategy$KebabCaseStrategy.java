package com.fasterxml.jackson.databind;

public class PropertyNamingStrategy$KebabCaseStrategy extends PropertyNamingStrategy$PropertyNamingStrategyBase {
   public PropertyNamingStrategy$KebabCaseStrategy() {
      super();
   }

   public String translate(String var1) {
      if (var1 == null) {
         return var1;
      } else {
         int var2 = var1.length();
         if (var2 == 0) {
            return var1;
         } else {
            StringBuilder var3 = new StringBuilder(var2 + (var2 >> 1));
            int var4 = 0;

            for(int var5 = 0; var5 < var2; ++var5) {
               char var6 = var1.charAt(var5);
               char var7 = Character.toLowerCase(var6);
               if (var7 == var6) {
                  if (var4 > 1) {
                     var3.insert(var3.length() - 1, '-');
                  }

                  var4 = 0;
               } else {
                  if (var4 == 0 && var5 > 0) {
                     var3.append('-');
                  }

                  ++var4;
               }

               var3.append(var7);
            }

            return var3.toString();
         }
      }
   }
}
