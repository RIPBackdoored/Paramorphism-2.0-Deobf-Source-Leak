package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JsonCreator$Mode;

class BasicDeserializerFactory$1 {
   static final int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonCreator$Mode = new int[JsonCreator$Mode.values().length];

   static {
      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonCreator$Mode[JsonCreator$Mode.DELEGATING.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonCreator$Mode[JsonCreator$Mode.PROPERTIES.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonCreator$Mode[JsonCreator$Mode.DEFAULT.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

   }
}
