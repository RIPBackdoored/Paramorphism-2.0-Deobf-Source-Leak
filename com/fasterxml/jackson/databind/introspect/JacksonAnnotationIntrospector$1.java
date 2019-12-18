package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.annotation.JsonSerialize$Inclusion;

class JacksonAnnotationIntrospector$1 {
   static final int[] $SwitchMap$com$fasterxml$jackson$databind$annotation$JsonSerialize$Inclusion = new int[JsonSerialize$Inclusion.values().length];

   static {
      try {
         $SwitchMap$com$fasterxml$jackson$databind$annotation$JsonSerialize$Inclusion[JsonSerialize$Inclusion.ALWAYS.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$databind$annotation$JsonSerialize$Inclusion[JsonSerialize$Inclusion.NON_NULL.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$databind$annotation$JsonSerialize$Inclusion[JsonSerialize$Inclusion.NON_DEFAULT.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$databind$annotation$JsonSerialize$Inclusion[JsonSerialize$Inclusion.NON_EMPTY.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$databind$annotation$JsonSerialize$Inclusion[JsonSerialize$Inclusion.DEFAULT_INCLUSION.ordinal()] = 5;
      } catch (NoSuchFieldError var1) {
      }

   }
}
