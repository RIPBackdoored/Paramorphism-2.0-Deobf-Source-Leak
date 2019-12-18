package org.jline.builtins;

public final class Nano$WriteMode extends Enum {
   public static final Nano$WriteMode WRITE = new Nano$WriteMode("WRITE", 0);
   public static final Nano$WriteMode APPEND = new Nano$WriteMode("APPEND", 1);
   public static final Nano$WriteMode PREPEND = new Nano$WriteMode("PREPEND", 2);
   private static final Nano$WriteMode[] $VALUES = new Nano$WriteMode[]{WRITE, APPEND, PREPEND};

   public static Nano$WriteMode[] values() {
      return (Nano$WriteMode[])$VALUES.clone();
   }

   public static Nano$WriteMode valueOf(String var0) {
      return (Nano$WriteMode)Enum.valueOf(Nano$WriteMode.class, var0);
   }

   private Nano$WriteMode(String var1, int var2) {
      super(var1, var2);
   }
}
