package org.jline.builtins;

public final class TTop$Operation extends Enum {
   public static final TTop$Operation INCREASE_DELAY = new TTop$Operation("INCREASE_DELAY", 0);
   public static final TTop$Operation DECREASE_DELAY = new TTop$Operation("DECREASE_DELAY", 1);
   public static final TTop$Operation HELP = new TTop$Operation("HELP", 2);
   public static final TTop$Operation EXIT = new TTop$Operation("EXIT", 3);
   public static final TTop$Operation CLEAR = new TTop$Operation("CLEAR", 4);
   public static final TTop$Operation REVERSE = new TTop$Operation("REVERSE", 5);
   private static final TTop$Operation[] $VALUES = new TTop$Operation[]{INCREASE_DELAY, DECREASE_DELAY, HELP, EXIT, CLEAR, REVERSE};

   public static TTop$Operation[] values() {
      return (TTop$Operation[])$VALUES.clone();
   }

   public static TTop$Operation valueOf(String var0) {
      return (TTop$Operation)Enum.valueOf(TTop$Operation.class, var0);
   }

   private TTop$Operation(String var1, int var2) {
      super(var1, var2);
   }
}
