package com.fasterxml.jackson.databind.util;

public final class AccessPattern extends Enum {
   public static final AccessPattern ALWAYS_NULL = new AccessPattern("ALWAYS_NULL", 0);
   public static final AccessPattern CONSTANT = new AccessPattern("CONSTANT", 1);
   public static final AccessPattern DYNAMIC = new AccessPattern("DYNAMIC", 2);
   private static final AccessPattern[] $VALUES = new AccessPattern[]{ALWAYS_NULL, CONSTANT, DYNAMIC};

   public static AccessPattern[] values() {
      return (AccessPattern[])$VALUES.clone();
   }

   public static AccessPattern valueOf(String var0) {
      return (AccessPattern)Enum.valueOf(AccessPattern.class, var0);
   }

   private AccessPattern(String var1, int var2) {
      super(var1, var2);
   }
}
