package com.fasterxml.jackson.core.format;

public final class MatchStrength extends Enum {
   public static final MatchStrength NO_MATCH = new MatchStrength("NO_MATCH", 0);
   public static final MatchStrength INCONCLUSIVE = new MatchStrength("INCONCLUSIVE", 1);
   public static final MatchStrength WEAK_MATCH = new MatchStrength("WEAK_MATCH", 2);
   public static final MatchStrength SOLID_MATCH = new MatchStrength("SOLID_MATCH", 3);
   public static final MatchStrength FULL_MATCH = new MatchStrength("FULL_MATCH", 4);
   private static final MatchStrength[] $VALUES = new MatchStrength[]{NO_MATCH, INCONCLUSIVE, WEAK_MATCH, SOLID_MATCH, FULL_MATCH};

   public static MatchStrength[] values() {
      return (MatchStrength[])$VALUES.clone();
   }

   public static MatchStrength valueOf(String var0) {
      return (MatchStrength)Enum.valueOf(MatchStrength.class, var0);
   }

   private MatchStrength(String var1, int var2) {
      super(var1, var2);
   }
}
