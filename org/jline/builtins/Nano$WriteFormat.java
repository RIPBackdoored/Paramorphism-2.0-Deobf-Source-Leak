package org.jline.builtins;

public final class Nano$WriteFormat extends Enum {
   public static final Nano$WriteFormat UNIX = new Nano$WriteFormat("UNIX", 0);
   public static final Nano$WriteFormat DOS = new Nano$WriteFormat("DOS", 1);
   public static final Nano$WriteFormat MAC = new Nano$WriteFormat("MAC", 2);
   private static final Nano$WriteFormat[] $VALUES = new Nano$WriteFormat[]{UNIX, DOS, MAC};

   public static Nano$WriteFormat[] values() {
      return (Nano$WriteFormat[])$VALUES.clone();
   }

   public static Nano$WriteFormat valueOf(String var0) {
      return (Nano$WriteFormat)Enum.valueOf(Nano$WriteFormat.class, var0);
   }

   private Nano$WriteFormat(String var1, int var2) {
      super(var1, var2);
   }
}
