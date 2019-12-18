package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser$NumberType;

class NumberDeserializers$1 {
   static final int[] $SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType = new int[JsonParser$NumberType.values().length];

   static {
      try {
         $SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[JsonParser$NumberType.INT.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[JsonParser$NumberType.LONG.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[JsonParser$NumberType.BIG_INTEGER.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

   }
}
