package com.fasterxml.jackson.databind;

public class PropertyNamingStrategy$LowerCaseStrategy extends PropertyNamingStrategy$PropertyNamingStrategyBase {
   public PropertyNamingStrategy$LowerCaseStrategy() {
      super();
   }

   public String translate(String var1) {
      return var1.toLowerCase();
   }
}
