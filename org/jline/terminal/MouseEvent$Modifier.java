package org.jline.terminal;

public final class MouseEvent$Modifier extends Enum {
   public static final MouseEvent$Modifier Shift = new MouseEvent$Modifier("Shift", 0);
   public static final MouseEvent$Modifier Alt = new MouseEvent$Modifier("Alt", 1);
   public static final MouseEvent$Modifier Control = new MouseEvent$Modifier("Control", 2);
   private static final MouseEvent$Modifier[] $VALUES = new MouseEvent$Modifier[]{Shift, Alt, Control};

   public static MouseEvent$Modifier[] values() {
      return (MouseEvent$Modifier[])$VALUES.clone();
   }

   public static MouseEvent$Modifier valueOf(String var0) {
      return (MouseEvent$Modifier)Enum.valueOf(MouseEvent$Modifier.class, var0);
   }

   private MouseEvent$Modifier(String var1, int var2) {
      super(var1, var2);
   }
}
