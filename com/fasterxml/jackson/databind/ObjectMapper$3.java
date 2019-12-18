package com.fasterxml.jackson.databind;

class ObjectMapper$3 {
   static final int[] $SwitchMap$com$fasterxml$jackson$databind$ObjectMapper$DefaultTyping = new int[ObjectMapper$DefaultTyping.values().length];

   static {
      try {
         $SwitchMap$com$fasterxml$jackson$databind$ObjectMapper$DefaultTyping[ObjectMapper$DefaultTyping.NON_CONCRETE_AND_ARRAYS.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$databind$ObjectMapper$DefaultTyping[ObjectMapper$DefaultTyping.OBJECT_AND_NON_CONCRETE.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$databind$ObjectMapper$DefaultTyping[ObjectMapper$DefaultTyping.NON_FINAL.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

   }
}
