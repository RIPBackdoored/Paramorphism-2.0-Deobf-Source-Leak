package org.jline.terminal;

public final class Terminal$MouseTracking extends Enum {
   public static final Terminal$MouseTracking Off = new Terminal$MouseTracking("Off", 0);
   public static final Terminal$MouseTracking Normal = new Terminal$MouseTracking("Normal", 1);
   public static final Terminal$MouseTracking Button = new Terminal$MouseTracking("Button", 2);
   public static final Terminal$MouseTracking Any = new Terminal$MouseTracking("Any", 3);
   private static final Terminal$MouseTracking[] $VALUES = new Terminal$MouseTracking[]{Off, Normal, Button, Any};

   public static Terminal$MouseTracking[] values() {
      return (Terminal$MouseTracking[])$VALUES.clone();
   }

   public static Terminal$MouseTracking valueOf(String var0) {
      return (Terminal$MouseTracking)Enum.valueOf(Terminal$MouseTracking.class, var0);
   }

   private Terminal$MouseTracking(String var1, int var2) {
      super(var1, var2);
   }
}
