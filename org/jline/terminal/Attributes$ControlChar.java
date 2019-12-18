package org.jline.terminal;

public final class Attributes$ControlChar extends Enum {
   public static final Attributes$ControlChar VEOF = new Attributes$ControlChar("VEOF", 0);
   public static final Attributes$ControlChar VEOL = new Attributes$ControlChar("VEOL", 1);
   public static final Attributes$ControlChar VEOL2 = new Attributes$ControlChar("VEOL2", 2);
   public static final Attributes$ControlChar VERASE = new Attributes$ControlChar("VERASE", 3);
   public static final Attributes$ControlChar VWERASE = new Attributes$ControlChar("VWERASE", 4);
   public static final Attributes$ControlChar VKILL = new Attributes$ControlChar("VKILL", 5);
   public static final Attributes$ControlChar VREPRINT = new Attributes$ControlChar("VREPRINT", 6);
   public static final Attributes$ControlChar VINTR = new Attributes$ControlChar("VINTR", 7);
   public static final Attributes$ControlChar VQUIT = new Attributes$ControlChar("VQUIT", 8);
   public static final Attributes$ControlChar VSUSP = new Attributes$ControlChar("VSUSP", 9);
   public static final Attributes$ControlChar VDSUSP = new Attributes$ControlChar("VDSUSP", 10);
   public static final Attributes$ControlChar VSTART = new Attributes$ControlChar("VSTART", 11);
   public static final Attributes$ControlChar VSTOP = new Attributes$ControlChar("VSTOP", 12);
   public static final Attributes$ControlChar VLNEXT = new Attributes$ControlChar("VLNEXT", 13);
   public static final Attributes$ControlChar VDISCARD = new Attributes$ControlChar("VDISCARD", 14);
   public static final Attributes$ControlChar VMIN = new Attributes$ControlChar("VMIN", 15);
   public static final Attributes$ControlChar VTIME = new Attributes$ControlChar("VTIME", 16);
   public static final Attributes$ControlChar VSTATUS = new Attributes$ControlChar("VSTATUS", 17);
   private static final Attributes$ControlChar[] $VALUES = new Attributes$ControlChar[]{VEOF, VEOL, VEOL2, VERASE, VWERASE, VKILL, VREPRINT, VINTR, VQUIT, VSUSP, VDSUSP, VSTART, VSTOP, VLNEXT, VDISCARD, VMIN, VTIME, VSTATUS};

   public static Attributes$ControlChar[] values() {
      return (Attributes$ControlChar[])$VALUES.clone();
   }

   public static Attributes$ControlChar valueOf(String var0) {
      return (Attributes$ControlChar)Enum.valueOf(Attributes$ControlChar.class, var0);
   }

   private Attributes$ControlChar(String var1, int var2) {
      super(var1, var2);
   }
}
