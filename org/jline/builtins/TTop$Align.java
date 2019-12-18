package org.jline.builtins;

public final class TTop$Align extends Enum {
   public static final TTop$Align Left = new TTop$Align("Left", 0);
   public static final TTop$Align Right = new TTop$Align("Right", 1);
   private static final TTop$Align[] $VALUES = new TTop$Align[]{Left, Right};

   public static TTop$Align[] values() {
      return (TTop$Align[])$VALUES.clone();
   }

   public static TTop$Align valueOf(String var0) {
      return (TTop$Align)Enum.valueOf(TTop$Align.class, var0);
   }

   private TTop$Align(String var1, int var2) {
      super(var1, var2);
   }
}
