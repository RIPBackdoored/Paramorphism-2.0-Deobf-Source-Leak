package org.jline.terminal;

public final class Terminal$Signal extends Enum {
   public static final Terminal$Signal INT = new Terminal$Signal("INT", 0);
   public static final Terminal$Signal QUIT = new Terminal$Signal("QUIT", 1);
   public static final Terminal$Signal TSTP = new Terminal$Signal("TSTP", 2);
   public static final Terminal$Signal CONT = new Terminal$Signal("CONT", 3);
   public static final Terminal$Signal INFO = new Terminal$Signal("INFO", 4);
   public static final Terminal$Signal WINCH = new Terminal$Signal("WINCH", 5);
   private static final Terminal$Signal[] $VALUES = new Terminal$Signal[]{INT, QUIT, TSTP, CONT, INFO, WINCH};

   public static Terminal$Signal[] values() {
      return (Terminal$Signal[])$VALUES.clone();
   }

   public static Terminal$Signal valueOf(String var0) {
      return (Terminal$Signal)Enum.valueOf(Terminal$Signal.class, var0);
   }

   private Terminal$Signal(String var1, int var2) {
      super(var1, var2);
   }
}
