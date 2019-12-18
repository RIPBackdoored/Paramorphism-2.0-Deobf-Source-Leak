package com.fasterxml.jackson.annotation;

public final class Nulls extends Enum {
   public static final Nulls SET = new Nulls("SET", 0);
   public static final Nulls SKIP = new Nulls("SKIP", 1);
   public static final Nulls FAIL = new Nulls("FAIL", 2);
   public static final Nulls AS_EMPTY = new Nulls("AS_EMPTY", 3);
   public static final Nulls DEFAULT = new Nulls("DEFAULT", 4);
   private static final Nulls[] $VALUES = new Nulls[]{SET, SKIP, FAIL, AS_EMPTY, DEFAULT};

   public static Nulls[] values() {
      return (Nulls[])$VALUES.clone();
   }

   public static Nulls valueOf(String var0) {
      return (Nulls)Enum.valueOf(Nulls.class, var0);
   }

   private Nulls(String var1, int var2) {
      super(var1, var2);
   }
}
