package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.JsonInclude$Include;

class MapEntrySerializer$1 {
   static final int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include = new int[JsonInclude$Include.values().length];

   static {
      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.NON_DEFAULT.ordinal()] = 1;
      } catch (NoSuchFieldError var6) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.NON_ABSENT.ordinal()] = 2;
      } catch (NoSuchFieldError var5) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.NON_EMPTY.ordinal()] = 3;
      } catch (NoSuchFieldError var4) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.CUSTOM.ordinal()] = 4;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.NON_NULL.ordinal()] = 5;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude$Include.ALWAYS.ordinal()] = 6;
      } catch (NoSuchFieldError var1) {
      }

   }
}
