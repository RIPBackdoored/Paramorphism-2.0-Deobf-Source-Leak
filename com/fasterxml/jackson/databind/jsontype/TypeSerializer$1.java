package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo$As;

class TypeSerializer$1 {
   static final int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As = new int[JsonTypeInfo$As.values().length];

   static {
      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[JsonTypeInfo$As.EXISTING_PROPERTY.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[JsonTypeInfo$As.EXTERNAL_PROPERTY.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[JsonTypeInfo$As.PROPERTY.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[JsonTypeInfo$As.WRAPPER_ARRAY.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[JsonTypeInfo$As.WRAPPER_OBJECT.ordinal()] = 5;
      } catch (NoSuchFieldError var1) {
      }

   }
}
