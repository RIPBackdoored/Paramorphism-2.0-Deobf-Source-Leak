package org.jline.terminal;

public final class Attributes$ControlFlag extends Enum {
   public static final Attributes$ControlFlag CIGNORE = new Attributes$ControlFlag("CIGNORE", 0);
   public static final Attributes$ControlFlag CS5 = new Attributes$ControlFlag("CS5", 1);
   public static final Attributes$ControlFlag CS6 = new Attributes$ControlFlag("CS6", 2);
   public static final Attributes$ControlFlag CS7 = new Attributes$ControlFlag("CS7", 3);
   public static final Attributes$ControlFlag CS8 = new Attributes$ControlFlag("CS8", 4);
   public static final Attributes$ControlFlag CSTOPB = new Attributes$ControlFlag("CSTOPB", 5);
   public static final Attributes$ControlFlag CREAD = new Attributes$ControlFlag("CREAD", 6);
   public static final Attributes$ControlFlag PARENB = new Attributes$ControlFlag("PARENB", 7);
   public static final Attributes$ControlFlag PARODD = new Attributes$ControlFlag("PARODD", 8);
   public static final Attributes$ControlFlag HUPCL = new Attributes$ControlFlag("HUPCL", 9);
   public static final Attributes$ControlFlag CLOCAL = new Attributes$ControlFlag("CLOCAL", 10);
   public static final Attributes$ControlFlag CCTS_OFLOW = new Attributes$ControlFlag("CCTS_OFLOW", 11);
   public static final Attributes$ControlFlag CRTS_IFLOW = new Attributes$ControlFlag("CRTS_IFLOW", 12);
   public static final Attributes$ControlFlag CDTR_IFLOW = new Attributes$ControlFlag("CDTR_IFLOW", 13);
   public static final Attributes$ControlFlag CDSR_OFLOW = new Attributes$ControlFlag("CDSR_OFLOW", 14);
   public static final Attributes$ControlFlag CCAR_OFLOW = new Attributes$ControlFlag("CCAR_OFLOW", 15);
   private static final Attributes$ControlFlag[] $VALUES = new Attributes$ControlFlag[]{CIGNORE, CS5, CS6, CS7, CS8, CSTOPB, CREAD, PARENB, PARODD, HUPCL, CLOCAL, CCTS_OFLOW, CRTS_IFLOW, CDTR_IFLOW, CDSR_OFLOW, CCAR_OFLOW};

   public static Attributes$ControlFlag[] values() {
      return (Attributes$ControlFlag[])$VALUES.clone();
   }

   public static Attributes$ControlFlag valueOf(String var0) {
      return (Attributes$ControlFlag)Enum.valueOf(Attributes$ControlFlag.class, var0);
   }

   private Attributes$ControlFlag(String var1, int var2) {
      super(var1, var2);
   }
}
