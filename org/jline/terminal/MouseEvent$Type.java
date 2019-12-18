package org.jline.terminal;

public final class MouseEvent$Type extends Enum {
   public static final MouseEvent$Type Released = new MouseEvent$Type("Released", 0);
   public static final MouseEvent$Type Pressed = new MouseEvent$Type("Pressed", 1);
   public static final MouseEvent$Type Wheel = new MouseEvent$Type("Wheel", 2);
   public static final MouseEvent$Type Moved = new MouseEvent$Type("Moved", 3);
   public static final MouseEvent$Type Dragged = new MouseEvent$Type("Dragged", 4);
   private static final MouseEvent$Type[] $VALUES = new MouseEvent$Type[]{Released, Pressed, Wheel, Moved, Dragged};

   public static MouseEvent$Type[] values() {
      return (MouseEvent$Type[])$VALUES.clone();
   }

   public static MouseEvent$Type valueOf(String var0) {
      return (MouseEvent$Type)Enum.valueOf(MouseEvent$Type.class, var0);
   }

   private MouseEvent$Type(String var1, int var2) {
      super(var1, var2);
   }
}
