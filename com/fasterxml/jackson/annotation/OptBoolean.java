package com.fasterxml.jackson.annotation;

public final class OptBoolean extends Enum {
   public static final OptBoolean TRUE = new OptBoolean("TRUE", 0);
   public static final OptBoolean FALSE = new OptBoolean("FALSE", 1);
   public static final OptBoolean DEFAULT = new OptBoolean("DEFAULT", 2);
   private static final OptBoolean[] $VALUES = new OptBoolean[]{TRUE, FALSE, DEFAULT};

   public static OptBoolean[] values() {
      return (OptBoolean[])$VALUES.clone();
   }

   public static OptBoolean valueOf(String var0) {
      return (OptBoolean)Enum.valueOf(OptBoolean.class, var0);
   }

   private OptBoolean(String var1, int var2) {
      super(var1, var2);
   }

   public Boolean asBoolean() {
      if (this == DEFAULT) {
         return null;
      } else {
         return this == TRUE ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   public boolean asPrimitive() {
      return this == TRUE;
   }

   public static OptBoolean fromBoolean(Boolean var0) {
      if (var0 == null) {
         return DEFAULT;
      } else {
         return var0 ? TRUE : FALSE;
      }
   }

   public static boolean equals(Boolean var0, Boolean var1) {
      if (var0 == null) {
         return var1 == null;
      } else {
         return var0.equals(var1);
      }
   }
}
