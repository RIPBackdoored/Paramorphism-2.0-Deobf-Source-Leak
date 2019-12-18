package com.fasterxml.jackson.databind;

public class PropertyNamingStrategy$UpperCamelCaseStrategy extends PropertyNamingStrategy$PropertyNamingStrategyBase {
   public PropertyNamingStrategy$UpperCamelCaseStrategy() {
      super();
   }

   public String translate(String var1) {
      if (var1 != null && var1.length() != 0) {
         char var2 = var1.charAt(0);
         char var3 = Character.toUpperCase(var2);
         if (var2 == var3) {
            return var1;
         } else {
            StringBuilder var4 = new StringBuilder(var1);
            var4.setCharAt(0, var3);
            return var4.toString();
         }
      } else {
         return var1;
      }
   }
}
