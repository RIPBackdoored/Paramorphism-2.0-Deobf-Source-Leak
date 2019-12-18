package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonProperty$Access;

class POJOPropertyBuilder$10 {
   static final int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access = new int[JsonProperty$Access.values().length];

   static {
      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access[JsonProperty$Access.READ_ONLY.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access[JsonProperty$Access.READ_WRITE.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access[JsonProperty$Access.WRITE_ONLY.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access[JsonProperty$Access.AUTO.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
      }

   }
}
