package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonFormat$Shape;
import com.fasterxml.jackson.annotation.JsonInclude$Include;

class BasicSerializerFactory$1 {
   static final int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonFormat$Shape;
   static final int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include = new int[JsonInclude$Include.values().length];

   static {
      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.NON_DEFAULT.ordinal()] = 1;
      } catch (NoSuchFieldError var9) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.NON_ABSENT.ordinal()] = 2;
      } catch (NoSuchFieldError var8) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.NON_EMPTY.ordinal()] = 3;
      } catch (NoSuchFieldError var7) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.CUSTOM.ordinal()] = 4;
      } catch (NoSuchFieldError var6) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.NON_NULL.ordinal()] = 5;
      } catch (NoSuchFieldError var5) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.USE_DEFAULTS.ordinal()] = 6;
      } catch (NoSuchFieldError var4) {
      }

      $SwitchMap$com$fasterxml$jackson$annotation$JsonFormat$Shape = new int[JsonFormat$Shape.values().length];

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonFormat$Shape[JsonFormat$Shape.STRING.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonFormat$Shape[JsonFormat$Shape.OBJECT.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonFormat$Shape[JsonFormat$Shape.ARRAY.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

   }
}
