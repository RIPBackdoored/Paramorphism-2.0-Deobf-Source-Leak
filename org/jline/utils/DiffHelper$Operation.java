package org.jline.utils;

public final class DiffHelper$Operation extends Enum {
   public static final DiffHelper$Operation DELETE = new DiffHelper$Operation("DELETE", 0);
   public static final DiffHelper$Operation INSERT = new DiffHelper$Operation("INSERT", 1);
   public static final DiffHelper$Operation EQUAL = new DiffHelper$Operation("EQUAL", 2);
   private static final DiffHelper$Operation[] $VALUES = new DiffHelper$Operation[]{DELETE, INSERT, EQUAL};

   public static DiffHelper$Operation[] values() {
      return (DiffHelper$Operation[])$VALUES.clone();
   }

   public static DiffHelper$Operation valueOf(String var0) {
      return (DiffHelper$Operation)Enum.valueOf(DiffHelper$Operation.class, var0);
   }

   private DiffHelper$Operation(String var1, int var2) {
      super(var1, var2);
   }
}
